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

public class In extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (In_N_C.ARGUMENTS.check(arguments))
			return new In_N_C(arguments.getElement(0));
		if (In_N_C.ARGUMENTS_NO_R.check(arguments))
			return new In_N_C(Register.HL);
		if (In_N_N.ARGUMENTS.check(arguments))
			return new In_N_N(arguments.getElement(1));
		throw new ArgumentException();
	}
	
	public static class In_N_C extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R, Schema.INDIRECT_C);
		public static Schema ARGUMENTS_NO_R = new Schema(Schema.INDIRECT_C);
		
		private Expression argument;
		
		public In_N_C(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize() {
			return 2;
		}
		
		@Override
		public byte[] getBytes() {
			return new byte[] { (byte)0xED, (byte)(0x40 | argument.getRegister().get8BitCode() << 3) };
		}
		
	}
	
	public static class In_N_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.INDIRECT_N);
		
		private Expression argument;
		
		public In_N_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize() {
			return 2;
		}
		
		@Override
		public byte[] getBytes() {
			return new byte[] { (byte)0xDB, (byte)argument.getInteger() };
		}
		
	}
	
}
