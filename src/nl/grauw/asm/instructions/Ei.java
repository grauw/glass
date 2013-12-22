package nl.grauw.asm.instructions;

public class Ei extends Instruction {

	@Override
	public String getName() {
		return "ei";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
