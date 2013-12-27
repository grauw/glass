package nl.grauw.asm;

import java.io.File;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.IntegerLiteral;
import nl.grauw.asm.instructions.Instruction;
import nl.grauw.asm.instructions.InstructionRegistry;

public class Line implements Context {
	
	private final File sourceFile;
	private final int lineNumber;
	private Label label;
	private String mnemonic;
	private Expression arguments;
	private Comment comment;
	private Scope scope;
	private int address;
	
	private Instruction instruction;
	
	public Line(File sourceFile, int lineNumber) {
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
	}
	
	public File getSourceFile() {
		return sourceFile;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public Label getLabel() {
		return label;
	}
	
	public void setLabel(Label label) {
		this.label = label;
	}
	
	public String getMnemonic() {
		return mnemonic;
	}
	
	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}
	
	public Expression getArguments() {
		return arguments;
	}
	
	public void setArguments(Expression arguments) {
		this.arguments = arguments;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	@Override
	public Expression getLabel(String label) {
		return scope.getLabel(label);
	}
	
	@Override
	public int getAddress() {
		return address;
	}
	
	public void setScopeAndAddress(Scope scope, int address) {
		this.scope = scope;
		this.address = address;
		if (label != null)
			scope.addLabel(label.getName(), new IntegerLiteral(address));
	}
	
	public Instruction getInstruction() {
		return instruction;
	}
	
	public void resolveInstruction(InstructionRegistry factory) {
		if (mnemonic != null)
			instruction = factory.createInstruction(mnemonic, arguments);
	}
	
	public byte[] getBytes() {
		return instruction.getBytes(this);
	}
	
	public String toString() {
		return (label != null ? label + ":" : "") +
			(mnemonic != null ? (label != null ? " " : "\t") + mnemonic + (arguments != null ? " " + arguments : "") : "") +
			(comment != null ? (label != null || mnemonic != null ? " ;" : ";") + comment : "");
	}
	
}
