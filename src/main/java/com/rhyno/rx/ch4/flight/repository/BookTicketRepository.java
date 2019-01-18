package com.rhyno.rx.ch4.flight.repository;

import com.rhyno.rx.ch4.flight.BookTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTicketRepository extends CrudRepository<BookTicket, Long> {
}
