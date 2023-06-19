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
     * @return list
     */
    public List<SalableProduct> getInventory() {
        return inventory;
    }
}
