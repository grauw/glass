package nl.grauw.glass.instructions;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.ParameterScope;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;

public class MacroInstruction extends Instruction {
	
	private final Expression parameters;
	private final Source source;
	
	public MacroInstruction(Expression parameters, Source source) {
		this.parameters = parameters;
		this.source = source;
		
		Expression parameter = parameters != null ? parameters.getElement(0) : null;
		for (int i = 0; parameter != null; i++) {
			if (!(parameter instanceof Identifier))
				throw new ArgumentException("Parameter must be an identifier.");
			parameter = parameters.getElement(i);
		}
	}
	
	public InstructionObject createObject(Expression arguments, Scope scope) {
		Scope parameterScope = new ParameterScope(scope, parameters, arguments);
		Source copy = new Source(scope);
		for (Line line : source.getLines())
			copy.addLine(new Line(new Scope(parameterScope), line));
		return new MacroInstructionInstance(copy);
	}
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		throw new AssemblyException("Not implemented.");
	}
	
	public static class MacroInstructionInstance extends Directive {
		
		private final Source source;
		
		public MacroInstructionInstance(Source source) {
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
		
	}
	
}
