package badsmells;

/*
 * Refactored: Alternative Classes with Different Interfaces
 *
 * Original smell: ZoomClassroom.beginSession() and TeamsClassroom.openMeeting()
 * did the same job under different names, so callers had to branch instead of
 * using one shared protocol.
 * Refactoring applied:
 *   - Introduce a Classroom interface with start()
 *   - Both implementations adopt the shared name; the client now treats them
 *     uniformly as Classroom
 * Behavior preserved: same lines are printed.
 */
public class AlternativeClassesWithDifferentInterfacesExample {

	interface Classroom {
		void start();
	}

	static class ZoomClassroom implements Classroom {
		@Override
		public void start() {
			System.out.println("Zoom session started");
		}
	}

	static class TeamsClassroom implements Classroom {
		@Override
		public void start() {
			System.out.println("Teams meeting started");
		}
	}

	public void clientCode() {
		Classroom zoom = new ZoomClassroom();
		Classroom teams = new TeamsClassroom();

		zoom.start();
		teams.start();
	}
}
