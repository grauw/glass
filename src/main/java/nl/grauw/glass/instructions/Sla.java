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

public class Sla extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (Sla_R.ARGUMENTS.check(arguments))
			return new Sla_R(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Sla_R extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Sla_R(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize() {
			return indexifyIndirect(argument.getRegister(), 2);
		}
		
		@Override
		public byte[] getBytes() {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)0xCB, (byte)(0x20 + register.get8BitCode()));
		}
		
	}
	
}
