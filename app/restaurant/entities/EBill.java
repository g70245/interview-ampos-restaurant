package restaurant.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import restaurant.helpers.CalendarSerializer;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class EBill {
    public Long id;
    @JsonSerialize(using= CalendarSerializer.class)
    public Calendar orderedTime;
    public List<EBillOrder> orders;

    @JsonIgnore
    public boolean isUpdated = false;

    public EBill(Long id, List<EBillOrder> orders) {
        this.id = id;
        this.orders = orders;
    }

    public EBill(Long id, Calendar orderedTime, List<EBillOrder> orders) {
        this(id, orders);

        this.orderedTime = orderedTime;
    }

    public EBill(List<EBillOrder> orders) {
        this.orders = orders;
    }

    public void submit(Long id, Calendar orderedTime, List<EBillOrder> orders) {
        this.id = id;
        this.orderedTime = orderedTime;
        this.orders = orders;
    }

    @JsonGetter
    public BigDecimal getTotalPrice() {
        return orders.parallelStream()
                .map(EBillOrder::getOrderPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @JsonIgnore
    public boolean isUpdated() {
        return isUpdated;
    }
}
