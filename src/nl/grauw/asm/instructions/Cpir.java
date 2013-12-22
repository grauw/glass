package nl.grauw.asm.instructions;

public class Cpir extends Instruction {

	@Override
	public String getName() {
		return "cpir";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB1 };
	}

}
