package nl.grauw.glass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Source {

	private final Scope scope;
	private List<Line> lines = new ArrayList<Line>();

	public Source() {
		scope = new GlobalScope();
	}

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
		return lines.size() > 0 ? lines.get(lines.size() - 1) : null;
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

	public void assemble(OutputStream output) throws IOException {
		register();
		expand();
		resolve();

		byte[] objectCode = getBytes();
		output.write(objectCode, 0, objectCode.length);
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

	public int resolve() {
		return resolve(0);
	}

	public int resolve(int address) {
		for (Line line : lines)
			address = line.resolve(address);
		return address;
	}

	public byte[] getBytes() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		for (Line line : lines)
		{
			byte[] object = line.getBytes();
			bytes.write(object, 0, object.length);
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
