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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import nl.grauw.glass.directives.Directive;
import nl.grauw.glass.directives.Ds;
import nl.grauw.glass.directives.Equ;
import nl.grauw.glass.directives.If;
import nl.grauw.glass.directives.Include;
import nl.grauw.glass.directives.Instruction;
import nl.grauw.glass.directives.Irp;
import nl.grauw.glass.directives.Macro;
import nl.grauw.glass.directives.Proc;
import nl.grauw.glass.directives.Rept;
import nl.grauw.glass.directives.Section;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Sequence;

public class SourceParser {
	
	public static final List<String> END_TERMINATORS = Arrays.asList(new String[] { "end", "END" });
	public static final List<String> ENDM_TERMINATORS = Arrays.asList(new String[] { "endm", "ENDM" });
	public static final List<String> ENDP_TERMINATORS = Arrays.asList(new String[] { "endp", "ENDP" });
	public static final List<String> ENDS_TERMINATORS = Arrays.asList(new String[] { "ends", "ENDS" });
	public static final List<String> ELSE_TERMINATORS = Arrays.asList(new String[] { "else", "ELSE", "endif", "ENDIF" });
	public static final List<String> ENDIF_TERMINATORS = Arrays.asList(new String[] { "endif", "ENDIF" });
	
	private final Source source;
	private final List<String> terminators;
	private final List<File> includePaths;
	private final LineParser lineParser = new LineParser();
	
	public SourceParser(List<File> includePaths) {
		this.source = new Source();
		this.terminators = END_TERMINATORS;
		this.includePaths = includePaths;
	}
	
	public SourceParser(Source source, List<String> terminators, List<File> includePaths) {
		this.source = source;
		this.terminators = terminators;
		this.includePaths = includePaths;
	}
	
	public Source parse(File sourceFile) {
		try {
			parse(new FileInputStream(sourceFile), sourceFile);
		} catch (FileNotFoundException e) {
			throw new AssemblyException(e);
		}
		return source;
	}
	
	private Source parseInclude(File sourceFile, File basePath) {
		File fullPath = new File(basePath.getParent(), sourceFile.getPath());
		if (fullPath.exists())
			return parse(fullPath);
		return parseInclude(sourceFile);
	}
	
	private Source parseInclude(File sourceFile) {
		for (File includePath : includePaths) {
			File fullPath = new File(includePath, sourceFile.getPath());
			if (fullPath.exists())
				return parse(fullPath);
		}
		throw new AssemblyException("Include file not found: " + sourceFile);
	}
	
	public Source parse(InputStream reader, File sourceFile) {
		return parse(new InputStreamReader(reader, Charset.forName("ISO-8859-1")), sourceFile);
	}
	
	public Source parse(Reader reader, File sourceFile) {
		return parse(new LineNumberReader(reader), sourceFile);
	}
	
	private Source parse(LineNumberReader reader, File sourceFile) {
		try {
			String lineText;
			while ((lineText = reader.readLine()) != null) {
				try {
					Line line = lineParser.parse(lineText, new Scope(source.getScope()), sourceFile, reader.getLineNumber());
					line.setDirective(getDirective(line, reader, sourceFile));
					source.addLine(line);
					if (line.getMnemonic() != null && terminators.contains(line.getMnemonic()))
						return source;
				} catch (AssemblyException e) {
					e.setContext(sourceFile, reader.getLineNumber(), lineText);
					throw e;
				}
			}
			if (terminators != END_TERMINATORS)
				throw new AssemblyException("Unexpected end of file. Expecting: " + terminators.toString());
		} catch (IOException e) {
			throw new AssemblyException(e);
		}
		return source;
	}
	
	public Directive getDirective(Line line, LineNumberReader reader, File sourceFile) {
		if (line.getMnemonic() == null)
			return new Instruction();
		
		switch (line.getMnemonic()) {
		case "equ":
		case "EQU":
			return new Equ();
		case "include":
		case "INCLUDE":
			return getIncludeDirective(line, sourceFile);
		case "macro":
		case "MACRO":
			return new Macro(parseBlock(line.getScope(), ENDM_TERMINATORS, reader, sourceFile));
		case "rept":
		case "REPT":
			return new Rept(parseBlock(line.getScope(), ENDM_TERMINATORS, reader, sourceFile));
		case "irp":
		case "IRP":
			return new Irp(parseBlock(line.getScope(), ENDM_TERMINATORS, reader, sourceFile));
		case "proc":
		case "PROC":
			return new Proc(parseBlock(line.getScope(), ENDP_TERMINATORS, reader, sourceFile));
		case "if":
		case "IF":
			Source thenBlock = parseBlock(source.getScope(), ELSE_TERMINATORS, reader, sourceFile);
			Source elseBlock = !ENDIF_TERMINATORS.contains(thenBlock.getLastLine().getMnemonic()) ?
					parseBlock(source.getScope(), ENDIF_TERMINATORS, reader, sourceFile) : null;
			return new If(thenBlock, elseBlock);
		case "section":
		case "SECTION":
			return new Section(parseBlock(source.getScope(), ENDS_TERMINATORS, reader, sourceFile));
		case "ds":
		case "DS":
			return new Ds();
		default:
			return new Instruction();
		}
	}
	
	private Directive getIncludeDirective(Line line, File sourceFile) {
		if (line.getArguments() instanceof Sequence)
			throw new AssemblyException("Include only accepts 1 argument.");
		Expression argument = line.getArguments();
		if (!argument.isString())
			throw new AssemblyException("A string literal is expected.");
		SourceParser parser = new SourceParser(source, END_TERMINATORS, includePaths);
		parser.parseInclude(new File(argument.getString()), sourceFile);
		return new Include();
	}
	
	private Source parseBlock(Scope scope, List<String> terminators, LineNumberReader reader, File sourceFile) {
		return new SourceParser(new Source(scope), terminators, includePaths).parse(reader, sourceFile);
	}
	
}
