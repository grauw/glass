package nl.grauw.asm;

import java.util.ArrayList;
import java.util.List;

public class Statement {
	
	private final String instruction;
	private final ArrayList<String> arguments = new ArrayList<String>();
	
	public Statement(String instruction) {
		this.instruction = instruction;
	}
	
	public String AddArgument(String argument) {
		arguments.add(argument);
		return argument;
	}
	
	public String getInstruction() {
		return instruction;
	}
	
	public List<String> getArguments() {
		return arguments;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder(instruction);
		builder.append(' ');
		for (String argument : arguments)
			builder.append(argument);
		return builder.toString();
	}
	
}
