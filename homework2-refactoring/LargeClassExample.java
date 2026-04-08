package badsmells;

import java.util.ArrayList;
import java.util.List;

/*
 * Refactored: Large Class
 *
 * Original smell: one class held enrollment lists, staffing, courses, finance,
 * help-desk tickets, cafeteria menu, bus schedule, website theme and payroll
 * day - eight unrelated responsibilities in one place.
 * Refactoring applied:
 *   - Extract Class along the cohesive axes:
 *       Enrollment       (students)
 *       Staff            (teachers)
 *       Curriculum       (courses)
 *       Finance          (budget, charge/pay)
 *       HelpDesk         (open tickets)
 *       Facilities       (cafeteria menu, bus schedule, payroll day)
 *       WebsiteSettings  (theme)
 *   - The School class becomes a thin façade that owns the new collaborators.
 *     Each new class is small, focused and changes for one reason.
 * Behavior preserved: clientCode performs the same operations through the new
 * façade and the same data ends up in the same places.
 */
public class LargeClassExample {

	static class Enrollment {
		private final List<String> students = new ArrayList<>();

		public void enroll(String student) {
			students.add(student);
		}
	}

	static class Staff {
		private final List<String> teachers = new ArrayList<>();

		public void hire(String teacher) {
			teachers.add(teacher);
		}
	}

	static class Curriculum {
		private final List<String> courses = new ArrayList<>();

		public void addCourse(String course) {
			courses.add(course);
		}
	}

	static class Finance {
		private double budget;

		public void chargeTuition(double amount) {
			budget += amount;
		}

		public void paySalary(double amount) {
			budget -= amount;
		}
	}

	static class HelpDesk {
		private int openTickets;

		public void openTicket() {
			openTickets++;
		}
	}

	static class Facilities {
		private String cafeteriaMenu;
		private String busSchedule;
		private String payrollDay;

		public void publishCafeteriaMenu(String menu) {
			this.cafeteriaMenu = menu;
		}

		public void publishBusSchedule(String schedule) {
			this.busSchedule = schedule;
		}

		public void setPayrollDay(String day) {
			this.payrollDay = day;
		}
	}

	static class WebsiteSettings {
		private String theme;

		public void updateTheme(String theme) {
			this.theme = theme;
		}
	}

	static class School {
		private String schoolName;
		private String address;
		final Enrollment enrollment = new Enrollment();
		final Staff staff = new Staff();
		final Curriculum curriculum = new Curriculum();
		final Finance finance = new Finance();
		final HelpDesk helpDesk = new HelpDesk();
		final Facilities facilities = new Facilities();
		final WebsiteSettings website = new WebsiteSettings();
	}

	public void clientCode() {
		School school = new School();
		school.enrollment.enroll("Nino");
		school.staff.hire("Ms. Kapanadze");
		school.curriculum.addCourse("Refactoring");
		school.finance.chargeTuition(2400);
		school.finance.paySalary(1200);
		school.helpDesk.openTicket();
		school.website.updateTheme("blue");
		school.facilities.publishBusSchedule("Route A at 08:00");
		school.facilities.publishCafeteriaMenu("Soup and salad");
		school.facilities.setPayrollDay("Friday");
	}
}
