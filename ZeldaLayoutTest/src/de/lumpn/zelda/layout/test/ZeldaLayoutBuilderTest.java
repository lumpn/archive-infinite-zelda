package de.lumpn.zelda.layout.test;

import org.junit.Assert;
import org.junit.Test;
import de.lumpn.zelda.layout.ScriptIdentifier;
import de.lumpn.zelda.layout.ZeldaLayout;
import de.lumpn.zelda.layout.ZeldaLayoutBuilder;

public class ZeldaLayoutBuilderTest {

	@Test
	public void testBuild1() {
		ZeldaLayoutBuilder builder = new ZeldaLayoutBuilder();
		ZeldaLayout layout = builder.build();
	}

	@Test
	public void testBuild2() {
		ZeldaLayoutBuilder builder = new ZeldaLayoutBuilder();
		builder.addUndirectedTransition(0, 1, ScriptIdentifier.EMPTY.toString());
		ZeldaLayout layout = builder.build();
	}

}
