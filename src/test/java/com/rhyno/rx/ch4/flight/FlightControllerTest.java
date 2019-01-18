package com.rhyno.rx.ch4.flight;

import com.rhyno.rx.ch4.flight.api.SmtpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class FlightControllerTest {
    private static final String ANY_FLIGHT_NO = "any-flight-no";
    private static final String ANY_NAME = "any-name";
    private static final long ANY_NO = 1L;

    private static final TicketDTO anyTicketDTO = TicketDTO.builder()
            .flightNo(ANY_FLIGHT_NO).passengerId(ANY_NO).build();
    private static final Flight anyFlight = Flight.builder().flightNo(ANY_FLIGHT_NO).id(ANY_NO).build();
    private static final Passenger anyPassenger = Passenger.builder().id(ANY_NO).name(ANY_NAME).build();

    @InjectMocks
    private FlightController subject;
    @Mock
    private FlightService mockFlightService;
    @Captor
    private ArgumentCaptor<BookTicket> bookTicketCaptor;

    @Test
    public void whenBookTicket_thenLookupFlight_withFlightNoFromView() throws Exception {
        subject.bookTicket(anyTicketDTO);

        then(mockFlightService).should().lookupFlight("any-flight-no");
    }

    @Test
    public void whenBookTicket_thenFindPassenger_withPassengerIdFromView() throws Exception {
        subject.bookTicket(anyTicketDTO);

        then(mockFlightService).should().findPassenger(1L);
    }

    @Test
    public void whenBookTicket_thenBookTicket_withFoundFlightAndPassenger() throws Exception {
        //given
        mockBookTicket();

        //when
        subject.bookTicket(TicketDTO.builder()
                .flightNo(ANY_FLIGHT_NO)
                .passengerId(ANY_NO)
                .build());

        //then
        then(mockFlightService).should().bookTicket(anyFlight, anyPassenger);
    }

    @Test
    public void givenBookedTicketNoExist_whenBookTicket_thenNotSendEmail() throws Exception {
        mockBookTicket();

        SmtpResponse response = subject.bookTicket(TicketDTO.builder()
                .flightNo("not-exist-flight-no")
                .passengerId(ANY_NO)
                .build());

        then(mockFlightService).should(times(0)).sendEmail(any(BookTicket.class));
        assertThat(response).isEqualTo(SmtpResponse.builder().status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @Test
    public void givenBookedTicketExists_whenBookTicket_thenSendEmailWithBookedTicket() throws Exception {
        mockBookTicket();

        subject.bookTicket(TicketDTO.builder()
                .flightNo(ANY_FLIGHT_NO)
                .passengerId(ANY_NO)
                .build());

        then(mockFlightService).should().sendEmail(bookTicketCaptor.capture());
        BookTicket bookedTicket = bookTicketCaptor.getValue();
        assertThat(bookedTicket).isEqualTo(BookTicket.builder()
                .flight(anyFlight)
                .passenger(anyPassenger)
                .build());
    }

    private void mockBookTicket() throws Exception {
        when(mockFlightService.lookupFlight(ANY_FLIGHT_NO)).thenReturn(anyFlight);
        when(mockFlightService.findPassenger(ANY_NO)).thenReturn(anyPassenger);
        when(mockFlightService.bookTicket(anyFlight, anyPassenger))
                .thenReturn(BookTicket.builder().flight(anyFlight).passenger(anyPassenger).build());
    }

    @Test
    public void whenGetTicketsByFlight_thenReturnAllTicketByFlight() throws Exception {
        subject.getTicketsByFlight(ANY_FLIGHT_NO);

        then(mockFlightService).should().getTicketsByFlight("any-flight-no");
    }
}