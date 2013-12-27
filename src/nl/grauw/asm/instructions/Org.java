package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Org extends Directive {
	
	public static Schema ARGUMENTS = new Schema(Schema.INTEGER);
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "org";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Org();
			return null;
		}
		
	}
	
}
