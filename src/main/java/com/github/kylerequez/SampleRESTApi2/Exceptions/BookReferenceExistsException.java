package com.github.kylerequez.SampleRESTApi2.Exceptions;

public class BookReferenceExistsException extends RuntimeException{
    public BookReferenceExistsException()
    {
        super();
    }

    public BookReferenceExistsException(String message)
    {
        super(message);
    }
}
