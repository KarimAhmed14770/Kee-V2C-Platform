package com.Kee.Ecommerce.exception;


import com.Kee.Ecommerce.entity.Product;
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
    public ResponseEntity<UserErrorResponse> handleException
            (CategoryNotFoundException categoryNotFoundException){
        UserErrorResponse error=new UserErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(categoryNotFoundException.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException
            (InventoryNotFoundException inventoryNotFoundException){
        UserErrorResponse error=new UserErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(inventoryNotFoundException.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException
            (ProductNotFoundException productNotFoundException){
        UserErrorResponse error=new UserErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(productNotFoundException.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException
            (CartItemNotFoundException cartItemNotFoundException){
        UserErrorResponse error=new UserErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(cartItemNotFoundException.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException
            (InsufficientStockException insufficientStockException){
        UserErrorResponse error=new UserErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(insufficientStockException.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException
            (CartEmptyException cartEmptyException){
        UserErrorResponse error=new UserErrorResponse();
        error.setStatus(HttpStatus.NO_CONTENT.value());
        error.setMessage(cartEmptyException.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error,HttpStatus.NO_CONTENT);
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