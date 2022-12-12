package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Add;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Type;

public abstract class InstructionObject {

	private static final byte[] EMPTY_BYTES = new byte[] {};

	protected final Expression address;

	public InstructionObject(Expression address) {
		this.address = address;
	}

	public Expression resolve() {
		return new Add(getSize(), address).get(Type.INTEGER);
	}

	public abstract Expression getSize();

	public abstract byte[] getBytes();

	protected final Expression indexifyDirect(Register register, Expression size) {
		return register.isIndex() ? new Add(IntegerLiteral.ONE, size) : size;
	}

	protected final Expression indexifyIndirect(Register register, Expression size) {
		return register.isIndex() ? register.isPair() ? new Add(IntegerLiteral.TWO, size) : new Add(IntegerLiteral.ONE, size) : size;
	}

	/**
	 * Inserts index register prefix in the object code if needed.
	 */
	protected final byte[] indexifyDirect(Register register, int byte1) {
		if (!register.isIndex())
			return b(byte1);
		if (register.isPair() && register.getIndexOffset().getInteger() != 0)
			throw new ArgumentException("Can not have index offset for direct addressing.");
		return b(register.getIndexCode(), byte1);
	}

	protected final byte[] indexifyDirect(Register register, int byte1, int byte2) {
		if (!register.isIndex())
			return b(byte1, byte2);
		if (register.isPair() && register.getIndexOffset().getInteger() != 0)
			throw new ArgumentException("Can not have index offset for direct addressing.");
		return b(register.getIndexCode(), byte1, byte2);
	}

	protected final byte[] indexifyDirect(Register register, int byte1, int byte2, int byte3) {
		if (!register.isIndex())
			return b(byte1, byte2, byte3);
		if (register.isPair() && register.getIndexOffset().getInteger() != 0)
			throw new ArgumentException("Can not have index offset for direct addressing.");
		return b(register.getIndexCode(), byte1, byte2, byte3);
	}

	/**
	 * Inserts index register prefix + offset in the object code if needed.
	 */
	protected final byte[] indexifyIndirect(Register register, int byte1) {
		if (!register.isIndex())
			return b(byte1);
		if (!register.isPair())
			return indexifyDirect(register, byte1);
		int offset = register.getIndexOffset().getInteger();
		if (offset < -128 || offset > 127)
			throw new ArgumentException("Index offset out of range: " + offset);
		return b(register.getIndexCode(), byte1, offset);
	}

	protected final byte[] indexifyIndirect(Register register, int byte1, int byte2) {
		if (!register.isIndex())
			return b(byte1, byte2);
		if (!register.isPair())
			return indexifyDirect(register, byte1, byte2);
		int offset = register.getIndexOffset().getInteger();
		if (offset < -128 || offset > 127)
			throw new ArgumentException("Index offset out of range: " + offset);
		return b(register.getIndexCode(), byte1, offset, byte2);
	}

	protected final byte[] indexifyOnlyIndirect(Register register, int byte1, int byte2) {
		if (!register.isIndex())
			return b(byte1, byte2);
		if (!register.isPair())
			throw new ArgumentException();
		int offset = register.getIndexOffset().getInteger();
		if (offset < -128 || offset > 127)
			throw new ArgumentException("Index offset out of range: " + offset);
		return b(register.getIndexCode(), byte1, offset, byte2);
	}

	protected final byte[] b() {
		return EMPTY_BYTES;
	}

	protected final byte[] b(int byte1) {
		return new byte[] { (byte)byte1 };
	}

	protected final byte[] b(int byte1, int byte2) {
		return new byte[] { (byte)byte1, (byte)byte2 };
	}

	protected final byte[] b(int byte1, int byte2, int byte3) {
		return new byte[] { (byte)byte1, (byte)byte2, (byte)byte3 };
	}

	protected final byte[] b(int byte1, int byte2, int byte3, int byte4) {
		return new byte[] { (byte)byte1, (byte)byte2, (byte)byte3, (byte)byte4 };
	}

}
