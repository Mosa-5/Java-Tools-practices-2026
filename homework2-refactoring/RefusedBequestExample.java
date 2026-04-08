package badsmells;

/*
 * Refactored: Refused Bequest
 *
 * Original smell: Penguin extended Bird only to throw from fly(). The
 * inheritance lied about the contract: any code that thought it had a Bird
 * could blow up at runtime.
 * Refactoring applied:
 *   - Replace Subclass with Delegate / Push Down Method:
 *     Bird is now the common type with no fly(); a FlyingBird subclass adds
 *     fly(). Sparrow and Eagle extend FlyingBird; Penguin extends Bird only,
 *     so it cannot be asked to fly at all.
 *   - The runtime UnsupportedOperationException becomes a compile-time
 *     impossibility.
 *
 * Note on behavior: the original penguin.fly() call threw. The whole point of
 * the refactoring is to make that call structurally invalid, so clientCode is
 * updated to call only what the type system actually supports. This is the
 * intended outcome of the refactoring, not a regression.
 */
public class RefusedBequestExample {

	static class Bird {
	}

	static class FlyingBird extends Bird {
		public void fly() {
			System.out.println("Flying");
		}
	}

	static class Sparrow extends FlyingBird {
		@Override
		public void fly() {
			System.out.println("Sparrow is flying");
		}
	}

	static class Eagle extends FlyingBird {
		@Override
		public void fly() {
			System.out.println("Eagle is soaring");
		}
	}

	static class Penguin extends Bird {
	}

	public void clientCode() {
		FlyingBird sparrow = new Sparrow();
		FlyingBird eagle = new Eagle();
		Penguin penguin = new Penguin();

		sparrow.fly();
		eagle.fly();
		// penguin extends Bird, not FlyingBird, so penguin.fly() no longer compiles.
	}
}
