package columns.model.doubles;

import java.util.ArrayList;
import java.util.List;

import columns.model.kernel.Screen;

// Records draw calls as strings instead of drawing anything.
public class FakeScreen implements Screen {

	public static final int BLACK = 0;
	public static final int WHITE = 1;

	public final List<String> calls = new ArrayList<>();

	@Override
	public void setColor(int color) {
		calls.add("setColor(" + color + ")");
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		calls.add("fillRect(" + x + "," + y + "," + width + "," + height + ")");
	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		calls.add("drawRect(" + x + "," + y + "," + width + "," + height + ")");
	}

	@Override
	public void drawString(String string, int x, int y) {
		calls.add("drawString(" + string + "," + x + "," + y + ")");
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		calls.add("clearRect(" + x + "," + y + "," + width + "," + height + ")");
	}

	@Override
	public int Black() {
		return BLACK;
	}

	@Override
	public int White() {
		return WHITE;
	}

	public long countCallsContaining(String needle) {
		return calls.stream().filter(c -> c.contains(needle)).count();
	}
}
