package nl.grauw.asm;

import java.util.ArrayList;

import nl.grauw.asm.instructions.InstructionFactory;

public class Source {
	
	private final ArrayList<Line> lines = new ArrayList<Line>();
	private final InstructionFactory instructionFactory = new InstructionFactory();
	
	public Source() {
	}
	
	public Line addLine(Line line) {
		lines.add(line);
		return line;
	}
	
	public void resolveInstructions() {
		for (Line line : lines)
			line.resolveInstruction(instructionFactory);
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
