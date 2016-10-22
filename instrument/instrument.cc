/////////////////////////////////////////////////////////////////////////
// $Id: instrument.cc 11908 2013-10-23 21:18:19Z sshwarts $
/////////////////////////////////////////////////////////////////////////
//
//   Copyright (c) 2006-2012 Stanislav Shwartsman
//          Written by Stanislav Shwartsman [sshwarts at sourceforge net]
//
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Lesser General Public
//  License as published by the Free Software Foundation; either
//  version 2 of the License, or (at your option) any later version.
//
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA

#include <assert.h>
#include "bochs.h"
#include "cpu/cpu.h"
//#include <config.h>
//#include <cpu/descriptor.h>
//#include <cpu/instr.h>
#include <disasm/disasm.h>
//#include <gui/siminterface.h>
#include <instrument/gkd/instrument.h>
#include <netdb.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <pthread.h>
#include <stdio.h>
//#include <sys/_endian.h>
//#include <sys/_pthread/_pthread_mutex_t.h>
//#include <sys/_pthread/_pthread_t.h>
#include <sys/socket.h>
#include <unistd.h>
#include <cstdlib>
#include <map>
#include <set>
#include <vector>
#include <utility>

struct memoryRecord{
	bx_address lin;
	bx_address phy;
	unsigned len;
	unsigned memType;
	unsigned rw;
} __attribute__ ((packed));

using namespace std;
using std::set;
using std::vector;

#define GKD_INSTRUMENT_VERSION "20160620"
bxInstrumentation *icpu = NULL;

#define MAX_NO_OF_MEMORY_RECORD 100000
#define JMP_CACHE_SIZE 100000

int memorySockfd;
int jmpSockfd;
int interruptSockfd;

int memoryPortNo = 1980;
int jmpPortNo = 1981;
int interruptPortNo = 1982;

static disassembler bx_disassembler;

static FILE *log;
unsigned int zonesFrom[MAX_NO_OF_MEMORY_RECORD];
unsigned int zonesTo[MAX_NO_OF_MEMORY_RECORD];
unsigned int zonesHit[MAX_NO_OF_MEMORY_RECORD];
unsigned int zonesTail = 0;
set<unsigned int> zonesHitAddress[MAX_NO_OF_MEMORY_RECORD];
vector<struct memoryRecord> zonesHitDetails[MAX_NO_OF_MEMORY_RECORD];

bx_bool connectedToMemoryServer;
bx_bool connectedToJmpServer;
bx_bool connectedToInterruptServer;
bx_bool triedToContectToServer;

bx_address startRecordJumpAddress = 0x1600000;
bx_bool startRecordJump;

bx_address segmentBegin = startRecordJumpAddress;
bx_address segmentEnd;

//struct memoryRecord memoryRecords[MAX_NO_OF_MEMORY_RECORD];

int pointer = 0;

int physicalAddressSize = sizeof(bx_phy_address);
int whatSize = sizeof(unsigned);
int segmentSize = sizeof(bx_address);
int registerSize = sizeof(BX_CPU(cpu)->gen_reg[BX_32BIT_REG_ECX].dword.erx);
int segmentRegisterSize = sizeof(BX_CPU(cpu)->sregs[BX_SEG_REG_ES].selector.value);

pthread_t jmpThread;
pthread_mutex_t jmpMutex;

pthread_t memoryThread;
pthread_mutex_t memoryMutex;


int exceptionNoVector[JMP_CACHE_SIZE];
int errorCodeVector[JMP_CACHE_SIZE];

bx_phy_address fromAddressVector[JMP_CACHE_SIZE];
bx_phy_address toAddressVector[JMP_CACHE_SIZE];
unsigned whatVector[JMP_CACHE_SIZE];
bx_address segmentBeginVector[JMP_CACHE_SIZE];
bx_address segmentEndVector[JMP_CACHE_SIZE];

Bit32u eaxVector[JMP_CACHE_SIZE];
Bit32u ecxVector[JMP_CACHE_SIZE];
Bit32u edxVector[JMP_CACHE_SIZE];
Bit32u ebxVector[JMP_CACHE_SIZE];
Bit32u espVector[JMP_CACHE_SIZE];
Bit32u ebpVector[JMP_CACHE_SIZE];
Bit32u esiVector[JMP_CACHE_SIZE];
Bit32u ediVector[JMP_CACHE_SIZE];

Bit16u esVector[JMP_CACHE_SIZE];
Bit16u csVector[JMP_CACHE_SIZE];
Bit16u ssVector[JMP_CACHE_SIZE];
Bit16u dsVector[JMP_CACHE_SIZE];
Bit16u fsVector[JMP_CACHE_SIZE];
Bit16u gsVector[JMP_CACHE_SIZE];

#define STACK_SIZE 2048
Bit8u stack[JMP_CACHE_SIZE][STACK_SIZE];
bx_phy_address stackBase[JMP_CACHE_SIZE];

unsigned int jumpIndex = 0;

void saveData(int exceptionNo, int error_code, bx_phy_address fromPhysicalAddress, bx_phy_address toPhysicalAddress, unsigned what, bx_address segmentBegin, bx_address segmentEnd, Bit32u eax, Bit32u ecx,
		Bit32u edx, Bit32u ebx, Bit32u esp, Bit32u ebp, Bit32u esi, Bit32u edi, Bit16u es, Bit16u cs, Bit16u ss, Bit16u ds, Bit16u fs, Bit16u gs, Bit8u *stackValue,
		bx_phy_address stackBaseValue) {
	pthread_mutex_lock(&jmpMutex);


	exceptionNoVector[jumpIndex] = exceptionNo;
	errorCodeVector[jumpIndex] = error_code;

	fromAddressVector[jumpIndex] = fromPhysicalAddress;
	toAddressVector[jumpIndex] = toPhysicalAddress;

	whatVector[jumpIndex] = what;

	segmentBeginVector[jumpIndex] = segmentBegin;
	segmentEndVector[jumpIndex] = segmentEnd;

	eaxVector[jumpIndex] = eax;
	ecxVector[jumpIndex] = ecx;
	edxVector[jumpIndex] = edx;
	ebxVector[jumpIndex] = ebx;
	espVector[jumpIndex] = esp;
	ebpVector[jumpIndex] = ebp;
	esiVector[jumpIndex] = esi;
	ediVector[jumpIndex] = edi;

	esVector[jumpIndex] = es;
	csVector[jumpIndex] = cs;
	ssVector[jumpIndex] = ss;
	dsVector[jumpIndex] = ds;
	fsVector[jumpIndex] = fs;
	gsVector[jumpIndex] = gs;

	memcpy(stack[jumpIndex], stackValue, STACK_SIZE);
	stackBase[jumpIndex] = stackBaseValue;

	jumpIndex++;


	pthread_mutex_unlock(&jmpMutex);
}

unsigned int convert(unsigned char *inBuffer) {
	return inBuffer[3] + (inBuffer[2] << 8) + (inBuffer[1] << 16) + (inBuffer[0] << 24);
}

void safeRead(int socketFD, unsigned char *inBuffer, unsigned int size) {
	int noOfBytes = 0;
	while (noOfBytes != size) {
		noOfBytes += read(socketFD, inBuffer + noOfBytes, size - noOfBytes);
	}
}

int writeToSocket(int sock, const void *data, int size) {
	int byteSent = 0;
	while (byteSent < size) {
		int b = write(jmpSockfd, (char *) data + byteSent, size - byteSent);

		byteSent += b;
	}
	return byteSent;
}

void * jmpTimer(void *arg) {
	while (1) {
		sleep(1);

		pthread_mutex_lock(&jmpMutex);

		if (jumpIndex > 0) {
			fprintf(log, "jmpTimer, sending back %d records\n", jumpIndex);
			fflush(log);

			writeToSocket(jmpSockfd, "start", 5);
			writeToSocket(jmpSockfd, &jumpIndex, 4);

			writeToSocket(jmpSockfd, exceptionNoVector, 4 * jumpIndex);
			writeToSocket(jmpSockfd, errorCodeVector, 4 * jumpIndex);

			writeToSocket(jmpSockfd, fromAddressVector, physicalAddressSize * jumpIndex);
			writeToSocket(jmpSockfd, toAddressVector, physicalAddressSize * jumpIndex);

			writeToSocket(jmpSockfd, whatVector, whatSize * jumpIndex);

			writeToSocket(jmpSockfd, segmentBeginVector, segmentSize * jumpIndex);
			writeToSocket(jmpSockfd, segmentEndVector, segmentSize * jumpIndex);

			writeToSocket(jmpSockfd, eaxVector, registerSize * jumpIndex);
			writeToSocket(jmpSockfd, ecxVector, registerSize * jumpIndex);
			writeToSocket(jmpSockfd, edxVector, registerSize * jumpIndex);
			writeToSocket(jmpSockfd, ebxVector, registerSize * jumpIndex);
			writeToSocket(jmpSockfd, espVector, registerSize * jumpIndex);
			writeToSocket(jmpSockfd, ebpVector, registerSize * jumpIndex);
			writeToSocket(jmpSockfd, esiVector, registerSize * jumpIndex);
			writeToSocket(jmpSockfd, ediVector, registerSize * jumpIndex);

			writeToSocket(jmpSockfd, esVector, segmentRegisterSize * jumpIndex);
			writeToSocket(jmpSockfd, csVector, segmentRegisterSize * jumpIndex);
			writeToSocket(jmpSockfd, ssVector, segmentRegisterSize * jumpIndex);
			writeToSocket(jmpSockfd, dsVector, segmentRegisterSize * jumpIndex);
			writeToSocket(jmpSockfd, fsVector, segmentRegisterSize * jumpIndex);
			writeToSocket(jmpSockfd, gsVector, segmentRegisterSize * jumpIndex);

			writeToSocket(jmpSockfd, stack, STACK_SIZE * jumpIndex);
			writeToSocket(jmpSockfd, stackBase, physicalAddressSize * jumpIndex);

			writeToSocket(jmpSockfd, "end", 3);

			char readBytes[4];
			read(jmpSockfd, readBytes, 4);
			if (strncmp(readBytes, "done", 4) != 0) {
				fprintf(log, "jmp socket read/write error = %s\n", readBytes);
				fflush(log);
				exit(-1);
			}
			jumpIndex = 0;
		}
		pthread_mutex_unlock(&jmpMutex);


		//logGKD("b4\n");
	}
	return 0;
}

void * memoryTimer(void *arg) {
	while (1) {
		sleep(1);

		pthread_mutex_lock(&memoryMutex);

		//if (pointer>0) {
			//fprintf(log, "send back memory %d records\n", pointer);
			//fflush(log);

			// send hit count
			//write(memorySockfd, (char *) memoryRecords, sizeof(memoryRecords));
			// end send hit count

			// send zones
			write(memorySockfd, &zonesTail, sizeof(unsigned int));
			for (int x = 0; x < zonesTail; x++) {
				write(memorySockfd, &zonesFrom[x], sizeof(unsigned int));
				write(memorySockfd, &zonesTo[x], sizeof(unsigned int));
				write(memorySockfd, &zonesHit[x], sizeof(unsigned int));

				unsigned int size;
				if (zonesHitAddress[x].size() < 10) {
					size = zonesHitAddress[x].size();
				} else {
					size = 10;
				}

				//noOfZoneHitAddress
				write(memorySockfd, &size, sizeof(unsigned int));
				set<unsigned int>::iterator theIterator;
				int yy;
				for (yy = 0, theIterator = zonesHitAddress[x].begin(); theIterator != zonesHitAddress[x].end() && yy < size; theIterator++) {
					write(memorySockfd, &*theIterator, sizeof(unsigned int));
					yy++;
				}

				// write zonesHitDetails
				size=zonesHitDetails[x].size();
				write(memorySockfd, &size, sizeof(unsigned int));
				for (int y=0;y<zonesHitDetails[x].size();y++){
					struct memoryRecord memRecord=zonesHitDetails[x][y];
					write(memorySockfd, &memRecord, sizeof(struct memoryRecord));
					//fprintf(log, "+++ %x,%x,%d,%d,%d\n", zonesHitDetails[x][y].lin, zonesHitDetails[x][y].phy, zonesHitDetails[x][y].len, zonesHitDetails[x][y].memType, zonesHitDetails[x][y].rw);
				}
				zonesHitDetails[x].clear();
				// end write zonesHitDetails
			}
			// end send zones

			// update zones from GKD
			unsigned char inBuffer[10000];
			safeRead(memorySockfd, inBuffer, 1);
			if (inBuffer[0] == 2) {
				safeRead(memorySockfd, inBuffer, 4);
				unsigned int noOfData = convert(inBuffer);
				int readSize = noOfData * sizeof(unsigned int) * 2;

				safeRead(memorySockfd, inBuffer, readSize);

				int offset = 0;
				for (int x = 0; x < noOfData; x++) {
					unsigned int from = convert(&inBuffer[offset]);
					zonesFrom[x] = from;
					offset += 4;

					unsigned int to = convert(&inBuffer[offset]);
					zonesTo[x] = to;
					offset += 4;
				}
				zonesTail = noOfData;
			}
			// end update zones from GKD

			pointer = 0;
		//}
		pthread_mutex_unlock(&memoryMutex);
		//logGKD("b4\n");
	}
	return 0;
}

void initMemorySocket() {
	fprintf(log, "initMemorySocket\n");
	memorySockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (memorySockfd < 0) {
		fprintf(log, "Memory socket server : ERROR opening socket\n");
		connectedToMemoryServer = false;
		return;
	}
	struct sockaddr_in serv_addr;
	struct hostent *server;
	server = gethostbyname("localhost");
	if (server == NULL) {
		fprintf(log, "Memory socket server : ERROR no such host\n");
		return;
	}
	bzero((char *) &serv_addr, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	bcopy((char *) server->h_addr, (char *) &serv_addr.sin_addr.s_addr, server->h_length);
	serv_addr.sin_port = htons(memoryPortNo);
	if (connect(memorySockfd, (const sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
		fprintf(log, "Memory socket server : ERROR connecting\n");
		connectedToMemoryServer = false;
		return;
	} else {
		fprintf(log, "Memory socket server : connected to server\n");
		connectedToMemoryServer = true;
	}

	int flag = 1;
	int ret = setsockopt(memorySockfd, IPPROTO_TCP, TCP_NODELAY, (char *) &flag, sizeof(flag));
	if (ret == -1) {
		fprintf(log, "memorySockfd couldn't setsockopt(TCP_NODELAY)\n");
	}

	pthread_mutex_init(&memoryMutex, NULL);
	int err = pthread_create(&memoryThread, NULL, memoryTimer, NULL);
	if (err != 0) {
		fprintf(log, "pthread_create error\n");
	}

	fprintf(log, "initMemorySocket end\n");
	fflush(log);
}

void initJmpSocket() {
	jmpSockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (jmpSockfd < 0) {
		fprintf(log, "Jmp socket server : ERROR opening socket\n");
		connectedToJmpServer = false;
		return;
	}
	struct hostent *server;
	server = gethostbyname("localhost");
	if (server == NULL) {
		fprintf(log, "Jmp socket server : ERROR no such host\n");
	}

	struct sockaddr_in serv_addr;
	bzero((char *) &serv_addr, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	bcopy((char *) server->h_addr, (char *) &serv_addr.sin_addr.s_addr, server->h_length);
	serv_addr.sin_port = htons(jmpPortNo);
	if (connect(jmpSockfd, (const sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
		fprintf(log, "Jmp socket server : ERROR connecting\n");
		connectedToJmpServer = false;
		return;
	} else {
		fprintf(log, "Jmp socket server : connected to server\n");
		connectedToJmpServer = true;
	}

	int flag = 1;
	int ret = setsockopt(jmpSockfd, IPPROTO_TCP, TCP_NODELAY, (char *) &flag, sizeof(flag));
	if (ret == -1) {
		fprintf(log, "jmpSockfd couldn't setsockopt(TCP_NODELAY)\n");
	}

	if (connectedToJmpServer) {
		write(jmpSockfd, &physicalAddressSize, 1);
		write(jmpSockfd, &whatSize, 1);
		write(jmpSockfd, &segmentSize, 1);
		write(jmpSockfd, &registerSize, 1);
		write(jmpSockfd, &segmentRegisterSize, 1);
	}

	pthread_mutex_init(&jmpMutex, NULL);
	int err = pthread_create(&jmpThread, NULL, jmpTimer, NULL);
	if (err != 0) {
		fprintf(log, "pthread_create error\n");
	}
	//logGKD("initJmpSocket end\n");

	fprintf(log, "initJmpSocket end\n");
	fflush(log);
}

void initInterruptSocket() {
	interruptSockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (interruptSockfd < 0) {
		fprintf(log, "Interrupt socket server : ERROR opening socket\n");
		connectedToInterruptServer = false;
		return;
	}

	struct hostent *server;
	server = gethostbyname("localhost");
	if (server == NULL) {
		fprintf(log, "Interrupt socket server : ERROR no such host\n");
	}

	struct sockaddr_in serv_addr;
	bzero((char *) &serv_addr, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	bcopy((char *) server->h_addr, (char *) &serv_addr.sin_addr.s_addr, server->h_length);
	serv_addr.sin_port = htons(interruptPortNo);
	if (connect(interruptSockfd, (const sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
		fprintf(log, "Interrupt socket server : ERROR connecting\n");
		connectedToInterruptServer = false;
		return;
	} else {
		fprintf(log, "Interrupt socket server : connected to server\n");
		connectedToInterruptServer = true;
	}

	int flag = 1;
	int ret = setsockopt(interruptSockfd, IPPROTO_TCP, TCP_NODELAY, (char *) &flag, sizeof(flag));
	if (ret == -1) {
		fprintf(log, "interruptSockfd couldn't setsockopt(TCP_NODELAY)\n");
	}
}

void bx_instr_init_env(void) {
}

void bx_instr_exit_env(void) {
}

void bx_instr_initialize(unsigned cpu) {
	assert(cpu < BX_SMP_PROCESSORS);

	if (icpu == NULL)
		icpu = new bxInstrumentation[BX_SMP_PROCESSORS];

	icpu[cpu].set_cpu_id(cpu);

	fprintf(stderr, "Initialize cpu %d\n", cpu);

	// GKD
	log = fopen("gkd.log", "a+");
	fprintf(stderr, "GKD instrument %s\n", GKD_INSTRUMENT_VERSION);


	fprintf(log, "sizeof(unsigned)=%d\n", sizeof(unsigned));

	/*
	 logGKD("registerSize=");
	 logGKD(registerSize);
	 logGKD("segmentregisterSize=");
	 logGKD(segmentRegisterSize);
	 */

	initMemorySocket();
	initJmpSocket();
	initInterruptSocket();
	if (connectedToMemoryServer) {
		for (int x = 0; x < MAX_NO_OF_MEMORY_RECORD; x++) {
			zonesFrom[x] = -1;
			zonesTo[x] = -1;
			zonesHit[x] = 0;
		}
	}
	fprintf(log, "init end\n");
}

void bxInstrumentation::bx_instr_reset(unsigned type) {
	ready = is_branch = 0;
	//num_data_accesses = 0;
	active = 1;
}

void bxInstrumentation::bx_print_instruction(void) {
	printf("bx_print_instruction\n");
	char disasm_tbuf[512];	// buffer for instruction disassembly
	bx_disassembler.disasm(is32, is64, 0, 0, opcode, disasm_tbuf);

	if (opcode_length != 0) {
		fprintf(stderr, "----------------------------------------------------------\n");
		printf("CPU: %d: %s\n", cpu_id, disasm_tbuf);
		fprintf(stderr, "LEN: %d\tBYTES: ", opcode_length);

		unsigned n;
		for (n = 0; n < opcode_length; n++){
			fprintf(stderr, "%02x", opcode[n]);
		}
		if (is_branch) {
			fprintf(stderr, "\tBRANCH ");

			if (is_taken) {
				fprintf(stderr, "TARGET " FMT_ADDRX " (TAKEN)", target_linear);
			} else {
				fprintf(stderr, "(NOT TAKEN)");
			}
		}
		fprintf(stderr, "\n");
	}
}

void bxInstrumentation::bx_instr_before_execution(bxInstruction_c *i) {
	if (!active)
		return;

	//if (ready)
	//	bx_print_instruction();

	// prepare instruction_t structure for new instruction
	ready = 1;
	//num_data_accesses = 0;
	is_branch = 0;

	//is32 = BX_CPU(cpu_id)->sregs[BX_SEG_REG_CS].cache.u.segment.d_b;
	//is64 = BX_CPU(cpu_id)->long64_mode();
	//opcode_length = i->ilen();
	//memcpy(opcode, i->get_opcode_bytes(), opcode_length);

	bx_phy_address phyAddress = BX_CPU(cpu_id)->get_instruction_pointer();
	segmentEnd = phyAddress;

	/*if (phyAddress == startRecordJumpAddress) {
	 startRecordJump = true;
	 }*/

	/*
	 if (!triedToContectToServer) {
	 initMemorySocket();
	 initJmpSocket();
	 initInterruptSocket();
	 triedToContectToServer = true;
	 }

	 // prepare instruction_t structure for new instruction
	 ready = 1;
	 num_data_accesses = 0;
	 is_branch = 0;

	 is32 = BX_CPU(cpu_id)->sregs[BX_SEG_REG_CS].cache.u.segment.d_b;
	 is64 = BX_CPU(cpu_id)->long64_mode();
	 opcode_length = i->ilen();
	 memcpy(opcode, i->get_opcode_bytes(), opcode_length);

	 bx_phy_address phyAddress = BX_CPU(cpu_id)->get_instruction_pointer();
	 segmentEnd = phyAddress;
	 if (phyAddress == startRecordJumpAddress) {
	 startRecordJump = true;
	 }
	 */
}

void bxInstrumentation::bx_instr_after_execution(bxInstruction_c *i) {
	if (!active)
		return;

	if (ready) {
		//bx_print_instruction();
		ready = 0;
	}
}

void bxInstrumentation::branch_taken(unsigned what, bx_address branch_eip, bx_address new_eip) {
	if (!active || !ready)
		return;

	// find linear address
	//target_linear = BX_CPU(cpu_id)->get_laddr(BX_SEG_REG_CS, new_eip);
	jmpSampling(what, branch_eip, new_eip);
	/*
	 // find linear address
	 bx_address laddr = BX_CPU(cpu_id)->get_laddr(BX_SEG_REG_CS, new_eip);

	 is_branch = 1;
	 is_taken = 1;
	 target_linear = laddr;

	 //printf("branch_taken=%x\n", new_eip);
	 */
}

void bxInstrumentation::bx_instr_cnear_branch_taken(bx_address branch_eip, bx_address new_eip) {
	branch_taken(0, branch_eip, new_eip);
}

void bxInstrumentation::bx_instr_cnear_branch_not_taken(bx_address branch_eip) {
	/*
	 if (!active || !ready)
	 return;

	 is_branch = 1;
	 is_taken = 0;

	 //printf("bxif _instr_cnear_branch_not_taken=%x\n", branch_eip);
	 */
}

void bxInstrumentation::bx_instr_ucnear_branch(unsigned what, bx_address branch_eip, bx_address new_eip) {
	branch_taken(what, branch_eip, new_eip);
	/*
	 branch_taken(new_eip);
	 if (connectedToJmpServer) {
	 jmpSampling(new_eip);
	 }

	 //printf("bx_instr_ucnear_branch=%x, %x\n", branch_eip, new_eip);
	 */
}

void bxInstrumentation::bx_instr_far_branch(unsigned what, Bit16u prev_cs, bx_address prev_eip, Bit16u new_cs, bx_address new_eip) {
	branch_taken(what, prev_eip, new_eip);
}

void bxInstrumentation::bx_instr_interrupt(unsigned vector) {
	/*
	 if (active) {
	 //fprintf(stderr, "CPU %u: interrupt %02xh\n", cpu_id, vector);
	 if (connectedToInterruptServer) {
	 write(interruptSockfd, &vector, sizeof(vector));
	 }
	 }
	 */
}

void bxInstrumentation::bx_instr_exception(unsigned vector, unsigned error_code) {
	/*
	 if (active) {
	 //fprintf(stderr, "CPU %u: exception %02xh error_code=%x\n", cpu_id, vector, error_code);
	 }
	 */

	bx_address linear_sp = BX_CPU(cpu)->get_reg32(BX_32BIT_REG_ESP);
	linear_sp = BX_CPU(cpu)->get_laddr(BX_SEG_REG_SS, linear_sp);
	Bit8u buf[STACK_SIZE];
	bx_dbg_read_linear(0, linear_sp, STACK_SIZE, buf);

	if (connectedToJmpServer && startRecordJump) {
		bx_phy_address paddr;
		bx_bool paddr_valid = BX_CPU(dbg_cpu)->dbg_xlate_linear2phy(linear_sp, &paddr);

		bx_list_c *dbg_cpu_list = (bx_list_c*) SIM->get_param("cpu0", SIM->get_bochs_root());
		bx_address cr2 = (bx_address) SIM->get_param_num("CR2", dbg_cpu_list)->get64();

		saveData(vector, error_code, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EIP].dword.erx, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EIP].dword.erx, -1, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EIP].dword.erx, cr2, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EAX].dword.erx,
		BX_CPU(cpu)->gen_reg[BX_32BIT_REG_ECX].dword.erx, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EDX].dword.erx,
		BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EBX].dword.erx, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_ESP].dword.erx, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EBP].dword.erx,
		BX_CPU(cpu)->gen_reg[BX_32BIT_REG_ESI].dword.erx, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EDI].dword.erx, BX_CPU(cpu)->sregs[BX_SEG_REG_ES].selector.value,
		BX_CPU(cpu)->sregs[BX_SEG_REG_CS].selector.value, BX_CPU(cpu)->sregs[BX_SEG_REG_SS].selector.value,
		BX_CPU(cpu)->sregs[BX_SEG_REG_DS].selector.value, BX_CPU(cpu)->sregs[BX_SEG_REG_FS].selector.value, BX_CPU(cpu)->sregs[BX_SEG_REG_GS].selector.value, buf,
				paddr_valid ? paddr : -1);
	}
}

void bxInstrumentation::bx_instr_hwinterrupt(unsigned vector, Bit16u cs, bx_address eip) {
	/*
	 if (active) {
	 //fprintf(stderr, "CPU %u: hardware interrupt %02xh\n", cpu_id, vector);
	 }
	 */
}

void bxInstrumentation::bx_instr_lin_access(bx_address lin, bx_phy_address phy, unsigned len, unsigned memtype, unsigned rw) {
	if (!active || !ready){
	 	return;
	}
	if (connectedToMemoryServer) {
		memorySampling(lin, phy, len, memtype, rw);
	}
}

void bxInstrumentation::memorySampling(bx_address lin, bx_phy_address paddr, unsigned len, unsigned memtype, unsigned rw) {
	if (!connectedToMemoryServer) {
		return;
	}
	pthread_mutex_lock(&memoryMutex);

	/*memoryRecords[pointer].lin=lin;
	memoryRecords[pointer].phy=paddr;
	memoryRecords[pointer].len=len;
	memoryRecords[pointer].memType=memtype;
	memoryRecords[pointer].rw=rw;
	pointer++;*/

	//fprintf(log, "pointer = %d", pointer);
	//fflush(log);

	// check zone hit count
	for (int x = 0; x < zonesTail; x++) {
		if (zonesFrom[x] <= paddr && paddr <= zonesTo[x]) {
			zonesHit[x]++;
			//fprintf(log, "BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EIP].dword.erx=%x\n", BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EIP].dword.erx);
			//fflush(log);
			zonesHitAddress[x].insert(BX_CPU(cpu)->get_eip());
			struct memoryRecord memRecord;
			memRecord.lin=lin;
			memRecord.phy=paddr;
			memRecord.len=len;
			memRecord.memType=memtype;
			memRecord.rw=rw;
			zonesHitDetails[x].push_back(memRecord);
		}
	}
	// end check zone hit count

	pthread_mutex_unlock(&memoryMutex);

	while (pointer >= MAX_NO_OF_MEMORY_RECORD) {
		fprintf(log, "memory buffer overflow, pointer=%d\n", pointer);
		fflush(log);
		sleep(1);
	}
}

void bxInstrumentation::jmpSampling(unsigned what, bx_address branch_eip, bx_address new_eip) {
	if (connectedToJmpServer && startRecordJump) {
		bx_phy_address fromPhysicalAddress;
		bx_phy_address toPhysicalAddress;
		BX_CPU(cpu)->dbg_xlate_linear2phy(branch_eip, &fromPhysicalAddress, false);
		BX_CPU(cpu)->dbg_xlate_linear2phy(new_eip, &toPhysicalAddress, false);

		while (jumpIndex >= JMP_CACHE_SIZE) {
			fprintf(log, "jmpSampling buffer overflow, jumpIndex=%d\n", jumpIndex);
			fflush(log);
			sleep(1);
		}

		bx_address linear_sp = BX_CPU(cpu)->get_reg32(BX_32BIT_REG_ESP);
		linear_sp = BX_CPU(cpu)->get_laddr(BX_SEG_REG_SS, linear_sp);
		Bit8u buf[STACK_SIZE];
		bx_dbg_read_linear(0, linear_sp, STACK_SIZE, buf);

		bx_phy_address paddr;
		bx_bool paddr_valid = BX_CPU(dbg_cpu)->dbg_xlate_linear2phy(linear_sp, &paddr);

		saveData(-1,-1, fromPhysicalAddress, toPhysicalAddress, what, segmentBegin, segmentEnd, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EAX].dword.erx,
		BX_CPU(cpu)->gen_reg[BX_32BIT_REG_ECX].dword.erx, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EDX].dword.erx,
		BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EBX].dword.erx, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_ESP].dword.erx, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EBP].dword.erx,
		BX_CPU(cpu)->gen_reg[BX_32BIT_REG_ESI].dword.erx, BX_CPU(cpu)->gen_reg[BX_32BIT_REG_EDI].dword.erx, BX_CPU(cpu)->sregs[BX_SEG_REG_ES].selector.value,
		BX_CPU(cpu)->sregs[BX_SEG_REG_CS].selector.value, BX_CPU(cpu)->sregs[BX_SEG_REG_SS].selector.value,
		BX_CPU(cpu)->sregs[BX_SEG_REG_DS].selector.value, BX_CPU(cpu)->sregs[BX_SEG_REG_FS].selector.value, BX_CPU(cpu)->sregs[BX_SEG_REG_GS].selector.value, buf,
				paddr_valid ? paddr : -1);
		segmentBegin = new_eip;
	}
}

void bxInstrumentation::bx_instr_prefetch_hint(unsigned cpu, unsigned what, unsigned seg, bx_address offset) {
	if (what == 1 && offset == 0x12345678) {
		fprintf(log, "bx_instr_prefetch_hint cpu=%d, what=%d, seg=%d, offset=%x\n", cpu, what, seg, offset);
		fflush (log);
		startRecordJump = true;
	} else if (what == 1 && offset == 0x87654321) {
		startRecordJump = false;
	}
}
