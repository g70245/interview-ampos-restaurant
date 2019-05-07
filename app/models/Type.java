package models;

import io.ebean.Finder;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
public class Type extends BaseModel {

    @Id
    public Long id;

    @Column
    public String name;

    @ManyToMany(mappedBy = "types", cascade = CascadeType.ALL)
    public List<Food> foods;

    public Type(@Constraints.Required String name) {
        this.name = name;
    }

    public static final Finder<Long, Type> find = new Finder<>(Type.class);
}
