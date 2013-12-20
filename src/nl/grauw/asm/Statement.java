package nl.grauw.asm;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.asm.expressions.Expression;

public class Statement {
	
	private final String instruction;
	private final List<Expression> arguments = new ArrayList<Expression>();
	
	public Statement(String instruction) {
		this.instruction = instruction;
	}
	
	public Expression AddArgument(Expression argument) {
		arguments.add(argument);
		return argument;
	}
	
	public String getInstruction() {
		return instruction;
	}
	
	public List<Expression> getArguments() {
		return arguments;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder(instruction);
		builder.append(' ');
		for (int i = 0; i < arguments.size(); i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(arguments.get(i));
		}
		return builder.toString();
	}
	
}
