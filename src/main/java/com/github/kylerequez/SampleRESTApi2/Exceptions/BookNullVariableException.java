package com.github.kylerequez.SampleRESTApi2.Exceptions;

public class BookNullVariableException extends RuntimeException {
    public BookNullVariableException()
    {
        super();
    }

    public BookNullVariableException(String message)
    {
        super(message);
    }
}
