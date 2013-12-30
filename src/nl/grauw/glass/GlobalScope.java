package nl.grauw.glass;

import nl.grauw.glass.instructions.*;

public class GlobalScope extends Scope {
	
	public GlobalScope() {
		super();
		setAddress(0);
		
		new Adc_A.Factory().register(this);
		new Add_A.Factory().register(this);
		new And.Factory().register(this);
		new Bit.Factory().register(this);
		new Call.Factory().register(this);
		new Ccf.Factory().register(this);
		new Cp.Factory().register(this);
		new Cpd.Factory().register(this);
		new Cpdr.Factory().register(this);
		new Cpi.Factory().register(this);
		new Cpir.Factory().register(this);
		new Cpl.Factory().register(this);
		new Daa.Factory().register(this);
		new Db.Factory().register(this);
		new Dec.Factory().register(this);
		new Di.Factory().register(this);
		new Djnz.Factory().register(this);
		new Ds.Factory().register(this);
		new Dw.Factory().register(this);
		new Ei.Factory().register(this);
		new Ex_AF.Factory().register(this);
		new Exx.Factory().register(this);
		new Halt.Factory().register(this);
		new Im.Factory().register(this);
		new In_C.Factory().register(this);
		new Inc.Factory().register(this);
		new Ind.Factory().register(this);
		new Indr.Factory().register(this);
		new Ini.Factory().register(this);
		new Inir.Factory().register(this);
		new Jp.Factory().register(this);
		new Jr.Factory().register(this);
		new Ld_R_R.Factory().register(this);
		new Ldd.Factory().register(this);
		new Lddr.Factory().register(this);
		new Ldi.Factory().register(this);
		new Ldir.Factory().register(this);
		new Mulub.Factory().register(this);
		new Muluw.Factory().register(this);
		new Neg.Factory().register(this);
		new Nop.Factory().register(this);
		new Or.Factory().register(this);
		new Otdr.Factory().register(this);
		new Otir.Factory().register(this);
		new Out_C.Factory().register(this);
		new Outi.Factory().register(this);
		new Outd.Factory().register(this);
		new Pop.Factory().register(this);
		new Push.Factory().register(this);
		new Res.Factory().register(this);
		new Ret.Factory().register(this);
		new Reti.Factory().register(this);
		new Retn.Factory().register(this);
		new Rl.Factory().register(this);
		new Rla.Factory().register(this);
		new Rlc.Factory().register(this);
		new Rlca.Factory().register(this);
		new Rld.Factory().register(this);
		new Rr.Factory().register(this);
		new Rra.Factory().register(this);
		new Rrc.Factory().register(this);
		new Rrca.Factory().register(this);
		new Rrd.Factory().register(this);
		new Rst.Factory().register(this);
		new Sbc_A.Factory().register(this);
		new Scf.Factory().register(this);
		new Set.Factory().register(this);
		new Sla.Factory().register(this);
		new Sra.Factory().register(this);
		new Srl.Factory().register(this);
		new Sub.Factory().register(this);
		new Xor.Factory().register(this);
		
		new Include.Factory().register(this);
		new Equ.Factory().register(this);
		new Org.Factory().register(this);
		new MacroDeclaration.Factory().register(this);
	}
	
}
