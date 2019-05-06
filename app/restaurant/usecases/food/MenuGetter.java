package restaurant.usecases.food;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import restaurant.entities.EMenu;
import restaurant.repos.FoodRepo;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

public class MenuGetter {
    private FoodRepo foodRepo;

    @Inject
    public MenuGetter(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    public JsonNode getMenu(GetRequest request) {
        EMenu eMenu = new EMenu(request.page, request.size, request.keyword);
        return Json.toJson(foodRepo.getMenu(eMenu));
    }

    public static class GetRequest {
        public String keyword;
        public Integer page;
        public Integer size;
        public GetRequest(Map<String, String[]> queryStrings) {
            try {
                this.keyword = Optional.ofNullable(queryStrings.get("keyword"))
                        .map(e -> e[0]).orElse(null);
                this.page = Optional.ofNullable(queryStrings.get("page"))
                        .map(e -> Integer.valueOf(e[0])).orElse(null);
                this.size = Optional.ofNullable(queryStrings.get("size"))
                        .map(e -> Integer.valueOf(e[0])).orElse(null);
            } catch (Exception e) {
                // TODO: 2019-05-06 Handle bad request here
                throw e;
            }
        }
    }
}
