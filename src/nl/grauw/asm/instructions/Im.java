package nl.grauw.asm.instructions;

public class Im extends Instruction {

	@Override
	public String getName() {
		return "im";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
