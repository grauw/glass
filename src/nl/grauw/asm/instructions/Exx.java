package nl.grauw.asm.instructions;

public class Exx extends Instruction {

	@Override
	public String getName() {
		return "exx";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xD9 };
	}

}
