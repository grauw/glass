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
package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Proc extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	
	private final Source source;
	
	public Proc(Source source) {
		this.source = source;
	}
	
	public List<Line> expand(Line line) {
		Expression arguments = line.getArguments();
		if (!ARGUMENTS.check(arguments))
			throw new ArgumentException();
		
		List<Line> lines = super.expand(line);
		List<Line> lineCopies = source.getLineCopies(line.getScope());
		for (Line lineCopy : lineCopies)
			lineCopy.register(line.getScope());
		for (Line lineCopy : lineCopies)
			lines.addAll(lineCopy.expand());
		return lines;
	}
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		return Empty.EmptyObject.INSTANCE;
	}
	
}
