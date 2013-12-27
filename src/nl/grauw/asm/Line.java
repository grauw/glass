package nl.grauw.asm;

import java.io.File;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.IntegerLiteral;
import nl.grauw.asm.instructions.InstructionRegistry;

public class Line implements Context {
	
	private final File sourceFile;
	private final int lineNumber;
	private Label label;
	private Statement statement;
	private Comment comment;
	private Scope scope;
	private int address;
	
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
	
	public Scope getScope() {
		return scope;
	}
	
	@Override
	public Expression getLabel(String label) {
		return scope.getLabel(label);
	}
	
	@Override
	public int getAddress() {
		return address;
	}
	
	public void setScopeAndAddress(Scope scope, int address) {
		this.scope = scope;
		this.address = address;
		if (label != null)
			scope.addLabel(label.getName(), new IntegerLiteral(address));
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
