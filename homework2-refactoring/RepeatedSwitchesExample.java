package badsmells;

/*
 * Refactored: Repeated Switches
 *
 * Original smell: tuitionDiscount() and dormPriority() each switched on the same
 * studentType string. Adding a new type required edits in two places.
 * Refactoring applied:
 *   - Replace Type Code with Class/Enum: StudentType enum carries both the
 *     discount rate and the dorm priority. New cases live in one place.
 * Behavior preserved: same return values for the same inputs.
 */
public class RepeatedSwitchesExample {

	enum StudentType {
		STUDENT(0.05, "NORMAL"),
		ATHLETE(0.15, "HIGH"),
		EMPLOYEE_CHILD(0.25, "LOW");

		private final double discount;
		private final String dormPriority;

		StudentType(double discount, String dormPriority) {
			this.discount = discount;
			this.dormPriority = dormPriority;
		}

		public double tuitionDiscount() {
			return discount;
		}

		public String dormPriority() {
			return dormPriority;
		}
	}

	public double tuitionDiscount(StudentType type) {
		return type == null ? 0 : type.tuitionDiscount();
	}

	public String dormPriority(StudentType type) {
		return type == null ? "UNKNOWN" : type.dormPriority();
	}

	public void clientCode() {
		System.out.println(tuitionDiscount(StudentType.ATHLETE));
		System.out.println(dormPriority(StudentType.ATHLETE));
	}
}
