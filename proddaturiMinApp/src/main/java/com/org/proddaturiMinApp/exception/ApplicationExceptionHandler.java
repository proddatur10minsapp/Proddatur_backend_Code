package com.org.proddaturiMinApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DetailsNotFound.class)
    public ResponseEntity detailsNotFound(DetailsNotFound detailsNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailsNotFoundException);
    }

    @ExceptionHandler(InputFieldRequried.class)
    public ResponseEntity inputFieldRequried(InputFieldRequried inputFieldRequriedException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(inputFieldRequriedException);
    }
}
