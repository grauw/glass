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

public class Ex extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (Ex_AF.ARGUMENTS.check(arguments))
			return new Ex_AF();
		if (Ex_DE_HL.ARGUMENTS.check(arguments))
			return new Ex_DE_HL();
		if (Ex_SP.ARGUMENTS.check(arguments))
			return new Ex_SP(arguments.getElement(1));
		throw new ArgumentException();
	}
	
	public static class Ex_AF extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_AF, Schema.DIRECT_AF_);
		
		@Override
		public int getSize() {
			return 1;
		}
		
		@Override
		public byte[] getBytes() {
			return new byte[] { (byte)0x08 };
		}
		
	}
	
	public static class Ex_DE_HL extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_DE, Schema.DIRECT_HL);
		
		@Override
		public int getSize() {
			return 1;
		}
		
		@Override
		public byte[] getBytes() {
			return new byte[] { (byte)0xEB };
		}
		
	}
	
	public static class Ex_SP extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_SP, Schema.DIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Ex_SP(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize() {
			return indexifyDirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes() {
			return indexifyDirect(argument.getRegister(), (byte)0xE3);
		}
		
	}
	
}
