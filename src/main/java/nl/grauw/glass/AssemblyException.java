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

public class AssemblyException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private File file = null;
	private int line = -1;
	private int column = -1;
	private String text = null;
	
	public AssemblyException() {
		this((Throwable)null);
	}
	
	public AssemblyException(String message) {
		this(message, null);
	}
	
	public AssemblyException(Throwable cause) {
		this("Error during assembly.", null);
	}
	
	public AssemblyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public void setContext(Line line) {
		setContext(line.getSourceFile(), line.getLineNumber(), line.toString());
	}
	
	public void setContext(File file, int line, String text) {
		this.file = this.file == null ? file : this.file;
		this.line = this.line == -1 ? line : this.line;
		this.text = this.text == null ? text : this.text;
	}
	
	public void setContext(File file, int line, String text, int column) {
		setContext(file, line, text);
		this.column = this.column == -1 ? column : this.column;
	}
	
	@Override
	public String getMessage() {
		String message = super.getMessage();
		
		if (line != -1) {
			String prefix = "[at " + file + ":" + line + (column != -1 ? "," + column : "") + "] ";
			String context = prefix + text;
			
			if (column != -1)
				context += "\n" + context.substring(0, column + prefix.length()).replaceAll("[^\t]", " ") + "^";
			
			return message + "\n" + context;
		}
		
		return message;
	}
	
	public String getPlainMessage() {
		return super.getMessage();
	}
	
}
