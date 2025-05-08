package it.ifts.ifoa.teletubbies.exception;

public class InsertFailedException extends RuntimeException
{
    public InsertFailedException(String message)
    {
        super(message);
    }
}
