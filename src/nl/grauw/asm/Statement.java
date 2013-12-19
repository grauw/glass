package nl.grauw.asm;

import java.util.ArrayList;

public class Statement {
	
	private final String instruction;
	private final ArrayList<String> arguments = new ArrayList<String>();
	
	public Statement(String instruction) {
		this.instruction = instruction;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(instruction);
		builder.append(' ');
		for (String argument : arguments)
			builder.append(argument);
		return builder.toString();
	}
	
}
