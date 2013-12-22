package nl.grauw.asm.instructions;

public class Rlca extends Instruction {

	@Override
	public String getName() {
		return "rlca";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x07 };
	}

}
