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
	public void testMacroLabels() {
		assertArrayEquals(b(0x00, 0x21, 0x04, 0x00, 0x21, 0x07, 0x00), assemble(
			" nop",
			"test: MACRO",
			" ld hl,test2",
			"test2:",
			" ENDM",
			" test",
			" test"
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
	
	@Test
	public void testMacroNesting() {
		assertArrayEquals(b(0x3E, 0x13, 0x3E, 0x23), assemble(
			"test: MACRO arg",
			"test: MACRO arg",
			" ld a,20H + arg + value",
			" ENDM",
			" ld a,10H + arg + value",
			" test arg",
			"value: equ 2",
			" ENDM",
			" test 1H"
		));
	}
	
	@Test
	public void testMacroTwiceWithLocalReferences() {
		assertArrayEquals(b(0x3E, 0x14, 0x3E, 0x23), assemble(
			"test: MACRO arg",
			" ld a,10H + arg + value",
			" test2 arg",
			"value: equ 3",
			" ENDM",
			"test2: MACRO arg",
			" ld a,20H + arg + value",
			"value: equ 2",
			" ENDM",
			" test 1H"
		));
	}
	
	@Test
	public void testMacroUnboundReference() {
		assertArrayEquals(b(0x3E, 0x14, 0x3E, 0x24), assemble(
			"test: MACRO arg",
			" ld a,10H + arg + value",
			" test2 arg",
			"value: equ 3",
			" ENDM",
			"test2: MACRO arg",
			" ld a,20H + arg + value",
			" ENDM",
			" test 1H"
		));
	}
	
	@Test
	public void testMacroLabelsDereference() {
		assertArrayEquals(b(0x11, 0x09, 0x00, 0x21, 0x06, 0x00, 0x21, 0x09, 0x00), assemble(
			" ld de,test.z.test2",
			"test: MACRO",
			" ld hl,test2",
			"test2:",
			" ENDM",
			" test",
			"test.z: test"
		));
	}
	
	@Test
	public void testRept() {
		assertArrayEquals(b(0x00, 0xFF, 0x00, 0xFF, 0x00, 0xFF), assemble(
			" REPT 3",
			" nop",
			" rst 38H",
			" ENDM"
		));
	}
	
	@Test
	public void testReptIndirect() {
		assertArrayEquals(b(0x00, 0x00, 0x00, 0x00), assemble(
			" REPT count",
			" nop",
			" ENDM",
			"count: equ 4"
		));
	}
	
	@Test
	public void testReptParameter() {
		assertArrayEquals(b(0x3E, 0x00, 0x3E, 0x01, 0x3E, 0x02), assemble(
			" REPT 3, ?value",
			" ld a,?value",
			" ENDM"
		));
	}
	
	@Test
	public void testReptParameterStart() {
		assertArrayEquals(b(0x3E, 0x10, 0x3E, 0x11, 0x3E, 0x12), assemble(
			" REPT 3, ?value, 10H",
			" ld a,?value",
			" ENDM"
		));
	}
	
	@Test
	public void testReptParameterStartStep() {
		assertArrayEquals(b(0x3E, 0x10, 0x3E, 0x13, 0x3E, 0x16), assemble(
			" REPT 3, ?value, 10H, 3",
			" ld a,?value",
			" ENDM"
		));
	}
	
	@Test
	public void testReptWithLabel() {
		assertArrayEquals(b(0x00, 0x00, 0x00, 0x21, 0x02, 0x00), assemble(
			"test: REPT 3",
			"test: nop",
			" ENDM",
			" ld hl,test.2.test"
		));
	}
	
	@Test
	public void testReptNested() {
		assertArrayEquals(b(0x00, 0x01, 0x02, 0x10, 0x11, 0x12), assemble(
			" REPT 2, ?value, 0, 10H",
			" REPT 3, ?value2",
			" db ?value + ?value2",
			" ENDM",
			" ENDM"
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
