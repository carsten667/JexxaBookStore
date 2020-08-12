package de.ck.jexxatutorial.bookstore.applicationservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import de.ck.jexxatutorial.bookstore.domain.aggregate.Book;
import de.ck.jexxatutorial.bookstore.domain.businessexception.BookNotInStockException;
import de.ck.jexxatutorial.bookstore.domain.valueobject.ISBN13;
import de.ck.jexxatutorial.bookstore.domainservice.ReferenceLibrary;
import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.RepositoryManager;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.imdb.IMDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookStoreServiceTest
{
    private static final String DRIVEN_ADAPTER_PERSISTENCE = "de.ck.jexxatutorial.bookstore.infrastructure.drivenadapter.persistence";
    private static final String DRIVEN_ADAPTER_MESSAGING = "de.ck.jexxatutorial.bookstore.infrastructure.drivenadapter.console";

    private JexxaMain jexxaMain;


    @BeforeEach
    void initTest()
    {
        // Here you can define the desired DB strategy without adjusting your tests
        // Within your tests you can completely focus on the domain logic which allows
        // you to run the tests as unit tests within daily development or as integration
        // tests on a build server
        RepositoryManager.getInstance().setDefaultStrategy(IMDBRepository.class);
        jexxaMain = new JexxaMain(BookStoreServiceTest.class.getSimpleName());
        jexxaMain.addToInfrastructure(DRIVEN_ADAPTER_PERSISTENCE)
                .addToInfrastructure(DRIVEN_ADAPTER_MESSAGING);

        //Clean up the repository
        RepositoryManager.getInstance()
                .getStrategy(Book.class, Book::getISBN13, jexxaMain.getProperties())
                .removeAll();

        //Get the latest books when starting the application
        jexxaMain.bootstrap(ReferenceLibrary.class).with(ReferenceLibrary::addLatestBooks);

    }

    @Test
    public void testGetAll()
    {
        //Arrange : Get the inbound port that we would like to test
        var bookStore = jexxaMain.getInstanceOfPort(BookStoreService.class);

        //Act
        final List<Book> books = bookStore.getBooks();

        //Assert: After adding books via our service, our bookstore must know theses books
        assertFalse(books.isEmpty());
    }

    @Test
    public void testInStock() throws BookNotInStockException
    {
        //Arrange : Get the inbound port that we would like to test
        var bookStore = jexxaMain.getInstanceOfPort(BookStoreService.class);
        ISBN13 isbn13 = new ISBN13("978-1-60309-265-4");

        boolean ergebnis = bookStore.inStock(isbn13);

        assertTrue(ergebnis);
    }

    @Test
    public void addToStock()
    {
        //Arrange : Get the inbound port that we would like to test
        var bookStore = jexxaMain.getInstanceOfPort(BookStoreService.class);
        ISBN13 isbn13 = new ISBN13("978-1-891830-99-3");

        bookStore.addToStock(Book.createBook(isbn13, 2));

        assertEquals(2, bookStore.get(isbn13).getAmount());
    }
}