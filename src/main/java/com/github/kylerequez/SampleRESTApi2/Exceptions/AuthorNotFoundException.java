package com.github.kylerequez.SampleRESTApi2.Exceptions;

public class AuthorNotFoundException extends RuntimeException
{
    public AuthorNotFoundException()
    {
        super();
    }

    public AuthorNotFoundException(String message)
    {
        super(message);
    }
}
