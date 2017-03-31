package utils;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import javax.validation.ConstraintViolation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValidationErrorMessageUtil {

    public static <T> JsonNode buildValidationErrorMessage(Set<ConstraintViolation<T>> violations) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation violation : violations) {
            errors.add(violation.getMessage());
        }
        Map<String, List<String>> errorMap = new HashMap<String, List<String>>();
        errorMap.put("errors", errors);

        return Json.toJson(errorMap);
    }
}
