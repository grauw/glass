package nl.grauw.asm;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.Instruction;
import nl.grauw.asm.instructions.InstructionFactory;

public class Statement {
	
	private final String mnemonic;
	private final List<Expression> arguments = new ArrayList<Expression>();
	
	private Instruction instruction;
	
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
	
	public Instruction getInstruction() {
		return instruction;
	}
	
	public void resolveInstruction(InstructionFactory factory) {
		instruction = factory.createInstruction(mnemonic);
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
