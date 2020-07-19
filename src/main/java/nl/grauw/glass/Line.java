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
	private final List<String> labels;
	private final String mnemonic;
	private final Expression arguments;
	private final String comment;
	private final SourceFileSpan sourceSpan;

	private InstructionFactory instruction;
	private InstructionObject instructionObject;
	private Directive directive;

	public Line(Scope scope, List<String> labels, String mnemonic, Expression arguments, String comment, SourceFileSpan sourceSpan) {
		if (mnemonic == null)
			throw new AssemblyException("Missing mnemonic.");
		this.scope = scope;
		this.labels = labels;
		this.mnemonic = mnemonic;
		this.arguments = arguments;
		this.comment = comment;
		this.sourceSpan = sourceSpan;
	}

	public Line copy(Scope scope) {
		Line newLine = new Line(scope, labels, mnemonic, arguments != null ? arguments.copy(scope) : null, comment, sourceSpan);
		newLine.setDirective(directive.copy(scope));
		return newLine;
	}

	public Scope getScope() {
		return scope;
	}

	public List<String> getLabels() {
		return labels;
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

	public Expression resolve(Expression address) {
		try {
			instructionObject = getInstruction().createObject(scope, arguments);
			return instructionObject.resolve(address);
		} catch (AssemblyException e) {
			e.addContext(sourceSpan);
			throw e;
		}
	}

	public Expression getSize() {
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
		StringBuilder builder = new StringBuilder();
		for (String label : labels) {
			builder.append(label).append(":\n");
		}
		if (mnemonic != null) {
			builder.append("\t").append(mnemonic);
			if (arguments != null)
				builder.append(" ").append(arguments);
		}
		if (comment != null) {
			if (mnemonic != null)
				builder.append(" ");
			builder.append(";").append(comment);
		}
		return builder.toString();
	}

}
