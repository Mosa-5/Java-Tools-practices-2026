package badsmells;

/*
 * Refactored: Message Chains
 *
 * Original smell: clientCode walked university -> department -> coordinator ->
 * office -> phone, coupling itself to the entire object graph just to read one
 * value.
 * Refactoring applied:
 *   - Hide Delegate: one new method on University (getCoordinatorPhone) hides
 *     the chain so callers depend only on University. The other classes are
 *     untouched.
 * Behavior preserved: same phone number is printed.
 */
public class MessageChainsExample {

	static class University {

		Department getDepartment() {
			return new Department();
		}

		public String getCoordinatorPhone() {
			return getDepartment().getCoordinator().getOffice().getPhoneNumber();
		}
	}

	static class Department {

		Coordinator getCoordinator() {
			return new Coordinator();
		}
	}

	static class Coordinator {

		Office getOffice() {
			return new Office();
		}
	}

	static class Office {

		String getPhoneNumber() {
			return "555-0101";
		}
	}

	public void clientCode() {
		University university = new University();
		System.out.println(university.getCoordinatorPhone());
	}
}
