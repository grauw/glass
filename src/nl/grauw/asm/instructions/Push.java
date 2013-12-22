package nl.grauw.asm.instructions;

public class Push extends Instruction {

	@Override
	public String getName() {
		return "push";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
