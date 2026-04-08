package badsmells;

/*
 * Refactored: Feature Envy
 *
 * Original smell: ScholarshipCalculator.qualifies() used only StudentAccount
 * data and contributed nothing of its own.
 * Refactoring applied:
 *   - Move Method: qualifiesForScholarship() now lives on StudentAccount, where
 *     the data lives. The calculator class is gone because it added no value.
 * Behavior preserved: same boolean for the same inputs.
 */
public class FeatureEnvyExample {

	static class StudentAccount {

		private final int completedCredits;
		private final double gpa;

		StudentAccount(int completedCredits, double gpa) {
			this.completedCredits = completedCredits;
			this.gpa = gpa;
		}

		public int getCompletedCredits() {
			return completedCredits;
		}

		public double getGpa() {
			return gpa;
		}

		public boolean qualifiesForScholarship() {
			return completedCredits >= 30 && gpa >= 3.7;
		}
	}

	public void clientCode() {
		StudentAccount account = new StudentAccount(36, 3.9);
		System.out.println(account.qualifiesForScholarship());
	}
}
