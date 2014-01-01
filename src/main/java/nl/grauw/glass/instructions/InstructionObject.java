package nl.grauw.glass.instructions;

import java.io.IOException;
import java.io.OutputStream;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Register;

public abstract class InstructionObject {
	
	public int resolve(Scope context, int address) {
		context.setAddress(address);
		return address + getSize(context);
	}
	
	public abstract int getSize(Scope context);
	
	public int generateObjectCode(Scope context, int address, OutputStream output) throws IOException {
		if (address != context.getAddress())
			throw new AssemblyException("Address changed between passes.");
		
		byte[] object = getBytes(context);
		output.write(object, 0, object.length);
		
		return address + object.length;
	}
	
	public abstract byte[] getBytes(Scope context);
	
	public int indexifyDirect(Register register, int size) {
		return register.isIndex() ? size + 1 : size;
	}
	
	public int indexifyIndirect(Register register, int size) {
		return register.isIndex() ? register.isPair() ? size + 2 : size + 1 : size;
	}
	
	/**
	 * Inserts index register prefix in the object code if needed.
	 */
	public byte[] indexifyDirect(Register register, byte byte1) {
		if (!register.isIndex())
			return new byte[] { byte1 };
		if (register.isPair() && register.getIndexOffset().getInteger() != 0)
			throw new ArgumentException("Can not have index offset for direct addressing.");
		return new byte[] { register.getIndexCode(), byte1 };
	}
	
	public byte[] indexifyDirect(Register register, byte byte1, byte byte2) {
		if (!register.isIndex())
			return new byte[] { byte1, byte2 };
		if (register.isPair() && register.getIndexOffset().getInteger() != 0)
			throw new ArgumentException("Can not have index offset for direct addressing.");
		return new byte[] { register.getIndexCode(), byte1, byte2 };
	}
	
	public byte[] indexifyDirect(Register register, byte byte1, byte byte2, byte byte3) {
		if (!register.isIndex())
			return new byte[] { byte1, byte2, byte3 };
		if (register.isPair() && register.getIndexOffset().getInteger() != 0)
			throw new ArgumentException("Can not have index offset for direct addressing.");
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
		int offset = register.getIndexOffset().getInteger();
		if (offset < -128 || offset > 127)
			throw new ArgumentException("Index offset out of range: " + offset);
		return new byte[] { register.getIndexCode(), byte1, (byte)offset };
	}
	
	public byte[] indexifyIndirect(Register register, byte byte1, byte byte2) {
		if (!register.isIndex())
			return new byte[] { byte1, byte2 };
		if (!register.isPair())
			return indexifyDirect(register, byte1, byte2);
		int offset = register.getIndexOffset().getInteger();
		if (offset < -128 || offset > 127)
			throw new ArgumentException("Index offset out of range: " + offset);
		return new byte[] { register.getIndexCode(), byte1, (byte)offset, byte2 };
	}
	
}
