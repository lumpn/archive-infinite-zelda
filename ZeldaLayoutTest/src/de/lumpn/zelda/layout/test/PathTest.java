package de.lumpn.zelda.layout.test;

import org.junit.Assert;
import org.junit.Test;
import de.lumpn.util.Pair;
import de.lumpn.zelda.layout.Path;
import de.lumpn.zelda.layout.Position;

public class PathTest {

	@Test
	public void testPath() {
		Path path = new Path(new Position(0, 0, 0));

		Assert.assertNotNull(path);
	}

	@Test
	public void testGetPosition() {
		Position position = new Position(1, 2, 3);
		Path path = new Path(position);

		Assert.assertEquals(position, path.getPosition());
	}

	@Test
	public void testHasNext() {
		Path path = new Path(new Position(0, 0, 0));
		Path next = new Path(new Position(1, 0, 0));
		path.setNext(next);

		Assert.assertTrue(path.hasNext());
		Assert.assertFalse(next.hasNext());
	}

	@Test
	public void testNext() {
		Path path = new Path(new Position(0, 0, 0));
		Path next = new Path(new Position(1, 0, 0));
		path.setNext(next);

		Assert.assertSame(next, path.next());
	}

	@Test
	public void testLength() {
		Path a = new Path(new Position(0, 0, 0));
		Path b = new Path(new Position(1, 0, 0));
		Path c = new Path(new Position(2, 0, 0));
		a.setNext(b);
		b.setNext(c);

		Assert.assertEquals(0, Path.length(c));
		Assert.assertEquals(1, Path.length(b));
		Assert.assertEquals(2, Path.length(a));
	}

	@Test
	public void testSplit() {
		Path a = new Path(new Position(0, 0, 0));
		Path b = new Path(new Position(1, 0, 0));
		Path c = new Path(new Position(2, 0, 0));
		Path d = new Path(new Position(2, 1, 0));
		Path e = new Path(new Position(2, 2, 0));
		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(e);

		int length = Path.length(a);
		Pair<Path> split = Path.split(a, length / 2);

		Assert.assertEquals(2, Path.length(split.first()));
		Assert.assertEquals(2, Path.length(split.second()));
	}
}
