package columns.model.doubles;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import columns.model.GameEvent;
import columns.model.kernel.Platform;
import columns.model.kernel.RandomGenerator;
import columns.model.kernel.Screen;

// Controllable Platform: time never moves on its own and delay() never sleeps.
public class FakePlatform implements Platform {

	private final Screen screen;
	private final RandomGenerator random;

	public long currentTime = 0;
	public long tc = 0;
	public boolean keyPressed = false;
	public GameEvent event = GameEvent.NONE;

	// recorded delay() calls, never actually slept
	public final List<Long> delays = new ArrayList<>();

	// scripted answers for isKeyPressed(), used to end the pause loop
	public final Deque<Boolean> keyPressedScript = new ArrayDeque<>();

	public FakePlatform(Screen screen, RandomGenerator random) {
		this.screen = screen;
		this.random = random;
	}

	public FakePlatform(Screen screen) {
		this(screen, new FakeRandomGenerator(0, 1, 2));
	}

	@Override
	public void delay(long t) {
		delays.add(t);
	}

	@Override
	public long currentTime() {
		return currentTime;
	}

	@Override
	public boolean isKeyPressed() {
		if (!keyPressedScript.isEmpty()) {
			return keyPressedScript.poll();
		}
		return keyPressed;
	}

	@Override
	public void setKeyPressed(boolean isKeyPressed) {
		this.keyPressed = isKeyPressed;
	}

	@Override
	public Screen getScreen() {
		return screen;
	}

	@Override
	public long getTc() {
		return tc;
	}

	@Override
	public void setTc(long time) {
		this.tc = time;
	}

	@Override
	public int getKeyPressed() {
		return 0;
	}

	@Override
	public GameEvent getEvent() {
		return event;
	}

	@Override
	public RandomGenerator getRandomGenerator() {
		return random;
	}
}
