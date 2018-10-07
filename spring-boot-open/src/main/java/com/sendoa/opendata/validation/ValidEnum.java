package commons.model.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import commons.model.validation.EnumValidator;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ValidEnum {

    String[] allowedValues();

    String message() default "Invalid value";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
