package de.ck.jexxatutorial.bookstore.domainservice;

import de.ck.jexxatutorial.bookstore.domain.domainevent.BookSoldOut;

public interface IDomainEventPublisher
{
    void publishBookSoldOut(final BookSoldOut bookSoldOut);
}
