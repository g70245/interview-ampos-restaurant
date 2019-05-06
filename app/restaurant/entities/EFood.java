package restaurant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EFood {
    public Long id;
    public String name;
    public String description;
    public String image;
    public BigDecimal price;
    public Set<String> types;

    @JsonIgnore
    public boolean isUpdated = false;

    public EFood(String name, String description, String image, BigDecimal price, Set<String> types) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.types = types;
    }

    public EFood(Long id, String name, String description, String image, BigDecimal price, Set<String> types) {
        this(name, description, image, price, types);

        this.id = id;
    }

    public EFood(Long id) {
        this.id = id;
    }
}