package Milestone239;

public class Weapon extends SalableProduct {
    private int damage;

    /**
     * @param name
     * @param description
     * @param price
     * @param quantity
     * @param damage
     */
    public Weapon(String name, String description, double price, int quantity, int damage) {
        super(name, description, price, quantity);
        this.damage = damage;
    }

    /**
     * @return damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @param damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
