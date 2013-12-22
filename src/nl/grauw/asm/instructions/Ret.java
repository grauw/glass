package nl.grauw.asm.instructions;

public class Ret extends Instruction {

	@Override
	public String getName() {
		return "ret";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
