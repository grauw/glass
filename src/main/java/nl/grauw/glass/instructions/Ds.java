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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Ds extends Instruction {
	
	public static Schema ARGUMENTS_N = new Schema(new Schema.IsAnnotation(Schema.INTEGER));
	public static Schema ARGUMENTS_N_N = new Schema(Schema.INTEGER, Schema.INTEGER);
	
	private final List<Section> sections = new ArrayList<>();
	
	public void addSection(Section section) {
		sections.add(section);
	}
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		if (ARGUMENTS_N.check(arguments))
			return new Ds_N_N(context, arguments.getElement(0).getAnnotation(),
					arguments.getElement(0).getAnnotee(), IntegerLiteral.ZERO);
		if (ARGUMENTS_N_N.check(arguments))
			return new Ds_N_N(context, null, arguments.getElement(0), arguments.getElement(1));
		throw new ArgumentException();
	}
	
	public class Ds_N_N extends InstructionObject {
		
		private final boolean virtual;
		private final Expression size;
		private final Expression value;
		
		public Ds_N_N(Scope context, Identifier annotation, Expression size, Expression value) {
			super(context);
			this.virtual = annotation != null && ("virtual".equals(annotation.getName()) || "VIRTUAL".equals(annotation.getName()));
			this.size = size;
			this.value = value;
			
			if (annotation != null && !virtual)
				throw new ArgumentException("Unsupported annotation: " + annotation.getName());
		}
		
		@Override
		public int resolve(Scope context, int address) {
			int innerAddress = address;
			for (Section section : sections)
				innerAddress = section.getSource().resolve(innerAddress);
			return super.resolve(context, address);
		}
		
		@Override
		public int getSize() {
			return size.getInteger();
		}
		
		@Override
		public void generateObjectCode(OutputStream output) throws IOException {
			byte[] bytes = getSectionBytes();
			if (bytes.length > size.getInteger())
				throw new AssemblyException("Section size exceeds space.");
			
			if (virtual)
				return;
			
			output.write(bytes);
			
			byte[] padding = new byte[size.getInteger() - bytes.length];
			Arrays.fill(padding, (byte)value.getInteger());
			
			output.write(padding);
		}
		
		public byte[] getSectionBytes() throws IOException {
			ByteArrayOutputStream sourceByteStream = new ByteArrayOutputStream(size.getInteger());
			for (Section section : sections)
				section.getSource().generateObjectCode(sourceByteStream);
			return sourceByteStream.toByteArray();
		}
		
		@Override
		public byte[] getBytes() {
			if (virtual)
				return new byte[] {};
			byte[] bytes = new byte[size.getInteger()];
			Arrays.fill(bytes, (byte)value.getInteger());
			return bytes;
		}
		
	}
	
}
