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

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Warning extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	public static Schema ARGUMENTS_S = new Schema(Schema.STRING);
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (ARGUMENTS.check(arguments) || ARGUMENTS_S.check(arguments))
			return new Warning_(arguments);
		throw new ArgumentException();
	}
	
	public static class Warning_ extends Empty.EmptyObject {
		
		private final Expression argument;
		
		public Warning_(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public byte[] getBytes() {
			if (argument == null)
				System.out.println("Warning: A warning directive was encountered.");
			else
				System.out.println("Warning: " + argument.getString());
			return super.getBytes();
		}
		
	}
	
}
