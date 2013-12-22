package nl.grauw.asm;

import java.util.ArrayList;

public class Source {
	
	private ArrayList<Line> lines = new ArrayList<Line>();
	
	public Source() {
	}
	
	public Line addLine(Line line) {
		lines.add(line);
		return line;
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
