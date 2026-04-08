package badsmells;

/*
 * Refactored: Comments
 *
 * Original smell: comments narrated each step of finalPrice() because the code
 * could not stand on its own (raw arithmetic on a variable called "result").
 * Refactoring applied:
 *   - Extract Method on each step (vipDiscount, bulkDiscount, withTax)
 *   - Rename Variable so the values speak for themselves
 *   - Remove the now-redundant comments; the method names ARE the explanation
 * Behavior preserved: same final price for the same inputs.
 */
public class CommentsExample {

	private static final double VIP_DISCOUNT = 0.10;
	private static final double BULK_DISCOUNT = 0.05;
	private static final int BULK_THRESHOLD = 20;
	private static final double TAX_RATE = 0.18;

	public double finalPrice(double basePrice, boolean vip, int quantity) {
		double afterVipDiscount = vip ? applyVipDiscount(basePrice) : basePrice;
		double afterBulkDiscount = isBulkOrder(quantity) ? applyBulkDiscount(afterVipDiscount) : afterVipDiscount;
		return withTax(afterBulkDiscount);
	}

	private double applyVipDiscount(double price) {
		return price - price * VIP_DISCOUNT;
	}

	private boolean isBulkOrder(int quantity) {
		return quantity > BULK_THRESHOLD;
	}

	private double applyBulkDiscount(double price) {
		return price - price * BULK_DISCOUNT;
	}

	private double withTax(double price) {
		return price + price * TAX_RATE;
	}

	public void clientCode() {
		double vipOrder = finalPrice(120, true, 25);
		double regularOrder = finalPrice(120, false, 5);
		System.out.println(vipOrder);
		System.out.println(regularOrder);
	}
}
