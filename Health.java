package Milestone239;

public class Health extends SalableProduct {
    private int healingPoints;
    private String type;
    
    /**
     * field inputs
     */
    public Health() {
        super("", "", 0, 0, "");
        this.healingPoints = 0;
    }

    /**
     * @param name
     * @param description
     * @param price
     * @param quantity
     * @param healingPoints
     */
    public Health(String name, String description, double price, int quantity, int healingPoints, String type) {
        super(name, description, price, quantity, type);
        this.healingPoints = healingPoints;
        this.type = type;
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
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
