package com.rhyno.rx.ch4.flight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final BookTicketRepository bookTicketRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository, PassengerRepository passengerRepository,
                         BookTicketRepository bookTicketRepository) {
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.bookTicketRepository = bookTicketRepository;
    }

    public Flight lookupFlight(String flightNo) {
        return flightRepository.findByFlightNo(flightNo).orElse(null);
    }

    public Passenger findPassenger(long id) throws Exception {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new Exception("No exists passenger"));
    }

    public BookTicket bookTicket(Flight flight, Passenger passenger) throws Exception {
        Flight foundFlight = flightRepository.findByFlightNo(flight.getFlightNo())
                .orElseThrow(() -> new Exception("No flight error"));

        Passenger foundPassenger = passengerRepository.findById(passenger.getId())
                .orElseThrow(() -> new Exception("No passenger error"));

        return bookTicketRepository.save(BookTicket.builder()
                .flight(foundFlight)
                .passenger(foundPassenger)
                .build());
    }

    public SmtpResponse sendEmail(BookTicket bookTicket) {
        return null;
    }
}
