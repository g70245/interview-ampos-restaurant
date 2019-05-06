package controllers;

import actions.ExceptionHandler;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import restaurant.usecases.food.FoodEditor;
import restaurant.usecases.food.FoodEditor.*;
import restaurant.usecases.food.MenuGetter;
import restaurant.usecases.food.MenuGetter.GetRequest;

import javax.inject.Inject;


public class FoodController extends AbstractController {
    @Inject MenuGetter menuGetter;
    @Inject FoodEditor foodEditor;

    // @With(ExceptionHandler.class)
    public Result getMenu(Http.Request request) {
        GetRequest getterRequest = new GetRequest(request.queryString());

        return ok(menuGetter.getMenu(getterRequest));
    }

    // @With(ExceptionHandler.class)
    public Result addFood(Http.Request request) {
        AddRequest editorRequest = bindRequestWith(request, AddRequest.class);

        return created(foodEditor.addFood(editorRequest));
    }

    // @With(ExceptionHandler.class)
    public Result updateFood(Http.Request request, Long id) {
        UpdateRequest editorRequest = bindRequestWith(request, id, UpdateRequest.class);
        foodEditor.updateFood(editorRequest);

        return ok();
    }

    // @With(ExceptionHandler.class)
    public Result deleteFood(Http.Request request, Long id) {
        DeleteRequest editorRequest = bindRequestWith(request, id, DeleteRequest.class);
        foodEditor.deleteFood(editorRequest);

        return ok();
    }
}
