package nl.grauw.asm.parser;

import static org.junit.Assert.*;
import nl.grauw.asm.Line;

import org.junit.Test;

public class ExpressionBuilderTest {

	@Test
	public void test() {
		assertEquals("a", parse("a"));
		assertEquals("{a + 1H}", parse("a + 1H"));
		assertEquals("{{a + 1H} + 2H}", parse("a + 1H + 2H"));
		assertEquals("{a + {1H * 2H}}", parse("a + 1H * 2H"));
		assertEquals("{{a + {1H * 2H}} + b}", parse("a + 1H * 2H + b"));
	}
	
	public String parse(String text) {
		Line line = new LineParser().parse(" test " + text, null, 0);
		return line.getStatement().getArguments().get(0).toDebugString();
	}
	
}
