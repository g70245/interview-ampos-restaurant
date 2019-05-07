package restaurant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EBillOrder {
    @JsonIgnore
    public Long foodId;
    public String foodName;
    public BigDecimal unitPrice;
    public Integer quantity;

    public EBillOrder(String foodName, Integer quantity) {
        this.foodName = foodName;
        this.quantity = quantity;
    }

    public EBillOrder(String foodName, BigDecimal unitPrice, Integer quantity) {
        this(foodName, quantity);

        this.unitPrice = unitPrice;
    }

    public EBillOrder(Long foodId, Integer quantity) {
        this.foodId = foodId;
        this.quantity = quantity;
    }

    public void setOrderDetails(String name, BigDecimal price) {
        this.foodName = name;
        this.unitPrice = price;
    }

    @JsonIgnore
    public boolean isNewOrder() {
        return foodId != null;
    }

    @JsonIgnore
    BigDecimal getOrderPrice() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}
