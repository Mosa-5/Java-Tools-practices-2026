package badsmells;

/*
 * Refactored: Mysterious Name
 *
 * Original smell: f / a / b / c / x / y revealed nothing about intent.
 * Refactoring applied:
 *   - Rename Method on f -> halfOfNetAmount
 *   - Rename Variable on a/b/c/x/y -> quantity, unitPrice, discount, gross, net
 * Behavior preserved: same arithmetic on the same inputs (verified by clientCode).
 */
public class MysteriousNameExample {

	public int halfOfNetAmount(int quantity, int unitPrice, int discount) {
		int gross = quantity * unitPrice;
		int net = gross - discount;
		return net / 2;
	}

	public void clientCode() {
		int result = halfOfNetAmount(8, 4, 6);
		System.out.println(result);
	}
}
