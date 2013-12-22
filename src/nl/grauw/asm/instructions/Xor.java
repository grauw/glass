package nl.grauw.asm.instructions;

public class Xor extends Instruction {

	@Override
	public String getName() {
		return "xor";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
