package nl.grauw.asm.instructions;

public class Retn extends Instruction {

	@Override
	public String getName() {
		return "retn";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
