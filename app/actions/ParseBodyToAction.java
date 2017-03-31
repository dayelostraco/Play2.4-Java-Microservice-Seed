package actions;

import annotations.ParseBodyTo;
import com.fasterxml.jackson.databind.JsonNode;
import constants.ApplicationConstants;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;

@SuppressWarnings("unchecked")
public class ParseBodyToAction extends Action<ParseBodyTo> {

    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {

        try {
            JsonNode jsonNode = ctx.request().body().asJson();
            if (jsonNode == null) {
                return delegate.call(ctx);
            }

            Object o = Json.fromJson(jsonNode, configuration.value());
            ctx.args.put(ApplicationConstants.PARSED_BODY_OBJECT_KEY, o);
        } catch (IOException e) {
            return F.Promise.pure((Result) badRequest(Json.parse("{\"errors\": [\"Invalid request data.\"]}")));
        }

        return delegate.call(ctx);
    }
}
