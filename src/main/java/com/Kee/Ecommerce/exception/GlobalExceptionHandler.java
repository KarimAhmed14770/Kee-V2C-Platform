package com.Kee.Ecommerce.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException
            (UserAlreadyExistsException userAlreadyExistsException){
        UserErrorResponse error=new UserErrorResponse();
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(userAlreadyExistsException.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException
            (UserNotFoundException userNotFoundException){
        UserErrorResponse error=new UserErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(userNotFoundException.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception exc) {
        UserErrorResponse error = new UserErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("An unexpected error occurred. Please try again.");
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}

/*
public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){
        //this handler can handle or catch StudentNotFoundException, and it returns
        //ResponseEntity<StudentErrorResponse>

        //create a student error response
        StudentErrorResponse error=new StudentErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());


        //return a responseEntity

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

 */