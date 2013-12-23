package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Add;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Group;
import nl.grauw.asm.expressions.IntegerLiteral;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Sequence;
import nl.grauw.asm.expressions.Subtract;

public abstract class Arithmetic8Bit extends Instruction {
	
	private Expression argument;
	private InstructionMask mask;
	
	public Arithmetic8Bit(Expression arguments, InstructionMask mask) {
		if (arguments instanceof Sequence)
			throw new ArgumentException();
		
		this.argument = arguments;
		this.mask = mask;
	}
	
	@Override
	public byte[] getBytes() {
		if (argument instanceof IntegerLiteral) {
			int value = ((IntegerLiteral)argument).getValue();
			return new byte[] { (byte)(0xC6 | mask.mask), (byte)value };
		} else {
			Register register = getRegister(argument);
			if (register != null) {
				if (!register.isPair()) {
					if (!register.isIndex())
						return new byte[] { (byte)(0x80 | mask.mask | register.getCode()) };
					else
						return new byte[] { register.getIndexCode(), (byte)(0x80 | mask.mask | register.getCode()) };
				} else {
					if (!register.isIndex())
						return new byte[] { (byte)(0x80 | mask.mask | 6) };  // TODO magic constant
					else
						return new byte[] { register.getIndexCode(), (byte)(0x80 | mask.mask | 6), getOffset(argument) };
				}
			}
		}
		throw new ArgumentException();
	}
	
	private Register getRegister(Expression argument) {
		if (argument instanceof Register) {
			Register register = (Register)argument;
			if (!register.isPair())
				return register;
		} else if (argument instanceof Group) {
			Expression contents = ((Group)argument).getTerm();
			if (contents == Register.HL) {
				return Register.HL;
			} else if (contents instanceof Add) {
				Add addition = (Add)contents;
				if (addition.getAugend() instanceof Register) {
					if (addition.getAugend() == Register.IX)
						return Register.IX;
					else if (addition.getAugend() == Register.IY)
						return Register.IY;
				}
			} else if (contents instanceof Subtract) {
				Subtract subtraction = (Subtract)contents;
				if (subtraction.getMinuend() instanceof Register) {
					if (subtraction.getMinuend() == Register.IX)
						return Register.IX;
					else if (subtraction.getMinuend() == Register.IY)
						return Register.IY;
				}
			}
		}
		throw new ArgumentException("Not a register.");
	}
	
	private byte getOffset(Expression argument) {
		Expression contents = ((Group)argument).getTerm();
		if (contents instanceof Add) {
			Expression addend = ((Add)contents).getAddend();
			if (addend instanceof IntegerLiteral) {
				int offset = ((IntegerLiteral)addend).getValue();
				if (offset < -128 || offset > 127)
					throw new ArgumentException("Offset out of range.");
				return (byte)offset;
			}
		} else if (contents instanceof Subtract) {
			Expression subtrahend = ((Subtract)contents).getSubtrahend();
			if (subtrahend instanceof IntegerLiteral) {
				int offset = -((IntegerLiteral)subtrahend).getValue();
				if (offset < -128 || offset > 127)
					throw new ArgumentException("Offset out of range.");
				return (byte)offset;
			}
		}
		throw new ArgumentException("Not an offset.");
	}
	
	public enum InstructionMask {
		ADD_A(0b00000000),
		ADC_A(0b00001000),
		SUB(0b00010000),
		SBC_A(0b00011000),
		AND(0b00100000),
		XOR(0b00101000),
		OR(0b00110000),
		CP(0b00111000);
		
		private final int mask;
		private InstructionMask(int mask) {
			this.mask = mask;
		}
	}
	
}
