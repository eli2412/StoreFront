package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Assert;
import Milestone239.Weapon;

public class WeaponTest {

	@Test
    public void testGetDamage() {
        // Create a Weapon instance
        Weapon weapon = new Weapon("Sword", "Sharp blade", 100.0, 1, 50, "Melee");

        // Test the getDamage() method
        int damage = weapon.getDamage();

        // Verify the result
        Assert.assertEquals(50, damage);
    }

    @Test
    public void testSetDamage() {
        // Create a Weapon instance
        Weapon weapon = new Weapon("Sword", "Sharp blade", 100.0, 1, 50, "Melee");

        // Set a new damage value
        weapon.setDamage(60);

        // Test the getDamage() method
        int damage = weapon.getDamage();

        // Verify the result
        Assert.assertEquals(60, damage);
    }

    @Test
    public void testCompareTo() {
        // Create Weapon instances
        Weapon weapon1 = new Weapon("Sword", "Sharp blade", 100.0, 1, 50, "weapon");
        Weapon weapon2 = new Weapon("Axe", "Heavy chopping tool", 150.0, 1, 70, "weapon");
        Weapon weapon3 = new Weapon("Bow", "Ranged weapon", 200.0, 1, 60, "weapon");

        // Test the compareTo() method
        int result1 = weapon1.compareTo(weapon2);
        int result2 = weapon2.compareTo(weapon1);
        int result3 = weapon2.compareTo(weapon3);

        // Verify the results
        Assert.assertTrue(result1 < 0);  // weapon1's damage is less than weapon2's damage
        Assert.assertTrue(result2 > 0);  // weapon2's damage is greater than weapon1's damage
        Assert.assertEquals(0, result3); // weapon2's damage is equal to weapon3's damage
    }
}
