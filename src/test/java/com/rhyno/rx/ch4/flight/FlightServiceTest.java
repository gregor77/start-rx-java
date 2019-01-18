package com.rhyno.rx.ch4.flight;

import com.rhyno.rx.ch4.flight.api.SmtpApi;
import com.rhyno.rx.ch4.flight.api.SmtpResponse;
import com.rhyno.rx.ch4.flight.repository.BookTicketRepository;
import com.rhyno.rx.ch4.flight.repository.FlightRepository;
import com.rhyno.rx.ch4.flight.repository.PassengerRepository;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.List;
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
    @Mock
    private SmtpApi mockSmtpApi;
    @Captor
    private ArgumentCaptor<BookTicket> bookTicketCaptor;

    @Test
    public void givenNoExistFlight_whenLookupFlight_thenThrowNoFlightError() {
        try {
            subject.lookupFlight("any-no");
            Assert.fail("must be not called");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("No flight error");
        }
    }

    @Test
    public void whenLookUpFlight_thenReturnFoundFlightByNo() throws Exception {
        when(mockFlightRepository.findByFlightNo("any-no")).thenReturn(Optional.of(Flight.builder().build()));
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
            assertThat(e.getMessage()).isEqualTo("No passenger error");
        }
    }

    @Test
    public void whenBookTicket_withFlightAndPassenger_thenReturnBookTicket() throws Exception {
        subject.bookTicket(anyFlight, anyPassenger);

        then(mockBookTicketRepository).should().save(bookTicketCaptor.capture());
        BookTicket createdBookTicket = bookTicketCaptor.getValue();
        assertThat(createdBookTicket.getFlight()).isEqualTo(anyFlight);
        assertThat(createdBookTicket.getPassenger()).isEqualTo(anyPassenger);
    }

    @Test
    public void whenSendEmail_thenReturnSmtpResponse() {
        BookTicket anyTicket = BookTicket.builder().id(ANY_ID).flight(anyFlight).passenger(anyPassenger).build();
        when(mockSmtpApi.sendEmail(anyTicket))
                .thenReturn(SmtpResponse.builder().status(HttpStatus.OK).build());

        SmtpResponse smtpResponse = subject.sendEmail(anyTicket);

        assertThat(smtpResponse).isEqualTo(SmtpResponse.builder().status(HttpStatus.OK).build());
    }

    @Test
    public void whenGetTicketsByFlight_thenFindTicketsByFlightNo() throws Exception {
        BookTicket firstTicket = BookTicket.builder().id(1L).build();
        BookTicket secondTicket = BookTicket.builder().id(2L).build();

        when(mockFlightRepository.findByFlightNo(ANY_FLIGHT_NO))
                .thenReturn(Optional.of(Flight.builder()
                        .flightNo(ANY_FLIGHT_NO)
                        .bookTickets(Lists.newArrayList(firstTicket, secondTicket))
                        .build()));

        List<BookTicket> result = subject.getTicketsByFlight(ANY_FLIGHT_NO);
        assertThat(result).containsExactly(firstTicket, secondTicket);
    }
}