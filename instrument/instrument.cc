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
#include <bochs.h>
#include <config.h>
#include <cpu/cpu.h>
#include <cpu/descriptor.h>
#include <cpu/instr.h>
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
#include <utility>

using namespace std;
using std::set;

#define GKD_INSTRUMENT_VERSION "20150709"
bxInstrumentation *icpu = NULL;

#define MAX_SEND_BYTE 500
int memorySockfd;
int jmpSockfd;
int interruptSockfd;

int memoryPortNo = 1980;
int jmpPortNo = 1981;
int interruptPortNo = 1982;

static disassembler bx_disassembler;

static FILE *log;
unsigned int zonesFrom[MAX_SEND_BYTE];
unsigned int zonesTo[MAX_SEND_BYTE];
unsigned int zonesHit[MAX_SEND_BYTE];
unsigned int zonesTail = 0;
set<unsigned int> zonesHitAddress[MAX_SEND_BYTE];

bx_bool connectedToMemoryServer;
bx_bool connectedToJmpServer;
bx_bool connectedToInterruptServer;
bx_bool triedToContectToServer;

bx_address startRecordJumpAddress = 0x1600000;
bx_bool startRecordJump;

bx_address segmentBegin = startRecordJumpAddress;
bx_address segmentEnd;

unsigned int buffer[MAX_SEND_BYTE];
int pointer = 0;

int physicalAddressSize = sizeof(bx_phy_address);
int whatSize = sizeof(unsigned);
int segmentSize = sizeof(bx_address);
int registerSize = sizeof(BX_CPU(0)->gen_reg[BX_32BIT_REG_ECX].dword.erx);
int segmentRegisterSize = sizeof(BX_CPU(0)->sregs[BX_SEG_REG_ES].selector.value);

pthread_t jmpThread;
pthread_mutex_t jmpMutex;

#define JMP_CACHE_SIZE 100000
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

#define STACK_SIZE 256
Bit8u stack[JMP_CACHE_SIZE][STACK_SIZE];

unsigned int jumpIndex = 0;

//int writeToSocket(int sock, const void *data, int size) {
//	return writeToSocket(sock, (char *) data, size);
//}

int writeToSocket(int sock, const void *data, int size) {
	int byteSent = 0;
//	for (int x=0;x<10;x++){
//		fprintf(log, "%d ", x);
//	}
//	fprintf(log, "\n");
//	fflush(log);

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
			writeToSocket(jmpSockfd, "start", 5);
			writeToSocket(jmpSockfd, &jumpIndex, 4);

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

			writeToSocket(jmpSockfd, "end", 3);

//			char temp[1];
//			read(jmpSockfd, &temp, 1);
//
//			while (temp[0] == 0) {
//				Bit64u offset;
//				int noOfByte = read(jmpSockfd, &offset, 8);
//
//				//Bit64u value = address+1;
//				bx_address linear_sp;
//				linear_sp = BX_CPU(dbg_cpu)->get_reg32(BX_32BIT_REG_ESP);
//				fprintf(log, "read stack 1 = %lx\n", linear_sp);
//				linear_sp = BX_CPU(dbg_cpu)->get_laddr(BX_SEG_REG_SS, linear_sp);
//				Bit8u buf[8];
//
//				fprintf(log, "read stack 2 = %lx\n", linear_sp);
//				bx_dbg_read_linear(dbg_cpu, linear_sp + offset, 8, buf);
//
//				writeToSocket(jmpSockfd, &buf, 8);
//
//				read(jmpSockfd, &temp, 1);
//			}

			char readBytes[4];
			read(jmpSockfd, readBytes, 4);
			if (strncmp(readBytes, "done", 4) != 0) {
				fprintf(log, "jmp socket read/write error = %s\n", readBytes);
				fflush(log);
				exit(-1);
			}

			// handle read parameter values
//			char noOfParameterStr[1];
//			read(jmpSockfd, noOfParameterStr, 1);
//			int noOfParameter = noOfParameterStr[0];
//
//			Bit32u values[noOfParameter];
//			for (int x = 0; x < noOfParameter; x++) {
//				char address[4];
//				read(jmpSockfd, address, 4);
//				values[x * 4] = 0x12345678;
//			}
//
//			// end handle read parameter values
//			writeToSocket(jmpSockfd, esVector, noOfParameter * 4);
//			writeToSocket(jmpSockfd, "end", 3);
//
//			char readBytes[4];
//			read(jmpSockfd, readBytes, 4);
//			if (strncmp(readBytes, "done", 4) != 0) {
//				fprintf(log, "jmp socket read/write error = %s\n", readBytes);
//				fflush(log);
//				exit(-1);
//			}

			jumpIndex = 0;
		}
		pthread_mutex_unlock(&jmpMutex);
		//logGKD("b4\n");
	}
	return 0;
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

void initMemorySocket() {
	fprintf(log, "initMemorySocket\n");
	memorySockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (memorySockfd < 0) {
		fprintf(log, "Memory socket server : ERROR opening socket\n");
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
	fprintf(log, "initMemorySocket end\n");
	fflush(log);
}

void initJmpSocket() {
	fprintf(log, "initJmpSocket 1\n");
	jmpSockfd = socket(AF_INET, SOCK_STREAM, 0);
	fprintf(log, "initJmpSocket 2\n");
	if (jmpSockfd < 0) {
		fprintf(log, "Jmp socket server : ERROR opening socket\n");
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

	fprintf(stderr, "GKD instrument %s - Initialize cpu %d\n", GKD_INSTRUMENT_VERSION, cpu);

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
		for (int x = 0; x < MAX_SEND_BYTE; x++) {
			zonesFrom[x] = -1;
			zonesTo[x] = -1;
			zonesHit[x] = 0;
		}
	}
	fprintf(log, "init end\n");
}

void bxInstrumentation::bx_instr_reset(unsigned type) {
	ready = is_branch = 0;
	num_data_accesses = 0;
	active = 1;
}

void bxInstrumentation::bx_print_instruction(void) {
	printf("bx_print_instruction\n");
	char disasm_tbuf[512];	// buffer for instruction disassembly
	bx_disassembler.disasm(is32, is64, 0, 0, opcode, disasm_tbuf);

	if (opcode_length != 0) {
		unsigned n;

		/*
		 //fprintf(stderr, "----------------------------------------------------------\n");
		 printf("CPU: %d: %s\n", cpu_id, disasm_tbuf);
		 //fprintf(stderr, "LEN: %d\tBYTES: ", opcode_length);
		 for (n = 0; n < opcode_length; n++){
		 fprintf(stderr, "%02x", opcode[n]);
		 }
		 if (is_branch) {
		 //fprintf(stderr, "\tBRANCH ");

		 if (is_taken) {
		 //fprintf(stderr, "TARGET " FMT_ADDRX " (TAKEN)", target_linear);
		 } else {
		 //fprintf(stderr, "(NOT TAKEN)");
		 }
		 }
		 //fprintf(stderr, "\n");
		 */
		if (connectedToMemoryServer) {
			for (n = 0; n < num_data_accesses; n++) {
				//fprintf(stderr, "MEM ACCESS[%u]: 0x" FMT_ADDRX " (linear) 0x" FMT_PHY_ADDRX " (physical) %s SIZE: %d\n", n, data_access[n].laddr, data_access[n].paddr,
				//		data_access[n].op == BX_READ ? "RD" : "WR", data_access[n].size);

				memorySampling(data_access[n].paddr);
			}
		}

//fprintf(stderr, "\n");
	}
}

void bxInstrumentation::bx_instr_before_execution(bxInstruction_c *i) {
	if (!active)
		return;

	//if (ready)
	//	bx_print_instruction();

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
}

void bxInstrumentation::bx_instr_hwinterrupt(unsigned vector, Bit16u cs, bx_address eip) {
	/*
	 if (active) {
	 //fprintf(stderr, "CPU %u: hardware interrupt %02xh\n", cpu_id, vector);
	 }
	 */
}

void bxInstrumentation::bx_instr_lin_access(bx_address lin, bx_phy_address phy, unsigned len, unsigned memtype, unsigned rw) {
	/*
	 if (!active || !ready)
	 return;

	 if (num_data_accesses >= MAX_DATA_ACCESSES) {
	 return;
	 }

	 //bx_address lin = BX_CPU(cpu)->get_laddr(seg, offset);
	 bx_bool page_valid = BX_CPU(cpu)->dbg_xlate_linear2phy(lin, &phy);
	 phy = A20ADDR(phy);

	 // If linear translation doesn't exist, a paging exception will occur.
	 // Invalidate physical address data for now.
	 if (!page_valid)
	 phy = (bx_phy_address) (-1);

	 data_access[num_data_accesses].laddr = lin;
	 data_access[num_data_accesses].paddr = phy;
	 data_access[num_data_accesses].rw = rw;
	 data_access[num_data_accesses].size = len;

	 num_data_accesses++;

	 if (connectedToMemoryServer) {
	 memorySampling(phy);
	 }
	 */
}

void bxInstrumentation::memorySampling(bx_phy_address paddr) {
	if (!connectedToMemoryServer) {
		return;
	}
	buffer[pointer] = paddr;
	pointer++;

// check zone
	for (int x = 0; x < zonesTail; x++) {
		if (zonesFrom[x] <= paddr && paddr <= zonesTo[x]) {
			zonesHit[x]++;
			zonesHitAddress[x].insert(paddr);
		}
	}
// end check zone
	if (pointer == MAX_SEND_BYTE) {
		send(memorySockfd, (char *) buffer,
		MAX_SEND_BYTE * sizeof(unsigned int), 0);

		send(memorySockfd, &zonesTail, sizeof(unsigned int), 0);

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
			int yy = 0;
			for (theIterator = zonesHitAddress[x].begin(); theIterator != zonesHitAddress[x].end() && yy < size; theIterator++) {
				write(memorySockfd, &*theIterator, sizeof(unsigned int));
				yy++;
			}
		}
		// end send zones back to GKD

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
		pointer = 0;
	}
}

void bxInstrumentation::jmpSampling(unsigned what, bx_address branch_eip, bx_address new_eip) {
	if (connectedToJmpServer && startRecordJump) {
		bx_phy_address fromPhysicalAddress;
		bx_phy_address toPhysicalAddress;
		BX_CPU(cpu)->dbg_xlate_linear2phy(branch_eip, &fromPhysicalAddress, false);
		BX_CPU(cpu)->dbg_xlate_linear2phy(new_eip, &toPhysicalAddress, false);

		while (jumpIndex >= JMP_CACHE_SIZE) {
//			fprintf(log, "buffer overflow, jumpIndex=%d\n", jumpIndex);
//			fflush(log);
			sleep(1);
		}

		pthread_mutex_lock(&jmpMutex);
		fromAddressVector[jumpIndex] = fromPhysicalAddress;
		toAddressVector[jumpIndex] = toPhysicalAddress;

		whatVector[jumpIndex] = what;

		segmentBeginVector[jumpIndex] = segmentBegin;
		segmentEndVector[jumpIndex] = segmentEnd;

		eaxVector[jumpIndex] = BX_CPU(0)->gen_reg[BX_32BIT_REG_EAX].dword.erx;
		ecxVector[jumpIndex] = BX_CPU(0)->gen_reg[BX_32BIT_REG_ECX].dword.erx;
		edxVector[jumpIndex] = BX_CPU(0)->gen_reg[BX_32BIT_REG_EDX].dword.erx;
		ebxVector[jumpIndex] = BX_CPU(0)->gen_reg[BX_32BIT_REG_EBX].dword.erx;
		espVector[jumpIndex] = BX_CPU(0)->gen_reg[BX_32BIT_REG_ESP].dword.erx;
		ebpVector[jumpIndex] = BX_CPU(0)->gen_reg[BX_32BIT_REG_EBP].dword.erx;
		esiVector[jumpIndex] = BX_CPU(0)->gen_reg[BX_32BIT_REG_ESI].dword.erx;
		ediVector[jumpIndex] = BX_CPU(0)->gen_reg[BX_32BIT_REG_EDI].dword.erx;

		esVector[jumpIndex] = BX_CPU(0)->sregs[BX_SEG_REG_ES].selector.value;
		csVector[jumpIndex] = BX_CPU(0)->sregs[BX_SEG_REG_CS].selector.value;
		ssVector[jumpIndex] = BX_CPU(0)->sregs[BX_SEG_REG_SS].selector.value;
		dsVector[jumpIndex] = BX_CPU(0)->sregs[BX_SEG_REG_DS].selector.value;
		fsVector[jumpIndex] = BX_CPU(0)->sregs[BX_SEG_REG_FS].selector.value;
		gsVector[jumpIndex] = BX_CPU(0)->sregs[BX_SEG_REG_GS].selector.value;

//		bx_address linear_sp = BX_CPU(dbg_cpu)->get_reg32(BX_32BIT_REG_ESP);
//		linear_sp = BX_CPU(dbg_cpu)->get_laddr(BX_SEG_REG_SS, linear_sp);
//		Bit8u buf[STACK_SIZE];
//		//fprintf(log, "read stack 2 = %lx\n", linear_sp);
//		bx_dbg_read_linear(dbg_cpu, linear_sp, STACK_SIZE, buf);

		bx_address linear_sp = BX_CPU(0)->get_reg32(BX_32BIT_REG_ESP);
		linear_sp = BX_CPU(0)->get_laddr(BX_SEG_REG_SS, linear_sp);
		Bit8u buf[STACK_SIZE];
		//fprintf(log, "read stack 2 = %lx\n", linear_sp);
		bx_dbg_read_linear(0, linear_sp, STACK_SIZE, buf);

//		fprintf(log, "jmp %lx, %lx > ", toPhysicalAddress, linear_sp);
//		fflush(log);
//
//		for (int x = 0; x < STACK_SIZE; x++) {
//			fprintf(log, "%x ", buf[x]);
//			fflush(log);
//		}
//		fprintf(log, "\n");
//		fflush(log);

//		for (int x = 0; x < STACK_SIZE; x++) {
//			buf[x] = x;
//		}

		memcpy(stack[jumpIndex], buf, STACK_SIZE);

		jumpIndex++;
		pthread_mutex_unlock(&jmpMutex);
		segmentBegin = new_eip;
	}
}
