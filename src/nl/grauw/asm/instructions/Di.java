package nl.grauw.asm.instructions;

public class Di extends Instruction {

	@Override
	public String getName() {
		return "di";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
