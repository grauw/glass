package nl.grauw.glass.expressions;

import static org.junit.Assert.*;

import java.io.LineNumberReader;
import java.io.StringReader;

import nl.grauw.glass.Line;
import nl.grauw.glass.LineParser;
import nl.grauw.glass.Scope;

import org.junit.Test;

public class ExpressionTest {
	
	@Test
	public void testDivide() {
		assertEquals(3, parse("11 / 3").getInteger());
	}
	
	@Test(expected=EvaluationException.class)
	public void testDivideByZero() {
		parse("1 / 0").getInteger();
	}
	
	@Test
	public void testModulo() {
		assertEquals(2, parse("11 % 3").getInteger());
	}
	
	@Test(expected=EvaluationException.class)
	public void testModuloByZero() {
		parse("1 % 0").getInteger();
	}
	
	public Expression parse(String text) {
		LineNumberReader reader = new LineNumberReader(new StringReader(" test " + text));
		Line line = new LineParser().parse(reader, new Scope(), null);
		return line.getArguments();
	}
	
}
