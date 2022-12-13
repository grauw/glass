package nl.grauw.glass;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import nl.grauw.glass.expressions.Add;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Type;

public class Source {

	private final Scope scope;
	private List<Line> lines = new ArrayList<Line>();

	public Source(Scope scope) {
		this.scope = scope;
	}

	public Scope getScope() {
		return scope;
	}

	public List<Line> getLines() {
		return lines;
	}

	public Line getLastLine() {
		return lines.isEmpty() ? null : lines.get(lines.size() - 1);
	}

	public Source copy(Scope scope) {
		Source newSource = new Source(scope);
		for (Line line : lines)
			newSource.addLine(line.copy(new Scope(scope)));
		return newSource;
	}

	public void addLine(Line line) {
		lines.add(line);
	}

	public void addLines(List<Line> lines) {
		this.lines.addAll(lines);
	}

	public void assemble() {
		register();
		expand();
		resolve(IntegerLiteral.ZERO);
	}

	public void register() {
		register(scope);
	}

	public void register(Scope scope) {
		for (Line line : lines)
			line.register(scope);
	}

	public void expand() {
		List<Line> newLines = new ArrayList<>();
		expand(newLines);
		lines = newLines;
	}

	public void expand(List<Line> newLines) {
		for (Line line : lines)
			line.expand(newLines);
	}

	public Expression resolve(Expression address) {
		for (Line line : lines)
			address = line.resolve(address).get(Type.INTEGER);
		return address;
	}

	public Expression getSize() {
		Expression size = IntegerLiteral.ZERO;
		for (Line line : lines)
			size = new Add(line.getSize(), size).get(Type.INTEGER);
		return size;
	}

	public byte[] getBytes() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		for (Line line : lines) {
			bytes.writeBytes(line.getBytes());
		}
		return bytes.toByteArray();
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		for (Line line : lines) {
			string.append(line);
			string.append('\n');
		}
		return string.toString();
	}

}
