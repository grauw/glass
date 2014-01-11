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

public class Im extends Instruction {
	
	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Im_N.ARGUMENTS.check(arguments))
			return new Im_N(context, arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Im_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Im_N(Scope context, Expression argument) {
			super(context);
			this.argument = argument;
		}
		
		@Override
		public int getSize() {
			return 2;
		}
		
		@Override
		public byte[] getBytes() {
			int value = argument.getInteger();
			if (value == 0) {
				return new byte[] { (byte)0xED, (byte)0x46 };
			} else if (value == 1) {
				return new byte[] { (byte)0xED, (byte)0x56 };
			} else if (value == 2) {
				return new byte[] { (byte)0xED, (byte)0x5E };
			}
			throw new ArgumentException();
		}
		
	}
	
}
