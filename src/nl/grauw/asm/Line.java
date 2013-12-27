package nl.grauw.asm;

import java.io.File;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.EvaluationException;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry;

public class Line implements Context {
	
	private final File sourceFile;
	private final int lineNumber;
	private Label label;
	private Statement statement;
	private Comment comment;
	
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
	
	public Statement getStatement() {
		return statement;
	}
	
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public void setComment(Comment comment) {
		this.comment = comment;
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
	
	public byte[] getBytes() {
		return statement.getInstruction().getBytes(this);
	}
	
	public String toString() {
		return (label != null ? label + ":" : "") +
			(statement != null ? label != null ? " " + statement : "\t" + statement: "") +
			(comment != null ? (label != null || statement != null ? " ;" : ";") + comment : "");
	}
	
}
