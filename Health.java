package Milestone239;

public class Health extends SalableProduct {
    private int healingPoints;

    /**
     * @param name
     * @param description
     * @param price
     * @param quantity
     * @param healingPoints
     */
    public Health(String name, String description, double price, int quantity, int healingPoints) {
        super(name, description, price, quantity);
        this.healingPoints = healingPoints;
    }

    /**
     * @return healingPoints
     */
    public int getHealingPoints() {
        return healingPoints;
    }

    /**
     * @param healingPoints
     */
    public void setHealingPoints(int healingPoints) {
        this.healingPoints = healingPoints;
    }
}
