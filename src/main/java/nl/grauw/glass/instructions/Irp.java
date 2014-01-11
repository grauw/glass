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
import nl.grauw.glass.ParameterScope;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Irp extends Instruction {
	
	private final Source source;
	
	public Irp(Source source) {
		this.source = source;
	}
	
	public List<Line> expand(Line line) {
		Expression arguments = line.getArguments();
		if (arguments == null || !Schema.IDENTIFIER.check(arguments.getElement()))
			throw new ArgumentException();
		
		List<Line> lines = super.expand(line);
		Expression parameter = arguments.getElement();
		for (int i = 0; (arguments = arguments.getNext()) != null; i++) {
			Scope parameterScope = new ParameterScope(line.getScope(), parameter, arguments.getElement());
			
			// set up the number symbol
			line.getScope().addSymbol(Integer.toString(i), parameterScope);
			Line rept = new Line(parameterScope, line);
			rept.setInstruction(Empty.INSTANCE);
			lines.add(rept);  // so that the parameterScope address gets initialised
			
			// copy & expand content
			List<Line> lineCopies = source.getLineCopies(parameterScope);
			for (Line lineCopy : lineCopies)
				lineCopy.register(parameterScope);
			for (Line lineCopy : lineCopies)
				lines.addAll(lineCopy.expand());
		}
		return lines;
	}
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		return new Empty.EmptyObject(context);
	}
	
}
