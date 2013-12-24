package nl.grauw.asm;

import java.io.File;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.EvaluationException;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry;

public class Line implements Context {
	
	private Label label;
	private Statement statement;
	private Comment comment;
	private final File sourceFile;
	private final int lineNumber;
	
	public Line(File sourceFile, int lineNumber) {
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
	}
	
	public void setLabel(Label label) {
		this.label = label;
	}
	
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
	public Label getLabel() {
		return label;
	}
	
	public Statement getStatement() {
		return statement;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public File getSourceFile() {
		return sourceFile;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	@Override
	public Expression getLabel(String label) {
		throw new EvaluationException("Label not found: " + label);
	}
	
	@Override
	public int getAddress() {
		return 0;
	}
	
	public void resolveInstruction(InstructionRegistry factory) {
		if (statement != null)
			statement.resolveInstruction(factory);
	}
	
	public String toString() {
		return (label != null ? label + ":" : "") +
			(statement != null ? label != null ? " " + statement : "\t" + statement: "") +
			(comment != null ? (label != null || statement != null ? " ;" : ";") + comment : "");
	}
	
}
