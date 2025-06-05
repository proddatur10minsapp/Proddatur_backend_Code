package com.org.proddaturiMinApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DetailsNotFoundException.class)
    public ResponseEntity<DetailsNotFoundException> handleDetailsNotFound(DetailsNotFoundException detailsNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailsNotFoundException);
    }

    @ExceptionHandler(InputFieldRequried.class)
    public ResponseEntity<InputFieldRequried> handleInputFieldRequried(InputFieldRequried inputFieldRequriedException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(inputFieldRequriedException);
    }
    @ExceptionHandler(CannotModifyException.class)
    public ResponseEntity<CannotModifyException> handleCannotModifyException(CannotModifyException cannotModifyException){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(cannotModifyException);
    }
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonException> handleCommonException(CommonException commonException){
        return ResponseEntity.status(HttpStatus.OK).body(commonException);

    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Database error occurred: " + ex.getMessage());
    }
}
