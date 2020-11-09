package pl.damianrowinski.code_guardians.validation.validators;

import lombok.extern.slf4j.Slf4j;
import pl.damianrowinski.code_guardians.validation.annotations.OutFolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;
import java.io.IOException;

@Slf4j
public class OutFolderValidator implements ConstraintValidator<OutFolder, String> {

    @Override
    public boolean isValid(String folderPath, ConstraintValidatorContext context) {

        File folderPathToCheck = new File(folderPath);
        try {
            folderPathToCheck.getCanonicalFile();
        } catch (IOException e) {
            log.error("Raised IOException, wrong folder path.");
            return false;
        }
        return true;
    }
}
