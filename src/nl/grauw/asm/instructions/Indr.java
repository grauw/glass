package nl.grauw.asm.instructions;

public class Indr extends Instruction {

	@Override
	public String getName() {
		return "indr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xBA };
	}

}
