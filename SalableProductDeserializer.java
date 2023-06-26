package Milestone239;

	import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

	import java.io.IOException;

	/**
	 * @author eliascruz
	 *
	 */
	public class SalableProductDeserializer extends StdDeserializer<SalableProduct> {
	    public SalableProductDeserializer() {
	        this(null);
	    }

	    /**
	     * @param vc
	     */
	    public SalableProductDeserializer(Class<?> vc) {
	        super(vc);
	    }

	    /**
	     * reads JSON file
	     * specifies the type of the product and adds to class 
	     */
	    @Override
	    public SalableProduct deserialize(JsonParser parser, DeserializationContext context) throws IOException {
	    	ObjectCodec codec = parser.getCodec();
	        JsonNode node = codec.readTree(parser);
	        
	        String type = node.get("type").asText();
	        switch (type) {
	            case "armor":
	                return codec.treeToValue(node, Armor.class);
	            case "health":
	                return codec.treeToValue(node, Health.class);
	            case "weapon":
	                return codec.treeToValue(node, Weapon.class);
	            default:
	                throw new IllegalArgumentException("Unknown product type: " + type);
	    }
	}

}
