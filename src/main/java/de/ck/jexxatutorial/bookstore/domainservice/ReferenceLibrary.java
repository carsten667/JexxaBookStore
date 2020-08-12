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
        bookRepository.add(Book.createBook(new ISBN13("978-1-891830-85-3"), 1));
        bookRepository.add(Book.createBook(new ISBN13("978-1-60309-025-4"), 2));
        bookRepository.add(Book.createBook(new ISBN13("978-1-60309-016-2"), 3));
        bookRepository.add(Book.createBook(new ISBN13("978-1-60309-265-4"), 4));
        bookRepository.add(Book.createBook(new ISBN13("978-1-60309-047-6"), 5));
        bookRepository.add(Book.createBook(new ISBN13("978-1-60309-322-4"), 6));
    }
}
