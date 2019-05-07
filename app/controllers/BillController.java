package controllers;

import actions.ExceptionHandler;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import restaurant.services.BillService;
import restaurant.services.BillServiceRequest.*;

import javax.inject.Inject;

public class BillController extends AbstractController {
    @Inject private BillService billService;

    @With(ExceptionHandler.class)
    public Result getBills() {

        return ok(billService.getBills());
    }

    //@With(ExceptionHandler.class)
    public Result getBill(Long id) {

        return ok(billService.getBill(id));
    }

    //@With(ExceptionHandler.class)
    public Result createBill(Http.Request request) {
        CreateRequest createRequest = bindRequestWith(request, CreateRequest.class);

        return created(billService.createBill(createRequest));
    }

    public Result updateBill(Http.Request request, Long id) {
        UpdateRequest updateRequest = bindRequestWith(request, id, UpdateRequest.class);
        billService.updateBill(updateRequest);

        return ok();
    }
}
