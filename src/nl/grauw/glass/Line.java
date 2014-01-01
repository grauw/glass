package nl.grauw.glass;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import nl.grauw.glass.directives.Directive;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.instructions.Empty;
import nl.grauw.glass.instructions.Instruction;
import nl.grauw.glass.instructions.InstructionObject;
import nl.grauw.glass.instructions.Org;

public class Line {
	
	private final Scope scope;
	private final String label;
	private final String mnemonic;
	private final Expression arguments;
	private final String comment;
	private final File sourceFile;
	private final int lineNumber;
	
	private Instruction instruction;
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
		instruction = other.instruction;
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
	
	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}
	
	public Instruction getInstruction() {
		if (instruction == null)
			instruction = mnemonic != null ? scope.getInstruction(mnemonic) : Empty.INSTANCE;
		return instruction;
	}
	
	public void register(Scope sourceScope) {
		directive.register(sourceScope, this);
	}
	
	public List<Line> expand() {
		try {
			return getInstruction().expand(this);
		} catch (AssemblyException e) {
			e.setContext(this);
			throw e;
		}
	}
	
	public int resolve(int address) {
		try {
			instructionObject = getInstruction().createObject(arguments, scope);
			return instructionObject.resolve(scope, address);
		} catch (AssemblyException e) {
			e.setContext(this);
			throw e;
		}
	}
	
	public int generateObjectCode(int address, OutputStream output) throws IOException {
		try {
			address = instruction instanceof Org ? scope.getAddress() : address;
			if (address != scope.getAddress())
				throw new AssemblyException("Address changed between passes.");
			
			byte[] object = getBytes();
			output.write(object, 0, object.length);
			
			return address + object.length;
		} catch (AssemblyException e) {
			e.setContext(this);
			throw e;
		}
	}
	
	public int getSize() {
		return instructionObject.getSize(scope);
	}
	
	public byte[] getBytes() {
		return instructionObject.getBytes(scope);
	}
	
	public String toString() {
		return (label != null ? label + ":" : "") +
			(mnemonic != null ? (label != null ? " " : "\t") + mnemonic + (arguments != null ? " " + arguments : "") : "") +
			(comment != null ? (label != null || mnemonic != null ? " ;" : ";") + comment : "");
	}
	
}
