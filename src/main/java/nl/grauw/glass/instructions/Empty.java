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

public class Empty extends Instruction {
	
	public static final Empty INSTANCE = new Empty();
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		return EmptyObject.INSTANCE;
	}
	
	public static class EmptyObject extends InstructionObject {
		
		public static final EmptyObject INSTANCE = new EmptyObject();
		private static final byte[] NO_BYTES = new byte[] {};
		
		@Override
		public int getSize(Scope context) {
			return 0;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return NO_BYTES;
		}
		
	}
	
}
