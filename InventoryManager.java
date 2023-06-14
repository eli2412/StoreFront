package Milestone239;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eliascruz
 *
 */
public class InventoryManager {
	private List<SalableProduct> inventory;

    /**
     * list
     */
    public InventoryManager() {
        this.inventory = new ArrayList<>();
    }

    /**
     * @param product add to inventory
     */
    public void addProduct(SalableProduct product) {
        inventory.add(product);
    }

    /**
     * @param product removed
     */
    public void removeProduct(SalableProduct product) {
        inventory.remove(product);
    }

    /**
     * @return
     */
    public List<SalableProduct> getInventory() {
        return inventory;
    }
    public SalableProduct findProductByName(String productName) {
        for (SalableProduct product : inventory) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null; // Product not found
    }
}
