package pl.damianrowinski.code_guardians.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.code_guardians.exception.EmptyFileException;
import pl.damianrowinski.code_guardians.exception.FileSizeException;
import pl.damianrowinski.code_guardians.exception.FileTypeException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error("Validation didn't passed.");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler({IOException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String raisingIOException(IOException ex) {
        log.error("Raised IOException.");
        return ex.getMessage();
    }

    @ExceptionHandler({EmptyFileException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String emptyFileException(EmptyFileException ex) {
        log.error("Raised EmptyFileException.");
        return ex.getMessage();
    }
}
