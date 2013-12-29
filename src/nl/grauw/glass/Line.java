package nl.grauw.glass;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import nl.grauw.glass.expressions.Context;
import nl.grauw.glass.expressions.ContextLiteral;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.instructions.Instruction;
import nl.grauw.glass.instructions.Org;

public class Line implements Context {
	
	private static final byte[] NO_BYTES = new byte[] {};
	
	private final Scope scope;
	private final File sourceFile;
	private final int lineNumber;
	private Label label;
	private String mnemonic;
	private Expression arguments;
	private Comment comment;
	private int address = -1;
	
	private Instruction instruction;
	
	public Line(Scope scope, File sourceFile, int lineNumber) {
		this.scope = scope;
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
	}
	
	public Line(Scope scope, Line other) {
		this(scope, other.sourceFile, other.lineNumber);
		label = other.label != null ? new Label(other.label, this) : null;
		mnemonic = other.mnemonic;
		arguments = other.arguments != null ? other.arguments.copy(this) : null;
		comment = other.comment;
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
		scope.addLabel(label.getName(), new ContextLiteral(this));
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
	public Expression getLabel(String name) {
		return scope.getLabel(name);
	}
	
	@Override
	public int getAddress() {
		if (address == -1)
			throw new AssemblyException("Address not initialized.");
		return address;
	}
	
	public void setAddress(int address) {
		if (address < 0 || address >= 0x10000)
			throw new AssemblyException("Address out of range: " + Integer.toHexString(address) + "H");
		this.address = address;
	}
	
	public Instruction getInstruction() {
		return instruction;
	}
	
	public int resolve(int address) {
		if (mnemonic != null)
			instruction = scope.createInstruction(mnemonic, arguments);
		this.address = instruction instanceof Org ? ((Org)instruction).getAddress() : address;
		if (instruction != null)
			return instruction.resolve(this);
		return this.address;
	}
	
	public int generateObjectCode(int address, OutputStream output) throws IOException {
		address = instruction instanceof Org ? ((Org)instruction).getAddress() : address;
		if (address != this.address)
			throw new AssemblyException("Address changed between passes.");
		
		byte[] object = getBytes();
		output.write(object, 0, object.length);
		
		return address + object.length;
	}
	
	public int getSize() {
		if (instruction == null)
			return 0;
		return instruction.getSize(this);
	}
	
	public byte[] getBytes() {
		if (instruction == null)
			return NO_BYTES;
		return instruction.getBytes(this);
	}
	
	public String toString() {
		return (label != null ? label + ":" : "") +
			(mnemonic != null ? (label != null ? " " : "\t") + mnemonic + (arguments != null ? " " + arguments : "") : "") +
			(comment != null ? (label != null || mnemonic != null ? " ;" : ";") + comment : "");
	}
	
}