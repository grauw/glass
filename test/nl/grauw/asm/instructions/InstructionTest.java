package nl.grauw.asm.instructions;

import static org.junit.Assert.*;

import nl.grauw.asm.Line;
import nl.grauw.asm.parser.LineParser;

import org.junit.Test;

public class InstructionTest {
	
	private InstructionFactory factory = new InstructionFactory();
	
	@Test
	public void testAdc() {
		Instruction in = parse("adc");
		assertEquals("adc", in.getName());
	}
	
	@Test
	public void testAdd() {
		Instruction in = parse("add");
		assertEquals("add", in.getName());
	}
	
	@Test
	public void testAnd() {
		Instruction in = parse("and");
		assertEquals("and", in.getName());
	}
	
	@Test
	public void testBit() {
		Instruction in = parse("bit");
		assertEquals("bit", in.getName());
	}
	
	@Test
	public void testCall() {
		Instruction in = parse("call");
		assertEquals("call", in.getName());
	}
	
	@Test
	public void testCcf() {
		Instruction in = parse("ccf");
		assertEquals("ccf", in.getName());
	}
	
	@Test
	public void testCp() {
		Instruction in = parse("cp");
		assertEquals("cp", in.getName());
	}
	
	@Test
	public void testCpd() {
		Instruction in = parse("cpd");
		assertEquals("cpd", in.getName());
	}
	
	@Test
	public void testCpdr() {
		Instruction in = parse("cpdr");
		assertEquals("cpdr", in.getName());
	}
	
	@Test
	public void testCpi() {
		Instruction in = parse("cpi");
		assertEquals("cpi", in.getName());
	}
	
	@Test
	public void testCpir() {
		Instruction in = parse("cpir");
		assertEquals("cpir", in.getName());
	}
	
	@Test
	public void testCpl() {
		Instruction in = parse("cpl");
		assertEquals("cpl", in.getName());
	}
	
	@Test
	public void testDaa() {
		Instruction in = parse("daa");
		assertEquals("daa", in.getName());
	}
	
	@Test
	public void testDec() {
		Instruction in = parse("dec");
		assertEquals("dec", in.getName());
	}
	
	@Test
	public void testDi() {
		Instruction in = parse("di");
		assertEquals("di", in.getName());
	}
	
	@Test
	public void testDjnz() {
		Instruction in = parse("djnz");
		assertEquals("djnz", in.getName());
	}
	
	@Test
	public void testEi() {
		Instruction in = parse("ei");
		assertEquals("ei", in.getName());
	}
	
	@Test
	public void testEx() {
		Instruction in = parse("ex");
		assertEquals("ex", in.getName());
	}
	
	@Test
	public void testExx() {
		Instruction in = parse("exx");
		assertEquals("exx", in.getName());
	}
	
	@Test
	public void testHalt() {
		Instruction in = parse("halt");
		assertEquals("halt", in.getName());
	}
	
	@Test
	public void testIm() {
		Instruction in = parse("im");
		assertEquals("im", in.getName());
	}
	
	@Test
	public void testIn() {
		Instruction in = parse("in");
		assertEquals("in", in.getName());
	}
	
	@Test
	public void testInc() {
		Instruction in = parse("inc");
		assertEquals("inc", in.getName());
	}
	
	@Test
	public void testInd() {
		Instruction in = parse("ind");
		assertEquals("ind", in.getName());
	}
	
	@Test
	public void testIndr() {
		Instruction in = parse("indr");
		assertEquals("indr", in.getName());
	}
	
	@Test
	public void testIni() {
		Instruction in = parse("ini");
		assertEquals("ini", in.getName());
	}
	
	@Test
	public void testInir() {
		Instruction in = parse("inir");
		assertEquals("inir", in.getName());
	}
	
	@Test
	public void testJp() {
		Instruction in = parse("jp");
		assertEquals("jp", in.getName());
	}
	
	@Test
	public void testJr() {
		Instruction in = parse("jr");
		assertEquals("jr", in.getName());
	}
	
	@Test
	public void testLd() {
		Instruction in = parse("ld");
		assertEquals("ld", in.getName());
	}
	
	@Test
	public void testLdd() {
		Instruction in = parse("ldd");
		assertEquals("ldd", in.getName());
	}
	
	@Test
	public void testLddr() {
		Instruction in = parse("lddr");
		assertEquals("lddr", in.getName());
	}
	
	@Test
	public void testLdi() {
		Instruction in = parse("ldi");
		assertEquals("ldi", in.getName());
	}
	
	@Test
	public void testLdir() {
		Instruction in = parse("ldir");
		assertEquals("ldir", in.getName());
	}
	
	@Test
	public void testMulub() {
		Instruction in = parse("mulub");
		assertEquals("mulub", in.getName());
	}
	
	@Test
	public void testMuluw() {
		Instruction in = parse("muluw");
		assertEquals("muluw", in.getName());
	}
	
	@Test
	public void testNeg() {
		Instruction in = parse("neg");
		assertEquals("neg", in.getName());
	}
	
	@Test
	public void testNop() {
		Instruction in = parse("nop");
		assertEquals("nop", in.getName());
	}
	
	@Test
	public void testOr() {
		Instruction in = parse("or");
		assertEquals("or", in.getName());
	}
	
	@Test
	public void testOtdr() {
		Instruction in = parse("otdr");
		assertEquals("otdr", in.getName());
	}
	
	@Test
	public void testOtir() {
		Instruction in = parse("otir");
		assertEquals("otir", in.getName());
	}
	
	@Test
	public void testOut() {
		Instruction in = parse("out");
		assertEquals("out", in.getName());
	}
	
	@Test
	public void testOutd() {
		Instruction in = parse("outd");
		assertEquals("outd", in.getName());
	}
	
	@Test
	public void testOuti() {
		Instruction in = parse("outi");
		assertEquals("outi", in.getName());
	}
	
	@Test
	public void testPop() {
		Instruction in = parse("pop");
		assertEquals("pop", in.getName());
	}
	
	@Test
	public void testPush() {
		Instruction in = parse("push");
		assertEquals("push", in.getName());
	}
	
	@Test
	public void testRes() {
		Instruction in = parse("res");
		assertEquals("res", in.getName());
	}
	
	@Test
	public void testRet() {
		Instruction in = parse("ret");
		assertEquals("ret", in.getName());
	}
	
	@Test
	public void testReti() {
		Instruction in = parse("reti");
		assertEquals("reti", in.getName());
	}
	
	@Test
	public void testRetn() {
		Instruction in = parse("retn");
		assertEquals("retn", in.getName());
	}
	
	@Test
	public void testRl() {
		Instruction in = parse("rl");
		assertEquals("rl", in.getName());
	}
	
	@Test
	public void testRla() {
		Instruction in = parse("rla");
		assertEquals("rla", in.getName());
	}
	
	@Test
	public void testRlc() {
		Instruction in = parse("rlc");
		assertEquals("rlc", in.getName());
	}
	
	@Test
	public void testRlca() {
		Instruction in = parse("rlca");
		assertEquals("rlca", in.getName());
	}
	
	@Test
	public void testRld() {
		Instruction in = parse("rld");
		assertEquals("rld", in.getName());
	}
	
	@Test
	public void testRr() {
		Instruction in = parse("rr");
		assertEquals("rr", in.getName());
	}
	
	@Test
	public void testRra() {
		Instruction in = parse("rra");
		assertEquals("rra", in.getName());
	}
	
	@Test
	public void testRrc() {
		Instruction in = parse("rrc");
		assertEquals("rrc", in.getName());
	}
	
	@Test
	public void testRrca() {
		Instruction in = parse("rrca");
		assertEquals("rrca", in.getName());
	}
	
	@Test
	public void testRrd() {
		Instruction in = parse("rrd");
		assertEquals("rrd", in.getName());
	}
	
	@Test
	public void testRst() {
		Instruction in = parse("rst");
		assertEquals("rst", in.getName());
	}
	
	@Test
	public void testSbc() {
		Instruction in = parse("sbc");
		assertEquals("sbc", in.getName());
	}
	
	@Test
	public void testScf() {
		Instruction in = parse("scf");
		assertEquals("scf", in.getName());
	}
	
	@Test
	public void testSet() {
		Instruction in = parse("set");
		assertEquals("set", in.getName());
	}
	
	@Test
	public void testSla() {
		Instruction in = parse("sla");
		assertEquals("sla", in.getName());
	}
	
	@Test
	public void testSra() {
		Instruction in = parse("sra");
		assertEquals("sra", in.getName());
	}
	
	@Test
	public void testSrl() {
		Instruction in = parse("srl");
		assertEquals("srl", in.getName());
	}
	
	@Test
	public void testSub() {
		Instruction in = parse("sub");
		assertEquals("sub", in.getName());
	}
	
	@Test
	public void testXor() {
		Instruction in = parse("xor");
		assertEquals("xor", in.getName());
	}
	
	public Instruction parse(String string) {
		Line line = new LineParser().parse(" " + string, null, 0);
		line.resolveInstruction(factory);
		return line.getStatement().getInstruction();
	}
	
}
