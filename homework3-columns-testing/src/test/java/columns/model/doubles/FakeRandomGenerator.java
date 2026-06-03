package columns.model.doubles;

import columns.model.kernel.RandomGenerator;

// Returns a fixed, repeating sequence so figure colours are predictable.
public class FakeRandomGenerator implements RandomGenerator {

	private final int[] sequence;
	private int index = 0;

	public FakeRandomGenerator(int... sequence) {
		if (sequence.length == 0) {
			throw new IllegalArgumentException("sequence must not be empty");
		}
		this.sequence = sequence;
	}

	@Override
	public int nextInt() {
		int value = sequence[index % sequence.length];
		index++;
		return value;
	}
}
