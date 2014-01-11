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
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Set extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (Set_N_R.ARGUMENTS.check(arguments))
			return new Set_N_R(context, arguments.getElement(0), arguments.getElement(1));
		throw new ArgumentException();
	}
	
	public static class Set_N_R extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument1;
		private Expression argument2;
		
		public Set_N_R(Scope context, Expression argument1, Expression argument2) {
			super(context);
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize() {
			return indexifyIndirect(argument2.getRegister(), 2);
		}
		
		@Override
		public byte[] getBytes() {
			int value = argument1.getInteger();
			if (value < 0 || value > 7)
				throw new ArgumentException();
			Register register = argument2.getRegister();
			return indexifyIndirect(register, (byte)0xCB, (byte)(0xC0 | value << 3 | register.get8BitCode()));
		}
		
	}
	
}
