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

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.expressions.IntegerLiteral;

public class Macro extends Instruction {
	
	private final Source source;
	private final Scope parameterScope;
	
	public Macro(Source source) {
		this.source = new Source(source.getScope());
		this.parameterScope = new Scope(source.getScope());
		this.source.addLines(source.getLineCopies(parameterScope));
		this.source.register();
	}
	
	@Override
	public List<Line> expand(Line line) {
		Expression parameters = line.getArguments();
		while (parameters != null) {
			Expression parameter = parameters.getElement();
			if (!(parameter instanceof Identifier))
				throw new ArgumentException("Parameter must be an identifier.");
			parameterScope.addSymbol(((Identifier)parameter).getName(), IntegerLiteral.ZERO);
			parameters = parameters.getNext();
		}
		
		try {
			source.expand();
		} catch (AssemblyException e) {
			// ignore
		}
		return super.expand(line);
	}
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		return new MacroObject();
	}
	
	public class MacroObject extends Empty.EmptyObject {
		
		@Override
		public int resolve(Scope context, int address) {
			try {
				source.resolve(0);
			} catch (AssemblyException e) {
				// ignore
			}
			return super.resolve(context, address);
		}
		
	}
	
}
