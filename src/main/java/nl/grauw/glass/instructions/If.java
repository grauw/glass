package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IfElse;
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
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (ARGUMENTS.check(arguments))
			return new IfObject(address, arguments);
		throw new ArgumentException();
	}

	public class IfObject extends InstructionObject {

		private final Expression argument;

		public IfObject(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public Expression resolve() {
			if (argument.getInteger() != 0) {
				return thenSource.resolve(address);
			} else {
				return elseSource.resolve(address);
			}
		}

		@Override
		public Expression getSize() {
			return new IfElse(argument, thenSource.getSize(), elseSource.getSize());
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
