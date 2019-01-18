package com.rhyno.rx.ch4.flight.repository;

import com.rhyno.rx.ch4.flight.Passenger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Long> {
}

