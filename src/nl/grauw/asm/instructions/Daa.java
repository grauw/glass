package nl.grauw.asm.instructions;

public class Daa extends Instruction {

	@Override
	public String getName() {
		return "daa";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x27 };
	}

}
