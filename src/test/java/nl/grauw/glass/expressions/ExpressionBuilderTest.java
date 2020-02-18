package nl.grauw.glass.expressions;

import static org.junit.jupiter.api.Assertions.*;

import nl.grauw.glass.Line;
import nl.grauw.glass.Parser;
import nl.grauw.glass.Parser.SyntaxError;
import nl.grauw.glass.Scope;
import nl.grauw.glass.SourceFile;
import nl.grauw.glass.TestBase;
import nl.grauw.glass.expressions.ExpressionBuilder.ExpressionError;

import org.junit.jupiter.api.Test;

public class ExpressionBuilderTest extends TestBase {
	
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
	
	@Test
	public void testGrouping3() {
		assertThrows(ExpressionError.class, () -> {
			parse("10H + (15H * (5H - 2H) + 4H");
		});
	}
	
	@Test
	public void testGrouping4() {
		assertThrows(ExpressionError.class, () -> {
			parse("10H + 15H * (5H - 2H)) + 4H");
		});
	}
	
	@Test
	public void testMember() {
		assertEquals("{($).member}", parse("($).member"));
	}
	
	@Test
	public void testMemberCombinedIdentifier() {
		assertEquals("object.member", parse("object.member"));
	}
	
	@Test
	public void testIndex() {
		assertEquals("{({4H, 5H})[0H]}", parse("(4H, 5H)[0H]"));
	}
	
	@Test
	public void testIndexPrecedence() {
		assertEquals("{{$.member}[0H]}", parse("$.member[0]"));
	}
	
	@Test
	public void testTernaryIfElse() {
		assertEquals("{a ? 1H : 2H}", parse("a ? 1H : 2H"));
	}
	
	@Test
	public void testTernaryIfElseNested1() {
		assertEquals("{a ? 1H : {b ? 2H : 3H}}", parse("a ? 1H : b ? 2H : 3H"));
	}
	
	@Test
	public void testTernaryIfElseNested2() {
		assertEquals("{a ? {b ? 1H : 2H} : 3H}", parse("a ? b ? 1H : 2H : 3H"));
	}
	
	@Test
	public void testTernaryIfElseNested3() {
		assertEquals("{a ? {b ? 1H : 2H} : 3H}", parse("a ? b ? 1H : 2H : 3H"));
	}
	
	@Test
	public void testTernaryIfElseNested4() {
		assertEquals("{{ANN 1H}, {{ANN {a ? {b ? {2H + 3H} : {{c < d} ? 4H : {5H - 6H}}} : 7H}}, {e ? 8H : 9H}}}",
				parse("ANN 1H, ANN a ? b ? 2H + 3H : c < d ? 4H : 5H - 6H : 7H, e ? 8H : 9H"));
	}
	
	@Test
	public void testTernaryIfWithoutElse() {
		assertThrows(ExpressionError.class, () -> {
			parse("a ? 1H");
		});
	}
	
	@Test
	public void testTernaryElseWithoutIf() {
		assertThrows(ExpressionError.class, () -> {
			parse("a : b");
		});
	}
	
	@Test
	public void testTernaryIfElseHigherPrecedence() {
		assertEquals("{{a < 1H} ? {x + 1H} : {y + 2H}}", parse("a < 1H ? x + 1H : y + 2H"));
	}
	
	@Test
	public void testTernaryIfElseLowerPrecedence() {
		assertEquals("{0H, {{a ? 1H : 2H}, 3H}}", parse("0H, a ? 1H : 2H, 3H"));
	}
	
	@Test
	public void testTernaryIfElseLowerPrecedenceNegative() {
		assertThrows(ExpressionError.class, () -> {
			parse("a ? 1H, 2H : 3H");
		});
	}
	
	@Test
	public void testTernaryIfElseLowerPrecedenceGroup() {
		assertEquals("{a ? ({1H, 2H}) : 3H}", parse("a ? (1H, 2H) : 3H"));
	}
	
	@Test
	public void testSequence() {
		assertEquals("{a, 1H}", parse("a, 1H"));
	}
	
	@Test
	public void testSequence2() {
		assertEquals("{{a + 1H}, {b + 2H}}", parse("a + 1H, b + 2H"));
	}
	
	@Test
	public void testSequence3() {
		assertEquals("{a, {{1H + 2H}, {3H + 4H}}}", parse("a, 1H + 2H, 3H + 4H"));
	}
	
	@Test
	public void testSequenceInGroup() {
		assertEquals("{1H + {({a, {2H, 3H}}) * b}}", parse("1H + (a, 2H, 3H) * b"));
	}
	
	@Test
	public void testSequenceWithDoubleGroup() {
		assertEquals("{a, {{({10H + 15H}) * ({5H - 2H})} + 4H}}", parse("a, (10H + 15H) * (5H - 2H) + 4H"));
	}
	
	@Test
	public void testAnnotation() {
		assertEquals("{a 1H}", parse("a 1H"));
	}
	
	@Test
	public void testAnnotationTwice() {
		assertEquals("{a {b 1H}}", parse("a b 1H"));
	}
	
	@Test
	public void testAnnotationGroup() {
		assertEquals("{a (1H)}", parse("a (1H)"));
	}
	
	@Test
	public void testAnnotationNot() {
		assertEquals("{a !1H}", parse("a !1H"));
	}
	
	@Test
	public void testAnnotationComplement() {
		assertEquals("{a ~1H}", parse("a ~1H"));
	}
	
	@Test
	public void testAnnotationSubtract() {
		assertEquals("{a {1H - 2H}}", parse("a 1H - 2H"));
	}
	
	@Test
	public void testAnnotationLogicalOr() {
		assertEquals("{a {1H || 2H}}", parse("a 1H || 2H"));
	}
	
	@Test
	public void testAnnotationSequence() {
		assertEquals("{{a 1H}, {b 2H}}", parse("a 1H, b 2H"));
	}
	
	@Test
	public void testAnnotationInGroup() {
		assertEquals("{a {1H || ({b 2H})}}", parse("a 1H || (b 2H)"));
	}
	
	@Test
	public void testAnnotationInTheMiddle() {
		assertThrows(ExpressionError.class, () -> {
			parse("a 1H || b 2H");
		});
	}
	
	@Test
	public void testAnnotationNotAnIdentifier() {
		assertThrows(ExpressionError.class, () -> {
			parse("0 1H");
		});
	}
	
	@Test
	public void testAnnotationNotAnIdentifier2() {
		assertThrows(ExpressionError.class, () -> {
			parse("a 0 1H");
		});
	}
	
	@Test
	public void testAnnotationNoSpace1() {
		assertThrows(SyntaxError.class, () -> {
			parse("a!1H");
		});
	}
	
	@Test
	public void testAnnotationNoSpace2() {
		assertThrows(SyntaxError.class, () -> {
			parse("(x)a");
		});
	}
	
	@Test
	public void testAnnotationNoSpace3() {
		assertThrows(SyntaxError.class, () -> {
			parse("(x)[0]a");
		});
	}
	
	@Test
	public void testMultiline() {
		assertEquals("{a + 1H}", parse("a +\n1H"));
	}
	
	@Test
	public void testMultiline2() {
		assertEquals("{a, 1H}", parse("a, ;xx\n 1H"));
	}
	
	@Test
	public void testMultiline3() {
		assertEquals("a", parse("a \n + 1H"));
	}
	
	@Test
	public void testMultiline4() {
		assertEquals("({a + 1H})", parse("(a\n+ 1H)"));
	}
	
	@Test
	public void testMultiline5() {
		assertEquals("({a, 1H})", parse("(a;xx\n, 1H)"));
	}
	
	@Test
	public void testMultilineLabel() {
		assertThrows(ExpressionError.class, () -> {
			assertEquals(null, parse("a +\ntest: 1H"));
		});
	}
	
	@Test
	public void testIncomplete() {
		assertThrows(SyntaxError.class, () -> {
			assertEquals(null, parse("a,"));
		});
	}
	
	public String parse(String text) {
		Line line = new Parser(new SourceFile(" test " + text)).parse(new Scope());
		return line.getArguments().toDebugString();
	}
	
}
