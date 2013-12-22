package nl.grauw.asm.instructions;

public class Ex extends Instruction {

	@Override
	public String getName() {
		return "ex";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
