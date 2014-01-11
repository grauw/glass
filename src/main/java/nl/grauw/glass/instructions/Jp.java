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

public class Jp extends Instruction {
	
	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Jp_F_N.ARGUMENTS.check(arguments))
			return new Jp_F_N(context, arguments.getElement(0), arguments.getElement(1));
		if (Jp_HL.ARGUMENTS.check(arguments) || Jp_HL.ARGUMENTS_ALT.check(arguments))
			return new Jp_HL(context, arguments.getElement(0));
		if (Jp_N.ARGUMENTS.check(arguments))
			return new Jp_N(context, arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Jp_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Jp_N(Scope context, Expression argument) {
			super(context);
			this.argument = argument;
		}
		
		@Override
		public int getSize() {
			return 3;
		}
		
		@Override
		public byte[] getBytes() {
			int address = argument.getAddress();
			return new byte[] { (byte)0xC3, (byte)address, (byte)(address >> 8) };
		}
		
	}
	
	public static class Jp_F_N extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(new Schema.IsFlag(), Schema.DIRECT_N);
		
		private Expression argument1;
		private Expression argument2;
		
		public Jp_F_N(Scope context, Expression argument1, Expression argument2) {
			super(context);
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
			return new byte[] { (byte)(0xC2 | argument1.getFlag().getCode() << 3), (byte)address, (byte)(address >> 8) };
		}
		
	}
	
	public static class Jp_HL extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_HL_IX_IY);
		public static Schema ARGUMENTS_ALT = new Schema(Schema.DIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Jp_HL(Scope context, Expression argument) {
			super(context);
			this.argument = argument;
		}
		
		@Override
		public int getSize() {
			return indexifyDirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes() {
			return indexifyDirect(argument.getRegister(), (byte)0xE9);
		}
		
	}
	
}
