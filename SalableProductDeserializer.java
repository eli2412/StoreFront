package Milestone239;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.math.BigDecimal;

public class SalableProductDeserializer extends JsonDeserializer<SalableProduct> {
    @Override
    public SalableProduct deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        String name = node.get("name").asText();
        String description = node.get("description").asText();
        double price = node.get("price").doubleValue();
        int quantity = node.get("quantity").asInt();
        String type = node.get("type").asText();

        SalableProduct product;

        // Instantiate the appropriate subclass based on the type field
        if ("weapon".equals(type)) {
            int damage = node.get("damage").asInt();
            product = new Weapon(name, description, price, quantity, damage, type);
        } else if ("armor".equals(type)) {
            int defense = node.get("defense").asInt();
            product = new Armor(name, description, price, quantity, defense, type);
        } else if ("health".equals(type)) {
            int healingPoints = node.get("healingPoints").asInt();
            product = new Health(name, description, price, quantity, healingPoints, type);
        } else {
            // Handle unknown or unsupported types
            throw new IllegalArgumentException("Unsupported product type: " + type);
        }

        return product;
    }
}

