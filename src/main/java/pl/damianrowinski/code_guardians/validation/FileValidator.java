package pl.damianrowinski.code_guardians.validation;

import org.springframework.stereotype.Component;
import pl.damianrowinski.code_guardians.exception.FileTypeException;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class FileValidator {
    public void validFile(File fileToValid, FileType expectedFileType) throws IOException {
        File canonicalFile = fileToValid.getCanonicalFile();

        long fileSize = canonicalFile.length();
//        if (fileSize < 1_000_000) throw new FileSizeException("File should be at least 1mb.");

        String regex = ".+." + expectedFileType + "$";
        boolean matches = Pattern.matches(regex, fileToValid.getName());
        if (!matches) throw new FileTypeException("File should be with ." + expectedFileType + " extension");
    }
}


