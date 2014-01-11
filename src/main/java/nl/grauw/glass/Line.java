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
package nl.grauw.glass;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import nl.grauw.glass.directives.Directive;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.instructions.Empty;
import nl.grauw.glass.instructions.Instruction;
import nl.grauw.glass.instructions.InstructionObject;

public class Line {
	
	private final Scope scope;
	private final String label;
	private final String mnemonic;
	private final Expression arguments;
	private final String comment;
	private final File sourceFile;
	private final int lineNumber;
	
	private Instruction instruction;
	private InstructionObject instructionObject;
	private Directive directive;
	
	public Line(Scope scope, String label, String mnemonic, Expression arguments, String comment, File sourceFile, int lineNumber) {
		this.scope = scope;
		this.label = label;
		this.mnemonic = mnemonic;
		this.arguments = arguments;
		this.comment = comment;
		this.sourceFile = sourceFile;
		this.lineNumber = lineNumber;
	}
	
	public Line(Scope scope, Line other) {
		this(scope, other.label, other.mnemonic, other.arguments != null ? other.arguments.copy(scope) : null,
				other.comment, other.sourceFile, other.lineNumber);
		directive = other.directive;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getMnemonic() {
		return mnemonic;
	}
	
	public Expression getArguments() {
		return arguments;
	}
	
	public String getComment() {
		return comment;
	}
	
	public File getSourceFile() {
		return sourceFile;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public void setDirective(Directive directive) {
		this.directive = directive;
	}
	
	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}
	
	public Instruction getInstruction() {
		if (instruction == null)
			instruction = mnemonic != null ? scope.getInstruction(mnemonic) : Empty.INSTANCE;
		return instruction;
	}
	
	public void register(Scope sourceScope) {
		try {
			directive.register(sourceScope, this);
		} catch (AssemblyException e) {
			e.addContext(this);
			throw e;
		}
	}
	
	public List<Line> expand() {
		try {
			return getInstruction().expand(this);
		} catch (AssemblyException e) {
			e.addContext(this);
			throw e;
		}
	}
	
	public int resolve(int address) {
		try {
			instructionObject = getInstruction().createObject(arguments, scope);
			return instructionObject.resolve(scope, address);
		} catch (AssemblyException e) {
			e.addContext(this);
			throw e;
		}
	}
	
	public void generateObjectCode(OutputStream output) throws IOException {
		try {
			instructionObject.generateObjectCode(output);
		} catch (AssemblyException e) {
			e.addContext(this);
			throw e;
		}
	}
	
	public int getSize() {
		return instructionObject.getSize();
	}
	
	public byte[] getBytes() {
		return instructionObject.getBytes();
	}
	
	public String toString() {
		return (label != null ? label + ":" : "") +
			(mnemonic != null ? (label != null ? " " : "\t") + mnemonic + (arguments != null ? " " + arguments : "") : "") +
			(comment != null ? (label != null || mnemonic != null ? " ;" : ";") + comment : "");
	}
	
}
