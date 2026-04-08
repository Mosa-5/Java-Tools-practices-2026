package badsmells;

/*
 * Refactored: Duplicated Code
 *
 * Original smell: tax + shipping rules were copy-pasted in summer/winter invoices.
 * Refactoring applied:
 *   - Extract Method on the shared pricing core (tax + shipping + assembly)
 *   - Each season method now contributes only its own discount rule
 * Behavior preserved: same totals as the originals for any subtotal.
 */
public class DuplicatedCodeExample {

	public double summerInvoice(double subtotal) {
		return invoiceTotal(subtotal, summerDiscount(subtotal));
	}

	public double winterInvoice(double subtotal) {
		return invoiceTotal(subtotal, winterDiscount(subtotal));
	}

	private double invoiceTotal(double subtotal, double discount) {
		double tax = subtotal * 0.18;
		double shipping = subtotal > 100 ? 0 : 15;
		return subtotal + tax + shipping - discount;
	}

	private double summerDiscount(double subtotal) {
		return subtotal > 200 ? subtotal * 0.10 : 0;
	}

	private double winterDiscount(double subtotal) {
		return subtotal > 200 ? subtotal * 0.20 : 50;
	}

	public void clientCode() {
		System.out.println(summerInvoice(240));
		System.out.println(winterInvoice(240));
	}
}
