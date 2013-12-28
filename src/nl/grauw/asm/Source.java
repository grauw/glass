package nl.grauw.asm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import nl.grauw.asm.instructions.InstructionRegistry;

public class Source {
	
	private final ArrayList<Line> lines = new ArrayList<Line>();
	private final InstructionRegistry instructionFactory = new InstructionRegistry();
	private final Scope scope = new Scope();
	
	public Scope getScope() {
		return scope;
	}
	
	public Line addLine(Line line) {
		lines.add(line);
		return line;
	}
	
	public InstructionRegistry getInstructionFactory() {
		return instructionFactory;
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
				address = line.resolve(address, instructionFactory);
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
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (Line line : lines) {
			string.append(line);
			string.append('\n');
		}
		return string.toString();
	}
	
}
