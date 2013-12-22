package nl.grauw.asm.instructions;

public class Rl extends Instruction {

	@Override
	public String getName() {
		return "rl";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
