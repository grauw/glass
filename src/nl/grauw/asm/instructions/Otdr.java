package nl.grauw.asm.instructions;

public class Otdr extends Instruction {

	@Override
	public String getName() {
		return "otdr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
