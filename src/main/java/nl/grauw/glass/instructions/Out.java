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

public class Out extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Out_C_N.ARGUMENTS.check(arguments))
			return new Out_C_N(arguments.getElement(1));
		if (Out_N_N.ARGUMENTS.check(arguments))
			return new Out_N_N(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Out_C_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_C, Schema.DIRECT_R);
		
		private Expression argument;
		
		public Out_C_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)(0x41 | argument.getRegister().get8BitCode() << 3) };
		}
		
	}
	
	public static class Out_N_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_A);
		
		private Expression argument;
		
		public Out_N_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xD3, (byte)argument.getInteger() };
		}
		
	}
	
}
