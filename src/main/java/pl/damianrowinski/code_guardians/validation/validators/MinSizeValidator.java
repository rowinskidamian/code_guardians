package pl.damianrowinski.code_guardians.validation.validators;

import lombok.extern.slf4j.Slf4j;
import pl.damianrowinski.code_guardians.validation.annotations.MinSize;
import pl.damianrowinski.code_guardians.validation.types.FileSize;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class MinSizeValidator implements ConstraintValidator<MinSize, Map<String, String>> {
    private FileSize fileSize;

    @Override
    public void initialize(MinSize constraint) {
        fileSize = constraint.value();
    }

    @Override
    public boolean isValid(Map<String, String> filesMap, ConstraintValidatorContext constraintValidatorContext) {
        Collection<String> filesPaths = filesMap.values();
        long currentFileSize = 0;
        for (String currentFile : filesPaths) {
            File current = new File(currentFile);
            try {
                File currentFilePath = current.getCanonicalFile();
                currentFileSize = currentFilePath.length();
            } catch (IOException e) {
                log.error("Raised IOException");
                return false;
            }
        }
        return currentFileSize >= Long.parseLong(fileSize.toString());
    }

}
