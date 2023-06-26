package Milestone239;

public class Armor extends SalableProduct {
    private int defense;
    private String type;
    
    /**
     * field inputs
     */
    public Armor() {
    	super("", "", 0, 0, "");

    }

    /**
     * @param name
     * @param description
     * @param price
     * @param quantity
     * @param defense
     */
    public Armor(String name, String description, double price, int quantity, int defense, String type) {
        super(name, description, price, quantity, type);
        this.defense = defense;
        this.type = type;
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
