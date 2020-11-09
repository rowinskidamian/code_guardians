package pl.damianrowinski.code_guardians.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.damianrowinski.code_guardians.exception.EmptyFileException;
import pl.damianrowinski.code_guardians.exception.FileSizeException;
import pl.damianrowinski.code_guardians.exception.FileTypeException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ResponseBody
    @ExceptionHandler({EmptyFileException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String emptyFileException(EmptyFileException ex) {
        log.error("Raised EmptyFileException");
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({FileSizeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fileSizeException(FileSizeException ex) {
        log.error("Raised fileSizeException");
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({FileTypeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fileTypeException(FileTypeException ex) {
        log.error("Raised fileTypeException");
        return ex.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error("Validation didn't pass");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
