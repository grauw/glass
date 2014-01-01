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

import java.io.IOException;
import java.io.OutputStream;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Org extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Org_N.ARGUMENTS.check(arguments))
			return new Org_N(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Org_N extends Empty.EmptyObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.INTEGER);
		
		private Expression argument;
		
		public Org_N(Expression argument) {
			this.argument = argument;
		}
		
		public int getAddress() {
			return argument.getAddress();
		}
		
		@Override
		public int resolve(Scope context, int address) {
			return super.resolve(context, getAddress());
		}
		
		@Override
		public int generateObjectCode(Scope context, int address, OutputStream output) throws IOException {
			return getAddress();
		}
		
	}
	
}
