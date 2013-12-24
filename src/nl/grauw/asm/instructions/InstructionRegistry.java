package nl.grauw.asm.instructions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.grauw.asm.expressions.Expression;

public class InstructionRegistry {
	
	private Map<String, List<InstructionFactory>> registry = new HashMap<>();
	
	public InstructionRegistry() {
		add(new Adc_A.Factory());
		add(new Adc_A_N.Factory());
		add(new Adc_HL.Factory());
		add(new Add_A.Factory());
		add(new Add_A_N.Factory());
		add(new Add_HL.Factory());
		add(new And.Factory());
		add(new And_N.Factory());
		add(new Bit.Factory());
		add(new Call_F.Factory());
		add(new Call.Factory());
		add(new Ccf.Factory());
		add(new Cp.Factory());
		add(new Cp_N.Factory());
		add(new Cpd.Factory());
		add(new Cpdr.Factory());
		add(new Cpi.Factory());
		add(new Cpir.Factory());
		add(new Cpl.Factory());
		add(new Daa.Factory());
		add(new Dec.Factory());
		add(new Di.Factory());
		add(new Djnz.Factory());
		add(new Ei.Factory());
		add(new Ex_AF.Factory());
		add(new Ex_DE_HL.Factory());
		add(new Ex_SP.Factory());
		add(new Exx.Factory());
		add(new Halt.Factory());
		add(new Im.Factory());
		add(new In_C.Factory());
		add(new In_N.Factory());
		add(new Inc.Factory());
		add(new Ind.Factory());
		add(new Indr.Factory());
		add(new Ini.Factory());
		add(new Inir.Factory());
		add(new Jp_F.Factory());
		add(new Jp_HL.Factory());
		add(new Jp.Factory());
		add(new Jr_F.Factory());
		add(new Jr.Factory());
		add(new Ld_R_R.Factory());
		add(new Ld_A_BCDE.Factory());
		add(new Ld_BCDE_A.Factory());
		add(new Ld_SP_HL.Factory());
		add(new Ld_A_IR.Factory());
		add(new Ld_IR_A.Factory());
		add(new Ld_R_N.Factory());
		add(new Ld_RR_N.Factory());
		add(new Ld_A_NN.Factory());
		add(new Ld_HL_NN.Factory());
		add(new Ld_RR_NN.Factory());
		add(new Ld_NN_A.Factory());
		add(new Ld_NN_HL.Factory());
		add(new Ld_NN_RR.Factory());
		add(new Ldd.Factory());
		add(new Lddr.Factory());
		add(new Ldi.Factory());
		add(new Ldir.Factory());
		add(new Mulub.Factory());
		add(new Muluw.Factory());
		add(new Neg.Factory());
		add(new Nop.Factory());
		add(new Or.Factory());
		add(new Or_N.Factory());
		add(new Otdr.Factory());
		add(new Otir.Factory());
		add(new Out_C.Factory());
		add(new Out_N.Factory());
		add(new Outi.Factory());
		add(new Outd.Factory());
		add(new Pop.Factory());
		add(new Push.Factory());
		add(new Res.Factory());
		add(new Ret.Factory());
		add(new Ret_F.Factory());
		add(new Reti.Factory());
		add(new Retn.Factory());
		add(new Rl.Factory());
		add(new Rla.Factory());
		add(new Rlc.Factory());
		add(new Rlca.Factory());
		add(new Rld.Factory());
		add(new Rr.Factory());
		add(new Rra.Factory());
		add(new Rrc.Factory());
		add(new Rrca.Factory());
		add(new Rrd.Factory());
		add(new Rst.Factory());
		add(new Sbc_A.Factory());
		add(new Sbc_A_N.Factory());
		add(new Sbc_HL.Factory());
		add(new Scf.Factory());
		add(new Set.Factory());
		add(new Sla.Factory());
		add(new Sra.Factory());
		add(new Srl.Factory());
		add(new Sub.Factory());
		add(new Sub_N.Factory());
		add(new Xor.Factory());
		add(new Xor_N.Factory());
	}
	
	public void add(InstructionFactory factory) {
		List<InstructionFactory> factoryList = registry.get(factory.getMnemonic());
		if (factoryList == null) {
			factoryList = new ArrayList<InstructionFactory>();
			registry.put(factory.getMnemonic(), factoryList);
		}
		factoryList.add(factory);
	}
	
	public Instruction createInstruction(String mnemonic, Expression arguments) {
		List<InstructionFactory> factoryList = registry.get(mnemonic);
		if (factoryList != null) {
			for (InstructionFactory factory : factoryList) {
				Instruction instruction = factory.createInstruction(arguments);
				if (instruction != null)
					return instruction;
			}
			//throw new ArgumentException();
		}
		//throw new RuntimeException("Mnemonic not recognised.");
		return null;
	}
	
	public interface InstructionFactory {
		public String getMnemonic();
		public Instruction createInstruction(Expression arguments);
	}
	
}
