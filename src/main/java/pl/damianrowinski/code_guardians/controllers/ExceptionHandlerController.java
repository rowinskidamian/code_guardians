package pl.damianrowinski.code_guardians.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.damianrowinski.code_guardians.exception.EmptyFileException;

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

}