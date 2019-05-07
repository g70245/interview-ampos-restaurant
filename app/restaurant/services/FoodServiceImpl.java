package restaurant.services;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import restaurant.entities.EFood;
import restaurant.entities.EMenu;
import restaurant.repos.FoodRepo;
import restaurant.services.FoodServiceRequest.*;

import javax.inject.Inject;

public class FoodServiceImpl implements FoodService {
    private FoodRepo foodRepo;

    @Inject
    public FoodServiceImpl(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    @Override
    public JsonNode getFoods(GetRequest request) {
        EMenu eMenu = new EMenu(request.page, request.size, request.keyword);
        return Json.toJson(foodRepo.getMenu(eMenu));
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
}
