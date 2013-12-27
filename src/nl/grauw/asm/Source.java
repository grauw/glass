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
			line.setScopeAndAddress(scope, address);
			if (line.getMnemonic() != null) {
				line.resolveInstruction(instructionFactory);
				byte[] object = line.getBytes();
				address += object.length;
			}
		}
	}
	
	public void generateObjectCode(OutputStream output) throws IOException {
		for (Line line : lines) {
			byte[] object = line.getBytes();
			output.write(object, 0, object.length);
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
