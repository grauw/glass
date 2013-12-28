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
	
	public void resolve() {
		int address = 0;
		for (Line line : lines) {
			try {
				address = line.resolve(address, instructionFactory);
			} catch (Exception e) {
				throw new RuntimeException("An exception occurred on line " + line.getLineNumber() +
						" of " + line.getSourceFile() + ":\n" + line, e);
			}
		}
	}
	
	public void generateObjectCode(OutputStream output) throws IOException {
		int address = 0;
		for (Line line : lines) {
			try {
				address = line.generateObjectCode(address, output);
			} catch (Exception e) {
				throw new RuntimeException("An exception occurred on line " + line.getLineNumber() +
						" of " + line.getSourceFile() + ":\n" + line, e);
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
