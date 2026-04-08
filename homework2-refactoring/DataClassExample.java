package badsmells;

/*
 * Refactored: Data Class
 *
 * Original smell: StudentRecord was a bag of public fields. HonorsEvaluator,
 * TuitionDiscountCalculator, and AcademicStandingReporter all reached into it
 * to make decisions, leaving the record itself passive.
 * Refactoring applied:
 *   - Encapsulate Field: name/credits/gpa become private with read accessors
 *   - Move Method: isHonorsEligible / tuitionDiscountPercent / academicStanding
 *     now live on StudentRecord, where the data lives. The three external
 *     "calculator" classes are gone because they added no behavior of their own.
 * Behavior preserved: clientCode prints the same three values.
 */
public class DataClassExample {

	public static class StudentRecord {

		private final String name;
		private final int credits;
		private final double gpa;

		public StudentRecord(String name, int credits, double gpa) {
			this.name = name;
			this.credits = credits;
			this.gpa = gpa;
		}

		public boolean isHonorsEligible() {
			return credits >= 30 && gpa >= 3.7;
		}

		public double tuitionDiscountPercent() {
			if (gpa >= 3.8) {
				return 0.15;
			}
			if (gpa >= 3.5) {
				return 0.10;
			}
			return 0.0;
		}

		public String academicStanding() {
			if (gpa < 2.0) {
				return name + " is on academic probation";
			}
			if (credits < 15) {
				return name + " is a new student";
			}
			return name + " is in good standing";
		}
	}

	public void clientCode() {
		StudentRecord student = new StudentRecord("Nino", 32, 3.8);

		System.out.println(student.isHonorsEligible());
		System.out.println(student.tuitionDiscountPercent());
		System.out.println(student.academicStanding());
	}
}
