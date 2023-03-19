package com.github.kylerequez.SampleRESTApi2.Exceptions;

public class AuthorNullVariableException extends RuntimeException
{
    public AuthorNullVariableException()
    {
        super();
    }

    public AuthorNullVariableException(String message)
    {
        super(message);
    }
}
