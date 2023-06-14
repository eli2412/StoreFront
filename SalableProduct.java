package Milestone239;

import java.util.Objects;

/**
 * @author eliascruz
 *
 */
public abstract class SalableProduct {
		private String name;
		private String description;
		private double price;
		private int quantity;
		
		/**
		 * @param name
		 * @param description
		 * @param price
		 * @param quantity
		 */
		public SalableProduct(String name, String description, double price, int quantity) {
	        this.name = name;
	        this.description = description;
	        this.price = price;
	        this.quantity = quantity;
	    }

		/**
		 *check for if name is same
		 *returns true if name found
		 */
		@Override
		public boolean equals(Object obj) {
		    if (this == obj) {
		        return true;
		    }
		    if (obj == null || getClass() != obj.getClass()) {
		        return false;
		    }
		    SalableProduct other = (SalableProduct) obj;
		    return Objects.equals(name, other.name);
		           
		}
		
	    // Getters and setters
	    /**
	     * @return
	     */
	    public String getName() {
	        return name;
	    }

	    /**
	     * @param name
	     */
	    public void setName(String name) {
	        this.name = name;
	    }

	    /**
	     * @return
	     */
	    public String getDescription() {
	        return description;
	    }

	    /**
	     * @param description
	     */
	    public void setDescription(String description) {
	        this.description = description;
	    }

	    /**
	     * @return
	     */
	    public double getPrice() {
	        return price;
	    }

	    /**
	     * @param price
	     */
	    public void setPrice(double price) {
	        this.price = price;
	    }

	    /**
	     * @return
	     */
	    public int getQuantity() {
	        return quantity;
	    }

	    /**
	     * @param quantity
	     */
	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }
	}
