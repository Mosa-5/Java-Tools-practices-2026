package badsmells;

/*
 * Refactored: Middle Man
 *
 * Original smell: StudentPortal.findGrade just forwarded to TranscriptService
 * with no extra policy.
 * Refactoring applied:
 *   - Remove Middle Man: callers talk to TranscriptService directly. The portal
 *     class is gone because it added nothing.
 * Behavior preserved: same grade is printed for the same id.
 */
public class MiddleManExample {

	static class TranscriptService {

		public String findGrade(String studentId) {
			return "A";
		}
	}

	public void clientCode() {
		TranscriptService transcriptService = new TranscriptService();
		System.out.println(transcriptService.findGrade("s-1001"));
	}
}
