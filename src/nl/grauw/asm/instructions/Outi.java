package nl.grauw.asm.instructions;

public class Outi extends Instruction {

	@Override
	public String getName() {
		return "outi";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
