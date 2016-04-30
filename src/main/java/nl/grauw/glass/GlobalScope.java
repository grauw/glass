package nl.grauw.glass;

import nl.grauw.glass.instructions.*;
import nl.grauw.glass.instructions.Error;

public class GlobalScope extends Scope {
	
	public GlobalScope() {
		super();
		setAddress(0);
		
		addInstruction("adc", addInstruction("ADC", new Adc()));
		addInstruction("add", addInstruction("ADD", new Add()));
		addInstruction("and", addInstruction("AND", new And()));
		addInstruction("bit", addInstruction("BIT", new Bit()));
		addInstruction("call", addInstruction("CALL", new Call()));
		addInstruction("ccf", addInstruction("CCF", new Ccf()));
		addInstruction("cp", addInstruction("CP", new Cp()));
		addInstruction("cpd", addInstruction("CPD", new Cpd()));
		addInstruction("cpdr", addInstruction("CPDR", new Cpdr()));
		addInstruction("cpi", addInstruction("CPI", new Cpi()));
		addInstruction("cpir", addInstruction("CPIR", new Cpir()));
		addInstruction("cpl", addInstruction("CPL", new Cpl()));
		addInstruction("daa", addInstruction("DAA", new Daa()));
		addInstruction("db", addInstruction("DB", new Db()));
		addInstruction("dd", addInstruction("DD", new Dd()));
		addInstruction("dec", addInstruction("DEC", new Dec()));
		addInstruction("di", addInstruction("DI", new Di()));
		addInstruction("djnz", addInstruction("DJNZ", new Djnz()));
		addInstruction("ds", addInstruction("DS", new Ds()));
		addInstruction("dw", addInstruction("DW", new Dw()));
		addInstruction("ei", addInstruction("EI", new Ei()));
		addInstruction("ex", addInstruction("EX", new Ex()));
		addInstruction("exx", addInstruction("EXX", new Exx()));
		addInstruction("halt", addInstruction("HALT", new Halt()));
		addInstruction("im", addInstruction("IM", new Im()));
		addInstruction("in", addInstruction("IN", new In()));
		addInstruction("inc", addInstruction("INC", new Inc()));
		addInstruction("ind", addInstruction("IND", new Ind()));
		addInstruction("indr", addInstruction("INDR", new Indr()));
		addInstruction("ini", addInstruction("INI", new Ini()));
		addInstruction("inir", addInstruction("INIR", new Inir()));
		addInstruction("jp", addInstruction("JP", new Jp()));
		addInstruction("jr", addInstruction("JR", new Jr()));
		addInstruction("ld", addInstruction("LD", new Ld()));
		addInstruction("ldd", addInstruction("LDD", new Ldd()));
		addInstruction("lddr", addInstruction("LDDR", new Lddr()));
		addInstruction("ldi", addInstruction("LDI", new Ldi()));
		addInstruction("ldir", addInstruction("LDIR", new Ldir()));
		addInstruction("mulub", addInstruction("MULUB", new Mulub()));
		addInstruction("muluw", addInstruction("MULUW", new Muluw()));
		addInstruction("neg", addInstruction("NEG", new Neg()));
		addInstruction("nop", addInstruction("NOP", new Nop()));
		addInstruction("or", addInstruction("OR", new Or()));
		addInstruction("otdr", addInstruction("OTDR", new Otdr()));
		addInstruction("otir", addInstruction("OTIR", new Otir()));
		addInstruction("out", addInstruction("OUT", new Out()));
		addInstruction("outi", addInstruction("OUTI", new Outi()));
		addInstruction("outd", addInstruction("OUTD", new Outd()));
		addInstruction("pop", addInstruction("POP", new Pop()));
		addInstruction("push", addInstruction("PUSH", new Push()));
		addInstruction("res", addInstruction("RES", new Res()));
		addInstruction("ret", addInstruction("RET", new Ret()));
		addInstruction("reti", addInstruction("RETI", new Reti()));
		addInstruction("retn", addInstruction("RETN", new Retn()));
		addInstruction("rl", addInstruction("RL", new Rl()));
		addInstruction("rla", addInstruction("RLA", new Rla()));
		addInstruction("rlc", addInstruction("RLC", new Rlc()));
		addInstruction("rlca", addInstruction("RLCA", new Rlca()));
		addInstruction("rld", addInstruction("RLD", new Rld()));
		addInstruction("rr", addInstruction("RR", new Rr()));
		addInstruction("rra", addInstruction("RRA", new Rra()));
		addInstruction("rrc", addInstruction("RRC", new Rrc()));
		addInstruction("rrca", addInstruction("RRCA", new Rrca()));
		addInstruction("rrd", addInstruction("RRD", new Rrd()));
		addInstruction("rst", addInstruction("RST", new Rst()));
		addInstruction("sbc", addInstruction("SBC", new Sbc()));
		addInstruction("scf", addInstruction("SCF", new Scf()));
		addInstruction("set", addInstruction("SET", new Set()));
		addInstruction("sla", addInstruction("SLA", new Sla()));
		addInstruction("sra", addInstruction("SRA", new Sra()));
		addInstruction("srl", addInstruction("SRL", new Srl()));
		addInstruction("sub", addInstruction("SUB", new Sub()));
		addInstruction("xor", addInstruction("XOR", new Xor()));
		
		addInstruction("include", addInstruction("INCLUDE", new Include()));
		addInstruction("equ", addInstruction("EQU", new Equ()));
		addInstruction("org", addInstruction("ORG", new Org()));
		addInstruction("endm", addInstruction("ENDM", new Endm()));
		addInstruction("endp", addInstruction("ENDP", new Endp()));
		addInstruction("ends", addInstruction("ENDS", new Ends()));
		addInstruction("end", addInstruction("END", new End()));
		addInstruction("endif", addInstruction("ENDIF", new Endif()));
		addInstruction("else", addInstruction("ELSE", new Else()));
		addInstruction("error", addInstruction("ERROR", new Error()));
		addInstruction("warning", addInstruction("WARNING", new Warning()));
	}
	
}
