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
	public void testJumpBackwardsLabel() {
		assertArrayEquals(b(0x00, 0x00, 0xC3, 0x01, 0x00), assemble(
			" nop",
			"label: nop",
			" jp label"
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
