package nl.grauw.asm.instructions;

public class Srl extends Instruction {

	@Override
	public String getName() {
		return "srl";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
