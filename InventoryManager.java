package Milestone239;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eliascruz
 *
 */
public class InventoryManager<T extends SalableProduct> {
	private List<T> inventory;


    /**
     * list
     */
    public InventoryManager() {
    	this.inventory = new ArrayList<>();
    }
    /**
     * @param product add to inventory
     */
    public void addProduct(T product) {
        inventory.add(product);
    }

    /**
     * @param product removed
     */
    public void removeProduct(T product) {
        inventory.remove(product);
    }

    /**
     * @return list
     */
    public List<T> getInventory() {
        return inventory;
    }
}
