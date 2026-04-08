package badsmells;

/*
 * Refactored: Speculative Generality
 *
 * Original smell: send(message, futureTemplate, encrypted, urgent) carried three
 * extra parameters that the implementation completely ignored. The interface
 * was prepared for variation that did not exist.
 * Refactoring applied:
 *   - Remove Dead Parameter: drop futureTemplate / encrypted / urgent
 *   - The interface now expresses what is actually used: send(message)
 * Behavior preserved: same string is printed for the same call.
 */
public class SpeculativeGeneralityExample {

	interface NotificationChannel {
		void send(String message);
	}

	static class EmailChannel implements NotificationChannel {
		@Override
		public void send(String message) {
			System.out.println(message);
		}
	}

	public void clientCode() {
		NotificationChannel channel = new EmailChannel();
		channel.send("Exam starts at 10:00");
	}
}
