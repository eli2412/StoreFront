package Milestone239;

public class Health extends SalableProduct {
    private int healingPoints;

    public Health(String name, String description, double price, int quantity, int healingPoints) {
        super(name, description, price, quantity);
        this.healingPoints = healingPoints;
    }

    public int getHealingPoints() {
        return healingPoints;
    }

    public void setHealingPoints(int healingPoints) {
        this.healingPoints = healingPoints;
    }
}
