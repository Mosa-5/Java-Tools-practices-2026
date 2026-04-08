package badsmells;

/*
 * Refactored: Primitive Obsession
 *
 * Original smell: status, country, and balance were raw primitives, with the
 * magic strings "ACTIVE" / "GE" living in a boolean expression.
 * Refactoring applied:
 *   - Replace Type Code with Enum: AccountStatus, CountryCode
 *   - Replace Primitive with Object: Money for the unpaid balance
 * Behavior preserved: the same combinations return the same boolean.
 */
public class PrimitiveObsessionExample {

	enum AccountStatus { ACTIVE, BLOCKED }

	enum CountryCode { GE, US }

	static final class Money {
		private final double amount;

		Money(double amount) {
			this.amount = amount;
		}

		public boolean isLessThan(Money other) {
			return this.amount < other.amount;
		}
	}

	public boolean canRentDormRoom(int age, AccountStatus status, Money unpaidBalance, CountryCode country) {
		return age >= 18
				&& status == AccountStatus.ACTIVE
				&& unpaidBalance.isLessThan(new Money(100))
				&& country == CountryCode.GE;
	}

	public void clientCode() {
		System.out.println(canRentDormRoom(19, AccountStatus.ACTIVE, new Money(0.0), CountryCode.GE));
		System.out.println(canRentDormRoom(17, AccountStatus.BLOCKED, new Money(120.0), CountryCode.US));
	}
}
