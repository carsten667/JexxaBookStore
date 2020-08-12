package de.ck.jexxatutorial.bookstore.domain.aggregate;

import java.util.Optional;

import de.ck.jexxatutorial.bookstore.domain.domainevent.BookSoldOut;
import de.ck.jexxatutorial.bookstore.domain.valueobject.ISBN13;

public class Book
{
    private ISBN13 isbn13;
    private int amount;

    private Book(final ISBN13 isbn13, final int amount)
    {
        this.isbn13 = isbn13;
        this.amount = amount;
    }

    public static Book createBook(final ISBN13 isbn13, final int amount)
    {
        return new Book(isbn13, amount);
    }

    public ISBN13 getISBN13()
    {
        return isbn13;
    }

    public int getAmount()
    {
        return amount;
    }

    public Optional<BookSoldOut> sellBook()
    {
        amount--;
        BookSoldOut bookSoldOut = null;
        if (amount == 0)
        {
            bookSoldOut = new BookSoldOut();
        }
        return Optional.ofNullable(bookSoldOut);
    }


}
