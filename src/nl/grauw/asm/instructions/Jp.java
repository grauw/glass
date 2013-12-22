package nl.grauw.asm.instructions;

public class Jp extends Instruction {

	@Override
	public String getName() {
		return "jp";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
