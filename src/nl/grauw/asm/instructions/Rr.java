package nl.grauw.asm.instructions;

public class Rr extends Instruction {

	@Override
	public String getName() {
		return "rr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
