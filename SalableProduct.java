package Milestone239;


/**
 * @author eliascruz
 *
 */
public abstract class SalableProduct {
		private String name;
		private String description;
		private double price;
		private int quantity;
		private String type;
		
		
		/**
		 * @param name
		 * @param description
		 * @param price
		 * @param quantity
		 * @param type
		 */
		public SalableProduct(String name, String description, double price, int quantity, String type) {
	        this.name = name;
	        this.description = description;
	        this.price = price;
	        this.quantity = quantity;
	        this.type = type;
	    }

		/**
		 *check for if name is same
		 *returns true if name found
		 */
		@Override
		public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null || getClass() != obj.getClass())
	            return false;
	        SalableProduct other = (SalableProduct) obj;
	        return name.equalsIgnoreCase(other.name);
		           
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
	    /**
	     * @return
	     */
	    public String getType() {
	        return type;
	    }

	    /**
	     * @param type
	     */
	    public void setType(String type) {
	        this.type = type;
	    }
	}

