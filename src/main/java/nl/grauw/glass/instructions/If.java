package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class If extends InstructionFactory {

	private static Schema ARGUMENTS = new Schema(Schema.INTEGER);

	private final Source thenSource;
	private final Source elseSource;

	public If(Source thenSource, Source elseSource) {
		this.thenSource = thenSource;
		this.elseSource = elseSource;
	}

	public void expand(Line line, List<Line> lines) {
		Expression arguments = line.getArguments();
		if (!ARGUMENTS.check(arguments))
			throw new ArgumentException();

		super.expand(line, lines);
		thenSource.expand();
		elseSource.expand();
	}

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (ARGUMENTS.check(arguments))
			return new IfObject(context, arguments);
		throw new ArgumentException();
	}

	public class IfObject extends InstructionObject {

		private final Expression argument;

		public IfObject(Scope context, Expression argument) {
			super(context);
			this.argument = argument;
		}

		@Override
		public Expression resolve(Expression address) {
			context.setAddress(address);
			if (argument.getInteger() != 0) {
				return thenSource.resolve(address);
			} else {
				return elseSource.resolve(address);
			}
		}

		@Override
		public Expression getSize() {
			throw new AssemblyException("Not implemented.");
		}

		@Override
		public byte[] getBytes() {
			if (argument.getInteger() != 0) {
				return thenSource.getBytes();
			} else {
				return elseSource.getBytes();
			}
		}

	}

}
