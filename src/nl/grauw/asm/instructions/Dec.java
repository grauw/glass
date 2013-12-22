package nl.grauw.asm.instructions;

public class Dec extends Instruction {

	@Override
	public String getName() {
		return "dec";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
