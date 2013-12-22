package nl.grauw.asm;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.asm.expressions.Expression;

public class Statement {
	
	private final String mnemonic;
	private final List<Expression> arguments = new ArrayList<Expression>();
	
	public Statement(String mnemonic) {
		this.mnemonic = mnemonic;
	}
	
	public Expression AddArgument(Expression argument) {
		arguments.add(argument);
		return argument;
	}
	
	public String getMnemonic() {
		return mnemonic;
	}
	
	public List<Expression> getArguments() {
		return arguments;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder(mnemonic);
		builder.append(' ');
		for (int i = 0; i < arguments.size(); i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(arguments.get(i));
		}
		return builder.toString();
	}
	
}
