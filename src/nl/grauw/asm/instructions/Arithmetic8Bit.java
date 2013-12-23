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
			int value = argument.evaluateInteger();
			return new byte[] { (byte)(0xC6 | getMask()), (byte)value };
		} else if (argument.isRegister()) {
			Register register = argument.evaluateRegister();
			if (!register.isPair()) {
				if (!register.isIndex())
					return new byte[] { (byte)(0x80 | getMask() | register.getCode()) };
				else
					return new byte[] { register.getIndexCode(), (byte)(0x80 | getMask() | register.getCode()) };
			} else if (argument instanceof Group && (register == Register.HL || register.isIndex())) {
				if (!register.isIndex())
					return new byte[] { (byte)(0x80 | getMask() | 6) };  // TODO magic constant
				else
					return new byte[] { register.getIndexCode(), (byte)(0x80 | getMask() | 6), (byte)register.getOffset() };
			}
		}
		throw new ArgumentException();
	}
	
}
