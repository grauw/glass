package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

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
	public int getSize(Scope context) {
		return indexifyDirect(register1.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return indexifyDirect(register1.getRegister(), (byte)(0x09 | register2.getRegister().get16BitCode() << 4));
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("add", this);
			scope.addInstruction("ADD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Add_HL.ARGUMENTS.check(arguments))
				return new Add_HL(arguments.getElement(0).getRegister(), arguments.getElement(1).getRegister());
			return null;
		}
		
	}
	
}
