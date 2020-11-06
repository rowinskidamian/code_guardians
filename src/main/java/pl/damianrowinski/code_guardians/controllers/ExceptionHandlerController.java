package pl.damianrowinski.code_guardians.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.damianrowinski.code_guardians.exception.EmptyFileException;
import pl.damianrowinski.code_guardians.exception.FileSizeException;
import pl.damianrowinski.code_guardians.exception.FileTypeException;

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

}
