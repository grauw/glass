package nl.grauw.glass;

import static org.junit.jupiter.api.Assertions.*;

import nl.grauw.glass.expressions.CharacterLiteral;
import nl.grauw.glass.expressions.Flag;
import nl.grauw.glass.expressions.IntegerLiteral;

import org.junit.jupiter.api.Test;

public class ParserTest extends TestBase {
	
	@Test
	public void testLabel() {
		Line line = parse("test_label1:");
		assertIterableEquals(s("test_label1"), line.getLabels());
	}
	
	@Test
	public void testLabelNoColon() {
		Line line = parse("test_label1");
		assertIterableEquals(s("test_label1"), line.getLabels());
	}
	
	@Test
	public void testLabelIndented() {
		Line line = parse(" test_label:");
		assertIterableEquals(s("test_label"), line.getLabels());
	}
	
	@Test
	public void testLabelIndentedWithMnemonic() {
		Line line = parse(" test_label:exx");
		assertIterableEquals(s("test_label"), line.getLabels());
		assertEquals("exx", line.getMnemonic());
	}
	
	@Test
	public void testLabelMultiple() {
		Line line = parse("test_label:\n\ntest_label2:\n\texx");
		assertIterableEquals(s("test_label", "test_label2"), line.getLabels());
		assertEquals("exx", line.getMnemonic());
		assertEquals(null, line.getArguments());
		assertEquals(null, line.getComment());
	}
	
	@Test
	public void testMnemonic() {
		assertEquals("exx", parse(" exx").getMnemonic());
	}
	
	@Test
	public void testArguments() {
		assertTrue(parse("\tcp 0H").getArguments() instanceof IntegerLiteral);
	}
	
	@Test
	public void testComment() {
		assertEquals("test comment", parse(";test comment").getComment());
	}
	
	@Test
	public void testParser1() {
		assertEquals("\tEND ;test comment", parse(" ;test comment").toString());
	}
	
	@Test
	public void testParser2() {
		assertEquals("test_label1:\n\tEND ;test", parse("test_label1:;test").toString());
	}
	
	@Test
	public void testParser3() {
		assertEquals("test_label1:\n\tEND ;test", parse("test_label1;test").toString());
	}
	
	@Test
	public void testParser4() {
		assertEquals("test_label1:\n\texx ;test", parse("test_label1:exx;test").toString());
	}
	
	@Test
	public void testParser5() {
		assertEquals("test_label1:\n\tpush af ;test", parse("test_label1: push af ;test").toString());
	}
	
	@Test
	public void testParser6() {
		assertEquals("test_label1:\n\tex af, af' ;test", parse("test_label1: ex af,af';test").toString());
	}
	
	@Test
	public void testMultiLineArguments() {
		assertEquals("test_label1:\n\tex af, af' ;test", parse("test_label1: ex af,\naf';test").toString());
	}
	
	@Test
	public void testMultiLineArguments2() {
		assertEquals("test_label1:\n\tex af, af' ;test\ntest2", parse("test_label1: ex af,;test\naf';test2").toString());
	}
	
	@Test
	public void testMultiLineArguments3() {
		assertEquals("test_label1:\n\tdb (1H + 2H) ;test", parse("test_label1: db (1 +\n2);test").toString());
	}
	
	@Test
	public void testMultiLineArguments4() {
		assertEquals("test_label1:\n\tdb (1H + 2H) ;test\ntest2", parse("test_label1: db (1;test\n+ 2);test2").toString());
	}
	
	@Test
	public void testCharacterLiteral() {
		assertEquals('x', ((CharacterLiteral)parseExpression("'x'")).getCharacter());
	}
	
	@Test
	public void testCharacterLiteralEscape() {
		assertEquals('"', ((CharacterLiteral)parseExpression("'\\\"'")).getCharacter());
	}
	
	@Test
	public void testCharacterLiteralTooLong() {
		assertSyntaxError(0, 1, 2, () -> {
			parseExpression("'xx'");
		});
	}
	
	@Test
	public void testCharacterLiteralTooShort() {
		assertSyntaxError(0, 1, 1, () -> {
			parseExpression("''");
		});
	}
	
	@Test
	public void testCharacterLiteralUnclosed() {
		assertSyntaxError(0, 1, 1, () -> {
			parseExpression("'");
		});
	}
	
	@Test
	public void testCharacterLiteralUnclosedEscape() {
		assertSyntaxError(0, 1, 2, () -> {
			parseExpression("'\\");
		});
	}
	
	@Test
	public void testHexNumberTooShort() {
		assertSyntaxError(0, 1, 2, () -> {
			parseExpression("0x");
		});
	}
	
	@Test
	public void testHexNumberWrong() {
		assertSyntaxError(0, 1, 3, () -> {
			parseExpression("003x0");
		});
	}
	
	@Test
	public void testHexNumberWrong2() {
		assertSyntaxError(0, 1, 3, () -> {
			parseExpression("0x0x0");
		});
	}
	
	@Test
	public void testHexNumberWrong3() {
		assertSyntaxError(0, 1, 1, () -> {
			parseExpression("3x0");
		});
	}
	
	@Test
	public void testNumber() {
		assertEquals(127, parseExpression("127").getInteger());
		assertEquals(4095, parseExpression("0FFFH").getInteger());
		assertEquals(4095, parseExpression("#0FFF").getInteger());
		assertEquals(4095, parseExpression("$0FFF").getInteger());
		assertEquals(171, parseExpression("10101011B").getInteger());
		assertEquals(171, parseExpression("%10101011").getInteger());
		assertEquals(255, parseExpression("0xFF").getInteger());
		assertEquals(50, parseExpression("0X032").getInteger());
	}
	
	@Test
	public void testFlag() {
		assertEquals(Flag.NZ, parseExpression("nz").getFlag());
		assertEquals(Flag.Z,  parseExpression("z").getFlag());
		assertEquals(Flag.NC, parseExpression("nc").getFlag());
		assertEquals(Flag.C,  parseExpression("c").getFlag());
		assertEquals(Flag.PO, parseExpression("po").getFlag());
		assertEquals(Flag.PE, parseExpression("pe").getFlag());
		assertEquals(Flag.P,  parseExpression("p").getFlag());
		assertEquals(Flag.M,  parseExpression("m").getFlag());
	}
	
	@Test
	public void testFlagNegate() {
		assertEquals(Flag.Z,  parseExpression("!nz").getFlag());
		assertEquals(Flag.NZ, parseExpression("!z").getFlag());
		assertEquals(Flag.C,  parseExpression("!nc").getFlag());
		assertEquals(Flag.NC, parseExpression("!c").getFlag());
		assertEquals(Flag.PE, parseExpression("!po").getFlag());
		assertEquals(Flag.PO, parseExpression("!pe").getFlag());
		assertEquals(Flag.M,  parseExpression("!p").getFlag());
		assertEquals(Flag.P,  parseExpression("!m").getFlag());
	}
	
	public Line parse(String text) {
		return new Parser(new SourceFile(text)).parse(new Scope());
	}
	
}
