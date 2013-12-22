package nl.grauw.asm.instructions;

public class Ldir extends Instruction {

	@Override
	public String getName() {
		return "ldir";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB0 };
	}

}
