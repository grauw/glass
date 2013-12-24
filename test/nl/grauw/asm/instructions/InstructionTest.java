package nl.grauw.asm.instructions;

import static org.junit.Assert.*;

import nl.grauw.asm.Line;
import nl.grauw.asm.LineParser;

import org.junit.Test;

public class InstructionTest {
	
	private InstructionRegistry factory = new InstructionRegistry();
	
	@Test
	public void testAdcA() {
		assertArrayEquals(b(0x88), parse("adc a,b").getBytes());
		assertArrayEquals(b(0x89), parse("adc a,c").getBytes());
		assertArrayEquals(b(0x8A), parse("adc a,d").getBytes());
		assertArrayEquals(b(0x8B), parse("adc a,e").getBytes());
		assertArrayEquals(b(0x8C), parse("adc a,h").getBytes());
		assertArrayEquals(b(0x8D), parse("adc a,l").getBytes());
		assertArrayEquals(b(0x8E), parse("adc a,(hl)").getBytes());
		assertArrayEquals(b(0x8F), parse("adc a,a").getBytes());
		assertArrayEquals(b(0xDD, 0x8C), parse("adc a,ixh").getBytes());
		assertArrayEquals(b(0xFD, 0x8D), parse("adc a,iyl").getBytes());
		assertArrayEquals(b(0xDD, 0x8E, 0x47), parse("adc a,(ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0x8E, 0x86), parse("adc a,(iy - 7AH)").getBytes());
	}
	
	@Test
	public void testAdcAN() {
		assertArrayEquals(b(0xCE, 0x86), parse("adc a,86H").getBytes());
	}
	
	@Test
	public void testAdcHL() {
		assertArrayEquals(b(0xED, 0x4A), parse("adc hl,bc").getBytes());
		assertArrayEquals(b(0xED, 0x5A), parse("adc hl,de").getBytes());
		assertArrayEquals(b(0xED, 0x6A), parse("adc hl,hl").getBytes());
		assertArrayEquals(b(0xED, 0x7A), parse("adc hl,sp").getBytes());
	}
	
	@Test
	public void testAddA() {
		assertArrayEquals(b(0x80), parse("add a,b").getBytes());
		assertArrayEquals(b(0x81), parse("add a,c").getBytes());
		assertArrayEquals(b(0x82), parse("add a,d").getBytes());
		assertArrayEquals(b(0x83), parse("add a,e").getBytes());
		assertArrayEquals(b(0x84), parse("add a,h").getBytes());
		assertArrayEquals(b(0x85), parse("add a,l").getBytes());
		assertArrayEquals(b(0x86), parse("add a,(hl)").getBytes());
		assertArrayEquals(b(0x87), parse("add a,a").getBytes());
		assertArrayEquals(b(0xDD, 0x84), parse("add a,ixh").getBytes());
		assertArrayEquals(b(0xFD, 0x85), parse("add a,iyl").getBytes());
		assertArrayEquals(b(0xDD, 0x86, 0x47), parse("add a,(ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0x86, 0x86), parse("add a,(iy - 7AH)").getBytes());
	}
	
	@Test
	public void testAddAN() {
		assertArrayEquals(b(0xC6, 0x86), parse("add a,86H").getBytes());
	}
	
	@Test
	public void testAddHL() {
		assertArrayEquals(b(0x09), parse("add hl,bc").getBytes());
		assertArrayEquals(b(0x19), parse("add hl,de").getBytes());
		assertArrayEquals(b(0x29), parse("add hl,hl").getBytes());
		assertArrayEquals(b(0x39), parse("add hl,sp").getBytes());
		assertArrayEquals(b(0xDD, 0x09), parse("add ix,bc").getBytes());
		assertArrayEquals(b(0xDD, 0x19), parse("add ix,de").getBytes());
		assertArrayEquals(b(0xDD, 0x29), parse("add ix,ix").getBytes());
		assertArrayEquals(b(0xDD, 0x39), parse("add ix,sp").getBytes());
		assertArrayEquals(b(0xFD, 0x09), parse("add iy,bc").getBytes());
		assertArrayEquals(b(0xFD, 0x19), parse("add iy,de").getBytes());
		assertArrayEquals(b(0xFD, 0x29), parse("add iy,ix").getBytes());
		assertArrayEquals(b(0xFD, 0x39), parse("add iy,sp").getBytes());
	}
	
	@Test
	public void testAnd() {
		assertArrayEquals(b(0xA0), parse("and b").getBytes());
		assertArrayEquals(b(0xA1), parse("and c").getBytes());
		assertArrayEquals(b(0xA2), parse("and d").getBytes());
		assertArrayEquals(b(0xA3), parse("and e").getBytes());
		assertArrayEquals(b(0xA4), parse("and h").getBytes());
		assertArrayEquals(b(0xA5), parse("and l").getBytes());
		assertArrayEquals(b(0xA6), parse("and (hl)").getBytes());
		assertArrayEquals(b(0xA7), parse("and a").getBytes());
		assertArrayEquals(b(0xDD, 0xA4), parse("and ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xA5), parse("and iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xA6, 0x47), parse("and (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xA6, 0x86), parse("and (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testAndN() {
		assertArrayEquals(b(0xE6, 0x86), parse("and 86H").getBytes());
	}
	
	@Test
	public void testBit() {
		assertArrayEquals(b(0xCB, 0x78), parse("bit 7,b").getBytes());
		assertArrayEquals(b(0xCB, 0x71), parse("bit 6,c").getBytes());
		assertArrayEquals(b(0xCB, 0x6A), parse("bit 5,d").getBytes());
		assertArrayEquals(b(0xCB, 0x63), parse("bit 4,e").getBytes());
		assertArrayEquals(b(0xCB, 0x5C), parse("bit 3,h").getBytes());
		assertArrayEquals(b(0xCB, 0x55), parse("bit 2,l").getBytes());
		assertArrayEquals(b(0xCB, 0x4E), parse("bit 1,(hl)").getBytes());
		assertArrayEquals(b(0xCB, 0x47), parse("bit 0,a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x5C), parse("bit 3,ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x55), parse("bit 2,iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0x5E), parse("bit 3,(ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0x66), parse("bit 4,(iy - 7AH)").getBytes());
	}
	
	@Test
	public void testCall() {
		assertArrayEquals(b(0xCD, 0x86, 0x47), parse("call 4786H").getBytes());
		assertArrayEquals(b(0xC4, 0x86, 0x47), parse("call nz,4786H").getBytes());
		assertArrayEquals(b(0xCC, 0x86, 0x47), parse("call z,4786H").getBytes());
		assertArrayEquals(b(0xD4, 0x86, 0x47), parse("call nc,4786H").getBytes());
		assertArrayEquals(b(0xDC, 0x86, 0x47), parse("call c,4786H").getBytes());
		assertArrayEquals(b(0xE4, 0x86, 0x47), parse("call po,4786H").getBytes());
		assertArrayEquals(b(0xEC, 0x86, 0x47), parse("call pe,4786H").getBytes());
		assertArrayEquals(b(0xF4, 0x86, 0x47), parse("call p,4786H").getBytes());
		assertArrayEquals(b(0xFC, 0x86, 0x47), parse("call m,4786H").getBytes());
	}
	
	@Test
	public void testCcf() {
		assertArrayEquals(b(0x3F), parse("ccf").getBytes());
	}
	
	@Test
	public void testCp() {
		assertArrayEquals(b(0xB8), parse("cp b").getBytes());
		assertArrayEquals(b(0xB9), parse("cp c").getBytes());
		assertArrayEquals(b(0xBA), parse("cp d").getBytes());
		assertArrayEquals(b(0xBB), parse("cp e").getBytes());
		assertArrayEquals(b(0xBC), parse("cp h").getBytes());
		assertArrayEquals(b(0xBD), parse("cp l").getBytes());
		assertArrayEquals(b(0xBE), parse("cp (hl)").getBytes());
		assertArrayEquals(b(0xBF), parse("cp a").getBytes());
		assertArrayEquals(b(0xDD, 0xBC), parse("cp ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xBD), parse("cp iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xBE, 0x47), parse("cp (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xBE, 0x86), parse("cp (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testCpN() {
		assertArrayEquals(b(0xFE, 0x86), parse("cp 86H").getBytes());
	}
	
	@Test
	public void testCpd() {
		assertArrayEquals(b(0xED, 0xA9), parse("cpd").getBytes());
	}
	
	@Test
	public void testCpdr() {
		assertArrayEquals(b(0xED, 0xB9), parse("cpdr").getBytes());
	}
	
	@Test
	public void testCpi() {
		assertArrayEquals(b(0xED, 0xA1), parse("cpi").getBytes());
	}
	
	@Test
	public void testCpir() {
		assertArrayEquals(b(0xED, 0xB1), parse("cpir").getBytes());
	}
	
	@Test
	public void testCpl() {
		assertArrayEquals(b(0x2F), parse("cpl").getBytes());
	}
	
	@Test
	public void testDaa() {
		assertArrayEquals(b(0x27), parse("daa").getBytes());
	}
	
	@Test
	public void testDec() {
		assertArrayEquals(b(0x05), parse("dec b").getBytes());
		assertArrayEquals(b(0x0D), parse("dec c").getBytes());
		assertArrayEquals(b(0x15), parse("dec d").getBytes());
		assertArrayEquals(b(0x1D), parse("dec e").getBytes());
		assertArrayEquals(b(0x25), parse("dec h").getBytes());
		assertArrayEquals(b(0x2D), parse("dec l").getBytes());
		assertArrayEquals(b(0x35), parse("dec (hl)").getBytes());
		assertArrayEquals(b(0x3D), parse("dec a").getBytes());
		assertArrayEquals(b(0xDD, 0x25), parse("dec ixh").getBytes());
		assertArrayEquals(b(0xFD, 0x2D), parse("dec iyl").getBytes());
		assertArrayEquals(b(0xDD, 0x35, 0x47), parse("dec (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0x35, 0x86), parse("dec (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testDi() {
		assertArrayEquals(b(0xF3), parse("di").getBytes());
	}
	
	@Test
	public void testDjnz() {
		assertArrayEquals(b(0x10, 0xFE), parse("djnz $").getBytes());
	}
	
	@Test
	public void testEi() {
		assertArrayEquals(b(0xFB), parse("ei").getBytes());
	}
	
	@Test
	public void testExAF() {
		assertArrayEquals(b(0x08), parse("ex af,af'").getBytes());
	}
	
	@Test
	public void testExDEHL() {
		assertArrayEquals(b(0xEB), parse("ex de,hl").getBytes());
	}
	
	@Test
	public void testExSP() {
		assertArrayEquals(b(0xE3), parse("ex (sp),hl").getBytes());
		assertArrayEquals(b(0xDD, 0xE3), parse("ex (sp),ix").getBytes());
		assertArrayEquals(b(0xFD, 0xE3), parse("ex (sp),iy").getBytes());
	}
	
	@Test
	public void testExx() {
		assertArrayEquals(b(0xD9), parse("exx").getBytes());
	}
	
	@Test
	public void testHalt() {
		assertArrayEquals(b(0x76), parse("halt").getBytes());
	}
	
	@Test
	public void testIm() {
		assertArrayEquals(b(0xED, 0x46), parse("im 0").getBytes());
		assertArrayEquals(b(0xED, 0x56), parse("im 1").getBytes());
		assertArrayEquals(b(0xED, 0x5E), parse("im 2").getBytes());
	}
	
	@Test
	public void testIn() {
		assertArrayEquals(b(0xEB, 0x40), parse("in b,(c)").getBytes());
		assertArrayEquals(b(0xEB, 0x48), parse("in c,(c)").getBytes());
		assertArrayEquals(b(0xEB, 0x50), parse("in d,(c)").getBytes());
		assertArrayEquals(b(0xEB, 0x58), parse("in e,(c)").getBytes());
		assertArrayEquals(b(0xEB, 0x60), parse("in h,(c)").getBytes());
		assertArrayEquals(b(0xEB, 0x68), parse("in l,(c)").getBytes());
		assertArrayEquals(b(0xEB, 0x70), parse("in (c)").getBytes());
		assertArrayEquals(b(0xEB, 0x78), parse("in a,(c)").getBytes());
		assertArrayEquals(b(0xDB, 0x86), parse("in a,(86H)").getBytes());
	}
	
	@Test
	public void testInc() {
		assertArrayEquals(b(0x04), parse("inc b").getBytes());
		assertArrayEquals(b(0x0C), parse("inc c").getBytes());
		assertArrayEquals(b(0x14), parse("inc d").getBytes());
		assertArrayEquals(b(0x1C), parse("inc e").getBytes());
		assertArrayEquals(b(0x24), parse("inc h").getBytes());
		assertArrayEquals(b(0x2C), parse("inc l").getBytes());
		assertArrayEquals(b(0x34), parse("inc (hl)").getBytes());
		assertArrayEquals(b(0x3C), parse("inc a").getBytes());
		assertArrayEquals(b(0xDD, 0x24), parse("inc ixh").getBytes());
		assertArrayEquals(b(0xFD, 0x2C), parse("inc iyl").getBytes());
		assertArrayEquals(b(0xDD, 0x34, 0x47), parse("inc (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0x34, 0x86), parse("inc (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testInd() {
		assertArrayEquals(b(0xED, 0xAA), parse("ind").getBytes());
	}
	
	@Test
	public void testIndr() {
		assertArrayEquals(b(0xED, 0xBA), parse("indr").getBytes());
	}
	
	@Test
	public void testIni() {
		assertArrayEquals(b(0xED, 0xA2), parse("ini").getBytes());
	}
	
	@Test
	public void testInir() {
		assertArrayEquals(b(0xED, 0xB2), parse("inir").getBytes());
	}
	
	@Test
	public void testJp() {
		assertArrayEquals(b(0xC3, 0x86, 0x47), parse("jp 4786H").getBytes());
		assertArrayEquals(b(0xC2, 0x86, 0x47), parse("jp nz,4786H").getBytes());
		assertArrayEquals(b(0xCA, 0x86, 0x47), parse("jp z,4786H").getBytes());
		assertArrayEquals(b(0xD2, 0x86, 0x47), parse("jp nc,4786H").getBytes());
		assertArrayEquals(b(0xDA, 0x86, 0x47), parse("jp c,4786H").getBytes());
		assertArrayEquals(b(0xE2, 0x86, 0x47), parse("jp po,4786H").getBytes());
		assertArrayEquals(b(0xEA, 0x86, 0x47), parse("jp pe,4786H").getBytes());
		assertArrayEquals(b(0xF2, 0x86, 0x47), parse("jp p,4786H").getBytes());
		assertArrayEquals(b(0xFA, 0x86, 0x47), parse("jp m,4786H").getBytes());
		assertArrayEquals(b(0xE9), parse("jp (hl)").getBytes());
		assertArrayEquals(b(0xDD, 0xE9), parse("jp (ix)").getBytes());
		assertArrayEquals(b(0xFD, 0xE9), parse("jp (iy)").getBytes());
	}
	
	@Test
	public void testJr() {
		assertArrayEquals(b(0x18, 0xFE), parse("jr $").getBytes());
		assertArrayEquals(b(0x20, 0xFE), parse("jr nz,$").getBytes());
		assertArrayEquals(b(0x28, 0xFE), parse("jr z,$").getBytes());
		assertArrayEquals(b(0x30, 0xFE), parse("jr nc,$").getBytes());
		assertArrayEquals(b(0x38, 0xFE), parse("jr c,$").getBytes());
	}
	
	@Test
	public void testLd() {
		assertArrayEquals(b(0x78), parse("ld a,b").getBytes());
		assertArrayEquals(b(0x71), parse("ld (hl),c").getBytes());
		assertArrayEquals(b(0x6A), parse("ld l,d").getBytes());
		assertArrayEquals(b(0x63), parse("ld h,e").getBytes());
		assertArrayEquals(b(0x5C), parse("ld e,h").getBytes());
		assertArrayEquals(b(0x55), parse("ld d,l").getBytes());
		assertArrayEquals(b(0x4E), parse("ld c,(hl)").getBytes());
		assertArrayEquals(b(0x47), parse("ld b,a").getBytes());
		assertArrayEquals(b(0xDD, 0x6C), parse("ld ixl,ixh").getBytes());
		assertArrayEquals(b(0xFD, 0x65), parse("ld iyh,iyl").getBytes());
		assertArrayEquals(b(0xDD, 0x4E, 0x47), parse("add c,(ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0x71, 0x86), parse("add (iy - 7AH),c").getBytes());
		
		assertArrayEquals(b(0x06, 0x86), parse("ld b,86H").getBytes());
		assertArrayEquals(b(0x0E, 0x86), parse("ld c,86H").getBytes());
		assertArrayEquals(b(0x16, 0x86), parse("ld d,86H").getBytes());
		assertArrayEquals(b(0x1E, 0x86), parse("ld e,86H").getBytes());
		assertArrayEquals(b(0x26, 0x86), parse("ld h,86H").getBytes());
		assertArrayEquals(b(0x2E, 0x86), parse("ld l,86H").getBytes());
		assertArrayEquals(b(0x36, 0x86), parse("ld (hl),86H").getBytes());
		assertArrayEquals(b(0x3E, 0x86), parse("ld a,86H").getBytes());
		assertArrayEquals(b(0xDD, 0x26, 0x86), parse("ld ixh,86H").getBytes());
		assertArrayEquals(b(0xFD, 0x2E, 0x86), parse("ld iyl,86H").getBytes());
		assertArrayEquals(b(0xDD, 0x36, 0x47, 0x86), parse("ld (ix + 47H),86H").getBytes());
		assertArrayEquals(b(0xFD, 0x36, 0x86, 0x47), parse("ld (iy - 7AH),47H").getBytes());
		
		assertArrayEquals(b(0xED, 0x57), parse("ld a,i").getBytes());
		assertArrayEquals(b(0xED, 0x5F), parse("ld a,r").getBytes());
		assertArrayEquals(b(0xED, 0x47), parse("ld i,a").getBytes());
		assertArrayEquals(b(0xED, 0x4F), parse("ld r,a").getBytes());
		
		assertArrayEquals(b(0x0A), parse("ld a,(bc)").getBytes());
		assertArrayEquals(b(0x1A), parse("ld a,(de)").getBytes());
		assertArrayEquals(b(0x2A), parse("ld hl,(4786H)").getBytes());
		assertArrayEquals(b(0x3A, 0x86, 0x47), parse("ld a,(4786H)").getBytes());
		assertArrayEquals(b(0xDD, 0x2A, 0x86, 0x47), parse("ld ix,(4786H)").getBytes());
		assertArrayEquals(b(0xFD, 0x2A, 0x86, 0x47), parse("ld iy,(4786H)").getBytes());
		assertArrayEquals(b(0xED, 0xCB, 0x86, 0x47), parse("ld bc,(4786H)").getBytes());
		assertArrayEquals(b(0xED, 0xDB, 0x86, 0x47), parse("ld de,(4786H)").getBytes());
		assertArrayEquals(b(0xED, 0xFB, 0x86, 0x47), parse("ld sp,(4786H)").getBytes());
		
		assertArrayEquals(b(0x02), parse("ld (bc),a").getBytes());
		assertArrayEquals(b(0x12), parse("ld (de),a").getBytes());
		assertArrayEquals(b(0x22, 0x86, 0x47), parse("ld (4786H),hl").getBytes());
		assertArrayEquals(b(0x32, 0x86, 0x47), parse("ld (4786H),a").getBytes());
		assertArrayEquals(b(0xDD, 0x22, 0x86, 0x47), parse("ld (4786H),ix").getBytes());
		assertArrayEquals(b(0xFD, 0x22, 0x86, 0x47), parse("ld (4786H),iy").getBytes());
		assertArrayEquals(b(0xED, 0xC3, 0x86, 0x47), parse("ld (4786H),bc").getBytes());
		assertArrayEquals(b(0xED, 0xD3, 0x86, 0x47), parse("ld (4786H),de").getBytes());
		assertArrayEquals(b(0xED, 0xF3, 0x86, 0x47), parse("ld (4786H),sp").getBytes());
		
		assertArrayEquals(b(0x01), parse("ld bc,4786H").getBytes());
		assertArrayEquals(b(0x11), parse("ld de,4786H").getBytes());
		assertArrayEquals(b(0x21), parse("ld hl,4786H").getBytes());
		assertArrayEquals(b(0x31), parse("ld sp,4786H").getBytes());
		assertArrayEquals(b(0xDD, 0x21), parse("ld ix,4786H").getBytes());
		assertArrayEquals(b(0xFD, 0x21), parse("ld iy,4786H").getBytes());
		
		assertArrayEquals(b(0xF9), parse("ld sp,hl").getBytes());
		assertArrayEquals(b(0xDD, 0xF9), parse("ld sp,ix").getBytes());
		assertArrayEquals(b(0xFE, 0xF9), parse("ld sp,iy").getBytes());
	}
	
	@Test
	public void testLdd() {
		assertArrayEquals(b(0xED, 0xA8), parse("ldd").getBytes());
	}
	
	@Test
	public void testLddr() {
		assertArrayEquals(b(0xED, 0xB8), parse("lddr").getBytes());
	}
	
	@Test
	public void testLdi() {
		assertArrayEquals(b(0xED, 0xA0), parse("ldi").getBytes());
	}
	
	@Test
	public void testLdir() {
		assertArrayEquals(b(0xED, 0xB0), parse("ldir").getBytes());
	}
	
	@Test
	public void testMulub() {
		assertArrayEquals(b(0xED, 0xC1), parse("mulub a,b").getBytes());
		assertArrayEquals(b(0xED, 0xC8), parse("mulub a,c").getBytes());
		assertArrayEquals(b(0xED, 0xD1), parse("mulub a,d").getBytes());
		assertArrayEquals(b(0xED, 0xD8), parse("mulub a,e").getBytes());
	}
	
	@Test
	public void testMuluw() {
		assertArrayEquals(b(0xED, 0xC3), parse("mulub hl,bc").getBytes());
		assertArrayEquals(b(0xED, 0xF3), parse("mulub hl,sp").getBytes());
	}
	
	@Test
	public void testNeg() {
		assertArrayEquals(b(0xED, 0x44), parse("neg").getBytes());
	}
	
	@Test
	public void testNop() {
		assertArrayEquals(b(0x00), parse("nop").getBytes());
	}
	
	@Test
	public void testOr() {
		assertArrayEquals(b(0xB0), parse("or b").getBytes());
		assertArrayEquals(b(0xB1), parse("or c").getBytes());
		assertArrayEquals(b(0xB2), parse("or d").getBytes());
		assertArrayEquals(b(0xB3), parse("or e").getBytes());
		assertArrayEquals(b(0xB4), parse("or h").getBytes());
		assertArrayEquals(b(0xB5), parse("or l").getBytes());
		assertArrayEquals(b(0xB6), parse("or (hl)").getBytes());
		assertArrayEquals(b(0xB7), parse("or a").getBytes());
		assertArrayEquals(b(0xDD, 0xB4), parse("or ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xB5), parse("or iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xB6, 0x47), parse("or (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xB6, 0x86), parse("or (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testOrN() {
		assertArrayEquals(b(0xF6, 0x86), parse("or 86H").getBytes());
	}
	
	@Test
	public void testOtdr() {
		assertArrayEquals(b(0xED, 0xBB), parse("otdr").getBytes());
	}
	
	@Test
	public void testOtir() {
		assertArrayEquals(b(0xED, 0xB3), parse("otir").getBytes());
	}
	
	@Test
	public void testOut() {
		assertArrayEquals(b(0xED, 0x41), parse("out (c),b").getBytes());
		assertArrayEquals(b(0xED, 0x49), parse("out (c),c").getBytes());
		assertArrayEquals(b(0xED, 0x51), parse("out (c),d").getBytes());
		assertArrayEquals(b(0xED, 0x59), parse("out (c),e").getBytes());
		assertArrayEquals(b(0xED, 0x61), parse("out (c),h").getBytes());
		assertArrayEquals(b(0xED, 0x69), parse("out (c),l").getBytes());
		assertArrayEquals(b(0xED, 0x78), parse("out (c),a").getBytes());
		assertArrayEquals(b(0xD3, 0x86), parse("out (86H),a").getBytes());
	}
	
	@Test
	public void testOutd() {
		assertArrayEquals(b(0xED, 0xAB), parse("outd").getBytes());
	}
	
	@Test
	public void testOuti() {
		assertArrayEquals(b(0xED, 0xA3), parse("outi").getBytes());
	}
	
	@Test
	public void testPop() {
		assertArrayEquals(b(0xC1), parse("pop bc").getBytes());
		assertArrayEquals(b(0xD1), parse("pop de").getBytes());
		assertArrayEquals(b(0xE1), parse("pop hl").getBytes());
		assertArrayEquals(b(0xF1), parse("pop af").getBytes());
		assertArrayEquals(b(0xDD, 0xE1), parse("pop ix").getBytes());
		assertArrayEquals(b(0xFD, 0xE1), parse("pop iy").getBytes());
	}
	
	@Test
	public void testPush() {
		assertArrayEquals(b(0xC5), parse("pop bc").getBytes());
		assertArrayEquals(b(0xD5), parse("pop de").getBytes());
		assertArrayEquals(b(0xE5), parse("pop hl").getBytes());
		assertArrayEquals(b(0xF5), parse("pop af").getBytes());
		assertArrayEquals(b(0xDD, 0xE5), parse("pop ix").getBytes());
		assertArrayEquals(b(0xFD, 0xE5), parse("pop iy").getBytes());
	}
	
	@Test
	public void testRes() {
		assertArrayEquals(b(0xCB, 0xB8), parse("res 7,b").getBytes());
		assertArrayEquals(b(0xCB, 0xB1), parse("res 6,c").getBytes());
		assertArrayEquals(b(0xCB, 0xAA), parse("res 5,d").getBytes());
		assertArrayEquals(b(0xCB, 0xA3), parse("res 4,e").getBytes());
		assertArrayEquals(b(0xCB, 0x9C), parse("res 3,h").getBytes());
		assertArrayEquals(b(0xCB, 0x95), parse("res 2,l").getBytes());
		assertArrayEquals(b(0xCB, 0x8E), parse("res 1,(hl)").getBytes());
		assertArrayEquals(b(0xCB, 0x87), parse("res 0,a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x9C), parse("res 3,ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x95), parse("res 2,iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0x9E), parse("res 3,(ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0xA6), parse("res 4,(iy - 7AH)").getBytes());
	}
	
	@Test
	public void testRet() {
		assertArrayEquals(b(0xC9), parse("ret").getBytes());
	}
	
	@Test
	public void testRetF() {
		assertArrayEquals(b(0xC0), parse("ret nz").getBytes());
		assertArrayEquals(b(0xC8), parse("ret z").getBytes());
		assertArrayEquals(b(0xD0), parse("ret nc").getBytes());
		assertArrayEquals(b(0xD8), parse("ret c").getBytes());
		assertArrayEquals(b(0xE0), parse("ret po").getBytes());
		assertArrayEquals(b(0xE8), parse("ret pe").getBytes());
		assertArrayEquals(b(0xF0), parse("ret p").getBytes());
		assertArrayEquals(b(0xF8), parse("ret m").getBytes());
	}
	
	@Test
	public void testReti() {
		assertArrayEquals(b(0xED, 0x4D), parse("reti").getBytes());
	}
	
	@Test
	public void testRetn() {
		assertArrayEquals(b(0xED, 0x45), parse("retn").getBytes());
	}
	
	@Test
	public void testRl() {
		assertArrayEquals(b(0xCB, 0x10), parse("rl b").getBytes());
		assertArrayEquals(b(0xCB, 0x11), parse("rl c").getBytes());
		assertArrayEquals(b(0xCB, 0x12), parse("rl d").getBytes());
		assertArrayEquals(b(0xCB, 0x13), parse("rl e").getBytes());
		assertArrayEquals(b(0xCB, 0x14), parse("rl h").getBytes());
		assertArrayEquals(b(0xCB, 0x15), parse("rl l").getBytes());
		assertArrayEquals(b(0xCB, 0x16), parse("rl (hl)").getBytes());
		assertArrayEquals(b(0xCB, 0x17), parse("rl a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x14), parse("rl ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x15), parse("rl iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0x16), parse("rl (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0x16), parse("rl (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testRla() {
		assertArrayEquals(b(0x17), parse("rla").getBytes());
	}
	
	@Test
	public void testRlc() {
		assertArrayEquals(b(0xCB, 0x00), parse("rlc b").getBytes());
		assertArrayEquals(b(0xCB, 0x01), parse("rlc c").getBytes());
		assertArrayEquals(b(0xCB, 0x02), parse("rlc d").getBytes());
		assertArrayEquals(b(0xCB, 0x03), parse("rlc e").getBytes());
		assertArrayEquals(b(0xCB, 0x04), parse("rlc h").getBytes());
		assertArrayEquals(b(0xCB, 0x05), parse("rlc l").getBytes());
		assertArrayEquals(b(0xCB, 0x06), parse("rlc (hl)").getBytes());
		assertArrayEquals(b(0xCB, 0x07), parse("rlc a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x04), parse("rlc ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x05), parse("rlc iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0x06), parse("rlc (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0x06), parse("rlc (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testRlca() {
		assertArrayEquals(b(0x07), parse("rlca").getBytes());
	}
	
	@Test
	public void testRld() {
		assertArrayEquals(b(0xED, 0x6F), parse("rld").getBytes());
	}
	
	@Test
	public void testRr() {
		assertArrayEquals(b(0xCB, 0x18), parse("rr b").getBytes());
		assertArrayEquals(b(0xCB, 0x19), parse("rr c").getBytes());
		assertArrayEquals(b(0xCB, 0x1A), parse("rr d").getBytes());
		assertArrayEquals(b(0xCB, 0x1B), parse("rr e").getBytes());
		assertArrayEquals(b(0xCB, 0x1C), parse("rr h").getBytes());
		assertArrayEquals(b(0xCB, 0x1D), parse("rr l").getBytes());
		assertArrayEquals(b(0xCB, 0x1E), parse("rr (hl)").getBytes());
		assertArrayEquals(b(0xCB, 0x1F), parse("rr a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x1C), parse("rr ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x1D), parse("rr iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0x1E), parse("rr (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0x1E), parse("rr (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testRra() {
		assertArrayEquals(b(0x1F), parse("rra").getBytes());
	}
	
	@Test
	public void testRrc() {
		assertArrayEquals(b(0xCB, 0x08), parse("rrc b").getBytes());
		assertArrayEquals(b(0xCB, 0x09), parse("rrc c").getBytes());
		assertArrayEquals(b(0xCB, 0x0A), parse("rrc d").getBytes());
		assertArrayEquals(b(0xCB, 0x0B), parse("rrc e").getBytes());
		assertArrayEquals(b(0xCB, 0x0C), parse("rrc h").getBytes());
		assertArrayEquals(b(0xCB, 0x0D), parse("rrc l").getBytes());
		assertArrayEquals(b(0xCB, 0x0E), parse("rrc (hl)").getBytes());
		assertArrayEquals(b(0xCB, 0x0F), parse("rrc a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x0C), parse("rrc ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x0D), parse("rrc iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0x0E), parse("rrc (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0x0E), parse("rrc (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testRrca() {
		assertArrayEquals(b(0x0F), parse("rrca").getBytes());
	}
	
	@Test
	public void testRrd() {
		assertArrayEquals(b(0xED, 0x67), parse("rrd").getBytes());
	}
	
	@Test
	public void testRst() {
		assertArrayEquals(b(0xC7), parse("rst 00H").getBytes());
		assertArrayEquals(b(0xCF), parse("rst 08H").getBytes());
		assertArrayEquals(b(0xD7), parse("rst 10H").getBytes());
		assertArrayEquals(b(0xDF), parse("rst 18H").getBytes());
		assertArrayEquals(b(0xE7), parse("rst 20H").getBytes());
		assertArrayEquals(b(0xEF), parse("rst 28H").getBytes());
		assertArrayEquals(b(0xF7), parse("rst 30H").getBytes());
		assertArrayEquals(b(0xFF), parse("rst 38H").getBytes());
	}
	
	@Test
	public void testSbcA() {
		assertArrayEquals(b(0x98), parse("sbc a,b").getBytes());
		assertArrayEquals(b(0x99), parse("sbc a,c").getBytes());
		assertArrayEquals(b(0x9A), parse("sbc a,d").getBytes());
		assertArrayEquals(b(0x9B), parse("sbc a,e").getBytes());
		assertArrayEquals(b(0x9C), parse("sbc a,h").getBytes());
		assertArrayEquals(b(0x9D), parse("sbc a,l").getBytes());
		assertArrayEquals(b(0x9E), parse("sbc a,(hl)").getBytes());
		assertArrayEquals(b(0x9F), parse("sbc a,a").getBytes());
		assertArrayEquals(b(0xDD, 0x9C), parse("sbc a,ixh").getBytes());
		assertArrayEquals(b(0xFD, 0x9D), parse("sbc a,iyl").getBytes());
		assertArrayEquals(b(0xDD, 0x9E, 0x47), parse("sbc a,(ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0x9E, 0x86), parse("sbc a,(iy - 7AH)").getBytes());
	}
	
	@Test
	public void testSbcAN() {
		assertArrayEquals(b(0xDE, 0x86), parse("sbc a,86H").getBytes());
	}
	
	@Test
	public void testSbcHL() {
		assertArrayEquals(b(0xED, 0x42), parse("sbc hl,bc").getBytes());
		assertArrayEquals(b(0xED, 0x52), parse("sbc hl,de").getBytes());
		assertArrayEquals(b(0xED, 0x62), parse("sbc hl,hl").getBytes());
		assertArrayEquals(b(0xED, 0x72), parse("sbc hl,sp").getBytes());
	}
	
	@Test
	public void testScf() {
		assertArrayEquals(b(0x37), parse("scf").getBytes());
	}
	
	@Test
	public void testSet() {
		assertArrayEquals(b(0xCB, 0xF8), parse("set 7,b").getBytes());
		assertArrayEquals(b(0xCB, 0xF1), parse("set 6,c").getBytes());
		assertArrayEquals(b(0xCB, 0xEA), parse("set 5,d").getBytes());
		assertArrayEquals(b(0xCB, 0xE3), parse("set 4,e").getBytes());
		assertArrayEquals(b(0xCB, 0xDC), parse("set 3,h").getBytes());
		assertArrayEquals(b(0xCB, 0xD5), parse("set 2,l").getBytes());
		assertArrayEquals(b(0xCB, 0xCE), parse("set 1,(hl)").getBytes());
		assertArrayEquals(b(0xCB, 0xC7), parse("set 0,a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0xDC), parse("set 3,ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0xD5), parse("set 2,iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0xDE), parse("set 3,(ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0xE6), parse("set 4,(iy - 7AH)").getBytes());
	}
	
	@Test
	public void testSla() {
		assertArrayEquals(b(0xCB, 0x20), parse("sla b").getBytes());
		assertArrayEquals(b(0xCB, 0x21), parse("sla c").getBytes());
		assertArrayEquals(b(0xCB, 0x22), parse("sla d").getBytes());
		assertArrayEquals(b(0xCB, 0x23), parse("sla e").getBytes());
		assertArrayEquals(b(0xCB, 0x24), parse("sla h").getBytes());
		assertArrayEquals(b(0xCB, 0x25), parse("sla l").getBytes());
		assertArrayEquals(b(0xCB, 0x26), parse("sla (hl)").getBytes());
		assertArrayEquals(b(0xCB, 0x27), parse("sla a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x24), parse("sla ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x25), parse("sla iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0x26), parse("sla (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0x26), parse("sla (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testSra() {
		assertArrayEquals(b(0xCB, 0x28), parse("sra b").getBytes());
		assertArrayEquals(b(0xCB, 0x29), parse("sra c").getBytes());
		assertArrayEquals(b(0xCB, 0x2A), parse("sra d").getBytes());
		assertArrayEquals(b(0xCB, 0x2B), parse("sra e").getBytes());
		assertArrayEquals(b(0xCB, 0x2C), parse("sra h").getBytes());
		assertArrayEquals(b(0xCB, 0x2D), parse("sra l").getBytes());
		assertArrayEquals(b(0xCB, 0x2E), parse("sra (hl)").getBytes());
		assertArrayEquals(b(0xCB, 0x2F), parse("sra a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x2C), parse("sra ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x2D), parse("sra iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0x2E), parse("sra (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0x2E), parse("sra (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testSrl() {
		assertArrayEquals(b(0xCB, 0x38), parse("srl b").getBytes());
		assertArrayEquals(b(0xCB, 0x39), parse("srl c").getBytes());
		assertArrayEquals(b(0xCB, 0x3A), parse("srl d").getBytes());
		assertArrayEquals(b(0xCB, 0x3B), parse("srl e").getBytes());
		assertArrayEquals(b(0xCB, 0x3C), parse("srl h").getBytes());
		assertArrayEquals(b(0xCB, 0x3D), parse("srl l").getBytes());
		assertArrayEquals(b(0xCB, 0x3E), parse("srl (hl)").getBytes());
		assertArrayEquals(b(0xCB, 0x3F), parse("srl a").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x3C), parse("srl ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x3D), parse("srl iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xCB, 0x47, 0x3E), parse("srl (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xCB, 0x86, 0x3E), parse("srl (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testSub() {
		assertArrayEquals(b(0x90), parse("sub b").getBytes());
		assertArrayEquals(b(0x91), parse("sub c").getBytes());
		assertArrayEquals(b(0x92), parse("sub d").getBytes());
		assertArrayEquals(b(0x93), parse("sub e").getBytes());
		assertArrayEquals(b(0x94), parse("sub h").getBytes());
		assertArrayEquals(b(0x95), parse("sub l").getBytes());
		assertArrayEquals(b(0x96), parse("sub (hl)").getBytes());
		assertArrayEquals(b(0x97), parse("sub a").getBytes());
		assertArrayEquals(b(0xDD, 0x94), parse("sub ixh").getBytes());
		assertArrayEquals(b(0xFD, 0x95), parse("sub iyl").getBytes());
		assertArrayEquals(b(0xDD, 0x96, 0x47), parse("sub (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0x96, 0x86), parse("sub (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testSubN() {
		assertArrayEquals(b(0xD6, 0x86), parse("sub 86H").getBytes());
	}
	
	@Test
	public void testXor() {
		assertArrayEquals(b(0xA8), parse("xor b").getBytes());
		assertArrayEquals(b(0xA9), parse("xor c").getBytes());
		assertArrayEquals(b(0xAA), parse("xor d").getBytes());
		assertArrayEquals(b(0xAB), parse("xor e").getBytes());
		assertArrayEquals(b(0xAC), parse("xor h").getBytes());
		assertArrayEquals(b(0xAD), parse("xor l").getBytes());
		assertArrayEquals(b(0xAE), parse("xor (hl)").getBytes());
		assertArrayEquals(b(0xAF), parse("xor a").getBytes());
		assertArrayEquals(b(0xDD, 0xAC), parse("xor ixh").getBytes());
		assertArrayEquals(b(0xFD, 0xAD), parse("xor iyl").getBytes());
		assertArrayEquals(b(0xDD, 0xAE, 0x47), parse("xor (ix + 47H)").getBytes());
		assertArrayEquals(b(0xFD, 0xAE, 0x86), parse("xor (iy - 7AH)").getBytes());
	}
	
	@Test
	public void testXorN() {
		assertArrayEquals(b(0xEE, 0x86), parse("xor 86H").getBytes());
	}
	
	public Instruction parse(String string) {
		Line line = new LineParser().parse(" " + string, null, 0);
		line.resolveInstruction(factory);
		return line.getStatement().getInstruction();
	}
	
	public byte[] b(int... values) {
		byte[] bytes = new byte[values.length];
		for (int i = 0; i < values.length; i++)
			bytes[i] = (byte)values[i];
		return bytes;
	}
	
}
