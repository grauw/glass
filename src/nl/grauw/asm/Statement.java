package nl.grauw.asm;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.Instruction;
import nl.grauw.asm.instructions.InstructionFactory;

public class Statement {
	
	private final String mnemonic;
	private Expression arguments;
	
	private Instruction instruction;
	
	public Statement(String mnemonic) {
		this.mnemonic = mnemonic;
	}
	
	public Expression setArguments(Expression arguments) {
		return this.arguments = arguments;
	}
	
	public String getMnemonic() {
		return mnemonic;
	}
	
	public Expression getArguments() {
		return arguments;
	}
	
	public Instruction getInstruction() {
		return instruction;
	}
	
	public void resolveInstruction(InstructionFactory factory) {
		instruction = factory.createInstruction(mnemonic);
	}
	
	public String toString() {
		return "" + mnemonic + (arguments != null ? " " + arguments : "");
	}
	
}
