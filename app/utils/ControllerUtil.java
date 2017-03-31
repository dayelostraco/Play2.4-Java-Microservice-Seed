package utils;

import constants.ApplicationConstants;
import play.mvc.Http;

import java.util.Map;

public class ControllerUtil {

    @SuppressWarnings("unchecked")
    public static <T> T retrieveParsedBodyObject(Http.Context ctx, Class<T> clazz) {
        Map<String, Object> args = ctx.args;

        if (!args.containsKey(ApplicationConstants.PARSED_BODY_OBJECT_KEY)) {
            throw new RuntimeException("The parsed body object is missing.");
        }

        Object o = args.get(ApplicationConstants.PARSED_BODY_OBJECT_KEY);
        if (o == null) {
            //throw new RuntimeException("The parsed body object should not be null.");
            return null;
        }

        if (!clazz.isAssignableFrom(o.getClass())) {
            throw new RuntimeException("The parsed body object doesn't match the type given.");
        }

        return (T) o;
    }
}
