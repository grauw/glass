package nl.grauw.asm.instructions;

public class Inir extends Instruction {

	@Override
	public String getName() {
		return "inir";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB2 };
	}

}
