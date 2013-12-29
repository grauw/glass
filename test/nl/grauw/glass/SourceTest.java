package nl.grauw.glass;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import nl.grauw.glass.instructions.ArgumentException;

import org.junit.Test;

public class SourceTest {
	
	@Test
	public void testNops() {
		assertArrayEquals(b(0x00, 0x00), assemble(
			" nop",
			" nop"
		));
	}
	
	@Test
	public void testJumpSelf() {
		assertArrayEquals(b(0x00, 0xC3, 0x01, 0x00), assemble(
			" nop",
			" jp $"
		));
	}
	
	@Test
	public void testJumpLabelSelf() {
		assertArrayEquals(b(0x00, 0x00, 0xC3, 0x01, 0x00), assemble(
			" nop",
			"label: nop",
			" jp label"
		));
	}
	
	@Test
	public void testJumpLabelBackward() {
		assertArrayEquals(b(0x00, 0x00, 0xC3, 0x01, 0x00), assemble(
			" nop",
			"label: nop",
			" jp label"
		));
	}
	
	@Test
	public void testJumpLabelForward() {
		assertArrayEquals(b(0xC3, 0x03, 0x00), assemble(
			" jp label",
			"label:"
		));
	}
	
	@Test
	public void testEqu() {
		assertArrayEquals(b(0x3E, 0x10), assemble(
			" ld a,label",
			"label: equ 10H"
		));
	}
	
	@Test
	public void testOrg() {
		assertArrayEquals(b(0xC3, 0x32, 0x40, 0x00, 0x18, 0xFD), assemble(
			" jp label",
			"label: org 4032H",
			" nop",
			" jr label"
		));
	}
	
	@Test
	public void testRelativeJumpAssembly() {
		assertArrayEquals(b(0x18, 0x05, 0x28, 0x03, 0x10, 0x01, 0x00), assemble(
			" org 100H",
			" jr label",    // Because uninitialised labels use value 0, these
			" jr z,label",  // would be out of range if they would generate
			" djnz label",  // actual object code in the first pass.
			" nop",
			"label:"
		));
	}
	
	@Test
	public void testIndexDoubleAdd() {
		assertArrayEquals(b(
				0xDD, 0xA6, 0x03,
				0xDD, 0xA6, 0x05,
				0xDD, 0xA6, 0xFD,
				0xDD, 0xA6, 0xFB,
				0xDD, 0xA6, 0x06
			), assemble(
				" and (ix + 1 + 2)",
				" and (ix + 7 - 2)",
				" and (ix - 1 - 2)",
				" and (ix - 7 + 2)",
				" and (ix + 3 * 2)"
			)
		);
	}
	
	@Test
	public void testMacro() {
		assertArrayEquals(b(0x00, 0x00), assemble(
			"test: MACRO",
			" nop",
			" ENDM",
			" test",
			" test"
		));
	}
	
	@Test
	public void testMacroAddress() {
		assertArrayEquals(b(0x00, 0xC3, 0x01, 0x00, 0xC3, 0x04, 0x00), assemble(
			" nop",
			"test: MACRO",
			" jp $",
			" ENDM",
			" test",
			" test"
		));
	}
	
	@Test
	public void testMacroArguments() {
		assertArrayEquals(b(0x3E, 0x10, 0x3E, 0x20), assemble(
			"test: MACRO arg",
			" ld a,arg",
			" ENDM",
			" test 10H",
			" test 20H"
		));
	}
	
	@Test(expected=ArgumentException.class)
	public void testMacroTooManyArguments() {
		assemble(
			"test: MACRO",
			" ENDM",
			" test 10H"
		);
	}
	
	@Test(expected=ArgumentException.class)
	public void testMacroTooFewArguments() {
		assemble(
			"test: MACRO arg",
			" ENDM",
			" test"
		);
	}
	
	@Test(expected=ArgumentException.class)
	public void testMacroNonIdentifierArguments() {
		assemble(
			"test: MACRO (arg)",
			" ENDM"
		);
	}
	
	@Test(expected=AssemblyException.class)
	public void testMacroNoEnd() {
		assemble(
			"test: MACRO"
		);
	}
	
	@Test
	public void testMacroNesting() {
		assertArrayEquals(b(0x3E, 0x11, 0x3E, 0x21), assemble(
			"test: MACRO arg",
			"test2: MACRO arg",
			" ld a,20H + arg",
			" ENDM",
			" ld a,10H + arg",
			" test2 arg",
			" ENDM",
			" test 1H"
		));
	}
	
	@Test
	public void testMacroOuterScope() {
		assertArrayEquals(b(0x3E, 0x11), assemble(
			"test: MACRO arg",
			" ld a,value + arg",
			" ENDM",
			" test 1H",
			"value: equ 10H"
		));
	}
	
	public byte[] assemble(String... sourceLines) {
		StringBuilder builder = new StringBuilder();
		for (String lineText : sourceLines)
			builder.append(lineText).append("\n");
		SourceParser parser = new SourceParser(new ArrayList<File>());
		Source source = parser.parse(new StringReader(builder.toString()), null);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			source.assemble(output);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return output.toByteArray();
	}
	
	public byte[] b(int... values) {
		byte[] bytes = new byte[values.length];
		for (int i = 0; i < values.length; i++)
			bytes[i] = (byte)values[i];
		return bytes;
	}
	
	
}