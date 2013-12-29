package nl.grauw.glass;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import nl.grauw.glass.expressions.ContextLiteral;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.instructions.Instruction;
import nl.grauw.glass.instructions.Org;

public class Line {
	
	private static final byte[] NO_BYTES = new byte[] {};
	
	private final Scope scope;
	private final Scope innerScope;
	private final File sourceFile;
	private final int lineNumber;
	private Label label;
	private String mnemonic;
	private Expression arguments;
	private String comment;
	
	private Instruction instruction;
	
	public Line(Scope scope, File sourceFile, int lineNumber) {
		this.scope = scope;
		this.innerScope = new Scope(scope);
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
	}
	
	public Line(Scope scope, Line other) {
		this(scope, other.sourceFile, other.lineNumber);
		label = other.label != null ? new Label(other.label, innerScope) : null;
		mnemonic = other.mnemonic;
		arguments = other.arguments != null ? other.arguments.copy(innerScope) : null;
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
		scope.addLabel(label.getName(), new ContextLiteral(innerScope));
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
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	public Scope getInnerScope() {
		return innerScope;
	}
	
	public Instruction getInstruction() {
		return instruction;
	}
	
	public int resolve(int address) {
		if (mnemonic != null)
			instruction = scope.createInstruction(mnemonic, arguments);
		innerScope.setAddress(instruction instanceof Org ? ((Org)instruction).getAddress() : address);
		if (instruction != null)
			return instruction.resolve(innerScope);
		return innerScope.getAddress();
	}
	
	public int generateObjectCode(int address, OutputStream output) throws IOException {
		address = instruction instanceof Org ? ((Org)instruction).getAddress() : address;
		if (address != innerScope.getAddress())
			throw new AssemblyException("Address changed between passes.");
		
		byte[] object = getBytes();
		output.write(object, 0, object.length);
		
		return address + object.length;
	}
	
	public int getSize() {
		if (instruction == null)
			return 0;
		return instruction.getSize(innerScope);
	}
	
	public byte[] getBytes() {
		if (instruction == null)
			return NO_BYTES;
		return instruction.getBytes(innerScope);
	}
	
	public String toString() {
		return (label != null ? label + ":" : "") +
			(mnemonic != null ? (label != null ? " " : "\t") + mnemonic + (arguments != null ? " " + arguments : "") : "") +
			(comment != null ? (label != null || mnemonic != null ? " ;" : ";") + comment : "");
	}
	
}
