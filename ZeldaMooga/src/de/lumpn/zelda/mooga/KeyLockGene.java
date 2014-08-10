package de.lumpn.zelda.mooga;

import java.util.Random;
import de.lumpn.zelda.puzzle.VariableLookup;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;
import de.lumpn.zelda.puzzle.script.ZeldaScripts;

public final class KeyLockGene extends ZeldaGene {

	public KeyLockGene(ZeldaConfiguration configuration, Random random) {
		super(configuration);

		this.keyLocation = randomLocation(random);
		this.lockStart = randomLocation(random);
		this.lockEnd = differentLocation(lockStart, random);
	}

	private KeyLockGene(ZeldaConfiguration configuration, int keyLocation, int lockStart, int lockEnd) {
		super(configuration);
		this.keyLocation = keyLocation;
		this.lockStart = lockStart;
		this.lockEnd = lockEnd;
	}

	@Override
	public KeyLockGene mutate(Random random) {
		return new KeyLockGene(getConfiguration(), random);
	}

	@Override
	public void express(ZeldaPuzzleBuilder builder) {
		VariableLookup lookup = builder.lookup();

		// spawn key
		builder.addScript(keyLocation, ZeldaScripts.createKey(lookup));

		// spawn lock
		builder.addUndirectedTransition(lockStart, lockEnd, ZeldaScripts.createLock(lookup));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + keyLocation;
		result = prime * result + lockEnd;
		result = prime * result + lockStart;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof KeyLockGene)) return false;
		KeyLockGene other = (KeyLockGene) obj;
		if (keyLocation != other.keyLocation) return false;
		if (lockEnd != other.lockEnd) return false;
		if (lockStart != other.lockStart) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Key: %d, Lock: %d--%d", keyLocation, lockStart, lockEnd);
	}

	// location of key
	private final int keyLocation;

	// transition location of lock
	private final int lockStart, lockEnd;
}
