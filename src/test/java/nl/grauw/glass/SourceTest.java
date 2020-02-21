package nl.grauw.glass;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class SourceTest extends TestBase {

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
	public void testEquIndirectRegister() {
		assertArrayEquals(b(0x7E), assemble(
			" ld a,label",
			"label: equ (hl)"
		));
	}

	@Test
	public void testOrg() {
		assertArrayEquals(b(0xC3, 0x32, 0x40, 0x00, 0x18, 0xFD), assemble(
			" jp label",
			" org 4032H",
			"label:",
			" nop",
			" jr label"
		));
	}

	@Test
	public void testOrgLabel() {
		assertArrayEquals(b(0xC3, 0x03, 0x00, 0x00), assemble(
			" jp label",
			"label: org 4032H",
			" nop"
		));
	}

	@Test
	public void testOrgLabelBefore() {
		assertArrayEquals(b(0xC3, 0x03, 0x00, 0x00), assemble(
			" jp label",
			"label:",
			" org 4032H",
			" nop"
		));
	}

	@Test
	public void testOrgSelfAddress() {
		assertArrayEquals(b(0xC3, 0x04, 0x01, 0x00), assemble(
			" jp label",
			" org $ + 101H",
			"label:",
			" nop"
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

	@Test
	public void testMacroArgumentsTwo() {
		assertArrayEquals(b(0x3E, 0x30, 0x3E, 0x77), assemble(
			"test: MACRO arg1, arg2",
			" ld a,arg1 + arg2",
			" ENDM",
			" test 10H, 20H",
			" test 33H, 44H"
		));
	}

	@Test
	public void testMacroTooManyArguments() {
		assertArgumentException(2, () -> {
			assemble(
				"test: MACRO",
				" ENDM",
				" test 10H"
			);
		});
	}

	@Test
	public void testMacroTooFewArguments() {
		assertArgumentException(2, () -> {
			assemble(
				"test: MACRO arg",
				" ENDM",
				" test"
			);
		});
	}

	@Test
	public void testMacroNonIdentifierArguments() {
		assertArgumentException(0, () -> {
			assemble(
				"test: MACRO (arg)",
				" ENDM"
			);
		});
	}

	@Test
	public void testMacroDefaultArgument() {
		assertArrayEquals(b(0x3E, 0x10, 0x3E, 0x20), assemble(
			"test: MACRO arg = 10H",
			" ld a,arg",
			" ENDM",
			" test",
			" test 20H"
		));
	}

	@Test
	public void testMacroDefaultFlagArgument() {
		assertArrayEquals(b(0xC8, 0xC0), assemble(
			"test: MACRO arg = z",
			" ret arg",
			" ENDM",
			" test",
			" test nz"
		));
	}

	@Test
	public void testMacroNoEnd() {
		assertAssemblyException(1, () -> {
			assemble(
				"test: MACRO"
			);
		});
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
	public void testMacroNesting2() {
		assertArrayEquals(b(0x1E, 0x1E, 0x1E), assemble(
			"test2: MACRO ?x",
			"x: db ?x * 5",
			" ENDM",
			"test: MACRO arg", // ?x
			"x: equ arg * 2",  // ?x
			" test2 x * 3",
			" ENDM",
			" test 1H",
			" test 1H",
			" test 1H"
		));
	}

	@Test
	public void testMacroNesting3() {
		assertArrayEquals(b(0x02, 0x1E), assemble(
			"test2: MACRO ?x",
			"x: equ ?x * 5",
			" db x",
			" ENDM",
			"test: MACRO ?x = 1",
			"x: equ ?x * 2",
			"inner: test2 x * 3",
			" ENDM",
			" db test.x",
			" db test.inner.x"
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

	@Test
	public void testMacroUnboundReference() {
		assertSymbolNotFoundException("value", 6, () -> {
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
		});
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

	@Test
	public void testMacroDefinitionDereference() {
		assertArrayEquals(b(0x11, 0x03, 0x00), assemble(
			" ld de,test.test2",
			"test: MACRO",
			" ld hl,test2",
			"test2:",
			" ENDM"
		));
	}

	@Test
	public void testMacroDefinitionWithArgumentsDereference() {
		assertArrayEquals(b(0x11, 0x03, 0x00), assemble(
			" ld de,test.test2",
			"test: MACRO arg",
			" ld hl,arg",
			"test2:",
			" ENDM"
		));
	}

	@Test
	public void testMacroDefinitionWithDefaultArgumentsDereference() {
		assertArrayEquals(b(0x11, 0x03, 0x00), assemble(
			" ld de,test.test2",
			"test: MACRO arg = 0",
			" ld hl,arg",
			"test2:",
			" ENDM"
		));
	}

	@Disabled
	@Test
	public void testMacroDefinitionWithNonIntegerArgumentDereference() {
		assertArrayEquals(b(0x11, 0x03, 0x00), assemble(
			" ld de,test.test2",
			"test: MACRO arg1, arg2",
			" ld hl,arg1",
			"test2:",
			" ret arg2",
			" ENDM"
		));
	}

	@Test
	public void testMacroDefinitionWithNonIntegerArgumentDereferenceWorkaround() {
		assertArrayEquals(b(0x11, 0x03, 0x00), assemble(
			" ld de,test.test2",
			"test: MACRO arg1, arg2",
			" ld hl,arg1",
			"test2: equ $",
			" ret arg2",
			" ENDM"
		));
	}

	@Test
	public void testMacroDefinitionWithNonIntegerArgumentBeforeDereference() {
		assertEvaluationException(0, () -> {
			assertArrayEquals(b(0x11, 0x03, 0x00), assemble(
				" ld de,test.test2",
				"test: MACRO arg1, arg2",
				" ld hl,arg1",
				" ret arg2",
				"test2:",
				" ENDM"
			));
		});
	}

	@Test
	public void testMacroDefinitionWithDefaultFlagArgumentBeforeDereference() {
		assertArrayEquals(b(0x11, 0x01, 0x00), assemble(
			" ld de,test.test2",
			"test: MACRO arg = z",
			" ret arg",
			"test2:",
			" ENDM"
		));
	}

	@Test
	public void testMacroContextArgumentDereference() {
		assertArrayEquals(b(0x03), assemble(
			"macro1: MACRO",
			" ld hl,0",
			"test:",
			" ENDM",
			"macro2: MACRO ?arg",
			" db ?arg.test",
			" ENDM",
			" macro2 macro1"
		));
	}

	@Test
	public void testMacroContextArgumentDereference2() {
		assertArrayEquals(b(0x3E, 0x05, 0x21, 0x00, 0x00), assemble(
			"macro1: MACRO",
			" ld hl,0",
			"test:",
			" ENDM",
			"macro2: MACRO ?arg",
			" ld a,?arg.test",
			" ENDM",
			" macro2 (m1)",
			"m1: macro1"
		));
	}

	@Test
	public void testMacroInstructionArgument() {
		assertArrayEquals(b(0x3E, 0x10, 0xC9), assemble(
			"test: MACRO arg",
			" ld a,10H",
			" arg",
			" ENDM",
			" test ret"
		));
	}

	@Test
	public void testMacroInRepeat() {
		assertArrayEquals(b(0x3E, 0x30, 0x3E, 0x32), assemble(
			" REPT 2",
			"test: MACRO arg",
			" ld a,value + value2 + arg",
			"value2: equ 20H",
			" ENDM",
			" test $",
			"value: equ 10H",
			" ENDM"
		));
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
	public void testReptWithIndex() {
		assertArrayEquals(b(0x21, 0x05, 0x00, 0x00, 0x00, 0x00), assemble(
			" ld hl,test.2",
			"test: REPT 3",
			" nop",
			"test: ENDM"
		));
	}

	@Test
	public void testReptWithLabel() {
		assertArrayEquals(b(0x21, 0x06, 0x00, 0x00, 0x00, 0x00), assemble(
			" ld hl,test.2.test",
			"test: REPT 3",
			" nop",
			"test: ENDM"
		));
	}

	@Test
	public void testReptWithLabelNoIndex() {
		assertSymbolNotFoundException("test.test", 5, () -> {
			assemble(
				" nop",
				"test: REPT 2",
				" nop",
				"test: nop",
				" ENDM",
				" ld hl,test.test"
			);
		});
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

	@Test
	public void testReptNoCount() {
		assertArgumentException(0, () -> {
			assemble(
				" REPT",
				" ENDM"
			);
		});
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

	@Test
	public void testIrpNoIdentifier() {
		assertArgumentException(0, () -> {
			assertArrayEquals(b(), assemble(
				" IRP",
				" nop",
				" ENDM"
			));
		});
	}

	@Test
	public void testIrpWithIndex() {
		assertArrayEquals(b(0x21, 0x05, 0x00, 0x10, 0x20, 0x30), assemble(
			" ld hl,test.2",
			"test: IRP ?value, 10H, 20H, 30H",
			"test: db ?value",
			" ENDM"
		));
	}

	@Test
	public void testIrpWithLabel() {
		assertArrayEquals(b(0x21, 0x05, 0x00, 0x10, 0x20, 0x30), assemble(
			" ld hl,test.2.test",
			"test: IRP ?value, 10H, 20H, 30H",
			"test: db ?value",
			" ENDM"
		));
	}

	@Test
	public void testIrpWithLabelNoIndex() {
		assertSymbolNotFoundException("test.test", 5, () -> {
			assemble(
				" nop",
				"test: IRP ?value, 10H, 20H, 30H",
				" nop",
				"test: nop",
				" ENDM",
				" ld hl,test.test"
			);
		});
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
	public void testIfInMacro() {
		assertArrayEquals(b(0x3E, 0x20), assemble(
			" test 10H, 20H",
			"test: MACRO ?test, ?value",
			" IF ?test = 10H",
			" ld a,?value",
			" ELSE",
			" ld b,?value",
			" ENDIF",
			" ENDM"
		));
	}

	@Test
	public void testIfInRepeat() {
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

	@Test
	public void testIfWithAddress() {
		assertArrayEquals(b(0x11, 0x22), assemble(
			" IF $ < 100H",
			" db 11H",
			" ENDIF",
			" db 22H"
		));
	}

	@Test
	public void testIfWithEquForward() {
		assertArrayEquals(b(0xC2, 0x10, 0x00), assemble(
			" jp test2,test",
			" IF 1",
			"test: equ 10H",
			"test2: equ nz",
			" ENDIF"
		));
	}

	@Test
	public void testIfSymbolNotFound() {
		assertSymbolNotFoundException("y", 4, () -> {
			assemble(
				"x: equ y",
				" IF 0",
				"y: equ 1",
				" ENDIF",
				" jp x"
			);
		});
	}

	@Test
	public void testIfInclude() throws IOException {
		Files.write(temporaryDirectory.resolve("testIfInclude.asm"), Arrays.asList(
			"test: IF condition",
			" ld a,a",
			" ENDIF"
		));

		assertArrayEquals(b(0x00, 0x7F, 0xC3, 0x01, 0x00), assemble(
			" nop",
			" IF $ > 100H",
			"condition: equ 0",
			" include \"testIfInclude.asm\"",
			" ELSE",
			"condition: equ 1",
			" include \"testIfInclude.asm\"",
			" ENDIF",
			" jp test"
		));
	}

	@Test
	public void testIfIncludeConstant() throws IOException {
		Files.write(temporaryDirectory.resolve("testIfIncludeConstant.asm"), Arrays.asList(
			"DEBUG: equ 1",
			" nop"
		));

		assertArrayEquals(b(0x00, 0xFF), assemble(
			" include \"testIfIncludeConstant.asm\"",
			" IF DEBUG",
			" rst 38H",
			" ENDIF"
		));
	}

	@Test
	public void testError() {
		assertErrorDirectiveException("Error directive was encountered.", 0, () -> {
			assemble(
				" ERROR"
			);
		});
	}

	@Test
	public void testErrorWithMessage() {
		assertErrorDirectiveException("Test", 0, () -> {
			assemble(
				" ERROR \"Test\""
			);
		});
	}

	@Test
	public void testAnnotationNotSupported() {
		assertArgumentException(0, () -> {
			assemble(
				" or A 0"
			);
		});
	}

	@Test
	public void testDsVirtual() {
		assertArrayEquals(b(0x3E, 0x86, 0x21, 0x12, 0x00), assemble(
			" ld a,86H",
			" ds VIRTUAL 10H",
			" ld hl,$"
		));
	}

	@Test
	public void testDsVirtualWithFill() {
		assertArgumentException(0, () -> {
			assemble(
				" ds VIRTUAL 10H, 0"
			);
		});
	}

	@Test
	public void testDsUnknownAnnotation() {
		assertArgumentException(0, () -> {
			assemble(
				" ds UNKNOWN 10H"
			);
		});
	}

	@Test
	public void testSection() {
		assertArrayEquals(b(0x00, 0x21, 0x07, 0x00, 0x21, 0x04, 0x00, 0x86, 0x86, 0x00, 0x11, 0x07, 0x00), assemble(
			" nop",
			"ROM: ds 8H, 86H",
			" nop",
			" SECTION ROM",
			" ld hl,label",
			" ld hl,$",
			"label: ENDS",
			" ld de,label"
		));
	}

	@Test
	public void testSectionVirtual() {
		assertArrayEquals(b(0x00, 0x00, 0x11, 0x07, 0x00), assemble(
			" nop",
			"RAM: ds VIRTUAL 8H",
			" nop",
			" SECTION RAM",
			" ld hl,label",
			" ld hl,$",
			"label: ENDS",
			" ld de,label"
		));
	}

	@Test
	public void testSectionFitsSpace() {
		assertArrayEquals(b(0x21, 0x00, 0x00), assemble(
			"ROM: ds 3H",
			" SECTION ROM",
			" ld hl,$",
			" ENDS"
		));
	}

	@Test
	public void testSectionExceedsSpace() {
		assertAssemblyException(0, () -> {
			assemble(
				"ROM: ds 2H",
				" SECTION ROM",
				" ld hl,$",
				" ENDS"
			);
		});
	}

	@Test
	public void testSectionIndirect() {
		assertArrayEquals(b(0x21, 0x00, 0x00), assemble(
			"ROM2: ds 3H",
			"ROM: equ ROM2",
			" SECTION ROM",
			" ld hl,$",
			" ENDS"
		));
	}

	@Test
	public void testSectionInRepeat() {
		assertArrayEquals(b(0x21, 0x00, 0x00, 0x21, 0x03, 0x00), assemble(
			" REPT 2",
			"ROM: ds 3H",
			" SECTION ROM",
			" ld hl,$",
			"label:",
			" ENDS",
			" ENDM"
		));
	}

	@Test
	public void testEndm() {
		assertAssemblyException(0, () -> {
			assemble(
				" ENDM"
			);
		});
	}

	@Test
	public void testEndp() {
		assertAssemblyException(0, () -> {
			assemble(
				" ENDP"
			);
		});
	}

	@Test
	public void testEnds() {
		assertAssemblyException(0, () -> {
			assemble(
				" ENDS"
			);
		});
	}

	@Test
	public void testInclude() throws IOException {
		Files.write(temporaryDirectory.resolve("testInclude.asm"), Arrays.asList(
			" ld a,a"
		));

		assertArrayEquals(b(0x00, 0x7F, 0xFF), assemble(
			" nop",
			" include \"testInclude.asm\"",
			" rst 38H"
		));
	}

	@Test
	public void testIncludeLabel() throws IOException {
		Files.write(temporaryDirectory.resolve("testIncludeLabel.asm"), Arrays.asList(
			"test: ld a,a"
		));

		assertArrayEquals(b(0x00, 0x7F, 0xC3, 0x01, 0x00), assemble(
			" nop",
			" include \"testIncludeLabel.asm\"",
			" jp test"
		));
	}

	@Test
	public void testIncludeLabel2() throws IOException {
		Files.write(temporaryDirectory.resolve("testIncludeLabel2.asm"), Arrays.asList(
			" ld a,a"
		));

		assertArrayEquals(b(0x00, 0x7F, 0xC3, 0x01, 0x00), assemble(
			" nop",
			"test: include \"testIncludeLabel2.asm\"",
			" jp test"
		));
	}

	@Test
	public void testIncludeMacro() throws IOException {
		Files.write(temporaryDirectory.resolve("testIncludeMacro.asm"), Arrays.asList(
			"test: MACRO",
			"x: db 11H",
			"y: db 22H",
			" ENDM"
		));

		assertArrayEquals(b(0x11, 0x22, 0x00, 0x01), assemble(
			" test",
			" include \"testIncludeMacro.asm\"",
			" db test.x, test.y"
		));
	}

	@Test
	public void testIncludeInRepeat() throws IOException {
		Files.write(temporaryDirectory.resolve("testIncludeRept.asm"), Arrays.asList(
			"test: db test"
		));

		assertArrayEquals(b(0x00, 0x01, 0x02), assemble(
			" REPT 3",
			" include \"testIncludeRept.asm\"",
			" ENDM"
		));
	}

	@TempDir
	static Path temporaryDirectory;

	public byte[] assemble(String... sourceLines) {
		SourceBuilder sourceBuilder = new SourceBuilder(Arrays.asList(temporaryDirectory));
		Source source = sourceBuilder.parse(new SourceFile(String.join("\n", sourceLines)));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			source.assemble(output);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return output.toByteArray();
	}

}
