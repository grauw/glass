package nl.grauw.glass;

import java.util.ArrayList;
import java.util.Collections;

import nl.grauw.glass.SourceFile.SourceFileSpan;
import nl.grauw.glass.expressions.Expression;

public class LineBuilder {

	private ArrayList<String> labels;
	private String mnemonic;
	private Expression arguments;
	private String comment;

	public void setLabel(String label) {
		if (labels == null)
			labels = new ArrayList<>();
		this.labels.add(label);
	}

	public void setMnemonic(String mnemonic) {
		if (this.mnemonic != null)
			throw new AssemblyException("Mnemonic already set.");
		this.mnemonic = mnemonic;
	}

	public void setArguments(Expression arguments) {
		if (this.mnemonic == null)
			throw new AssemblyException("Mnemonic not set.");
		if (this.arguments != null)
			throw new AssemblyException("Arguments already set.");
		this.arguments = arguments;
	}

	public void setComment(String comment) {
		this.comment = this.comment == null ? comment : this.comment + "\n" + comment;
	}

	public boolean isEmpty() {
		return mnemonic == null;
	}

	public Line getLine(Scope scope, SourceFileSpan sourceSpan) {
		Line line = new Line(scope, labels == null ? Collections.emptyList() : labels, mnemonic, arguments, comment, sourceSpan);

		labels = null;
		mnemonic = null;
		arguments = null;
		comment = null;

		return line;
	}

}
