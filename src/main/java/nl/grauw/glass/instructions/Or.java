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

public class Or extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (Or_R.ARGUMENTS.check(arguments))
			return new Or_R(arguments);
		if (Or_N.ARGUMENTS.check(arguments))
			return new Or_N(arguments);
		throw new ArgumentException();
	}
	
	public static class Or_R extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Or_R(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize() {
			return indexifyIndirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes() {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)(0xB0 | register.get8BitCode()));
		}
		
	}
	
	public static class Or_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Or_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize() {
			return 2;
		}
		
		@Override
		public byte[] getBytes() {
			return new byte[] { (byte)0xF6, (byte)argument.getInteger() };
		}
		
	}
	
}
