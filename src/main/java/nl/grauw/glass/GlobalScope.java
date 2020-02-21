package nl.grauw.glass;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Instruction;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.instructions.*;
import nl.grauw.glass.instructions.Error;

public class GlobalScope extends Scope {

	public GlobalScope() {
		super();
		setAddress(IntegerLiteral.ZERO);

		addBuiltInSymbol("adc", new Instruction(new Adc(), new Scope(this)));
		addBuiltInSymbol("add", new Instruction(new Add(), new Scope(this)));
		addBuiltInSymbol("and", new Instruction(new And(), new Scope(this)));
		addBuiltInSymbol("bit", new Instruction(new Bit(), new Scope(this)));
		addBuiltInSymbol("call", new Instruction(new Call(), new Scope(this)));
		addBuiltInSymbol("ccf", new Instruction(new Ccf(), new Scope(this)));
		addBuiltInSymbol("cp", new Instruction(new Cp(), new Scope(this)));
		addBuiltInSymbol("cpd", new Instruction(new Cpd(), new Scope(this)));
		addBuiltInSymbol("cpdr", new Instruction(new Cpdr(), new Scope(this)));
		addBuiltInSymbol("cpi", new Instruction(new Cpi(), new Scope(this)));
		addBuiltInSymbol("cpir", new Instruction(new Cpir(), new Scope(this)));
		addBuiltInSymbol("cpl", new Instruction(new Cpl(), new Scope(this)));
		addBuiltInSymbol("daa", new Instruction(new Daa(), new Scope(this)));
		addBuiltInSymbol("db", new Instruction(new Db(), new Scope(this)));
		addBuiltInSymbol("dd", new Instruction(new Dd(), new Scope(this)));
		addBuiltInSymbol("dec", new Instruction(new Dec(), new Scope(this)));
		addBuiltInSymbol("di", new Instruction(new Di(), new Scope(this)));
		addBuiltInSymbol("djnz", new Instruction(new Djnz(), new Scope(this)));
		addBuiltInSymbol("ds", new Instruction(new Ds(), new Scope(this)));
		addBuiltInSymbol("dw", new Instruction(new Dw(), new Scope(this)));
		addBuiltInSymbol("ei", new Instruction(new Ei(), new Scope(this)));
		addBuiltInSymbol("ex", new Instruction(new Ex(), new Scope(this)));
		addBuiltInSymbol("exx", new Instruction(new Exx(), new Scope(this)));
		addBuiltInSymbol("halt", new Instruction(new Halt(), new Scope(this)));
		addBuiltInSymbol("im", new Instruction(new Im(), new Scope(this)));
		addBuiltInSymbol("in", new Instruction(new In(), new Scope(this)));
		addBuiltInSymbol("inc", new Instruction(new Inc(), new Scope(this)));
		addBuiltInSymbol("ind", new Instruction(new Ind(), new Scope(this)));
		addBuiltInSymbol("indr", new Instruction(new Indr(), new Scope(this)));
		addBuiltInSymbol("ini", new Instruction(new Ini(), new Scope(this)));
		addBuiltInSymbol("inir", new Instruction(new Inir(), new Scope(this)));
		addBuiltInSymbol("jp", new Instruction(new Jp(), new Scope(this)));
		addBuiltInSymbol("jr", new Instruction(new Jr(), new Scope(this)));
		addBuiltInSymbol("ld", new Instruction(new Ld(), new Scope(this)));
		addBuiltInSymbol("ldd", new Instruction(new Ldd(), new Scope(this)));
		addBuiltInSymbol("lddr", new Instruction(new Lddr(), new Scope(this)));
		addBuiltInSymbol("ldi", new Instruction(new Ldi(), new Scope(this)));
		addBuiltInSymbol("ldir", new Instruction(new Ldir(), new Scope(this)));
		addBuiltInSymbol("mulub", new Instruction(new Mulub(), new Scope(this)));
		addBuiltInSymbol("muluw", new Instruction(new Muluw(), new Scope(this)));
		addBuiltInSymbol("neg", new Instruction(new Neg(), new Scope(this)));
		addBuiltInSymbol("nop", new Instruction(new Nop(), new Scope(this)));
		addBuiltInSymbol("or", new Instruction(new Or(), new Scope(this)));
		addBuiltInSymbol("otdr", new Instruction(new Otdr(), new Scope(this)));
		addBuiltInSymbol("otir", new Instruction(new Otir(), new Scope(this)));
		addBuiltInSymbol("out", new Instruction(new Out(), new Scope(this)));
		addBuiltInSymbol("outi", new Instruction(new Outi(), new Scope(this)));
		addBuiltInSymbol("outd", new Instruction(new Outd(), new Scope(this)));
		addBuiltInSymbol("pop", new Instruction(new Pop(), new Scope(this)));
		addBuiltInSymbol("push", new Instruction(new Push(), new Scope(this)));
		addBuiltInSymbol("res", new Instruction(new Res(), new Scope(this)));
		addBuiltInSymbol("ret", new Instruction(new Ret(), new Scope(this)));
		addBuiltInSymbol("reti", new Instruction(new Reti(), new Scope(this)));
		addBuiltInSymbol("retn", new Instruction(new Retn(), new Scope(this)));
		addBuiltInSymbol("rl", new Instruction(new Rl(), new Scope(this)));
		addBuiltInSymbol("rla", new Instruction(new Rla(), new Scope(this)));
		addBuiltInSymbol("rlc", new Instruction(new Rlc(), new Scope(this)));
		addBuiltInSymbol("rlca", new Instruction(new Rlca(), new Scope(this)));
		addBuiltInSymbol("rld", new Instruction(new Rld(), new Scope(this)));
		addBuiltInSymbol("rr", new Instruction(new Rr(), new Scope(this)));
		addBuiltInSymbol("rra", new Instruction(new Rra(), new Scope(this)));
		addBuiltInSymbol("rrc", new Instruction(new Rrc(), new Scope(this)));
		addBuiltInSymbol("rrca", new Instruction(new Rrca(), new Scope(this)));
		addBuiltInSymbol("rrd", new Instruction(new Rrd(), new Scope(this)));
		addBuiltInSymbol("rst", new Instruction(new Rst(), new Scope(this)));
		addBuiltInSymbol("sbc", new Instruction(new Sbc(), new Scope(this)));
		addBuiltInSymbol("scf", new Instruction(new Scf(), new Scope(this)));
		addBuiltInSymbol("set", new Instruction(new Set(), new Scope(this)));
		addBuiltInSymbol("sla", new Instruction(new Sla(), new Scope(this)));
		addBuiltInSymbol("sra", new Instruction(new Sra(), new Scope(this)));
		addBuiltInSymbol("srl", new Instruction(new Srl(), new Scope(this)));
		addBuiltInSymbol("sub", new Instruction(new Sub(), new Scope(this)));
		addBuiltInSymbol("xor", new Instruction(new Xor(), new Scope(this)));

		addBuiltInSymbol("equ", new Instruction(new Equ(), new Scope(this)));
		addBuiltInSymbol("org", new Instruction(new Org(), new Scope(this)));
		addBuiltInSymbol("endm", new Instruction(new Endm(), new Scope(this)));
		addBuiltInSymbol("endp", new Instruction(new Endp(), new Scope(this)));
		addBuiltInSymbol("ends", new Instruction(new Ends(), new Scope(this)));
		addBuiltInSymbol("end", new Instruction(new End(), new Scope(this)));
		addBuiltInSymbol("endif", new Instruction(new Endif(), new Scope(this)));
		addBuiltInSymbol("else", new Instruction(new Else(), new Scope(this)));
		addBuiltInSymbol("error", new Instruction(new Error(), new Scope(this)));
		addBuiltInSymbol("warning", new Instruction(new Warning(), new Scope(this)));
	}

	private void addBuiltInSymbol(String symbol, Expression value) {
		addSymbol(symbol, value);
		addSymbol(symbol.toUpperCase(), value);
	}

}
