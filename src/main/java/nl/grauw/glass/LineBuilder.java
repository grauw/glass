package nl.grauw.glass;

import nl.grauw.glass.SourceFile.SourceFileSpan;
import nl.grauw.glass.expressions.Expression;

public class LineBuilder {
	
	private String label;
	private String mnemonic;
	private Expression arguments;
	private String comment;
	
	public void setLabel(String label) {
		if (this.label != null)
			throw new AssemblyException("Label already set.");
		this.label = label;
	}
	
	public void setMnemonic(String mnemonic) {
		if (this.mnemonic != null)
			throw new AssemblyException("Mnemonic already set.");
		this.mnemonic = mnemonic;
	}
	
	public void setArguments(Expression arguments) {
		if (this.arguments != null)
			throw new AssemblyException("Arguments already set.");
		this.arguments = arguments;
	}
	
	public void setComment(String comment) {
		this.comment = this.comment == null ? comment : this.comment + "\n" + comment;
	}

	public boolean isEmpty() {
		return label == null && mnemonic == null && arguments == null && comment == null;
	}
	
	public Line getLine(Scope scope, SourceFileSpan sourceSpan) {
		Line line = new Line(scope, label, mnemonic, arguments, comment, sourceSpan);
		
		label = null;
		mnemonic = null;
		arguments = null;
		comment = null;
		
		return line;
	}
	
}
