package badsmells;

/*
 * Refactored: Global Data
 *
 * Original smell: public static currentSemester / tuitionRate were mutable from
 * anywhere with no ownership.
 * Refactoring applied:
 *   - Encapsulate Variable: hide state behind a SchoolConfig instance
 *   - Inject the config into services that need it instead of reaching to globals
 * Behavior preserved: clientCode prints the same sequence (initial state, billed
 * amount, then state and bill after the administration changes).
 */
public class GlobalDataExample {

	static class SchoolConfig {
		private String currentSemester = "SPRING";
		private double tuitionRate = 1250.0;

		public String getCurrentSemester() {
			return currentSemester;
		}

		public double getTuitionRate() {
			return tuitionRate;
		}

		public void setCurrentSemester(String currentSemester) {
			this.currentSemester = currentSemester;
		}

		public void increaseTuition(double delta) {
			this.tuitionRate += delta;
		}
	}

	static class BillingService {
		private final SchoolConfig config;

		BillingService(SchoolConfig config) {
			this.config = config;
		}

		public double calculateInvoice(int credits) {
			return credits * config.getTuitionRate();
		}
	}

	static class SemesterAdministration {
		private final SchoolConfig config;

		SemesterAdministration(SchoolConfig config) {
			this.config = config;
		}

		public void openFallSemester() {
			config.setCurrentSemester("FALL");
		}

		public void approveRateIncrease() {
			config.increaseTuition(100);
		}
	}

	public void clientCode() {
		SchoolConfig config = new SchoolConfig();
		BillingService billingService = new BillingService(config);
		SemesterAdministration administration = new SemesterAdministration(config);

		System.out.println(config.getCurrentSemester());
		System.out.println(billingService.calculateInvoice(3));
		administration.openFallSemester();
		administration.approveRateIncrease();
		System.out.println(config.getCurrentSemester());
		System.out.println(billingService.calculateInvoice(3));
	}
}
