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
package nl.grauw.glass.directives;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.expressions.Schema;
import nl.grauw.glass.expressions.SectionContextLiteral;
import nl.grauw.glass.instructions.ArgumentException;
import nl.grauw.glass.instructions.Ds;

public class Section extends Directive {
	
	public static Schema ARGUMENTS = new Schema(Schema.IDENTIFIER);
	
	private final Source source;
	
	public Section(Source source) {
		this.source = source;
	}
	
	@Override
	public void register(Scope scope, Line line) {
		if (!ARGUMENTS.check(line.getArguments()))
			throw new ArgumentException();
		
		Expression argument = ((Identifier)line.getArguments()).resolve();
		if (!(argument instanceof SectionContextLiteral))
			throw new ArgumentException("Argument does not reference a section context.");
		
		nl.grauw.glass.instructions.Section section = new nl.grauw.glass.instructions.Section(source);
		
		Ds sectionContext = ((SectionContextLiteral)argument).getSectionContext();
		sectionContext.addSection(section);
		
		line.setInstruction(section);
		
		source.register();
		super.register(scope, line);
	}
	
}
