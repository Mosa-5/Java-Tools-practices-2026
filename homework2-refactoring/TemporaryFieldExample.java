package badsmells;

/*
 * Refactored: Temporary Field
 *
 * Original smell: examRoom was meaningful only when an onsite exam ran, and
 * onlineMeetingLink was meaningful only when an online exam ran. The object
 * always had at least one irrelevant field.
 * Refactoring applied:
 *   - Extract Class: OnsiteExam owns the room, OnlineExam owns the link
 *   - Inline Class on the now-empty façade: once the mode-specific state moved
 *     out, the original class had nothing left except routing, so it goes away.
 *     The boolean parameters that existed only to choose a mode disappear too -
 *     callers now use whichever extracted class they actually need.
 * Behavior preserved: clientCode prints the same three lines as the original.
 */
public class TemporaryFieldExample {

	static class OnsiteExam {
		private String examRoom;

		public String prepare() {
			examRoom = "B-204";
			return "Use room " + examRoom;
		}

		public String getExamRoom() {
			return examRoom;
		}
	}

	static class OnlineExam {
		private String onlineMeetingLink;

		public String prepare() {
			onlineMeetingLink = "https://meet.example/exam";
			return "Join " + onlineMeetingLink;
		}

		public String getOnlineMeetingLink() {
			return onlineMeetingLink;
		}
	}

	public void clientCode() {
		OnsiteExam onsite = new OnsiteExam();
		OnlineExam online = new OnlineExam();

		System.out.println(onsite.prepare());
		System.out.println(online.prepare());
		System.out.println("room=" + onsite.getExamRoom() + ", link=" + online.getOnlineMeetingLink());
	}
}
