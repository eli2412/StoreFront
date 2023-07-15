package test;

import static org.junit.Assert.*;


import java.util.List;

import org.junit.Test;

import Milestone239.Armor;
import Milestone239.InventoryManager;
import Milestone239.SalableProduct;
import org.junit.Assert;
import org.junit.Before;

public class InventoryManagerTest {

	private InventoryManager<SalableProduct> inventoryManager;

    @Before
    public void setUp() {
        inventoryManager = new InventoryManager<>();
    }

    @Test
    public void testAddProduct() {
        // Create a SalableProduct instance
        SalableProduct product = new Armor("Bronze Cap", "Protective gear", 50.0, 5, 20, "Armor");

        // Add the product to the inventory manager
        inventoryManager.addProduct(product);

        // Get the inventory from the inventory manager
        List<SalableProduct> inventory = inventoryManager.getInventory();

        // Verify that the product is added to the inventory
        Assert.assertTrue(inventory.contains(product));
    }

    @Test
    public void testRemoveProduct() {
        // Create a SalableProduct instance
    	// Add the product to the inventory manager
    	SalableProduct product = new Armor("Iron Cap", "Protective gear", 100.0, 1, 50, "Armor");        
        inventoryManager.addProduct(product);

        // Remove the product from the inventory manager
        inventoryManager.removeProduct(product);

        // Get the inventory from the inventory manager
        List<SalableProduct> inventory = inventoryManager.getInventory();

        // Verify that the product is removed from the inventory
        Assert.assertFalse(inventory.contains(product));
    }
}
