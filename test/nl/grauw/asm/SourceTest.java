package nl.grauw.asm;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

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
