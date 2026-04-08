package badsmells;

/*
 * Refactored: Insider Trading
 *
 * Original smell: AuditService reached into BankAccount.balance and wrote to
 * BankAccount.secretFlag directly. The two classes shared too much knowledge of
 * each other's internals.
 * Refactoring applied:
 *   - Encapsulate Field: balance and the frozen flag are now private
 *   - Move Method: the freeze rule (balance < 0) lives on BankAccount as
 *     freezeIfOverdrawn(); the audit service simply asks the account to do it
 * Behavior preserved: an overdrawn account ends in the FROZEN state, same as before.
 */
public class InsiderTradingExample {

	static class BankAccount {

		private double balance;
		private boolean frozen;

		public void setBalance(double balance) {
			this.balance = balance;
		}

		public boolean isFrozen() {
			return frozen;
		}

		public void freezeIfOverdrawn() {
			if (balance < 0) {
				frozen = true;
			}
		}
	}

	static class AuditService {

		public void freezeIfNeeded(BankAccount account) {
			account.freezeIfOverdrawn();
		}
	}

	public void clientCode() {
		BankAccount account = new BankAccount();
		account.setBalance(-50);

		AuditService auditService = new AuditService();
		auditService.freezeIfNeeded(account);

		System.out.println(account.isFrozen() ? "FROZEN" : "OK");
	}
}
