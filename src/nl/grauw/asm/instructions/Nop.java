package nl.grauw.asm.instructions;

public class Nop extends Instruction {

	@Override
	public String getName() {
		return "nop";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
