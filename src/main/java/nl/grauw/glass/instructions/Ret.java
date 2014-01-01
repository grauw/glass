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

public class Ret extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Ret_.ARGUMENTS.check(arguments))
			return new Ret_();
		if (Ret_F.ARGUMENTS.check(arguments))
			return new Ret_F(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Ret_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xC9 };
		}
		
	}
	
	public static class Ret_F extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(new Schema.IsFlag());
		
		private Expression argument;
		
		public Ret_F(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)(0xC0 | argument.getFlag().getCode() << 3) };
		}
		
	}
	
}
