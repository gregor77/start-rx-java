package com.rhyno.rx.ch4.flight;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {
    private static final long ANY_ID = 1L;
    private static final String ANY_FLIGHT_NO = "any-flight-no";
    private static final Flight anyFlight = Flight.builder().id(ANY_ID).flightNo(ANY_FLIGHT_NO).build();
    private static final Passenger anyPassenger = Passenger.builder().id(ANY_ID).name("any-name").build();

    @InjectMocks
    private FlightService subject;
    @Mock
    private FlightRepository mockFlightRepository;
    @Mock
    private PassengerRepository mockPassengerRepository;
    @Mock
    private BookTicketRepository mockBookTicketRepository;
    @Captor
    private ArgumentCaptor<BookTicket> bookTicketCaptor;

    @Test
    public void whenLookUpFlight_thenReturnFoundFlightByNo() {
        subject.lookupFlight("any-no");

        then(mockFlightRepository).should().findByFlightNo("any-no");
    }

    @Test
    public void whenFindPassenger_thenReturnFoundPassengerById() throws Exception {
        when(mockPassengerRepository.findById(ANY_ID))
                .thenReturn(Optional.of(Passenger.builder().id(ANY_ID).name("any-name").build()));

        subject.findPassenger(ANY_ID);

        then(mockPassengerRepository).should().findById(ANY_ID);
    }

    @Test
    public void givenNoExistPassengerById_whenFindPassenger_thenThrowError() {
        try {
            subject.findPassenger(1L);
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("No exists passenger");
        }
    }

    @Test
    public void givenNoFlight_whenBookTicket_thenThrowNoFlightError() {
        try {
            subject.bookTicket(anyFlight, anyPassenger);
            Assert.fail("must be not called");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("No flight error");
        }
    }

    @Test
    public void givenNoPassenger_whenBookTicket_thenThrowNoPassengerError() {
        when(mockFlightRepository.findByFlightNo(ANY_FLIGHT_NO))
                .thenReturn(Optional.of(Flight.builder().id(ANY_ID).flightNo(ANY_FLIGHT_NO).build()));

        try {
            subject.bookTicket(anyFlight, anyPassenger);
            Assert.fail("must be not called");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("No passenger error");
        }
    }

    @Test
    public void whenBookTicketWithFlightAndPassenger_thenReturnBookTicket() throws Exception {
        when(mockFlightRepository.findByFlightNo(ANY_FLIGHT_NO)).thenReturn(Optional.of(anyFlight));
        when(mockPassengerRepository.findById(ANY_ID)).thenReturn(Optional.of(anyPassenger));

        subject.bookTicket(anyFlight, anyPassenger);

        then(mockBookTicketRepository).should().save(bookTicketCaptor.capture());
        BookTicket createdBookTicket = bookTicketCaptor.getValue();
        assertThat(createdBookTicket.getFlight()).isEqualTo(anyFlight);
        assertThat(createdBookTicket.getPassenger()).isEqualTo(anyPassenger);
    }

    @Test
    public void whenSendEmail_thenReturnSmtpResponse() {
        BookTicket anyTicket = BookTicket.builder().id(ANY_ID).flight(anyFlight).passenger(anyPassenger).build();

        subject.sendEmail(anyTicket);
    }
}