package nl.grauw.asm.parser;

import static org.junit.Assert.*;
import nl.grauw.asm.Line;
import nl.grauw.asm.parser.LineParser.SyntaxError;

import org.junit.Test;

public class ExpressionBuilderTest {

	@Test
	public void testSingleValue() {
		assertEquals("a", parse("a"));
	}
	
	@Test
	public void testAddition() {
		assertEquals("{a + 1H}", parse("a + 1H"));
	}
	
	@Test
	public void testAddition2() {
		assertEquals("{{a + 1H} + 2H}", parse("a + 1H + 2H"));
	}
	
	@Test
	public void testPrecedence() {
		assertEquals("{a + {1H * 2H}}", parse("a + 1H * 2H"));
	}
	
	@Test
	public void testPrecedence2() {
		assertEquals("{{a + {1H * 2H}} + b}", parse("a + 1H * 2H + b"));
	}
	
	@Test
	public void testGrouping() {
		assertEquals("{a + {({1H + 2H}) * 3H}}", parse("a + (1H + 2H) * 3H"));
	}
	
	@Test
	public void testGrouping2() {
		assertEquals("{{10H + ({15H * ({5H - 2H})})} + 4H}", parse("10H + (15H * (5H - 2H)) + 4H"));
	}
	
	@Test(expected=SyntaxError.class)
	public void testGrouping3() {
		parse("10H + (15H * (5H - 2H) + 4H");
	}
	
	@Test(expected=SyntaxError.class)
	public void testGrouping4() {
		parse("10H + 15H * (5H - 2H)) + 4H");
	}
	
	@Test
	public void testSequence() {
		assertEquals("{a, {1H}}", parse("a, 1H"));
	}
	
	@Test
	public void testSequence2() {
		assertEquals("{{a + 1H}, {{b + 2H}}}", parse("a + 1H, b + 2H"));
	}
	
	@Test
	public void testSequence3() {
		assertEquals("{a, {{1H + 2H}, {{3H + 4H}}}}", parse("a, 1H + 2H, 3H + 4H"));
	}
	
	@Test
	public void testSequenceInGroup() {
		assertEquals("{1H + {({a, {2H, {3H}}}) * b}}", parse("1H + (a, 2H, 3H) * b"));
	}
	
	@Test
	public void testSequenceWithDoubleGroup() {
		assertEquals("{a, {{{({10H + 15H}) * ({5H - 2H})} + 4H}}}", parse("a, (10H + 15H) * (5H - 2H) + 4H"));
	}
	
	public String parse(String text) {
		Line line = new LineParser().parse(" test " + text, null, 0);
		return line.getStatement().getArguments().get(0).toDebugString();
	}
	
}
