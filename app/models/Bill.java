package models;

import io.ebean.Finder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Bill extends BaseModel {
    @Id
    public Long id;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    public List<BillOrder> billOrders;

    public static final Finder<Long, Bill> find = new Finder<>(Bill.class);
}
