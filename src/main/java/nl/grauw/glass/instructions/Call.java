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

public class Call extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (Call_F_N.ARGUMENTS.check(arguments))
			return new Call_F_N(arguments.getElement(0), arguments.getElement(1));
		if (Call_N.ARGUMENTS.check(arguments))
			return new Call_N(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Call_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Call_N(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize() {
			return 3;
		}
		
		@Override
		public byte[] getBytes() {
			int address = argument.getAddress();
			return new byte[] { (byte)0xCD, (byte)address, (byte)(address >> 8) };
		}
		
	}
	
	public static class Call_F_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(new Schema.IsFlag(), Schema.DIRECT_N);
		
		private Expression argument1;
		private Expression argument2;
		
		public Call_F_N(Expression argument1, Expression argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize() {
			return 3;
		}
		
		@Override
		public byte[] getBytes() {
			int address = argument2.getAddress();
			return new byte[] { (byte)(0xC4 | argument1.getFlag().getCode() << 3), (byte)address, (byte)(address >> 8) };
		}
		
	}
	
}
