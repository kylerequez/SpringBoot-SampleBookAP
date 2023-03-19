package com.github.kylerequez.SampleRESTApi2.Errors;

import com.github.kylerequez.SampleRESTApi2.Exceptions.AuthorNotFoundException;
import com.github.kylerequez.SampleRESTApi2.Exceptions.AuthorNullVariableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class AuthorExceptionHandler
{
    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFoundExceptions(Exception e)
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

    @ExceptionHandler(AuthorNullVariableException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNullVariableExceptions(Exception e)
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
}
