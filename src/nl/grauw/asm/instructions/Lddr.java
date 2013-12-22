package nl.grauw.asm.instructions;

public class Lddr extends Instruction {

	@Override
	public String getName() {
		return "lddr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB8 };
	}

}
