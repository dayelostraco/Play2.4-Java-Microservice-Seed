package actions;

import annotations.RequestBodyJsonRequired;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class RequestBodyJsonRequiredAction extends Action<RequestBodyJsonRequired> {

    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        if (ctx.request().body().asJson() == null) {
            return F.Promise.pure((Result) badRequest(Json.parse("{\"errors\": [\"Request body should contain JSON data.\"]}")));
        }
        return delegate.call(ctx);
    }
}
