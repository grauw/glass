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

public class Djnz extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (Djnz_N.ARGUMENTS.check(arguments))
			return new Djnz_N(context, arguments);
		throw new ArgumentException();
	}
	
	public static class Djnz_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private final Scope context;
		private Expression argument;
		
		public Djnz_N(Scope context, Expression arguments) {
			this.context = context;
			this.argument = arguments;
		}
		
		@Override
		public int getSize() {
			return 2;
		}
		
		@Override
		public byte[] getBytes() {
			int offset = argument.getAddress() - (context.getAddress() + 2);
			if (offset < -128 || offset > 127)
				throw new ArgumentException("Jump offset out of range: " + offset);
			return new byte[] { (byte)0x10, (byte)offset };
		}
		
	}
	
}
