package pl.damianrowinski.code_guardians.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
public class CerFileValidator implements ConstraintValidator<CerFile, String> {
    @Override
    public boolean isValid(String toValid, ConstraintValidatorContext context) {
        FileType expectedFileType = FileType.CER;
        File filePath = new File(toValid);
        try {
            File fileToValid = filePath.getCanonicalFile();
            String regex = ".+." + expectedFileType + "$";
            boolean matches = Pattern.matches(regex, fileToValid.getName());
            return matches;
        } catch (IOException e) {
            log.error("Raised IOException");
            return false;
        }
    }
}
