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
