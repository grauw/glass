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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import nl.grauw.glass.Scope.SymbolNotFoundException;
import nl.grauw.glass.instructions.ArgumentException;
import nl.grauw.glass.instructions.Error.ErrorDirectiveException;

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
	public void testEquRegister() {
		assertArrayEquals(b(0x78), assemble(
			" ld a,label",
			"label: equ b"
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
	public void testRelativeJumpAssembly() {
		assertArrayEquals(b(0x18, 0x05, 0x28, 0x03, 0x10, 0x01, 0x00), assemble(
			" org 100H",
			" jr label",    // Because uninitialised labels use value 0, these
			" jr z,label",  // would be out of range if they would generate
			" djnz label",  // actual object code in the first pass.
			" nop",
			"label:"
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
	
	@Test
	public void testMacro() {
		assertArrayEquals(b(0x00, 0x00), assemble(
			"test: MACRO",
			" nop",
			" ENDM",
			" test",
			" test"
		));
	}
	
	@Test
	public void testMacroAddress() {
		assertArrayEquals(b(0x00, 0xC3, 0x01, 0x00, 0xC3, 0x04, 0x00), assemble(
			" nop",
			"test: MACRO",
			" jp $",
			" ENDM",
			" test",
			" test"
		));
	}
	
	@Test
	public void testMacroArguments() {
		assertArrayEquals(b(0x3E, 0x10, 0x3E, 0x20), assemble(
			"test: MACRO arg",
			" ld a,arg",
			" ENDM",
			" test 10H",
			" test 20H"
		));
	}
	
	@Test(expected=ArgumentException.class)
	public void testMacroTooManyArguments() {
		assemble(
			"test: MACRO",
			" ENDM",
			" test 10H"
		);
	}
	
	@Test(expected=ArgumentException.class)
	public void testMacroTooFewArguments() {
		assemble(
			"test: MACRO arg",
			" ENDM",
			" test"
		);
	}
	
	@Test(expected=ArgumentException.class)
	public void testMacroNonIdentifierArguments() {
		assemble(
			"test: MACRO (arg)",
			" ENDM"
		);
	}
	
	@Test(expected=AssemblyException.class)
	public void testMacroNoEnd() {
		assemble(
			"test: MACRO"
		);
	}
	
	@Test
	public void testMacroLabels() {
		assertArrayEquals(b(0x00, 0x21, 0x04, 0x00, 0x21, 0x07, 0x00), assemble(
			" nop",
			"test: MACRO",
			" ld hl,test2",
			"test2:",
			" ENDM",
			" test",
			" test"
		));
	}
	
	@Test
	public void testMacroOuterScope() {
		assertArrayEquals(b(0x3E, 0x11), assemble(
			"test: MACRO arg",
			" ld a,value + arg",
			" ENDM",
			" test 1H",
			"value: equ 10H"
		));
	}
	
	@Test
	public void testMacroNesting() {
		assertArrayEquals(b(0x3E, 0x13, 0x3E, 0x23), assemble(
			"test: MACRO arg",
			"test: MACRO arg",
			" ld a,20H + arg + value",
			" ENDM",
			" ld a,10H + arg + value",
			" test arg",
			"value: equ 2",
			" ENDM",
			" test 1H"
		));
	}
	
	@Test
	public void testMacroTwiceWithLocalReferences() {
		assertArrayEquals(b(0x3E, 0x14, 0x3E, 0x23), assemble(
			"test: MACRO arg",
			" ld a,10H + arg + value",
			" test2 arg",
			"value: equ 3",
			" ENDM",
			"test2: MACRO arg",
			" ld a,20H + arg + value",
			"value: equ 2",
			" ENDM",
			" test 1H"
		));
	}
	
	@Test
	public void testMacroClosure() {
		assertArrayEquals(b(0x3E, 0x14, 0x3E, 0x25), assemble(
			"test: MACRO arg",
			" ld a,10H + arg + value",
			" test2 arg",
			"value: equ 3",
			" ENDM",
			"test2: MACRO arg",
			" ld a,20H + arg + value",
			" ENDM",
			"value: equ 4",
			" test 1H"
		));
	}
	
	@Test(expected=SymbolNotFoundException.class)
	public void testMacroUnboundReference() {
		assertArrayEquals(b(0x3E, 0x14, 0x3E, 0x24), assemble(
			"test: MACRO arg",
			" ld a,10H + arg + value",
			" test2 arg",
			"value: equ 3",
			" ENDM",
			"test2: MACRO arg",
			" ld a,20H + arg + value",
			" ENDM",
			" test 1H"
		));
	}
	
	@Test
	public void testMacroLabelsDereference() {
		assertArrayEquals(b(0x11, 0x09, 0x00, 0x21, 0x06, 0x00, 0x21, 0x09, 0x00), assemble(
			" ld de,test.z.test2",
			"test: MACRO",
			" ld hl,test2",
			"test2:",
			" ENDM",
			" test",
			"test.z: test"
		));
	}
	
	@Test(expected=SymbolNotFoundException.class)
	public void testMacroDefinitionDereference() {
		assemble(
			" ld de,test.test2",
			"test: MACRO",
			" ld hl,test2",
			"test2:",
			" ENDM",
			"test.z: test"
		);
	}
	
	@Test
	public void testRept() {
		assertArrayEquals(b(0x00, 0xFF, 0x00, 0xFF, 0x00, 0xFF), assemble(
			" REPT 3",
			" nop",
			" rst 38H",
			" ENDM"
		));
	}
	
	@Test
	public void testReptIndirect() {
		assertArrayEquals(b(0x00, 0x00, 0x00, 0x00), assemble(
			" REPT count",
			" nop",
			" ENDM",
			"count: equ 4"
		));
	}
	
	@Test
	public void testReptParameter() {
		assertArrayEquals(b(0x3E, 0x00, 0x3E, 0x01, 0x3E, 0x02), assemble(
			" REPT 3, ?value",
			" ld a,?value",
			" ENDM"
		));
	}
	
	@Test
	public void testReptParameterStart() {
		assertArrayEquals(b(0x3E, 0x10, 0x3E, 0x11, 0x3E, 0x12), assemble(
			" REPT 3, ?value, 10H",
			" ld a,?value",
			" ENDM"
		));
	}
	
	@Test
	public void testReptParameterStartStep() {
		assertArrayEquals(b(0x3E, 0x10, 0x3E, 0x13, 0x3E, 0x16), assemble(
			" REPT 3, ?value, 10H, 3",
			" ld a,?value",
			" ENDM"
		));
	}
	
	@Test
	public void testReptWithLabel() {
		assertArrayEquals(b(0x00, 0x00, 0x00, 0x21, 0x03, 0x00), assemble(
			"test: REPT 3",
			" nop",
			"test: ENDM",
			" ld hl,test.2.test"
		));
	}
	
	@Test(expected=SymbolNotFoundException.class)
	public void testReptWithLabelNoIndex() {
		assemble(
			" nop",
			"test: REPT 2",
			" nop",
			"test: nop",
			" ENDM",
			" ld hl,test.test"
		);
	}
	
	@Test
	public void testReptNested() {
		assertArrayEquals(b(0x00, 0x01, 0x02, 0x10, 0x11, 0x12), assemble(
			" REPT 2, ?value, 0, 10H",
			" REPT 3, ?value2",
			" db ?value + ?value2",
			" ENDM",
			" ENDM"
		));
	}
	
	@Test
	public void testReptNoBody() {
		assertArrayEquals(b(), assemble(
			" REPT 3",
			" ENDM"
		));
	}
	
	@Test(expected=ArgumentException.class)
	public void testReptNoCount() {
		assemble(
			" REPT",
			" ENDM"
		);
	}
	
	@Test
	public void testIrp() {
		assertArrayEquals(b(0x3E, 0x10, 0xFF, 0x3E, 0x20, 0xFF, 0x3E, 0x30, 0xFF), assemble(
			" IRP ?value, 10H, 20H, 30H",
			" ld a,?value",
			" rst 38H",
			" ENDM"
		));
	}
	
	@Test
	public void testIrpNoValues() {
		assertArrayEquals(b(), assemble(
			" IRP ?value",
			" ld a,?value",
			" ENDM"
		));
	}
	
	@Test
	public void testIrpNoBody() {
		assertArrayEquals(b(), assemble(
			" IRP ?value, 1, 2, 3",
			" ENDM"
		));
	}
	
	@Test
	public void testIrpRegisters() {
		assertArrayEquals(b(0xC5, 0xD5, 0xE5, 0xF5), assemble(
			" IRP ?register, bc, de, hl, af",
			" push ?register",
			" ENDM"
		));
	}
	
	@Test(expected=ArgumentException.class)
	public void testIrpNoIdentifier() {
		assertArrayEquals(b(), assemble(
			" IRP",
			" nop",
			" ENDM"
		));
	}
	
	@Test
	public void testIrpWithLabel() {
		assertArrayEquals(b(0x10, 0x20, 0x30, 0x21, 0x02, 0x00), assemble(
			"test: IRP ?value, 10H, 20H, 30H",
			"test: db ?value",
			" ENDM",
			" ld hl,test.2.test"
		));
	}
	
	@Test(expected=SymbolNotFoundException.class)
	public void testIrpWithLabelNoIndex() {
		assemble(
			" nop",
			"test: IRP ?value, 10H, 20H, 30H",
			" nop",
			"test: nop",
			" ENDM",
			" ld hl,test.test"
		);
	}
	
	@Test
	public void testProc() {
		assertArrayEquals(b(0x21, 0x0A, 0x00, 0xC3, 0x06, 0x00, 0xFF, 0xC3, 0x0A, 0x00, 0xFF), assemble(
			" ld hl,test2.test",
			"test1: PROC",
			" jp test",
			"test: rst 38H",
			" ENDP",
			"test2: PROC",
			" jp test",
			"test: rst 38H",
			" ENDP"
		));
	}
	
	@Test
	public void testIf() {
		assertArrayEquals(b(0x00), assemble(
			" IF 1",
			" nop",
			" ENDIF"
		));
	}
	
	@Test
	public void testIfThen() {
		assertArrayEquals(b(0x00), assemble(
			" IF 1 = 1",
			" nop",
			" ELSE",
			" rst 38H",
			" ENDIF"
		));
	}
	
	@Test
	public void testIfThenElse() {
		assertArrayEquals(b(0xFF), assemble(
			" IF 0 > 1",
			" nop",
			" ELSE",
			" rst 38H",
			" ENDIF"
		));
	}
	
	@Test
	public void testIfInsideRept() {
		assertArrayEquals(b(0xFF, 0x00, 0xFF), assemble(
			" IRP ?test, 00H, 10H, 11H",
			" IF ?test = 10H",
			" nop",
			" ELSE",
			" rst 38H",
			" ENDIF",
			" ENDM"
		));
	}
	
	@Test
	public void testIfWithEqu() {
		assertArrayEquals(b(0xC3, 0x10, 0x00), assemble(
			" IF 1",
			"test: equ 10H",
			" ELSE",
			"test: equ 20H",
			" ENDIF",
			" jp test"
		));
	}
	
	@Test
	public void testIfWithLabel() {
		assertArrayEquals(b(0x22, 0x22, 0xC3, 0x02, 0x00), assemble(
			" IF 0",
			" db 11H",
			"test: ELSE",
			" dw 2222H",
			"test: ENDIF",
			" jp test"
		));
	}
	
	@Test(expected=SymbolNotFoundException.class)
	public void testIfWithEquForward() {
		assemble(
			" jp test",
			" IF 1",
			"test: equ 10H",
			" ENDIF"
		);
	}
	
	@Test(expected=ErrorDirectiveException.class)
	public void testError() {
		assemble(
			" ERROR"
		);
	}
	
	@Test
	public void testErrorWithMessage() {
		try {
			assemble(
				" ERROR \"Test\""
			);
		} catch (ErrorDirectiveException e) {
			assertEquals("Test", e.getPlainMessage());
		} catch (Throwable e) {
			fail();
		}
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
