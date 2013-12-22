package nl.grauw.asm.instructions;

public class Res extends Instruction {

	@Override
	public String getName() {
		return "res";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
