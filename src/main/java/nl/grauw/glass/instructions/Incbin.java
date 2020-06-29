package nl.grauw.glass.instructions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Incbin extends InstructionFactory {

	private final List<Path> basePaths;

	public Incbin(List<Path> basePaths) {
		this.basePaths = basePaths;
	}

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Incbin_.ARGUMENTS_S.check(arguments))
			return new Incbin_(context, arguments.getElement(0),
					IntegerLiteral.ZERO, null, basePaths);
		if (Incbin_.ARGUMENTS_S_N.check(arguments))
			return new Incbin_(context, arguments.getElement(0),
					arguments.getElement(1), null, basePaths);
		if (Incbin_.ARGUMENTS_S_N_N.check(arguments))
			return new Incbin_(context, arguments.getElement(0),
					arguments.getElement(1), arguments.getElement(2), basePaths);
		throw new ArgumentException();
	}

	public static class Incbin_ extends InstructionObject {

		public static Schema ARGUMENTS_S = new Schema(Schema.STRING);
		public static Schema ARGUMENTS_S_N = new Schema(Schema.STRING, Schema.INTEGER);
		public static Schema ARGUMENTS_S_N_N = new Schema(Schema.STRING, Schema.INTEGER, Schema.INTEGER);

		private final Expression path;
		private final Expression start;
		private final Expression length;
		private final List<Path> basePaths;
		private byte[] bytes;

		public Incbin_(Scope context, Expression path, Expression start, Expression length, List<Path> basePaths) {
			super(context);
			this.path = path;
			this.start = start;
			this.length = length;
			this.basePaths = basePaths;
		}

		@Override
		public Expression getSize() {
			return length != null ? length : IntegerLiteral.of(getBytes().length);
		}

		@Override
		public byte[] getBytes() {
			if (bytes == null) {
				byte[] allBytes = loadFile();

				int from = this.start.getInteger();
				int to = this.length != null ? from + this.length.getInteger() : allBytes.length;
				if (from < 0 || from > allBytes.length)
					throw new AssemblyException("Incbin start exceeds file size.");
				if (to < from || to > allBytes.length)
					throw new AssemblyException("Incbin length exceeds file size.");

				bytes = Arrays.copyOfRange(allBytes, from, to);
			}
			return bytes;
		}

		private byte[] loadFile()
		{
			for (Path basePath : basePaths) {
				Path fullPath = basePath.resolve(path.getString());
				if (Files.exists(fullPath)) {
					try {
						return Files.readAllBytes(fullPath);
					} catch (IOException e) {
						throw new AssemblyException(e);
					}
				}
			}
			throw new AssemblyException("Incbin file not found: " + path.getString());
		}

	}

}
