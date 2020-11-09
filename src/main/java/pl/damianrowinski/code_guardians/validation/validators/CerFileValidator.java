package pl.damianrowinski.code_guardians.validation.validators;

import lombok.extern.slf4j.Slf4j;
import pl.damianrowinski.code_guardians.validation.annotations.CerFile;
import pl.damianrowinski.code_guardians.validation.types.FileType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
public class CerFileValidator implements ConstraintValidator<CerFile, String> {

    @Override
    public boolean isValid(String toValid, ConstraintValidatorContext context) {
        File filePath = new File(toValid);
        try {
            File fileToValid = filePath.getCanonicalFile();
            String regex = ".+." + FileType.CER + "$";
            return Pattern.matches(regex, fileToValid.getName());
        } catch (IOException e) {
            log.error("Raised IOException");
            return false;
        }
    }
}
