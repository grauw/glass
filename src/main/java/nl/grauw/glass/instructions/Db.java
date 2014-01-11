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

import java.util.List;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Db extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (arguments != null)
			return new Db_N(context, arguments.getList());
		throw new ArgumentException();
	}
	
	public static class Db_N extends InstructionObject {
		
		private List<Expression> arguments;
		
		public Db_N(Scope context, List<Expression> arguments) {
			super(context);
			this.arguments = arguments;
		}
		
		@Override
		public int getSize() {
			return arguments.size();
		}
		
		@Override
		public byte[] getBytes() {
			byte[] bytes = new byte[arguments.size()];
			for (int i = 0, length = arguments.size(); i < length; i++)
				bytes[i] = (byte)arguments.get(i).getInteger();
			return bytes;
		}
		
	}
	
}
