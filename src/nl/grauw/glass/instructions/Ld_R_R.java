package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Ld_R_R extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
	
	private Register register1;
	private Register register2;
	
	public Ld_R_R(Register register1, Register register2) {
		this.register1 = register1;
		this.register2 = register2;
		
		if (register1.isPair() && register2.isPair())
			throw new ArgumentException();  // forbid (hl),(hl), (ix+0),(ix-0), etc.
		if (register1.isIndex() && register2.isIndex() && register1.getIndexCode() != register2.getIndexCode())
			throw new ArgumentException();  // forbid ixh,iyl, etc.
		if (register1.isIndex() && register2.isPair() || register1.isPair() && register2.isIndex())
			throw new ArgumentException();  // forbid (hl),ixh, ixh,(ix+0)
		if (register1.isIndex() && !register1.isPair() && (register2 == Register.H || register2 == Register.L))
			throw new ArgumentException();  // forbid iyl,h
		if (register2.isIndex() && !register2.isPair() && (register1 == Register.H || register1 == Register.L))
			throw new ArgumentException();  // forbid h,iyl
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyIndirect(register1.isIndex() ? register1 : register2, 1);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return indexifyIndirect(register1.isIndex() ? register1 : register2,
				(byte)(0x40 | register1.get8BitCode() << 3 | register2.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ld", this);
			scope.addInstruction("LD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_R_R(arguments.getElement(0).getRegister(), arguments.getElement(1).getRegister());
			return null;
		}
		
	}
	
}
