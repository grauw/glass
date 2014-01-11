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

import java.io.IOException;
import java.io.OutputStream;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class If extends Instruction {
	
	private static Schema ARGUMENTS = new Schema(Schema.INTEGER);
	
	private final Source thenSource;
	private final Source elseSource;
	
	public If(Source thenSource, Source elseSource) {
		this.thenSource = thenSource;
		this.elseSource = elseSource;
	}
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (ARGUMENTS.check(arguments))
			return new IfObject(context, arguments);
		throw new ArgumentException();
	}
	
	public class IfObject extends InstructionObject {
		
		private final Expression argument;
		
		public IfObject(Scope context, Expression argument) {
			super(context);
			this.argument = argument;
		}
		
		@Override
		public int resolve(int address) {
			context.setAddress(address);
			if (argument.getInteger() != 0) {
				thenSource.register();
				thenSource.expand();
				return thenSource.resolve(address);
			} else if (elseSource != null) {
				elseSource.register();
				elseSource.expand();
				return elseSource.resolve(address);
			} else {
				return address;
			}
		}
		
		@Override
		public int getSize() {
			throw new AssemblyException("Not implemented.");
		}
		
		@Override
		public void generateObjectCode(OutputStream output) throws IOException {
			if (argument.getInteger() != 0) {
				thenSource.generateObjectCode(output);
			} else if (elseSource != null) {
				elseSource.generateObjectCode(output);
			}
		}
		
		@Override
		public byte[] getBytes() {
			throw new AssemblyException("Not implemented.");
		}
		
	}
	
}
