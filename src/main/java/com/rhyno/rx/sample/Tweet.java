package com.rhyno.rx.sample;

import lombok.Builder;

@Builder
public class Tweet {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
