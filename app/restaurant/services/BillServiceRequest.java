package restaurant.services;

import play.data.validation.Constraints;

import javax.validation.Valid;
import java.util.List;

public class BillServiceRequest {
    public static class CreateRequest {
        @Constraints.Required
        @Valid
        public List<NewOrder> newOrders;
    }

    public static class UpdateRequest {
        @Constraints.Required
        @Constraints.Min(1)
        public Long id;

        @Valid
        public List<NewOrder> newOrders;

        @Valid
        public List<UpdateOrder> orders;
    }

    public static class UpdateOrder {
        @Constraints.Required
        public String foodName;

        @Constraints.Required
        @Constraints.Min(1)
        public Integer quantity;
    }

    public static class NewOrder {
        @Constraints.Required
        @Constraints.Min(1)
        public Long id;

        @Constraints.Required
        @Constraints.Min(1)
        public Integer quantity;
    }
}
