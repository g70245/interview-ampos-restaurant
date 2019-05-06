package actions;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ExceptionHandler extends Action.Simple {

    @Override
    public CompletionStage<Result> call(Http.Request request) {
        try {
            return delegate.call(request);
        }
        catch (Throwable e) {
            // TODO: 2019-05-06 Handle exceptions here
            return CompletableFuture.completedFuture(badRequest());
        }
    }
}
