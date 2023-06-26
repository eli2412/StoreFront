package Milestone239;

public class Weapon extends SalableProduct implements Comparable<Weapon> {
    private int damage;
    private String type;
    //field input
    public Weapon() {
        super("", "", 0, 0, "");
        this.damage = 0;
    }
    

    /**
     * @param name
     * @param description
     * @param price
     * @param quantity
     * @param damage
     */
    public Weapon(String name, String description, double price, int quantity, int damage, String type) {
        super(name, description, price, quantity, type);
        this.damage = damage;
        this.type = type;
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    /**
     *search with out case sensitive
     */
    @Override
    public int compareTo(Weapon other) {
        return this.getName().compareToIgnoreCase(other.getName());
    }
}
