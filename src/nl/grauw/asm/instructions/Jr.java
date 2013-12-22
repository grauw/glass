package nl.grauw.asm.instructions;

public class Jr extends Instruction {

	@Override
	public String getName() {
		return "jr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
