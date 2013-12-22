package nl.grauw.asm.instructions;

public class Rld extends Instruction {

	@Override
	public String getName() {
		return "rld";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0x6F };
	}

}
