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
			if (line.getStatement() != null) {
				line.resolveInstruction(instructionFactory);
				if (line.getStatement().getInstruction() == null)
					continue; // throw
				byte[] object = line.getStatement().getInstruction().getBytes(line);
				address += object.length;
			}
		}
	}
	
	public void generateObjectCode(OutputStream output) throws IOException {
		for (Line line : lines) {
			if (line.getStatement() != null && line.getStatement().getInstruction() != null) {
				byte[] object = line.getStatement().getInstruction().getBytes(line);
				output.write(object, 0, object.length);
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
