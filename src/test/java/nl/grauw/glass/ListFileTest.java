package nl.grauw.glass;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ListFileTest extends TestBase {

	@Test
	public void testSimple() {
		assertIterableEquals(
			s(
				"# source: null",
				"   1	0000	00				\tnop",
				"   2	0001	FF				\trst 38H",
				"   3	0002	C3	02	00		\tjp $",
				"   4	0005					"
			),
			list(
				"\tnop",
				"\trst 38H",
				"\tjp $"
			)
		);
	}

	@Test
	public void testSection() {
		assertIterableEquals(
			s(
				"# source: null",
				"   1	0000	00				\tnop",
				"   2	0001					\tSECTION s",
				"   6	0001	00				\tnop",
				"   7	0002	FF	C3	03	00	s: ds 4",
				"   3	0002	FF				\trst 38H",
				"   4	0003	C3	03	00		\tjp $",
				"   5	0006					\tENDS",
				"   8	0006	00				\tnop",
				"   9	0007					"
			),
			list(
				"\tnop",
				"\tSECTION s",
				"\trst 38H",
				"\tjp $",
				"\tENDS",
				"\tnop",
				"s: ds 4",
				"\tnop"
			)
		);
	}

	@Test
	public void testInclude() throws IOException {
		Path testInclude = temporaryDirectory.resolve("testInclude.asm");
		Files.write(testInclude, Arrays.asList(
			"\trst 38H",
			"\tjp $"
		));

		assertIterableEquals(
			s(
				"# source: null",
				"   1	0000	00				\tnop",
				"   2	0001					\tINCLUDE \"testInclude.asm\"",
				"# source: " + testInclude,
				"   1	0001	FF				\trst 38H",
				"   2	0002	C3	02	00		\tjp $",
				"   3	0005					",
				"# source: null",
				"   3	0005	00				\tnop",
				"   4	0006					"
			),
			list(
				"\tnop",
				"\tINCLUDE \"testInclude.asm\"",
				"\tnop"
			)
		);
	}

	@TempDir
	static Path temporaryDirectory;

	public List<String> list(String... sourceLines) {
		SourceBuilder sourceBuilder = new SourceBuilder(Arrays.asList(temporaryDirectory));
		Source source = sourceBuilder.parse(new SourceFile(String.join("\n", sourceLines)));
		String list;
		try {
			source.assemble(new Assembler.NullOutputStream());
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				try (PrintStream printStream = new PrintStream(outputStream, false, "UTF-8")) {
					new ListingWriter(new PrintStream(outputStream)).write(source);
				}
				list = outputStream.toString("UTF-8");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return list.isEmpty() ? Collections.emptyList() : Arrays.asList(list.split("\\R"));
	}

}
