package pl.damianrowinski.code_guardians.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MaxSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxSize {
    FileSize value();
    String message() default "File should be max 10MB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
