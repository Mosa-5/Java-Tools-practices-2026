package badsmells;

/*
 * Refactored: Long Function
 *
 * Original smell: processOrder mixed discount, shipping, tax, approval, and
 * formatting in one block.
 * Refactoring applied:
 *   - Extract Method on each distinct concern (discount, shipping, approval, summary)
 * Behavior preserved: every branch produces the same numeric and string output.
 */
public class LongFunctionExample {

	public String processOrder(String customerType, int quantity, double price, boolean expressDelivery) {
		double subtotal = quantity * price;
		double discount = discountFor(customerType, subtotal);
		double shipping = shippingCost(expressDelivery, quantity);
		double tax = (subtotal - discount) * 0.18;
		double total = subtotal - discount + shipping + tax;
		String status = approvalStatus(total);
		return formatSummary(customerType, quantity, price, subtotal, discount, shipping, tax, total, status);
	}

	private double discountFor(String customerType, double subtotal) {
		if ("STUDENT".equals(customerType)) {
			return subtotal * 0.05;
		}
		if ("VIP".equals(customerType)) {
			return subtotal * 0.12;
		}
		if ("EMPLOYEE".equals(customerType)) {
			return subtotal * 0.20;
		}
		return 0;
	}

	private double shippingCost(boolean expressDelivery, int quantity) {
		double shipping;
		if (expressDelivery) {
			shipping = 25;
			if (quantity > 10) {
				shipping += 10;
			}
		} else {
			shipping = 10;
			if (quantity > 10) {
				shipping += 5;
			}
		}
		return shipping;
	}

	private String approvalStatus(double total) {
		if (total > 500) {
			return "MANAGER_APPROVAL";
		}
		if (total > 200) {
			return "FINANCE_REVIEW";
		}
		return "AUTO_APPROVED";
	}

	private String formatSummary(String customerType, int quantity, double price, double subtotal, double discount,
			double shipping, double tax, double total, String status) {
		StringBuilder summary = new StringBuilder();
		summary.append("customerType=").append(customerType).append('\n');
		summary.append("quantity=").append(quantity).append('\n');
		summary.append("price=").append(price).append('\n');
		summary.append("subtotal=").append(subtotal).append('\n');
		summary.append("discount=").append(discount).append('\n');
		summary.append("shipping=").append(shipping).append('\n');
		summary.append("tax=").append(tax).append('\n');
		summary.append("total=").append(total).append('\n');
		summary.append("status=").append(status);
		return summary.toString();
	}

	public void clientCode() {
		System.out.println(processOrder("VIP", 12, 30, true));
		System.out.println(processOrder("STUDENT", 2, 50, false));
	}
}
