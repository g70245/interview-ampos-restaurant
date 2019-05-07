package restaurant.services;

import com.fasterxml.jackson.databind.JsonNode;
import restaurant.services.FoodServiceRequest.*;

public interface FoodService {
    JsonNode getFoods(GetRequest request);
    JsonNode addFood(AddRequest request);
    void updateFood(UpdateRequest request);
    void deleteFood(DeleteRequest request);
}
