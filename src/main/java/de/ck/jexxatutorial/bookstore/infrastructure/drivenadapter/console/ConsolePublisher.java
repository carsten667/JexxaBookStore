package de.ck.jexxatutorial.bookstore.infrastructure.drivenadapter.console;

import de.ck.jexxatutorial.bookstore.domain.domainevent.BookSoldOut;
import de.ck.jexxatutorial.bookstore.domainservice.IDomainEventPublisher;
import io.jexxa.utils.JexxaLogger;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

@SuppressWarnings("unused")
public class ConsolePublisher implements IDomainEventPublisher
{
    private static final Logger LOGGER = JexxaLogger.getLogger(ConsolePublisher.class);

    @Override
    public void publishBookSoldOut(final BookSoldOut bookSoldOut)
    {
        Validate.notNull(bookSoldOut);

        LOGGER.info(String.valueOf(bookSoldOut));
    }
}
