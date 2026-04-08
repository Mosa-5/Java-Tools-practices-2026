package badsmells;

/*
 * Refactored: Lazy Element
 *
 * Original smell: StudentNameFormatter existed only to call String.trim().
 * That class added a layer with no behavior of its own.
 * Refactoring applied:
 *   - Inline Class: removed StudentNameFormatter; the caller uses String.trim()
 *     directly. There is no policy to preserve, so the abstraction was pure noise.
 * Behavior preserved: same printed value as the original.
 */
public class LazyElementExample {

	public void clientCode() {
		System.out.println("  Nino  ".trim());
	}
}
