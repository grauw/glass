package nl.grauw.glass;

import java.io.File;
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
			System.out.println("Usage: java -jar glass.jar SOURCE [OBJECT]");
			System.exit(1);
		}
		
		File sourcePath = null;
		File objectPath = null;
		List<File> includePaths = new ArrayList<File>();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-I") && (i + 1) < args.length) {
				includePaths.add(new File(args[++i]));
			} else if (sourcePath == null) {
				sourcePath = new File(args[i]);
			} else if (objectPath == null) {
				objectPath = new File(args[i]);
			} else {
				throw new AssemblyException("Too many arguments.");
			}
		}
		
		instance = new Assembler(sourcePath, objectPath, includePaths);
		System.out.print(instance.source);
	}
	
	public Assembler(File sourcePath, File objectPath, List<File> includePaths) {
		source = new SourceParser(includePaths).parse(sourcePath);
		source.expand();
		source.resolve();
	}
	
}
