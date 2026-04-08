package badsmells;

/*
 * Refactored: Data Clumps
 *
 * Original smell: (name, email, phone) traveled together everywhere as three
 * separate variables and was passed to four different operations per peer.
 * Refactoring applied:
 *   - Introduce Parameter Object: ContactInfo holds name + email + phone
 *   - Move Method: label/greeting/sms/isReachable now belong to ContactInfo
 * Behavior preserved: same printed output for each peer in clientCode.
 */
public class DataClumpsExample {

	static final class ContactInfo {
		private final String name;
		private final String email;
		private final String phone;

		ContactInfo(String name, String email, String phone) {
			this.name = name;
			this.email = email;
			this.phone = phone;
		}

		public String label() {
			return name + " <" + email + ">, phone: " + phone;
		}

		public String emailGreeting() {
			return "To: " + email + ", hello " + name;
		}

		public String smsMessage() {
			return "SMS to " + phone + ": Hi " + name;
		}

		public boolean isReachable() {
			return notBlank(email) && notBlank(phone);
		}

		private boolean notBlank(String s) {
			return s != null && !s.trim().isEmpty();
		}
	}

	public void clientCode() {
		ContactInfo student = new ContactInfo("Nino", "nino@example.com", "+995-555-000-001");
		ContactInfo advisor = new ContactInfo("Giorgi", "giorgi@example.com", "+995-555-000-002");
		ContactInfo accountant = new ContactInfo("Maka", "maka@example.com", "+995-555-000-003");

		printContact(student);
		printContact(advisor);
		printContact(accountant);
	}

	private void printContact(ContactInfo c) {
		System.out.println(c.label());
		System.out.println(c.emailGreeting());
		System.out.println(c.smsMessage());
		System.out.println(c.isReachable());
	}
}
