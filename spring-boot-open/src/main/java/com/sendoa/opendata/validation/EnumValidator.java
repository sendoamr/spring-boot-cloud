package commons.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import commons.model.validation.ValidEnum;

public final class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private List<String> allowedValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        allowedValues = new ArrayList<>();
        for(String val : constraintAnnotation.allowedValues()) {
            allowedValues.add(val);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return null == value || "".equals(value) || allowedValues.contains(value);
    }
}
