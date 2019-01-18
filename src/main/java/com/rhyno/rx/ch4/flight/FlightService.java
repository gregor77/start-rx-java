package com.rhyno.rx.ch4.flight;

import com.rhyno.rx.ch4.flight.api.SmtpApi;
import com.rhyno.rx.ch4.flight.api.SmtpResponse;
import com.rhyno.rx.ch4.flight.repository.BookTicketRepository;
import com.rhyno.rx.ch4.flight.repository.FlightRepository;
import com.rhyno.rx.ch4.flight.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final BookTicketRepository bookTicketRepository;
    private final SmtpApi smtpApi;

    @Autowired
    public FlightService(FlightRepository flightRepository, PassengerRepository passengerRepository,
                         BookTicketRepository bookTicketRepository, SmtpApi smtpApi) {
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.bookTicketRepository = bookTicketRepository;
        this.smtpApi = smtpApi;
    }

    @PostConstruct
    public void registerMetaData() {
        List<Flight> flights = Arrays.asList(
                Flight.builder().flightNo("F-001").build(),
                Flight.builder().flightNo("F-002").build(),
                Flight.builder().flightNo("F-003").build()
        );
        flightRepository.saveAll(flights);

        List<Passenger> passengers = Arrays.asList(
                Passenger.builder().name("rhyno").build(),
                Passenger.builder().name("jude").build(),
                Passenger.builder().name("iaan").build()
        );
        passengerRepository.saveAll(passengers);
    }

    public BookTicket bookTicket(Flight flight, Passenger passenger) {
        return bookTicketRepository.save(BookTicket.builder()
                .flight(flight)
                .passenger(passenger)
                .build());
    }

    public Flight lookupFlight(String flightNo) throws Exception {
        return flightRepository.findByFlightNo(flightNo)
                .orElseThrow(() -> new Exception("No flight error"));
    }

    public Passenger findPassenger(long id) throws Exception {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new Exception("No passenger error"));
    }


    public SmtpResponse sendEmail(BookTicket bookTicket) {
        return smtpApi.sendEmail(bookTicket);
    }

    public List<BookTicket> getTicketsByFlight(String flightNo) throws Exception {
        return flightRepository.findByFlightNo(flightNo)
                .map(flight -> {
                    return flight.getBookTickets();
                })
                .orElseThrow(() -> new Exception("No bookTicket error"));
    }
}
