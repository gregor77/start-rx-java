package com.rhyno.rx.ch4.flight;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {
    Optional<Flight> findByFlightNo(String flightNo);
}
