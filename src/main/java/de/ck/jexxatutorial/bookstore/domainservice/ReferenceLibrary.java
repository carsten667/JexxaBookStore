package de.ck.jexxatutorial.bookstore.domainservice;

import de.ck.jexxatutorial.bookstore.domain.aggregate.Book;
import de.ck.jexxatutorial.bookstore.domain.valueobject.ISBN13;

public class ReferenceLibrary
{
    private IBookRepository bookRepository;

    public ReferenceLibrary(final IBookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    public void addLatestBooks()
    {
        bookRepository.add(Book.createBook(new ISBN13("978-1-891830-85-1"), 1));
        bookRepository.add(Book.createBook(new ISBN13("978-1-891830-85-2"), 2));
        bookRepository.add(Book.createBook(new ISBN13("978-1-891830-85-3"), 3));
        bookRepository.add(Book.createBook(new ISBN13("978-1-891830-85-4"), 4));
        bookRepository.add(Book.createBook(new ISBN13("978-1-891830-85-5"), 5));
    }
}
