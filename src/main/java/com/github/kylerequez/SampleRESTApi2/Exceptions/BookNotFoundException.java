package com.github.kylerequez.SampleRESTApi2.Exceptions;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException()
    {
        super();
    }

    public BookNotFoundException(String message)
    {
        super(message);
    }
}
