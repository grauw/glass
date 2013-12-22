package nl.grauw.asm.instructions;

public class Rlc extends Instruction {

	@Override
	public String getName() {
		return "rlc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
