package nl.grauw.asm.instructions;

public class Rrc extends Instruction {

	@Override
	public String getName() {
		return "rrc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
