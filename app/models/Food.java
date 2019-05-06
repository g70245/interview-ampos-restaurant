package models;

import io.ebean.Finder;
import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Food extends BaseModel {
    @Id
    @Constraints.Min(1)
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String description;

    @URL
    @Constraints.Required
    public String image;

    @Column(precision = 7, scale = 2)
    @Constraints.Required
    @Constraints.Min(0)
    public BigDecimal price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    public List<Type> types;

    public static final Finder<Long, Food> find = new Finder<>(Food.class);
}
