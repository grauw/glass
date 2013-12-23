package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class InstructionRegistry {
	
	public Instruction createInstruction(String name, Expression arguments) {
		switch (name) {
		case "adc":
			return new Adc(arguments);
		case "add":
			return new Add(arguments);
		case "and":
			return new And(arguments);
		case "bit":
			return new Bit(arguments);
		case "call":
			return new Call(arguments);
		case "ccf":
			return new Ccf(arguments);
		case "cp":
			return new Cp(arguments);
		case "cpd":
			return new Cpd(arguments);
		case "cpdr":
			return new Cpdr(arguments);
		case "cpi":
			return new Cpi(arguments);
		case "cpir":
			return new Cpir(arguments);
		case "cpl":
			return new Cpl(arguments);
		case "daa":
			return new Daa(arguments);
		case "dec":
			return new Dec(arguments);
		case "di":
			return new Di(arguments);
		case "djnz":
			return new Djnz(arguments);
		case "ei":
			return new Ei(arguments);
		case "ex":
			return new Ex(arguments);
		case "exx":
			return new Exx(arguments);
		case "halt":
			return new Halt(arguments);
		case "im":
			return new Im(arguments);
		case "in":
			return new In(arguments);
		case "inc":
			return new Inc(arguments);
		case "ind":
			return new Ind(arguments);
		case "indr":
			return new Indr(arguments);
		case "ini":
			return new Ini(arguments);
		case "inir":
			return new Inir(arguments);
		case "jp":
			return new Jp(arguments);
		case "jr":
			return new Jr(arguments);
		case "ld":
			return new Ld(arguments);
		case "ldd":
			return new Ldd(arguments);
		case "lddr":
			return new Lddr(arguments);
		case "ldi":
			return new Ldi(arguments);
		case "ldir":
			return new Ldir(arguments);
		case "mulub":
			return new Mulub(arguments);
		case "muluw":
			return new Muluw(arguments);
		case "neg":
			return new Neg(arguments);
		case "nop":
			return new Nop(arguments);
		case "or":
			return new Or(arguments);
		case "otdr":
			return new Otdr(arguments);
		case "otir":
			return new Otir(arguments);
		case "out":
			return new Out(arguments);
		case "outi":
			return new Outi(arguments);
		case "outd":
			return new Outd(arguments);
		case "pop":
			return new Pop(arguments);
		case "push":
			return new Push(arguments);
		case "res":
			return new Res(arguments);
		case "ret":
			return new Ret(arguments);
		case "reti":
			return new Reti(arguments);
		case "retn":
			return new Retn(arguments);
		case "rl":
			return new Rl(arguments);
		case "rla":
			return new Rla(arguments);
		case "rlc":
			return new Rlc(arguments);
		case "rlca":
			return new Rlca(arguments);
		case "rld":
			return new Rld(arguments);
		case "rr":
			return new Rr(arguments);
		case "rra":
			return new Rra(arguments);
		case "rrc":
			return new Rrc(arguments);
		case "rrca":
			return new Rrca(arguments);
		case "rrd":
			return new Rrd(arguments);
		case "rst":
			return new Rst(arguments);
		case "sbc":
			return new Sbc(arguments);
		case "scf":
			return new Scf(arguments);
		case "set":
			return new Set(arguments);
		case "sla":
			return new Sla(arguments);
		case "sra":
			return new Sra(arguments);
		case "srl":
			return new Srl(arguments);
		case "sub":
			return new Sub(arguments);
		case "xor":
			return new Xor(arguments);
		}
		return null;
	}
	
}
