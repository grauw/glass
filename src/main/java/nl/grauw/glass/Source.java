/*
 * Copyright 2014 Laurens Holst
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
	
	public List<Line> addLines(List<Line> lines) {
		this.lines.addAll(lines);
		return lines;
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
		for (Line line : lines)
			line.generateObjectCode(output);
	}
	
	public byte[] generateObjectCode() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {
			generateObjectCode(bytes);
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
