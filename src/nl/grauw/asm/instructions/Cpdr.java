package nl.grauw.asm.instructions;

public class Cpdr extends Instruction {

	@Override
	public String getName() {
		return "cpdr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB9 };
	}

}
