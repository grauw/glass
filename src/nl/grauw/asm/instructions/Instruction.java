package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.ExpressionSchema;
import nl.grauw.asm.expressions.Register;

public abstract class Instruction {
	
	public static ExpressionSchema ARGUMENTS_NONE = ExpressionSchema.NONE;
	
	public abstract byte[] getBytes();
	
	/**
	 * Inserts index register prefix in the object code if needed.
	 */
	public byte[] indexifyDirect(Register register, byte... objectCode) {
		if (!register.isIndex())
			return objectCode;
		if (objectCode.length < 1 || objectCode.length > 3)
			throw new RuntimeException("Too little / too much object code.");
		if (objectCode[0] == (byte)0xED)
			throw new RuntimeException("Can not indexify ED prefixed instructions.");
		
		if (objectCode.length == 1)
			return new byte[] { register.getIndexCode(), objectCode[0] };
		if (objectCode.length == 2)
			return new byte[] { register.getIndexCode(), objectCode[0], objectCode[1] };
		return new byte[] { register.getIndexCode(), objectCode[0], objectCode[1], objectCode[2] };
	}
	
	/**
	 * Inserts index register prefix + offset in the object code if needed.
	 */
	public byte[] indexifyIndirect(Register register, byte... objectCode) {
		if (!register.isIndex())
			return objectCode;
		if (!register.isPair())
			throw new RuntimeException("Must be a register pair for indirect addressing.");
		if (objectCode.length < 1 || objectCode.length > 2)
			throw new RuntimeException("Too little / too much object code.");
		if (objectCode[0] == (byte)0xED)
			throw new RuntimeException("Can not indexify ED prefixed instructions.");
		
		if (objectCode.length == 1)
			return new byte[] { register.getIndexCode(), objectCode[0], (byte)register.getOffset() };
		return new byte[] { register.getIndexCode(), objectCode[0], (byte)register.getOffset(), objectCode[1] };
	}
	
}
