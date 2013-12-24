package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Group;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Sequence;

public abstract class Arithmetic8Bit extends Instruction {
	
	private Expression argument;
	
	public Arithmetic8Bit(Expression arguments) {
		if (arguments instanceof Sequence)
			throw new ArgumentException();
		
		this.argument = arguments;
	}
	
	protected abstract int getMask();
	
	@Override
	public byte[] getBytes() {
		if (argument.isInteger()) {
			int value = argument.getInteger();
			return new byte[] { (byte)(0xC6 | getMask()), (byte)value };
		} else if (argument.isRegister()) {
			Register register = argument.getRegister();
			if (!register.isPair()) {
				return indexifyDirect(register, (byte)(0x80 | getMask() | register.get8BitCode()));
			} else if (argument instanceof Group) {
				return indexifyIndirect(register, (byte)(0x80 | getMask() | register.get8BitCode()));
			}
		}
		throw new ArgumentException();
	}
	
}
