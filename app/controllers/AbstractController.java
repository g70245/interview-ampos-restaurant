package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

public abstract class AbstractController extends Controller {
    @Inject protected Validator validator;

    protected <T> T bindRequestWith(Http.Request request, Class<T> boundClass) {

        JsonNode reqJson = request.body().asJson();
        T payload = Json.fromJson(reqJson, boundClass);

        return getPayload(reqJson, boundClass);
    }

    protected <T> T bindRequestWith(Http.Request request, Long id, Class<T> boundClass) {

        JsonNode reqJson = request.body().asJson();
        ((ObjectNode)reqJson).put("id", id);

        return getPayload(reqJson, boundClass);
    }

    private <T> T getPayload(JsonNode reqJson, Class<T> boundClass) {
        T payload = Json.fromJson(reqJson, boundClass);

        Set<ConstraintViolation<T>> violations = validator.validate(payload);
        if (!violations.isEmpty()) {
            throw new ValidationException();
        }

        return payload;
    }
}
