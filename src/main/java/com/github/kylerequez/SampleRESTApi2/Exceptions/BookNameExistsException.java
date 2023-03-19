package com.github.kylerequez.SampleRESTApi2.Exceptions;

public class BookNameExistsException extends RuntimeException {
    public BookNameExistsException()
    {
        super();
    }

    public BookNameExistsException(String message)
    {
        super(message);
    }
}
