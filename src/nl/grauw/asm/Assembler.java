package nl.grauw.asm;

import java.io.File;

public class Assembler {
	
	private static Assembler instance;
	private final Source source;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java -jar asm.jar SOURCE [OBJECT]");
			System.exit(1);
		}
		
		instance = new Assembler(args[0]);
		System.out.print(instance.source);
	}
	
	public Assembler(String path) {
		source = new SourceParser().parse(new File(path));
	}
	
}
