package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Schema;

public class Pop extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_AF_INDEX);
	
	Expression argument;
	
	public Pop(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyDirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		Register register = argument.getRegister();
		return indexifyDirect(register, (byte)(0xC1 | register.get16BitCode() << 4));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("pop", this);
			scope.addInstruction("POP", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Pop(arguments.getElement(0));
			return null;
		}
		
	}
	
}
