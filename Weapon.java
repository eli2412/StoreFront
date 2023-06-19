package Milestone239;

public class Weapon extends SalableProduct implements Comparable<Weapon> {
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
    /**
     *search with out case sensitive
     */
    @Override
    public int compareTo(Weapon other) {
        return this.getName().compareToIgnoreCase(other.getName());
    }
}
