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

import nl.grauw.glass.expressions.Expression;

public class LineBuilder {
	
	private String label;
	private String mnemonic;
	private Expression arguments;
	private String comment;
	
	public void setLabel(String label) {
		if (this.label != null)
			throw new AssemblyException("Label already set.");
		this.label = label;
	}
	
	public void setMnemonic(String mnemonic) {
		if (this.mnemonic != null)
			throw new AssemblyException("Mnemonic already set.");
		this.mnemonic = mnemonic;
	}
	
	public void setArguments(Expression arguments) {
		if (this.arguments != null)
			throw new AssemblyException("Arguments already set.");
		this.arguments = arguments;
	}
	
	public void setComment(String comment) {
		if (this.comment != null)
			throw new AssemblyException("Comment already set.");
		this.comment = comment;
	}
	
	public Line getLine(Scope scope, File sourceFile, int lineNumber) {
		Line line = new Line(scope, label, mnemonic, arguments, comment, sourceFile, lineNumber);
		
		label = null;
		mnemonic = null;
		arguments = null;
		comment = null;
		
		return line;
	}
	
}
