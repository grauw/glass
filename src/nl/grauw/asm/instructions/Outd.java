package nl.grauw.asm.instructions;

public class Outd extends Instruction {

	@Override
	public String getName() {
		return "outd";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xAB };
	}

}
