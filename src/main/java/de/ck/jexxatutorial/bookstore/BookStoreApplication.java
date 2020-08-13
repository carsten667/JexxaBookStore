package de.ck.jexxatutorial.bookstore;

import de.ck.jexxatutorial.bookstore.applicationservice.BookStoreService;
import de.ck.jexxatutorial.bookstore.domainservice.ReferenceLibrary;
import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.RepositoryManager;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.imdb.IMDBRepository;
import io.jexxa.infrastructure.drivenadapterstrategy.persistence.jdbc.JDBCKeyValueRepository;
import io.jexxa.infrastructure.drivingadapter.jmx.JMXAdapter;
import io.jexxa.infrastructure.drivingadapter.rest.RESTfulRPCAdapter;
import io.jexxa.utils.JexxaLogger;

public final class BookStoreApplication
{
    //Declare the packages that should be used by Jexxa

    private static final String DRIVEN_ADAPTER = BookStoreApplication.class.getPackageName() + ".infrastructure.drivenadapter";
    private static final String OUTBOUND_PORTS = BookStoreApplication.class.getPackageName() + ".domainservice";

    public static void main(String[] args)
    {
        // Define the default strategy which is either an IMDB database or a JDBC based repository
        // In case of JDBC we use a simple key value approach which stores the key and the value as json strings.
        // Using json strings might be very inconvenient if you come from typical relational databases but in terms
        // of DDD our aggregate is responsible to ensure consistency of our data and not the database.
        RepositoryManager.getInstance().setDefaultStrategy(getDrivenAdapterStrategy(args));

        JexxaMain jexxaMain = new JexxaMain(BookStoreApplication.class.getSimpleName());

        jexxaMain
                //Define which outbound ports should be managed by Jexxa
                .addToApplicationCore(OUTBOUND_PORTS)
                .addToInfrastructure(DRIVEN_ADAPTER)

                //Get the latest books when starting the application
                .bootstrap(ReferenceLibrary.class).with(ReferenceLibrary::addLatestBooks)

                .bind(RESTfulRPCAdapter.class).to(BookStoreService.class)
                .bind(JMXAdapter.class).to(BookStoreService.class)

                .bind(JMXAdapter.class).to(jexxaMain.getBoundedContext())
                .bind(RESTfulRPCAdapter.class).to(jexxaMain.getBoundedContext())

                .start()

                .waitForShutdown()

                .stop();
    }

    private static Class getDrivenAdapterStrategy(String[] args)
    {
        if (isJDBCEnabled(args))
        {
            JexxaLogger.getLogger(BookStoreApplication.class).info("Use persistence strategy: JDBCKeyValueRepository ");
            return JDBCKeyValueRepository.class;
        }
        JexxaLogger.getLogger(BookStoreApplication.class).info("Use persistence strategy: IMDBRepository ");
        return IMDBRepository.class;
    }

    private static boolean isJDBCEnabled(String[] args)
    {
        return args.length == 1 && "jdbc".equals(args[0]);
    }

}
