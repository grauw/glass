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

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.instructions.ArgumentException;

public class ParameterScope extends Scope {
	
	public ParameterScope(Scope parent, Expression parameters, Expression arguments) {
		super(parent);
		
		while (parameters != null) {
			if (arguments == null)
				throw new ArgumentException("Not enough arguments.");
			Expression parameter = parameters.getElement();
			Expression argument = arguments.getElement();
			
			if (!(parameter instanceof Identifier))
				throw new ArgumentException("Parameter must be an identifier.");
			
			addSymbol(((Identifier)parameter).getName(), argument);
			
			parameters = parameter.getNext();
			arguments = argument.getNext();
		}
		if (arguments != null)
			throw new ArgumentException("Too many arguments.");
	}
	
}
