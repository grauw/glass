/*
 * Copyright 2014 Laurens Holst
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.grauw.glass;

import static org.junit.Assert.*;

import nl.grauw.glass.LineParser.SyntaxError;
import nl.grauw.glass.expressions.CharacterLiteral;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;

import org.junit.Test;

public class LineParserTest {
	
	@Test
	public void testLabel() {
		assertEquals("test_label1", parse("test_label1:").getLabel());
	}
	
	@Test
	public void testLabelNoColon() {
		assertEquals("test_label1", parse("test_label1").getLabel());
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
		assertEquals(";test comment", parse(" ;test comment").toString());
	}
	
	@Test
	public void testParser2() {
		assertEquals("test_label1: ;test", parse("test_label1:;test").toString());
	}
	
	@Test
	public void testParser3() {
		assertEquals("test_label1: ;test", parse("test_label1;test").toString());
	}
	
	@Test
	public void testParser4() {
		assertEquals("test_label1: exx ;test", parse("test_label1:exx;test").toString());
	}
	
	@Test
	public void testParser5() {
		assertEquals("test_label1: push af ;test", parse("test_label1: push af ;test").toString());
	}
	
	@Test
	public void testParser6() {
		assertEquals("test_label1: ex af, af' ;test", parse("test_label1: ex af,af';test").toString());
	}
	
	@Test
	public void testCharacterLiteral() {
		assertEquals('x', ((CharacterLiteral)parseExpression("'x'")).getCharacter());
	}
	
	@Test
	public void testCharacterLiteralEscape() {
		assertEquals('"', ((CharacterLiteral)parseExpression("'\\\"'")).getCharacter());
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
	
	public Line parse(String text) {
		return new LineParser().parse(text, new Scope(), null, 0);
	}
	
	public Expression parseExpression(String text) {
		return parse(" test " + text).getArguments();
	}
	
}
