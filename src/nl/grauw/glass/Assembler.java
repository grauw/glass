package nl.grauw.glass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Assembler {
	
	private static Assembler instance;
	private final Source source;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: java -jar glass.jar SOURCE [OBJECT] [SYMBOL]");
			System.exit(1);
		}
		
		File sourcePath = null;
		File objectPath = null;
		File symbolPath = null;
		List<File> includePaths = new ArrayList<File>();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-I") && (i + 1) < args.length) {
				includePaths.add(new File(args[++i]));
			} else if (sourcePath == null) {
				sourcePath = new File(args[i]);
			} else if (objectPath == null) {
				objectPath = new File(args[i]);
			} else if (symbolPath == null) {
				symbolPath = new File(args[i]);
			} else {
				throw new AssemblyException("Too many arguments.");
			}
		}
		
		instance = new Assembler(sourcePath, objectPath, symbolPath, includePaths);
		System.out.print(instance.source);
	}
	
	public Assembler(File sourcePath, File objectPath, File symbolPath, List<File> includePaths) {
		source = new SourceParser(includePaths).parse(sourcePath);
		
		try (OutputStream output = objectPath != null ? new FileOutputStream(objectPath) : new NullOutputStream()) {
			source.assemble(output);
			
			if (symbolPath != null) {
				try (PrintStream symbolOutput = new PrintStream(symbolPath)) {
					symbolOutput.print(source.getScope().serializeLabels());
				}
			}
		} catch (IOException e) {
			new RuntimeException(e);
		}
	}
	
	public static class NullOutputStream extends OutputStream {
		public void write(int b) throws IOException {}
		public void write(byte[] b) throws IOException {}
		public void write(byte[] b, int off, int len) throws IOException {}
	}
	
}
