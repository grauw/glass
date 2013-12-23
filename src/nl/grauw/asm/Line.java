package nl.grauw.asm;

import java.io.File;

import nl.grauw.asm.instructions.InstructionRegistry;

public class Line {
	
	private final Label label;
	private final Statement statement;
	private final Comment comment;
	private final File sourceFile;
	private final int lineNumber;
	
	public Line(Label label, Statement statement, Comment comment, File sourceFile, int lineNumber) {
		this.label = label;
		this.statement = statement;
		this.comment = comment;
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
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
