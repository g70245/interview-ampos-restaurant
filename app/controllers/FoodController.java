package controllers;

import actions.ExceptionHandler;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import restaurant.services.FoodService;
import restaurant.services.FoodServiceRequest.*;

import javax.inject.Inject;


public class FoodController extends AbstractController {
    @Inject private FoodService foodService;

    @With(ExceptionHandler.class)
    public Result getFoods(Http.Request request) {
        GetRequest getterRequest = new GetRequest(request.queryString());

        return ok(foodService.getFoods(getterRequest));
    }

    @With(ExceptionHandler.class)
    public Result addFood(Http.Request request) {
        AddRequest editorRequest = bindRequestWith(request, AddRequest.class);

        return created(foodService.addFood(editorRequest));
    }

    @With(ExceptionHandler.class)
    public Result updateFood(Http.Request request, Long id) {
        UpdateRequest editorRequest = bindRequestWith(request, id, UpdateRequest.class);
        foodService.updateFood(editorRequest);

        return ok();
    }

    @With(ExceptionHandler.class)
    public Result deleteFood(Http.Request request, Long id) {
        DeleteRequest editorRequest = bindRequestWith(request, id, DeleteRequest.class);
        foodService.deleteFood(editorRequest);

        return ok();
    }
}
