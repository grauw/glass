package nl.grauw.asm.instructions;

public class InstructionFactory {
	
	public Instruction createInstruction(String name) {
		switch (name) {
		case "adc":
			return new Adc();
		case "add":
			return new Add();
		case "and":
			return new And();
		case "bit":
			return new Bit();
		case "call":
			return new Call();
		case "ccf":
			return new Ccf();
		case "cp":
			return new Cp();
		case "cpd":
			return new Cpd();
		case "cpdr":
			return new Cpdr();
		case "cpi":
			return new Cpi();
		case "cpir":
			return new Cpir();
		case "cpl":
			return new Cpl();
		case "daa":
			return new Daa();
		case "dec":
			return new Dec();
		case "di":
			return new Di();
		case "djnz":
			return new Djnz();
		case "ei":
			return new Ei();
		case "ex":
			return new Ex();
		case "exx":
			return new Exx();
		case "halt":
			return new Halt();
		case "im":
			return new Im();
		case "in":
			return new In();
		case "inc":
			return new Inc();
		case "ind":
			return new Ind();
		case "indr":
			return new Indr();
		case "ini":
			return new Ini();
		case "inir":
			return new Inir();
		case "jp":
			return new Jp();
		case "jr":
			return new Jr();
		case "ld":
			return new Ld();
		case "ldd":
			return new Ldd();
		case "lddr":
			return new Lddr();
		case "ldi":
			return new Ldi();
		case "ldir":
			return new Ldir();
		case "mulub":
			return new Mulub();
		case "muluw":
			return new Muluw();
		case "neg":
			return new Neg();
		case "nop":
			return new Nop();
		case "or":
			return new Or();
		case "otdr":
			return new Otdr();
		case "otir":
			return new Otir();
		case "out":
			return new Out();
		case "outi":
			return new Outi();
		case "outd":
			return new Outd();
		case "pop":
			return new Pop();
		case "push":
			return new Push();
		case "res":
			return new Res();
		case "ret":
			return new Ret();
		case "reti":
			return new Reti();
		case "retn":
			return new Retn();
		case "rl":
			return new Rl();
		case "rla":
			return new Rla();
		case "rlc":
			return new Rlc();
		case "rlca":
			return new Rlca();
		case "rld":
			return new Rld();
		case "rr":
			return new Rr();
		case "rra":
			return new Rra();
		case "rrc":
			return new Rrc();
		case "rrca":
			return new Rrca();
		case "rrd":
			return new Rrd();
		case "rst":
			return new Rst();
		case "sbc":
			return new Sbc();
		case "scf":
			return new Scf();
		case "set":
			return new Set();
		case "sla":
			return new Sla();
		case "sra":
			return new Sra();
		case "srl":
			return new Srl();
		case "sub":
			return new Sub();
		case "xor":
			return new Xor();
		}
		return null;
	}
	
}
