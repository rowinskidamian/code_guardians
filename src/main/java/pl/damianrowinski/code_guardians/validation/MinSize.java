package pl.damianrowinski.code_guardians.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MinSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinSize {
    FileSize value();
    String message() default "File should be at least 1MB.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
