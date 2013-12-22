package nl.grauw.asm.instructions;

public class Rst extends Instruction {

	@Override
	public String getName() {
		return "rst";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
