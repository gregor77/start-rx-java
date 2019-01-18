package com.rhyno.rx.ch4.flight.api;

import com.rhyno.rx.ch4.flight.BookTicket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmtpResponse {
    private HttpStatus status;

    private BookTicket bookTicket;
}
