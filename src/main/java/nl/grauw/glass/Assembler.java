package nl.grauw.glass;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Assembler {

	private static Assembler instance;
	private final Source source;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println(String.format("%s %s by %s",
				Assembler.class.getPackage().getImplementationTitle(),
				Assembler.class.getPackage().getImplementationVersion(),
				Assembler.class.getPackage().getImplementationVendor()
			));
			System.out.println();
			System.out.println("Usage: java -jar glass.jar [OPTION] SOURCE [OBJECT] [SYMBOL]");
			System.exit(1);
		}

		Path sourcePath = null;
		Path objectPath = null;
		Path symbolPath = null;
		Path listPath = null;
		List<Path> includePaths = new ArrayList<Path>();
		Map<String, String> defines = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-I")) {
				if (++i >= args.length)
					throw new AssemblyException("Missing argument value.");
				includePaths.add(Paths.get(args[i]));
			} else if (args[i].equals("-L")) {
				if (++i >= args.length)
					throw new AssemblyException("Missing argument value.");
				listPath = Paths.get(args[i]);
			} else if (args[i].equals("-D")) {
				if (++i >= args.length)
					throw new AssemblyException("Missing define.");
				
				String defineArg = args[i];
				// Check if we have xxx=yyy
				int equalSign = defineArg.indexOf('=');
				if (equalSign > 0) {
					String name = defineArg.substring(0, equalSign);
					String value = defineArg.substring(equalSign+1);
					defines.put(name, value);
				} else {
					// No xxx=yyy, so we must have another argument
					if (++i >= args.length)
						throw new AssemblyException("Missing define value.");
					String defineValue = args[i];
					defines.put(defineArg, defineValue);
				}
			} else if (sourcePath == null) {
				sourcePath = Paths.get(args[i]);
			} else if (objectPath == null) {
				objectPath = Paths.get(args[i]);
			} else if (symbolPath == null) {
				symbolPath = Paths.get(args[i]);
			} else {
				throw new AssemblyException("Too many arguments.");
			}
		}

		instance = new Assembler(sourcePath, includePaths, defines);
		instance.writeObject(objectPath);
		if (symbolPath != null)
			instance.writeSymbols(symbolPath);
		if (listPath != null)
			instance.writeList(listPath);
	}

	public Assembler(Path sourcePath, List<Path> includePaths, Map<String, String> defines) {
		source = new SourceBuilder(includePaths, defines).parse(sourcePath);
	}

	public void writeObject(Path objectPath) {
		try (OutputStream output = objectPath != null ? createBufferedOutputStream(objectPath) : new NullOutputStream()) {
			source.assemble(output);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void writeSymbols(Path symbolPath) {
		try (PrintStream symbolOutput = new PrintStream(createBufferedOutputStream(symbolPath))) {
			symbolOutput.print(source.getScope().serializeSymbols());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void writeList(Path listPath) {
		try (PrintStream output = new PrintStream(createBufferedOutputStream(listPath))) {
			new ListingWriter(output).write(source);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static OutputStream createBufferedOutputStream(Path path) throws IOException {
		return new BufferedOutputStream(Files.newOutputStream(path), 0x10000);
	}

	public static class NullOutputStream extends OutputStream {
		public void write(int b) throws IOException {}
		public void write(byte[] b) throws IOException {}
		public void write(byte[] b, int off, int len) throws IOException {}
	}

}
