package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Add_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL_IX_IY, Schema.DIRECT_RR_INDEX);
	
	private Register register1;
	private Register register2;
	
	public Add_HL(Register register1, Register register2) {
		this.register1 = register1;
		this.register2 = register2;
		
		if (register1.get16BitCode() == register2.get16BitCode() && register1 != register2)
			throw new ArgumentException();
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyDirect(register1.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return indexifyDirect(register1.getRegister(), (byte)(0x09 | register2.getRegister().get16BitCode() << 4));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(InstructionRegistry registry) {
			registry.add("add", this);
			registry.add("ADD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Add_HL(arguments.getElement(0).getRegister(), arguments.getElement(1).getRegister());
			return null;
		}
		
	}
	
}
