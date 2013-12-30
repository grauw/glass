package nl.grauw.glass;

import nl.grauw.glass.instructions.*;

public class GlobalScope extends Scope {
	
	public GlobalScope() {
		super();
		setAddress(0);
		
		new Adc().register(this);
		new Add().register(this);
		new And().register(this);
		new Bit().register(this);
		new Call().register(this);
		new Ccf().register(this);
		new Cp().register(this);
		new Cpd().register(this);
		new Cpdr().register(this);
		new Cpi().register(this);
		new Cpir().register(this);
		new Cpl().register(this);
		new Daa().register(this);
		new Db().register(this);
		new Dec().register(this);
		new Di().register(this);
		new Djnz().register(this);
		new Ds().register(this);
		new Dw().register(this);
		new Ei().register(this);
		new Ex().register(this);
		new Exx().register(this);
		new Halt().register(this);
		new Im().register(this);
		new In().register(this);
		new Inc().register(this);
		new Ind().register(this);
		new Indr().register(this);
		new Ini().register(this);
		new Inir().register(this);
		new Jp().register(this);
		new Jr().register(this);
		new Ld().register(this);
		new Ldd().register(this);
		new Lddr().register(this);
		new Ldi().register(this);
		new Ldir().register(this);
		new Mulub().register(this);
		new Muluw().register(this);
		new Neg().register(this);
		new Nop().register(this);
		new Or().register(this);
		new Otdr().register(this);
		new Otir().register(this);
		new Out().register(this);
		new Outi().register(this);
		new Outd().register(this);
		new Pop().register(this);
		new Push().register(this);
		new Res().register(this);
		new Ret().register(this);
		new Reti().register(this);
		new Retn().register(this);
		new Rl().register(this);
		new Rla().register(this);
		new Rlc().register(this);
		new Rlca().register(this);
		new Rld().register(this);
		new Rr().register(this);
		new Rra().register(this);
		new Rrc().register(this);
		new Rrca().register(this);
		new Rrd().register(this);
		new Rst().register(this);
		new Sbc().register(this);
		new Scf().register(this);
		new Set().register(this);
		new Sla().register(this);
		new Sra().register(this);
		new Srl().register(this);
		new Sub().register(this);
		new Xor().register(this);
		
		new Include().register(this);
		new Equ().register(this);
		new Org().register(this);
		new MacroDeclaration().register(this);
	}
	
}
