package pl.damianrowinski.code_guardians.validation.validators;

import lombok.extern.slf4j.Slf4j;
import pl.damianrowinski.code_guardians.validation.annotations.PdfFiles;
import pl.damianrowinski.code_guardians.validation.types.FileType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class PdfFilesValidator implements ConstraintValidator<PdfFiles, Map<String, String>> {
    @Override
    public boolean isValid(Map<String, String> filesMap, ConstraintValidatorContext context) {
        boolean validated = false;
        Collection<String> filesPaths = filesMap.values();
        for (String currentFile : filesPaths) {
            File current = new File(currentFile);
            try {
                File currentFilePath = current.getCanonicalFile();
                String regex = ".+." + FileType.PDF + "$";
                validated = Pattern.matches(regex, currentFilePath.getName());
            } catch (IOException e) {
                log.error("Raised IOException");
            }
        }
        return validated;
    }
}
