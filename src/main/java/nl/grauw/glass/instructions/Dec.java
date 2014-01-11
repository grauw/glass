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

public class Dec extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (Dec_R.ARGUMENTS.check(arguments))
			return new Dec_R(arguments.getElement(0));
		if (Dec_RR.ARGUMENTS.check(arguments))
			return new Dec_RR(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Dec_R extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Dec_R(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize() {
			return indexifyIndirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes() {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)(0x05 | register.get8BitCode() << 3));
		}
		
	}
	
	public static class Dec_RR extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_INDEX);
		
		private Expression argument;
		
		public Dec_RR(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize() {
			return indexifyDirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes() {
			Register register = argument.getRegister();
			return indexifyDirect(register, (byte)(0x0B | register.get16BitCode() << 4));
		}
		
	}
	
}
