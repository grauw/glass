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
	private final String label;
	private final String mnemonic;
	private final Expression arguments;
	private final String comment;
	private final File sourceFile;
	private final int lineNumber;
	
	private InstructionObject instructionObject;
	private Directive directive;
	
	public Line(Scope scope, String label, String mnemonic, Expression arguments, String comment, File sourceFile, int lineNumber) {
		this.scope = scope;
		this.label = label;
		this.mnemonic = mnemonic;
		this.arguments = arguments;
		this.comment = comment;
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
	}
	
	public Line(Scope scope, Line other) {
		this(scope, other.label, other.mnemonic, other.arguments != null ? other.arguments.copy(scope) : null,
				other.comment, other.sourceFile, other.lineNumber);
		directive = other.directive;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getMnemonic() {
		return mnemonic;
	}
	
	public Expression getArguments() {
		return arguments;
	}
	
	public String getComment() {
		return comment;
	}
	
	public File getSourceFile() {
		return sourceFile;
	}
	
	public int getLineNumber() {
		return lineNumber;
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
