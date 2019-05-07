package models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class BillOrder {
    @EmbeddedId
    public BillOrderId id;

    @Column
    public String foodName;

    @Column(precision = 7, scale = 2)
    public BigDecimal unitPrice;

    @Column
    public Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", insertable = false, updatable = false)
    private Bill bill;

    public BillOrder updateQuantity(Integer quantity) {
        this.quantity = quantity;

        return this;
    }

    @Embeddable
    public static class BillOrderId implements Serializable {
        @Column
        Long billId;

        @Column
        String foodName;

        @Override
        public int hashCode() {
            return Objects.hash(billId, foodName);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            BillOrderId billOrderId = (BillOrderId) obj;

            return billOrderId.billId.equals(billId) && billOrderId.foodName.equals(foodName);

        }
    }
}
