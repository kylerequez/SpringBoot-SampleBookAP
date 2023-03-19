package com.github.kylerequez.SampleRESTApi2.Errors;

import com.github.kylerequez.SampleRESTApi2.Exceptions.BookNameExistsException;
import com.github.kylerequez.SampleRESTApi2.Exceptions.BookNotFoundException;
import com.github.kylerequez.SampleRESTApi2.Exceptions.BookNullVariableException;
import com.github.kylerequez.SampleRESTApi2.Exceptions.BookReferenceExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class BookExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundExceptions(Exception e)
    {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace
                ),
                status
        );
    }

    @ExceptionHandler(BookNullVariableException.class)
    public ResponseEntity<ErrorResponse> handleBookNullVariableExceptions(Exception e)
    {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace
                ),
                status
        );
    }

    @ExceptionHandler(BookNameExistsException.class)
    public ResponseEntity<ErrorResponse> handleBookNameExistsExceptions(Exception e)
    {
        HttpStatus status = HttpStatus.CONFLICT;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace
                ),
                status
        );
    }

    @ExceptionHandler(BookReferenceExistsException.class)
    public ResponseEntity<ErrorResponse> handleBookReferenceExistsExceptions(Exception e)
    {
        HttpStatus status = HttpStatus.CONFLICT;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();
        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace
                ),
                status
        );
    }
}
