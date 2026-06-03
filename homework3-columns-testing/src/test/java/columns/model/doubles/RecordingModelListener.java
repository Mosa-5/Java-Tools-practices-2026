package columns.model.doubles;

import java.util.ArrayList;
import java.util.List;

import columns.model.kernel.ModelListener;

// Records which callbacks the board fires so tests can check them.
// lastScore is a primitive long on purpose (a boxed Long broke assertEquals).
public class RecordingModelListener implements ModelListener {

	public final List<Integer> levelChanges = new ArrayList<>();
	public int tripletCount = 0;
	public final List<int[]> triplets = new ArrayList<>();
	public int fieldUpdateCount = 0;
	public int scoreUpdateCount = 0;
	public long lastScore = -1;

	@Override
	public void levelHasChanged(int level) {
		levelChanges.add(level);
	}

	@Override
	public void tripletDetected(int a, int b, int c, int d, int i, int j) {
		tripletCount++;
		triplets.add(new int[] { a, b, c, d, i, j });
	}

	@Override
	public void fieldWasUpdated(int[][] newField) {
		fieldUpdateCount++;
	}

	@Override
	public void scoreUpdated(long score) {
		scoreUpdateCount++;
		lastScore = score;
	}
}
