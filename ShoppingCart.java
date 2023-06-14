package Milestone239;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eliascruz
 *
 */
public class ShoppingCart {
	private List<SalableProduct> cartItems;

    /**
     * cart list
     */
    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    /**
     * @param product add to cart
     */
    public void addItem(SalableProduct product) {
        cartItems.add(product);
    }

    /**
     * @param product remove from cart
     */
    public void removeItem(SalableProduct product) {
        cartItems.remove(product);
    }

    /**
     * @return list
     */
    public List<SalableProduct> getCartItems() {
        return cartItems;
    }
}
