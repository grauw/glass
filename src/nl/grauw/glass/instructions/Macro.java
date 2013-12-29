package nl.grauw.glass.instructions;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.ParameterScope;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;

public class Macro extends Directive {
	
	private final Source source;
	
	public Macro(Source source) {
		this.source = source;
	}
	
	@Override
	public int resolve(Scope context, int address) {
		context.setAddress(address);
		return source.resolve(address);
	}
	
	@Override
	public int getSize(Scope context) {
		throw new AssemblyException("Not implemented.");
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return source.generateObjectCode(context.getAddress());
	}
	
	public static class Factory extends InstructionFactory {
		
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
		
		public Instruction createInstruction(Expression arguments, Scope scope) {
			Scope parameterScope = new ParameterScope(scope, parameters, arguments);
			Source copy = new Source(scope);
			for (Line line : source.getLines())
				copy.addLine(new Line(parameterScope, line));
			return new Macro(copy);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			throw new AssemblyException("Not implemented.");
		}
		
	}
	
}
