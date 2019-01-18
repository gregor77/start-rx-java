package com.rhyno.rx.ch4.flight;

import com.rhyno.rx.ch4.flight.api.SmtpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
public class FlightController {
    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public SmtpResponse bookTicket(@RequestBody TicketDTO ticketDTO) throws Exception {
        Flight flight = flightService.lookupFlight(ticketDTO.getFlightNo());
        Passenger passenger = flightService.findPassenger(ticketDTO.getPassengerId());

        return Optional.ofNullable(flightService.bookTicket(flight, passenger))
                .map(flightService::sendEmail)
                .orElse(new SmtpResponse().builder()
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .build());
    }

    @GetMapping
    public List<BookTicket> getTicketsByFlight(@RequestParam(name = "flightNo") String flightNo) throws Exception {
        return flightService.getTicketsByFlight(flightNo);
    }
}
