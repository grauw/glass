package nl.grauw.glass;

import java.util.Arrays;
import java.util.List;

public class TestBase {

	public static byte[] b(int... values) {
		byte[] bytes = new byte[values.length];
		for (int i = 0; i < values.length; i++)
			bytes[i] = (byte)values[i];
		return bytes;
	}

	public static List<String> s(String... values) {
		return Arrays.asList(values);
	}

}
