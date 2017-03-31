package actions;

import annotations.ValidateParsedBodyObject;
import play.Logger;
import play.data.validation.Validation;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import utils.ControllerUtil;
import utils.ValidationErrorMessageUtil;

import javax.validation.ConstraintViolation;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ValidateParsedBodyObjectAction extends Action<ValidateParsedBodyObject> {

    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        // Run validations on the body object.
        Object o = ControllerUtil.retrieveParsedBodyObject(ctx, configuration.value());
        Set<ConstraintViolation<Object>> validationResults = validate(o);
        if (!validationResults.isEmpty()) {
            return F.Promise.pure((Result) badRequest(ValidationErrorMessageUtil.buildValidationErrorMessage(validationResults)));
        }
        return delegate.call(ctx);
    }


    private Set<ConstraintViolation<Object>> validate(Object o) {
        Set<ConstraintViolation<Object>> validationResults = Validation.getValidator().validate(o);
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(o.getClass(), Object.class).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                try {
                    Object o1 = propertyDescriptor.getReadMethod().invoke(o);
                    if (o1 != null) {
                        validationResults.addAll(validate(o1));
                    }
                } catch (InvocationTargetException | IllegalAccessException e) {
                    Logger.info("Exception thrown while validating request body.", e);
                }
            }
        } catch (IntrospectionException e) {
            Logger.info("Exception thrown while retrieving properties for validating request body.", e);
        }
        return validationResults;
    }
}
