package nl.grauw.asm.instructions;

public class Muluw extends Instruction {

	@Override
	public String getName() {
		return "muluw";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
