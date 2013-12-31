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
	
	public List<Line> getLineCopies(Scope newParent) {
		List<Line> lineCopies = new ArrayList<>();
		for (Line line : lines)
			lineCopies.add(new Line(new Scope(newParent), line));
		return lineCopies;
	}
	
	public Line addLine(Line line) {
		lines.add(line);
		line.register(scope);
		return line;
	}
	
	public void assemble(OutputStream output) throws IOException {
		expand();
		resolve();
		generateObjectCode(output);
	}
	
	public void expand() {
		List<Line> newLines = new ArrayList<>();
		for (Line line : lines) {
			try {
				newLines.addAll(line.expand());
			} catch (AssemblyException e) {
				e.setContext(line);
				throw e;
			}
		}
		lines = newLines;
	}
	
	public int resolve() {
		return resolve(0);
	}
	
	public int resolve(int address) {
		for (Line line : lines) {
			try {
				address = line.resolve(address);
			} catch (AssemblyException e) {
				e.setContext(line);
				throw e;
			}
		}
		return address;
	}
	
	public void generateObjectCode(OutputStream output) throws IOException {
		generateObjectCode(0, output);
	}
	
	public void generateObjectCode(int address, OutputStream output) throws IOException {
		for (Line line : lines) {
			try {
				address = line.generateObjectCode(address, output);
			} catch (AssemblyException e) {
				e.setContext(line);
				throw e;
			}
		}
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
