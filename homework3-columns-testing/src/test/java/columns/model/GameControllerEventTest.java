package columns.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import columns.model.doubles.FakePlatform;
import columns.model.doubles.FakeRandomGenerator;
import columns.model.doubles.FakeScreen;

// Tests for GameController.processEvent using fake Platform and Screen,
// so no real time, randomness or AWT is needed.
class GameControllerEventTest {

	private FakeScreen screen;
	private FakePlatform platform;
	private GameController controller;

	@BeforeEach
	void setUp() {
		screen = new FakeScreen();
		platform = new FakePlatform(screen);
		controller = new GameController(platform);
	}

	private Figure placeFigure(int x, int y, int c1, int c2, int c3) {
		Figure f = new Figure(new FakeRandomGenerator(0, 1, 2));
		f.x = x;
		f.y = y;
		f.c[1] = c1;
		f.c[2] = c2;
		f.c[3] = c3;
		controller.board.figure = f;
		return f;
	}

	// LEFT

	@Test
	void leftEventMovesFigureLeftWhenAllowed() {
		Figure f = placeFigure(4, 1, 1, 2, 3);
		controller.processEvent(GameEvent.LEFT);
		assertEquals(3, f.x);
	}

	@Test
	void leftEventDoesNotMoveThroughWall() {
		Figure f = placeFigure(1, 1, 1, 2, 3);
		controller.processEvent(GameEvent.LEFT);
		assertEquals(1, f.x);
	}

	@Test
	void leftEventDoesNotMoveThroughBlock() {
		Figure f = placeFigure(4, 1, 1, 2, 3);
		controller.board.newField[3][3] = 5; // blocking cell at [x-1][y+2]
		controller.processEvent(GameEvent.LEFT);
		assertEquals(4, f.x);
	}

	// RIGHT

	@Test
	void rightEventMovesFigureRightWhenAllowed() {
		Figure f = placeFigure(4, 1, 1, 2, 3);
		controller.processEvent(GameEvent.RIGHT);
		assertEquals(5, f.x);
	}

	@Test
	void rightEventDoesNotMoveThroughWall() {
		Figure f = placeFigure(GameConfig.WIDTH, 1, 1, 2, 3); // x = 7
		controller.processEvent(GameEvent.RIGHT);
		assertEquals(GameConfig.WIDTH, f.x);
	}

	// rotation

	@Test
	void upEventRotatesFigureUpward() {
		Figure f = placeFigure(4, 1, 1, 2, 3);
		controller.processEvent(GameEvent.UP);
		assertArrayEquals(new int[] { 0, 2, 3, 1 }, f.c);
	}

	@Test
	void downEventRotatesFigureDownward() {
		Figure f = placeFigure(4, 1, 1, 2, 3);
		controller.processEvent(GameEvent.DOWN);
		assertArrayEquals(new int[] { 0, 3, 1, 2 }, f.c);
	}

	// DROP

	@Test
	void dropEventMovesFigureToBottomAndResetsTimer() {
		Figure f = placeFigure(4, 1, 1, 2, 3);
		platform.tc = 999; // some stale timer value

		controller.processEvent(GameEvent.DROP);

		assertEquals(13, f.y, "figure dropped to the bottom of an empty column");
		assertEquals(0, platform.getTc(), "DROP resets the controller timer to 0");
	}

	// LEVEL_UP / LEVEL_DOWN

	@Test
	void levelUpIncreasesLevel() {
		placeFigure(4, 1, 1, 2, 3);
		controller.board.level = 0;
		controller.processEvent(GameEvent.LEVEL_UP);
		assertEquals(1, controller.board.level);
	}

	@Test
	void levelUpStopsAtMaximum() {
		placeFigure(4, 1, 1, 2, 3);
		controller.board.level = GameConfig.MAX_LEVEL; // 7
		controller.processEvent(GameEvent.LEVEL_UP);
		assertEquals(GameConfig.MAX_LEVEL, controller.board.level);
	}

	@Test
	void levelDownDecreasesLevel() {
		placeFigure(4, 1, 1, 2, 3);
		controller.board.level = 3;
		controller.processEvent(GameEvent.LEVEL_DOWN);
		assertEquals(2, controller.board.level);
	}

	@Test
	void levelDownStopsAtZero() {
		placeFigure(4, 1, 1, 2, 3);
		controller.board.level = 0;
		controller.processEvent(GameEvent.LEVEL_DOWN);
		assertEquals(0, controller.board.level);
	}

	@Test
	void manualLevelChangeResetsMatchCounter() {
		placeFigure(4, 1, 1, 2, 3);
		controller.board.level = 3;
		controller.board.figuresMatchedCounter = 20;

		controller.processEvent(GameEvent.LEVEL_UP);

		assertEquals(0, controller.board.figuresMatchedCounter);
	}

	// drawing interaction

	@Test
	void movementDrivesDrawingThroughTheScreen() {
		placeFigure(4, 1, 1, 2, 3);
		screen.calls.clear();

		controller.processEvent(GameEvent.LEFT);

		// LEFT hides the old figure and draws the new one -> screen received calls
		assertTrue(screen.calls.size() > 0, "controller should draw via the screen");
		assertTrue(screen.countCallsContaining("fillRect") > 0);
	}

	// PAUSE (optional, per assignment)

	@Test
	void pauseLoopsUntilKeyPressedThenResumesTimer() {
		placeFigure(4, 1, 1, 2, 3);
		platform.currentTime = 5000;
		// scripted: not pressed once (one loop body), then pressed -> exit
		platform.keyPressedScript.add(false);
		platform.keyPressedScript.add(true);

		controller.processEvent(GameEvent.PAUSE);

		// one loop body ran -> two delay(500) calls recorded
		assertEquals(2, platform.delays.size());
		assertEquals(500L, platform.delays.get(0));
		// on resume the timer is set to the current time
		assertEquals(5000, platform.getTc());
	}
}
