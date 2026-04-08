package badsmells;

/*
 * Refactored: Shotgun Surgery
 *
 * Original smell: every consumer of a course title (Course, Invoice, Certificate)
 * formatted it on its own; renaming the wording in one place required edits in
 * all three.
 * Refactoring applied:
 *   - Move Function: introduce a CourseTitle value type that owns every phrasing
 *     of the title (label / invoiceDescription / certificateText). Each consumer
 *     holds a CourseTitle and asks it for the wording it needs, so the wording
 *     lives in exactly one place.
 * Behavior preserved: each consumer prints the same string as before.
 */
public class ShotgunSurgeryExample {

	static class CourseTitle {

		private final String title;

		CourseTitle(String title) {
			this.title = title;
		}

		public String label() {
			return "Course: " + title;
		}

		public String invoiceDescription() {
			return "Invoice for " + title;
		}

		public String certificateText() {
			return "Completed " + title;
		}
	}

	static class Course {

		private final CourseTitle title;

		Course(CourseTitle title) {
			this.title = title;
		}

		public String label() {
			return title.label();
		}
	}

	static class Invoice {

		private final CourseTitle title;

		Invoice(CourseTitle title) {
			this.title = title;
		}

		public String description() {
			return title.invoiceDescription();
		}
	}

	static class Certificate {

		private final CourseTitle title;

		Certificate(CourseTitle title) {
			this.title = title;
		}

		public String text() {
			return title.certificateText();
		}
	}

	public void clientCode() {
		CourseTitle refactoring = new CourseTitle("Refactoring");
		Course course = new Course(refactoring);
		Invoice invoice = new Invoice(refactoring);
		Certificate certificate = new Certificate(refactoring);

		System.out.println(course.label());
		System.out.println(invoice.description());
		System.out.println(certificate.text());
	}
}
