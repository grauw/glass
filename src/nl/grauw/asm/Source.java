package nl.grauw.asm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Source {
	
	private final ArrayList<Line> lines = new ArrayList<Line>();
	private final Scope scope;
	
	public Source() {
		scope = new GlobalScope();
	}
	
	public Source(Scope parentScope) {
		scope = new Scope(parentScope);
	}
	
	public Source(Source other) {
		scope = new Scope(other.scope);
		for (Line line : other.lines)
			addLine(new Line(scope, line));
	}
	
	public Scope getScope() {
		return scope;
	}
	
	public Line addLine(Line line) {
		lines.add(line);
		return line;
	}
	
	public void assemble(OutputStream output) throws IOException {
		resolve();
		generateObjectCode(output);
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
