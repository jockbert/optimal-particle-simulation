package eu.evensson.optpartsim.application;

import static eu.evensson.optpartsim.physics.Vector.polar;
import static eu.evensson.optpartsim.physics.Vector.vector;
import static java.lang.Math.PI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;
import java.util.Random;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;

public class RandomParticleGenerator implements ParticleGenerator {

	private static final double MAX_ANGLE = 2.0 * PI;
	private static final double START_TIME = 0.0;

	private final Random random;

	public RandomParticleGenerator(final Random random) {
		this.random = random;
	}

	@Override
	public List<Particle> generate(final long numParticles,
			final Box box,
			final double maxInitialVelocity) {
		final Iterator<Double> xPositions =
				randomNumbers(numParticles, box.x(), box.x() + box.width());
		final Iterator<Double> yPositions =
				randomNumbers(numParticles, box.y(), box.y() + box.height());
		final Iterator<Double> absVelocities =
				randomNumbers(numParticles, 0.0, maxInitialVelocity);
		final Iterator<Double> angles =
				randomNumbers(numParticles, 0.0, MAX_ANGLE);

		final List<Particle> particleList = new LinkedList<>();
		for (long index = 1; index <= numParticles; index++) {
			particleList.add(new Particle(index, START_TIME,
					vector(xPositions.next(), yPositions.next()),
					polar(absVelocities.next(), angles.next())));
		}
		return particleList;
	}

	private OfDouble randomNumbers(final long numValues,
			final double minValue, final double maxValue) {
		return random.doubles(numValues, minValue, maxValue).iterator();
	}

}
