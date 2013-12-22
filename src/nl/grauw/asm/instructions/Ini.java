package nl.grauw.asm.instructions;

public class Ini extends Instruction {

	@Override
	public String getName() {
		return "ini";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xA2 };
	}

}
