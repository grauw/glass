package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ld_R_R extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
	
	private Register register1;
	private Register register2;
	
	public Ld_R_R(Register register1, Register register2) {
		this.register1 = register1;
		this.register2 = register2;
		
		if (register1.isIndex() && register2.isIndex() && register1.getIndexCode() != register2.getIndexCode() ||
				register1.isIndex() && (register2 == Register.H || register2 == Register.L || register2 == Register.HL) ||
				register2.isIndex() && (register1 == Register.H || register1 == Register.L || register1 == Register.HL))
			throw new ArgumentException();
	}
	
	@Override
	public byte[] getBytes() {
		return indexifyIndirect(!register1.isIndex() ? register2 : register1,
				(byte)(0x40 | register1.get8BitCode() << 3 | register2.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ld";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_R_R(arguments.getElement(0).getRegister(), arguments.getElement(1).getRegister());
			return null;
		}
		
	}
	
}
