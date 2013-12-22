package nl.grauw.asm.instructions;

public class Cpi extends Instruction {

	@Override
	public String getName() {
		return "cpi";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xA1 };
	}

}
