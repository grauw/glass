package nl.grauw.asm.instructions;

public class Mulub extends Instruction {

	@Override
	public String getName() {
		return "mulub";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
