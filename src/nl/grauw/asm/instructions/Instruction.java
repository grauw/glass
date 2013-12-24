package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.expressions.Register;

public abstract class Instruction {
	
	public static Schema ARGUMENTS_NONE = new Schema();
	public static Schema ARGUMENTS_N = new Schema(Schema.DIRECT_N);
	public static Schema ARGUMENTS_A_N = new Schema(Schema.DIRECT_A, Schema.DIRECT_N);
	public static Schema ARGUMENTS_R = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
	public static Schema ARGUMENTS_A_R = new Schema(Schema.DIRECT_A, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
	public static Schema ARGUMENTS_N_R = new Schema(Schema.DIRECT_N, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
	
	public abstract byte[] getBytes();
	
	/**
	 * Inserts index register prefix in the object code if needed.
	 */
	public byte[] indexifyDirect(Register register, byte byte1) {
		if (!register.isIndex())
			return new byte[] { byte1 };
		return new byte[] { register.getIndexCode(), byte1 };
	}
	
	public byte[] indexifyDirect(Register register, byte byte1, byte byte2) {
		if (!register.isIndex())
			return new byte[] { byte1, byte2 };
		return new byte[] { register.getIndexCode(), byte1, byte2 };
	}
	
	public byte[] indexifyDirect(Register register, byte byte1, byte byte2, byte byte3) {
		if (!register.isIndex())
			return new byte[] { byte1, byte2, byte3 };
		return new byte[] { register.getIndexCode(), byte1, byte2, byte3 };
	}
	
	/**
	 * Inserts index register prefix + offset in the object code if needed.
	 */
	public byte[] indexifyIndirect(Register register, byte byte1) {
		if (!register.isIndex())
			return new byte[] { byte1 };
		if (!register.isPair())
			return indexifyDirect(register, byte1);
		return new byte[] { register.getIndexCode(), byte1, register.getIndexOffset() };
	}
	
	public byte[] indexifyIndirect(Register register, byte byte1, byte byte2) {
		if (!register.isIndex())
			return new byte[] { byte1, byte2 };
		if (!register.isPair())
			return indexifyDirect(register, byte1, byte2);
		return new byte[] { register.getIndexCode(), byte1, register.getIndexOffset(), byte2 };
	}
	
}
