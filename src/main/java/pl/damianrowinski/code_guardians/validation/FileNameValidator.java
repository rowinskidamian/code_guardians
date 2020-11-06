package pl.damianrowinski.code_guardians.validation;

import pl.damianrowinski.code_guardians.exception.FileTypeException;

import java.util.regex.Pattern;

public class FileNameValidator {
    public void validFile(String fileNameToValid, FileType expectedFileType) {
        String regex = ".+." + expectedFileType + "$";
        boolean matches = Pattern.matches(regex, fileNameToValid);
        if (!matches) throw new FileTypeException("File should be with ." + expectedFileType + " extension");
    }
}


