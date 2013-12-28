package nl.grauw.asm.instructions;

import nl.grauw.asm.AssemblyException;
import nl.grauw.asm.Scope;
import nl.grauw.asm.Source;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Identifier;

public class Macro extends Directive {
	
	private final Source source;
	
	public Macro(Source source) {
		this.source = source;
	}
	
	@Override
	public int resolve(Context context) {
		return source.resolve(context.getAddress());
	}
	
	@Override
	public int getSize(Context context) {
		throw new AssemblyException("Not implemented.");
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return source.generateObjectCode(context.getAddress());
	}
	
	public static class Factory implements InstructionFactory {
		
		private final String mnemonic;
		private final Expression parameters;
		private final Source source;
		
		public Factory(String mnemonic, Expression parameters, Source source) {
			this.mnemonic = mnemonic;
			this.parameters = parameters;
			this.source = source;
			
			Expression parameter = parameters != null ? parameters.getElement(0) : null;
			for (int i = 0; parameter != null; i++) {
				if (!(parameter instanceof Identifier))
					throw new ArgumentException("Parameter must be an identifier.");
				parameter = parameters.getElement(i);
			}
		}
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction(mnemonic, this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			Source copy = new Source(source);
			copy.getScope().addParameters(parameters, arguments);
			return new Macro(copy);
		}
		
	}
	
}
