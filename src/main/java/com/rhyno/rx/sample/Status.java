package com.rhyno.rx.sample;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Builder
@Getter
@Setter
@ToString
public class Status {
    private String name;
    private Date createdAt;
}
