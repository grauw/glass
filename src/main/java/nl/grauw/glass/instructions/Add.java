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

public class Add extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (Add_A_R.ARGUMENTS.check(arguments))
			return new Add_A_R(arguments.getElement(1));
		if (Add_A_N.ARGUMENTS.check(arguments))
			return new Add_A_N(arguments.getElement(1));
		if (Add_HL_RR.ARGUMENTS.check(arguments))
			return new Add_HL_RR(arguments.getElement(0).getRegister(), arguments.getElement(1).getRegister());
		throw new ArgumentException();
	}
	
	public static class Add_A_R extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Add_A_R(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)(0x80 | register.get8BitCode()));
		}
		
	}
	
	public static class Add_A_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_N);
		
		private Expression argument;
		
		public Add_A_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xC6, (byte)argument.getInteger() };
		}
		
	}
	
	public static class Add_HL_RR extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL_IX_IY, Schema.DIRECT_RR_INDEX);
		
		private Register register1;
		private Register register2;
		
		public Add_HL_RR(Register register1, Register register2) {
			this.register1 = register1;
			this.register2 = register2;
			
			if (register1.get16BitCode() == register2.get16BitCode() && register1 != register2)
				throw new ArgumentException();
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyDirect(register1.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return indexifyDirect(register1.getRegister(), (byte)(0x09 | register2.getRegister().get16BitCode() << 4));
		}
		
	}
	
}
