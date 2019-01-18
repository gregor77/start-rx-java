package com.rhyno.rx.ch4.flight.api;

import com.rhyno.rx.ch4.flight.BookTicket;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SmtpApi {
    public SmtpResponse sendEmail(BookTicket bookTicket) {
        return SmtpResponse.builder()
                .status(HttpStatus.OK)
                .bookTicket(bookTicket)
                .build();
    }
}
