package nl.grauw.glass;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class SymbolsTest extends TestBase {

	@Test
	public void testNone() {
		assertIterableEquals(
			s(),
			symbols(
				" nop"
			)
		);
	}

	@Test
	public void testInstruction() {
		assertIterableEquals(
			s(
				"test: equ 0H",
				"test2: equ 1H"
			),
			symbols(
				"test: nop",
				"test2: nop"
			)
		);
	}

	@Test
	public void testEqu() {
		assertIterableEquals(
			s(
				"test: equ 10H",
				"test2: equ -10H"
			),
			symbols(
				"test: equ 10H",
				"test2: equ -10H"
			)
		);
	}

	@Test
	public void testMacro() {
		assertIterableEquals(
			s(
				"Test._size: equ 5H",
				"Test.x: equ 0H",
				"Test.y: equ 4H",
				"test: equ 1H",
				"test._size: equ 6H",
				"test.x: equ 1H",
				"test.y: equ 5H"
			),
			symbols(
				" nop",
				"Test: MACRO",
				"x: dd -1",
				"y: db -1",
				"_size: ENDM",
				"test: Test"
			)
		);
	}

	@Test
	public void testProc() {
		assertIterableEquals(
			s(
				"Test: equ 1H",
				"Test.x: equ 1H",
				"Test.y: equ 3H",
				"Test.z: equ 5H"
			),
			symbols(
				" nop",
				"Test: PROC",
				"x: push ix",
				"y: pop ix",
				"z: ENDP"
			)
		);
	}

	@Test
	public void testRept() {
		assertIterableEquals(
			s(
				"Test: equ 1H",
				"Test.0: equ 1H",
				"Test.0.?value: equ 2H",
				"Test.0.x: equ 1H",
				"Test.0.y: equ 3H",
				"Test.0.z: equ 2H",
				"Test.1: equ 5H",
				"Test.1.?value: equ 6H",
				"Test.1.x: equ 5H",
				"Test.1.y: equ 7H",
				"Test.1.z: equ 6H"
			),
			symbols(
				" nop",
				"Test: REPT 2, ?value, 2, 4",
				"x: push ix",
				"y: pop ix",
				"z: equ ?value",
				" ENDM"
			)
		);
	}

	@Test
	public void testIrp() {
		assertIterableEquals(
			s(
				"Test: equ 1H",
				"Test.0: equ 1H",
				"Test.0.?value: equ 5H",
				"Test.0.x: equ 1H",
				"Test.0.y: equ 3H",
				"Test.0.z: equ 5H",
				"Test.1: equ 5H",
				"Test.1.?value: equ 7H",
				"Test.1.x: equ 5H",
				"Test.1.y: equ 7H",
				"Test.1.z: equ 7H"
			),
			symbols(
				" nop",
				"Test: IRP ?value, 5, 7",
				"x: push ix",
				"y: pop ix",
				"z: equ ?value",
				" ENDM"
			)
		);
	}

	@Test
	public void testIf() {
		assertIterableEquals(
			s(
				"Test: equ 1H",
				"x: equ 1H",
				"z: equ 4H"
			),
			symbols(
				" nop",
				"Test: IF 1",
				"x: ld hl,$",
				"z: ELSE",
				"y: pop ix",
				"z: ENDIF"
			)
		);
	}

	@Test
	public void testSection() {
		assertIterableEquals(
			s(
				"ROM: equ 1H",
				"x: equ 1H",
				"y: equ 4H",
				"z: equ 9H"
			),
			symbols(
				" nop",
				"ROM: ds 8H",
				"z: nop",
				" SECTION ROM",
				"x: ld hl,$",
				"y: ld hl,$",
				" ENDS"
			)
		);
	}

	@Test
	public void testInclude() throws IOException {
		Files.write(temporaryDirectory.resolve("testInclude.asm"), Arrays.asList(
			"test2: push ix",
			"test3: pop ix"
		));

		assertIterableEquals(
			s(
				"test1: equ 1H",
				"test2: equ 1H",
				"test3: equ 3H",
				"test4: equ 5H"
			),
			symbols(
				" nop",
				"test1: include \"testInclude.asm\"",
				"test4:"
			)
		);
	}

	@Test
	public void testCycle() {
		assertIterableEquals(
			s(
				"Test: equ 1H",
				"Test.x: equ 7H",
				"Test.y: equ 1H",
				"Test.z: equ 2H"
			),
			symbols(
				" nop",
				"Test: PROC",
				"x: equ 7",
				"y: equ Test",
				"   nop",
				"z: ENDP"
			)
		);
	}

	@TempDir
	static Path temporaryDirectory;

	public List<String> symbols(String... sourceLines) {
		SourceBuilder sourceBuilder = new SourceBuilder(Arrays.asList(temporaryDirectory));
		Source source = sourceBuilder.parse(new SourceFile(String.join("\n", sourceLines)));
		try {
			source.assemble(new Assembler.NullOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String symbols = source.getScope().serializeSymbols();
		return symbols.isEmpty() ? Collections.emptyList() : Arrays.asList(symbols.split("\\R"));
	}

}
