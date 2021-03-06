package eu.evensson.optpartsim.application;

import static eu.evensson.optpartsim.physics.Vector.polar;
import static eu.evensson.optpartsim.physics.Vector.vector;
import static java.lang.Math.PI;
import static java.util.Arrays.stream;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.physics.Vector;

@DisplayName("An Argument Parser")
public class ParticleGeneratorTest {

	private static final double EXPECTED_START_TIME = 0.0;

	Random random = mock(Random.class);

	ParticleGenerator particleGenerator;

	@BeforeEach
	void createParticleGenerator() {
		particleGenerator = new RandomParticleGenerator(random);
	}

	@DisplayName("returns empty list when num particles is zero")
	@Test
	void returnsEmptyListWhenNumParticlesIsZero() {
		final List<Particle> particleList = particleGenerator.generate(
				0, new Box(0.0, 0.0, 0.0, 0.0), 0.0);

		assertThat(particleList, is(empty()));
	}

	@DisplayName("returns list with particles")
	@ParameterizedTest
	@CsvSource({ "1, 5.0, 10.0, 20.0", "2, 10.0, 20.0, 40.0" })
	void returnsListWithParticles(final int numParticles,
			final double boxHeight, final double boxWidth,
			final double maxVelocity) {
		final double[] expectedXPositions =
				stubRandomDoubleStream(numParticles, 0.0, boxWidth);
		final double[] expectedYPositions =
				stubRandomDoubleStream(numParticles, 0.0, boxHeight);
		final double[] expectedAbsVelocities =
				stubRandomDoubleStream(numParticles, 0.0, maxVelocity);
		final double[] expectedAngles =
				stubRandomDoubleStream(numParticles, 0.0, 2.0 * PI);

		final List<Particle> particleList = particleGenerator.generate(
				numParticles,
				new Box(0.0, 0.0, boxWidth, boxHeight),
				maxVelocity);

		assertThat(particleList, hasSize(numParticles));
		int index = 0;
		for (final Particle actualParticle : particleList) {
			assertThat(actualParticle, is(notNullValue()));
			final Vector position =
					vector(expectedXPositions[index], expectedYPositions[index]);
			final Vector velocity =
					polar(expectedAbsVelocities[index], expectedAngles[index]);
			final Particle expectedParticle =
					new Particle(index + 1, EXPECTED_START_TIME, position, velocity);
			assertThat(actualParticle, is(expectedParticle));
			index += 1;
		}
	}

	private double[] stubRandomDoubleStream(final int numValues,
			final double minValue, final double maxValue) {
		final double[] values =
				new Random().doubles(numValues, minValue, maxValue).toArray();
		when(random.doubles(numValues, minValue, maxValue))
				.thenReturn(stream(values));
		return values;
	}
}
