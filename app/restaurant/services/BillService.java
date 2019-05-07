package restaurant.services;

import com.fasterxml.jackson.databind.JsonNode;
import restaurant.services.BillServiceRequest.*;

public interface BillService {
    JsonNode getBills();
    JsonNode getBill(Long id);
    JsonNode createBill(CreateRequest request);
    void updateBill(UpdateRequest request);
}
