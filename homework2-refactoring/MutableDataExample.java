package badsmells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Refactored: Mutable Data
 *
 * Original smell: getEnrolledStudents returned the live internal list, so any
 * caller could clear or mutate it.
 * Refactoring applied:
 *   - Encapsulate Collection: expose only an unmodifiable view, route writes
 *     through enroll()
 * Behavior preserved: enroll() still adds, the read still returns the current
 * contents. The original clientCode demonstrated the BUG (calling clear() on the
 * returned list emptied internal state); after refactor that mutation throws,
 * which is the entire point of the smell. clientCode is updated to read size
 * directly so the demonstration still runs end-to-end.
 */
public class MutableDataExample {

	private final List<String> enrolledStudents = new ArrayList<>();

	public List<String> getEnrolledStudents() {
		return Collections.unmodifiableList(enrolledStudents);
	}

	public void enroll(String studentId) {
		enrolledStudents.add(studentId);
	}

	public void clientCode() {
		enroll("s-1001");
		// Previously the caller could clear the returned list and erase state.
		// Now getEnrolledStudents() is read-only, so the only mutation path is enroll().
		System.out.println(getEnrolledStudents().size());
	}
}
