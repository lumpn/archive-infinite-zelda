package de.lumpn.zelda.mooga;

import de.lumpn.mooga.Environment;
import de.lumpn.mooga.Genome;
import de.lumpn.zelda.puzzle.ZeldaPuzzle;
import de.lumpn.zelda.puzzle.ZeldaPuzzleBuilder;

public final class ZeldaEnvironment implements Environment {

	@Override
	public ZeldaIndividual evaluate(Genome g) {
		ZeldaGenome genome = (ZeldaGenome) g;

		// build puzzle
		ZeldaPuzzleBuilder builder = new ZeldaPuzzleBuilder();
		genome.express(builder);
		ZeldaPuzzle puzzle = builder.puzzle();

		// crawl puzzle
		puzzle.crawl();

		// create individual
		return new ZeldaIndividual(genome, puzzle);
	}
}
