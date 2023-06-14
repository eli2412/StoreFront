package Milestone239;

public class Armor extends SalableProduct {
    private int defense;

    /**
     * @param name
     * @param description
     * @param price
     * @param quantity
     * @param defense
     */
    public Armor(String name, String description, double price, int quantity, int defense) {
        super(name, description, price, quantity);
        this.defense = defense;
    }

    /**
     * @return defense
     */
    public int getDefense() {
        return defense;
    }

    /**
     * @param defense
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }
}
