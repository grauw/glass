package nl.grauw.asm.instructions;

public class Sbc extends Instruction {

	@Override
	public String getName() {
		return "sbc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
