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
	
	public Source(Scope scope, Source other) {
		this(scope);
		for (Line line : other.lines)
			addLine(new Line(new Scope(scope), line));
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
	
	public List<Line> getLineCopies(Scope newParent) {
		List<Line> lineCopies = new ArrayList<>();
		for (Line line : lines)
			lineCopies.add(new Line(new Scope(newParent), line));
		return lineCopies;
	}
	
	public Line addLine(Line line) {
		lines.add(line);
		return line;
	}
	
	public void assemble(OutputStream output) throws IOException {
		register();
		expand();
		resolve();
		generateObjectCode(output);
	}
	
	public void register() {
		for (Line line : lines)
			line.register(scope);
	}
	
	public void expand() {
		List<Line> newLines = new ArrayList<>();
		for (Line line : lines)
			newLines.addAll(line.expand());
		lines = newLines;
	}
	
	public int resolve() {
		return resolve(0);
	}
	
	public int resolve(int address) {
		for (Line line : lines)
			address = line.resolve(address);
		return address;
	}
	
	public void generateObjectCode(OutputStream output) throws IOException {
		generateObjectCode(0, output);
	}
	
	public int generateObjectCode(int address, OutputStream output) throws IOException {
		for (Line line : lines)
			address = line.generateObjectCode(address, output);
		return address;
	}
	
	public byte[] generateObjectCode(int address) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {
			generateObjectCode(address, bytes);
		} catch (IOException e) {
			throw new AssemblyException(e);
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
