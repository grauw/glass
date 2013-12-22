package nl.grauw.asm.instructions;

public class Pop extends Instruction {

	@Override
	public String getName() {
		return "pop";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
