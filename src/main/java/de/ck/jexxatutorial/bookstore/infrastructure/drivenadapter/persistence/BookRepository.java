package de.ck.jexxatutorial.bookstore.infrastructure.drivenadapter.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import de.ck.jexxatutorial.bookstore.domain.aggregate.Book;
import de.ck.jexxatutorial.bookstore.domain.valueobject.ISBN13;
import de.ck.jexxatutorial.bookstore.domainservice.IBookRepository;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.IRepository;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.RepositoryManager;

public class BookRepository implements IBookRepository
{
    private final IRepository<Book, ISBN13> repository;

    private BookRepository(final IRepository<Book, ISBN13> repository)
    {
        this.repository = repository;
    }

    // Factory method that requests a repository strategy from Jexxa's RepositoryManager
    public static IBookRepository create(final Properties properties)
    {
        return new BookRepository(
                RepositoryManager.getInstance().getStrategy(Book.class, Book::getISBN13, properties)
        );
    }

    @Override
    public void add(final Book book)
    {
        repository.add(book);
    }

    @Override
    public Book get(final ISBN13 isbn13)
    {
        return repository.get(isbn13).orElseThrow();
    }

    @Override
    public boolean isRegistered(final ISBN13 isbn13)
    {
        return search(isbn13).isPresent();
    }

    @Override
    public Optional<Book> search(final ISBN13 isbn13)
    {
        return repository.get(isbn13);
    }

    @Override
    public void update(final Book book)
    {
        repository.update(book);
    }

    @Override
    public List<Book> getAll()
    {
        return repository.get();
    }
}
