package nl.grauw.glass;

import java.util.List;

import nl.grauw.glass.SourceFile.SourceFileSpan;
import nl.grauw.glass.directives.Directive;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.instructions.Empty;
import nl.grauw.glass.instructions.InstructionFactory;
import nl.grauw.glass.instructions.InstructionObject;

public class Line {
	
	private final Scope scope;
	private final String label;
	private final String mnemonic;
	private final Expression arguments;
	private final String comment;
	private final SourceFileSpan sourceSpan;
	
	private InstructionFactory instruction;
	private InstructionObject instructionObject;
	private Directive directive;
	
	public Line(Scope scope, String label, String mnemonic, Expression arguments, String comment, SourceFileSpan sourceSpan) {
		this.scope = scope;
		this.label = label;
		this.mnemonic = mnemonic;
		this.arguments = arguments;
		this.comment = comment;
		this.sourceSpan = sourceSpan;
	}
	
	public Line(Scope scope, Line other) {
		this(scope, other.label, other.mnemonic, other.arguments != null ? other.arguments.copy(scope) : null,
				other.comment, other.sourceSpan);
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
	
	public SourceFileSpan getSourceSpan() {
		return sourceSpan;
	}
	
	public void setDirective(Directive directive) {
		this.directive = directive;
	}
	
	public void setInstruction(InstructionFactory instruction) {
		this.instruction = instruction;
	}
	
	public InstructionFactory getInstruction() {
		if (instruction == null)
			instruction = mnemonic != null ? scope.getSymbol(mnemonic).getInstruction() : Empty.INSTANCE;
		return instruction;
	}
	
	public void register(Scope sourceScope) {
		try {
			directive.register(sourceScope, this);
		} catch (AssemblyException e) {
			e.addContext(sourceSpan);
			throw e;
		}
	}
	
	public void expand(List<Line> lines) {
		try {
			getInstruction().expand(this, lines);
		} catch (AssemblyException e) {
			e.addContext(sourceSpan);
			throw e;
		}
	}
	
	public int resolve(int address) {
		try {
			instructionObject = getInstruction().createObject(scope, arguments);
			return instructionObject.resolve(address);
		} catch (AssemblyException e) {
			e.addContext(sourceSpan);
			throw e;
		}
	}
	
	public int getSize() {
		try {
			return instructionObject.getSize();
		} catch (AssemblyException e) {
			e.addContext(sourceSpan);
			throw e;
		}
	}
	
	public byte[] getBytes() {
		try {
			return instructionObject.getBytes();
		} catch (AssemblyException e) {
			e.addContext(sourceSpan);
			throw e;
		}
	}
	
	public String toString() {
		return (label != null ? label + ":" : "") +
			(mnemonic != null ? (label != null ? " " : "\t") + mnemonic + (arguments != null ? " " + arguments : "") : "") +
			(comment != null ? (label != null || mnemonic != null ? " ;" : ";") + comment : "");
	}
	
}
