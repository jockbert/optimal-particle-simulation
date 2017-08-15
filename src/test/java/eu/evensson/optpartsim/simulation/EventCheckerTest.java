package eu.evensson.optpartsim.simulation;

import static eu.evensson.optpartsim.physics.Vector.vector;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import eu.evensson.optpartsim.physics.Box;
import eu.evensson.optpartsim.physics.Particle;
import eu.evensson.optpartsim.physics.Vector;

@DisplayName("An Event Checker")
@RunWith(JUnitPlatform.class)
public class EventCheckerTest {

	private static final long ID = 0;
	private static final Box WALLS = new Box(0.0, 0.0, 20.0, 10.0);
	private static final Vector CENTER = vector(
			(WALLS.x() + WALLS.width()) / 2.0 ,
			(WALLS.y() + WALLS.height()) / 2.0);
	private static final double TIME = 100.0;

	private CellStructure cellStructure = mock(CellStructure.class);

	private EventChecker anEventChecker;

	@BeforeEach
	void createEventChecker() {
		when(cellStructure.getWalls()).thenReturn(WALLS);
		anEventChecker = new EventChecker(cellStructure);
	}

	@DisplayName("returns wall bounce left event")
	@Test
	void returnsWallBounceLeftEvent() {
		final double speed = 1.0;
		final Vector velocity = vector(-speed , 0.0);
		final Particle particle = new Particle(ID, TIME, CENTER, velocity);

		final Event event = anEventChecker.check(particle);

		final double timeToWall = (CENTER.x() - WALLS.x()) / speed;
		final WallBounceEvent expectedEvent = new WallBounceEvent(
				TIME + timeToWall, particle, Particle.Direction.HORIZONTAL);
		assertThat(event, is(expectedEvent));
	}

	@DisplayName("returns wall bounce right event")
	@Test
	void returnsWallBounceRightEvent() {
		final double speed = 1.0;
		final Vector velocity = vector(speed , 0.0);
		final Particle particle = new Particle(ID, TIME, CENTER, velocity);

		final Event event = anEventChecker.check(particle);

		final double timeToWall = (CENTER.x() - WALLS.x()) / speed;
		final WallBounceEvent expectedEvent = new WallBounceEvent(
				TIME + timeToWall, particle, Particle.Direction.HORIZONTAL);
		assertThat(event, is(expectedEvent));
	}

	@DisplayName("returns wall bounce top event")
	@Test
	void returnsWallBounceTopEvent() {
		final double speed = 1.0;
		final Vector velocity = vector(0.0, -speed);
		final Particle particle = new Particle(ID, TIME, CENTER, velocity);

		final Event event = anEventChecker.check(particle);

		final double timeToWall = (CENTER.y() - WALLS.y()) / speed;
		final WallBounceEvent expectedEvent = new WallBounceEvent(
				TIME + timeToWall, particle, Particle.Direction.VERTICAL);
		assertThat(event, is(expectedEvent));
	}
}