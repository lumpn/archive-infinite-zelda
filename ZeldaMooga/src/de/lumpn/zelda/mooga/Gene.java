package de.lumpn.zelda.mooga;

import java.util.Random;

public interface Gene {

	Gene mutate(Random random);

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object obj);
}
