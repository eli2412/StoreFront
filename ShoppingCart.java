package Milestone239;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eliascruz
 *
 */
public class ShoppingCart<T extends SalableProduct> {
    private List<T> cartItems;
    /**
     * cart list
     */
    public ShoppingCart() {
    	this.cartItems = new ArrayList<>();
    }

    /**
     * @param product add to cart
     */
    public void addItem(T product) {
        cartItems.add(product);
    }

    /**
     * @param product remove from cart
     */
    public void removeItem(T product) {
        cartItems.remove(product);
    }

    /**
     * @return list
     */
    public List<T> getCartItems() {
        return cartItems;
    }
    /**
     * empties cart
     */
    public void emptyCart() {
        cartItems.clear();
    }
}
