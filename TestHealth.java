package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Assert;

import Milestone239.Health;

public class HealthTest {

	@Test
    public void testGetHealingPoints() {
        // Create a Health instance
        Health health = new Health("Potion1", "Restores health", 50.0, 1, 20, "health");

        // Test the getHealingPoints() method
        int healingPoints = health.getHealingPoints();

        // Verify the result
        Assert.assertEquals(20, healingPoints);
    }

    @Test
    public void testSetHealingPoints() {
        // Create a Health instance
        Health health = new Health("Potion2", "Restores health", 50.0, 1, 20, "health");

        // Set a new healingPoints value
        health.setHealingPoints(30);

        // Test the getHealingPoints() method
        int healingPoints = health.getHealingPoints();

        // Verify the result
        Assert.assertEquals(30, healingPoints);
    }
    @Test
    public void testGetType() {
        // Create a Health instance
        Health health = new Health("Potion", "Restores health", 50.0, 1, 20, "health");

        // Test the getType() method
        String type = health.getType();

        // Verify the result
        Assert.assertEquals("health", type);
    }
}
