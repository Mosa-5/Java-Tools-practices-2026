package badsmells;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Refactored: Loops
 *
 * Original smell: explicit for-loop hid a simple filter+map pipeline.
 * Refactoring applied:
 *   - Replace Loop with Pipeline: stream().filter(...).map(...).collect(...)
 *   - Extract the predicate to a named method on Student so the rule reads as
 *     domain language and can be reused.
 * Behavior preserved: same list of names, same order.
 */
public class LoopsExample {

	public List<String> honorStudents(List<Student> students) {
		return students.stream()
				.filter(Student::isHonorStudent)
				.map(s -> s.name)
				.collect(Collectors.toList());
	}

	static class Student {

		String name;
		double gpa;

		Student(String name, double gpa) {
			this.name = name;
			this.gpa = gpa;
		}

		public boolean isHonorStudent() {
			return gpa > 3.5;
		}
	}

	public void clientCode() {
		List<Student> students = new ArrayList<>();
		students.add(new Student("Nino", 3.9));
		students.add(new Student("Giorgi", 3.1));
		students.add(new Student("Maka", 3.7));

		System.out.println(honorStudents(students));
	}
}
