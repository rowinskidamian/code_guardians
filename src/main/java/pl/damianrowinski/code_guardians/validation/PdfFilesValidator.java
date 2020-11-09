package pl.damianrowinski.code_guardians.validation;

import lombok.extern.slf4j.Slf4j;

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
        Collection<String> filesPaths = filesMap.values();
        for (String currentFile : filesPaths) {
            File current = new File(currentFile);
            try {
                File currentFilePath = current.getCanonicalFile();
                String regex = ".+." + FileType.CER + "$";
                return Pattern.matches(regex, currentFilePath.getName());
            } catch (IOException e) {
                log.error("Raised IOException");
            }
        }
        return false;
    }
}
