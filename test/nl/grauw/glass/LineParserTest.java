package nl.grauw.glass;

import static org.junit.Assert.*;

import nl.grauw.glass.LineParser.SyntaxError;
import nl.grauw.glass.expressions.CharacterLiteral;
import nl.grauw.glass.expressions.Expression;

import org.junit.Test;

public class LineParserTest {
	
	@Test
	public void testCharacterLiteral() {
		assertEquals('x', ((CharacterLiteral)parse("'x'")).getCharacter());
	}
	
	@Test
	public void testCharacterLiteralEscape() {
		assertEquals('"', ((CharacterLiteral)parse("'\\\"'")).getCharacter());
	}
	
	@Test(expected=SyntaxError.class)
	public void testCharacterLiteralTooLong() {
		parse("'xx'");
	}
	
	@Test(expected=SyntaxError.class)
	public void testCharacterLiteralTooShort() {
		parse("''");
	}
	
	@Test(expected=SyntaxError.class)
	public void testCharacterLiteralUnclosed() {
		parse("'");
	}
	
	@Test(expected=SyntaxError.class)
	public void testCharacterLiteralUnclosedEscape() {
		parse("'\\");
	}
	
	public Expression parse(String text) {
		Line line = new Line(new Scope(), null, 0);
		new LineParser().parse(" test " + text, line);
		return line.getArguments();
	}
	
}
