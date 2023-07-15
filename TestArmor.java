package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Milestone239.Armor;
import org.junit.Assert;

public class ArmorTest {

	@Test
    public void testGetDefense() {
        // Create an instance of Armor
        Armor armor = new Armor("Iron Cap", "Protective gear", 100.0, 1, 50, "armor");
        
        // Test the getDefense() method
        int defense = armor.getDefense();
        
        // Verify the result
        Assert.assertEquals(50, defense);
    }
    
    @Test
    public void testSetDefense() {
        // Create an instance of Armor
        Armor armor = new Armor("Bronze Cap", "Protective gear", 100.0, 1, 50, "armor");
        
        // Set a new defense value
        armor.setDefense(60);
        
        // Test the getDefense() method
        int defense = armor.getDefense();
        
        // Verify the result
        Assert.assertEquals(60, defense);
    }
    
}
