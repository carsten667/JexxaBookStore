package de.ck.jexxatutorial.bookstore.domain.businessexception;

public class BookNotInStockException extends Exception
{
    public BookNotInStockException(final String message)
    {
        super(message);
    }
}
