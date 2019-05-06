package restaurant.usecases.food;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints;
import play.libs.Json;
import restaurant.entities.EFood;
import restaurant.repos.FoodRepo;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Set;

public class FoodEditor {
    private FoodRepo foodRepo;

    @Inject
    public FoodEditor(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    public JsonNode addFood(AddRequest request) {
        EFood eFood = new EFood(request.name, request.description, request.image, request.price, request.types);
        return Json.toJson(foodRepo.addFood(eFood));
    }

    public void updateFood(UpdateRequest request) {
        EFood eFood = new EFood(request.id, request.name, request.description, request.image, request.price, request.types);

        if (!foodRepo.updateFood(eFood).isUpdated) {
            // TODO: 2019-05-06 Handle use-case exception here
            throw new RuntimeException();
        }
    }

    public void deleteFood(DeleteRequest request) {
        EFood eFood = new EFood(request.id);
        foodRepo.deleteFood(eFood);
    }

    public static class AddRequest {
        @Constraints.Required
        @Constraints.MaxLength(64)
        public String name;

        @Constraints.Required
        @Constraints.MaxLength(255)
        public String description;

        @Constraints.Required
        @Constraints.MaxLength(255)
        @URL
        public String image;

        @Constraints.Required
        @Constraints.Min(0)
        @Constraints.Max(1000000)
        public BigDecimal price;

        @Constraints.Required
        public Set<String> types;
    }

    public static class UpdateRequest extends AddRequest {
        @Constraints.Required
        @Constraints.Min(1)
        public Long id;
    }

    public static class DeleteRequest {
        @Constraints.Required
        @Constraints.Min(1)
        public Long id;
    }
}
