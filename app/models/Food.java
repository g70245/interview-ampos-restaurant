package models;

import io.ebean.Finder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Food extends BaseModel {
    @Id
    public Long id;

    @Column
    public String name;

    @Column
    public String description;

    @Column
    public String image;

    @Column(precision = 7, scale = 2)
    public BigDecimal price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable
    public List<Type> types;

    public static final Finder<Long, Food> find = new Finder<>(Food.class);
}
