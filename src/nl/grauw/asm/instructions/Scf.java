package nl.grauw.asm.instructions;

public class Scf extends Instruction {

	@Override
	public String getName() {
		return "scf";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x37 };
	}

}
