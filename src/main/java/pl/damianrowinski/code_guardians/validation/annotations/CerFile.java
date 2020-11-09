package pl.damianrowinski.code_guardians.validation.annotations;

import pl.damianrowinski.code_guardians.validation.validators.CerFileValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CerFileValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CerFile {
    String message() default "File should be ending wit *.cer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
