package de.ck.jexxatutorial.bookstore.applicationservice;

import java.util.List;
import java.util.Optional;

import de.ck.jexxatutorial.bookstore.domain.aggregate.Book;
import de.ck.jexxatutorial.bookstore.domain.businessexception.BookNotInStockException;
import de.ck.jexxatutorial.bookstore.domain.domainevent.BookSoldOut;
import de.ck.jexxatutorial.bookstore.domain.valueobject.ISBN13;
import de.ck.jexxatutorial.bookstore.domainservice.IBookRepository;
import de.ck.jexxatutorial.bookstore.domainservice.IDomainEventPublisher;

public class BookStoreService
{
    private IBookRepository bookRepository = null;
    private IDomainEventPublisher domainEventPublisher = null;

    public BookStoreService(final IBookRepository bookRepository, final IDomainEventPublisher domainEventPublisher)
    {
        this.bookRepository = bookRepository;
        this.domainEventPublisher = domainEventPublisher;
    }


    public List<Book> getBooks()
    {
        return bookRepository.getAll();
    }

    public boolean inStock(final ISBN13 isbn13) throws BookNotInStockException
    {
        if (!bookRepository.isRegistered(isbn13))
        {
            throw new BookNotInStockException("Book with isbn " + isbn13 + " is not in stock");
        }
        return true;
    }

    public void addToStock(final Book book)
    {
        bookRepository.add(book);
    }

    public void sellBook(final Book book)
    {
        final Optional<BookSoldOut> bookSoldOut = book.sellBook();

        if (bookSoldOut.isPresent())
        {
            domainEventPublisher.publishBookSoldOut(new BookSoldOut());
        }
    }


}
