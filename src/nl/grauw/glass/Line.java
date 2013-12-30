package nl.grauw.glass;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import nl.grauw.glass.directives.Directive;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.instructions.InstructionObject;
import nl.grauw.glass.instructions.Org.Org_N;

public class Line {
	
	private static final byte[] NO_BYTES = new byte[] {};
	
	private final Scope scope;
	private final File sourceFile;
	private final int lineNumber;
	private String label;
	private String mnemonic;
	private Expression arguments;
	private String comment;
	
	private InstructionObject instructionObject;
	private Directive directive;
	
	public Line(Scope sourceScope, File sourceFile, int lineNumber) {
		this.scope = new Scope(sourceScope);
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
	}
	
	public Line(Scope sourceScope, Line other) {
		this(sourceScope, other.sourceFile, other.lineNumber);
		label = other.label;
		mnemonic = other.mnemonic;
		arguments = other.arguments != null ? other.arguments.copy(scope) : null;
		comment = other.comment;
		directive = other.directive;
	}
	
	public File getSourceFile() {
		return sourceFile;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
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
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	public InstructionObject getInstruction() {
		return instructionObject;
	}
	
	public void setDirective(Directive directive) {
		this.directive = directive;
	}
	
	public void register(Scope sourceScope) {
		directive.register(sourceScope, this);
	}
	
	public int resolve(int address) {
		if (mnemonic != null) {
			instructionObject = scope.getInstruction(mnemonic).createObject(arguments, scope);
			return instructionObject.resolve(scope, address);
		} else {
			scope.setAddress(address);
			return address;
		}
	}
	
	public int generateObjectCode(int address, OutputStream output) throws IOException {
		address = instructionObject instanceof Org_N ? ((Org_N)instructionObject).getAddress() : address;
		if (address != scope.getAddress())
			throw new AssemblyException("Address changed between passes.");
		
		byte[] object = getBytes();
		output.write(object, 0, object.length);
		
		return address + object.length;
	}
	
	public int getSize() {
		if (instructionObject == null)
			return 0;
		return instructionObject.getSize(scope);
	}
	
	public byte[] getBytes() {
		if (instructionObject == null)
			return NO_BYTES;
		return instructionObject.getBytes(scope);
	}
	
	public String toString() {
		return (label != null ? label + ":" : "") +
			(mnemonic != null ? (label != null ? " " : "\t") + mnemonic + (arguments != null ? " " + arguments : "") : "") +
			(comment != null ? (label != null || mnemonic != null ? " ;" : ";") + comment : "");
	}
	
}
