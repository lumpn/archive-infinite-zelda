package de.lumpn.zelda.layout.test;

import java.util.Random;
import org.junit.Test;
import de.lumpn.zelda.layout.Boundary;
import de.lumpn.zelda.layout.ZeldaLayout;
import de.lumpn.zelda.layout.ZeldaLayoutBuilder;

public class ZeldaLayoutBuilderTest {

	@Test
	public void testBuild1() {
		// TODO terminate earlier than exhaustive search by extension
		Boundary boundary = new Boundary(-2, 2, 0, 4, 0, 0);
		Random random = new Random();
		ZeldaLayoutBuilder builder = new ZeldaLayoutBuilder(boundary, random);
		ZeldaLayout layout = builder.build();
	}

	@Test
	public void testBuildMimimal() {
		Random random = new Random();
		Boundary boundary = new Boundary(-2, 2, 0, 4, 0, 0);
		ZeldaLayoutBuilder builder = new ZeldaLayoutBuilder(boundary, random);
		builder.addUndirectedTransition(0, 1, " ");
		ZeldaLayout layout = builder.build();
	}

	@Test
	public void testBuildOneRoom() {
		Random random = new Random();
		Boundary boundary = new Boundary(-2, 2, 0, 4, 0, 0);
		ZeldaLayoutBuilder builder = new ZeldaLayoutBuilder(boundary, random);
		builder.addUndirectedTransition(0, 2, " ");
		builder.addUndirectedTransition(2, 1, " ");
		ZeldaLayout layout = builder.build();
	}

	@Test
	public void testBuildDoubleTransition() {
		Random random = new Random();
		Boundary boundary = new Boundary(-2, 2, 0, 4, 0, 0);
		ZeldaLayoutBuilder builder = new ZeldaLayoutBuilder(boundary, random);
		builder.addUndirectedTransition(0, 1, " ");
		builder.addUndirectedTransition(0, 1, " ");
		ZeldaLayout layout = builder.build();
	}
}
