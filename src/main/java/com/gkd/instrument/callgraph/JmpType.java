package com.gkd.instrument.callgraph;

public enum JmpType {
	unknown, JMP, JMP_INDIRECT, CALL, CALL_INDIRECT, RET, IRET, INT, SYSCALL, SYSRET, SYSENTER, SYSEXIT,
}
