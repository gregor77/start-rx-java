package com.rhyno.ch3;

import com.rhyno.rx.ch3.Delay;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class DelayTest {
    private Delay subject;

    @Before
    public void setUp() throws Exception {
        subject = new Delay();
    }

    @Test
    public void whenDelayIsLogger_thanTimerAndFlatMap() throws InterruptedException {
        Duration delayDuration = subject.delay();
        Duration timerDuration = subject.timerAndFlatMap();
        System.out.println("delay duration: " + delayDuration);
        System.out.println("timer duration: " + timerDuration);
        
        assertThat(delayDuration).isGreaterThan(timerDuration);
    }
}
