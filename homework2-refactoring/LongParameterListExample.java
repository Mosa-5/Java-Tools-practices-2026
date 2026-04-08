package badsmells;

/*
 * Refactored: Long Parameter List
 *
 * Original smell: registerStudent took 12 raw parameters that naturally cluster
 * into a few concepts.
 * Refactoring applied:
 *   - Introduce Parameter Object: PersonName, ContactInfo, Address, Guardian, Enrollment
 *   - The method now takes 5 named clusters instead of 12 loose primitives
 * Behavior preserved: the produced summary string is the same as the original.
 */
public class LongParameterListExample {

	static final class PersonName {
		final String first;
		final String last;

		PersonName(String first, String last) {
			this.first = first;
			this.last = last;
		}

		@Override
		public String toString() {
			return first + " " + last;
		}
	}

	static final class ContactInfo {
		final String email;
		final String phone;

		ContactInfo(String email, String phone) {
			this.email = email;
			this.phone = phone;
		}
	}

	static final class Address {
		final String city;
		final String street;
		final String zipCode;

		Address(String city, String street, String zipCode) {
			this.city = city;
			this.street = street;
			this.zipCode = zipCode;
		}

		@Override
		public String toString() {
			return city + ", " + street + ", " + zipCode;
		}
	}

	static final class Guardian {
		final String name;
		final String phone;

		Guardian(String name, String phone) {
			this.name = name;
			this.phone = phone;
		}
	}

	static final class Enrollment {
		final String program;
		final int startYear;
		final boolean scholarship;

		Enrollment(String program, int startYear, boolean scholarship) {
			this.program = program;
			this.startYear = startYear;
			this.scholarship = scholarship;
		}
	}

	public String registerStudent(PersonName name, ContactInfo contact, Address address, Guardian guardian,
			Enrollment enrollment) {
		return name + " -> " + enrollment.program + " (" + enrollment.startYear + "), guardian=" + guardian.name
				+ ", scholarship=" + enrollment.scholarship + ", address=" + address + ", contact=" + contact.email
				+ "/" + contact.phone + ", guardianPhone=" + guardian.phone;
	}

	public void clientCode() {
		String summary = registerStudent(
				new PersonName("Nino", "Beridze"),
				new ContactInfo("nino@example.com", "+995-555-000-001"),
				new Address("Tbilisi", "Rustaveli Ave 10", "0108"),
				new Guardian("Maka Beridze", "+995-555-000-999"),
				new Enrollment("Computer Science", 2026, true));
		System.out.println(summary);
	}
}
