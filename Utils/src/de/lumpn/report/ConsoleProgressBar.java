package de.lumpn.report;

import java.util.Arrays;

public class ConsoleProgressBar implements ProgressConsumer {

	@Override
	public void reset(String caption) {
		System.out.println("---------- " + caption + " ----------");
	}

	@Override
	public void set(int current, int total) {
		String filled = repeat('-', current);
		String empty = repeat(' ', total - current);
		System.out.println("[" + filled + ">" + empty + "]");
	}

	private static String repeat(char c, int count) {
		if (count <= 0) return "";
		char[] chars = new char[count];
		Arrays.fill(chars, c);
		return new String(chars);
	}
}
