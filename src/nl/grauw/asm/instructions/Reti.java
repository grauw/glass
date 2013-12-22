package nl.grauw.asm.instructions;

public class Reti extends Instruction {

	@Override
	public String getName() {
		return "reti";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
