package nl.grauw.asm.instructions;

public class Call extends Instruction {

	@Override
	public String getName() {
		return "call";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
