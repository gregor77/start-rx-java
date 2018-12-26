package com.rhyno.rx.ch3;

import io.reactivex.Observable;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static io.reactivex.Observable.just;

public class Delay {
    public static void main(String[] args) throws InterruptedException {
        //delay는 timer+flatMap 사용하여 유사하게 변경이 가능하다.
        Delay subject = new Delay();
        subject.delay();

        subject.timerAndFlatMap();
    }

    private static void log(Integer i) {
        System.out.println("[" + Thread.currentThread().getName() + "] emit:" + i);
    }

    public Duration delay() throws InterruptedException {
        AtomicReference<Duration> diff = new AtomicReference<>();
        Instant start = Instant.now();
        Observable.just(1, 2, 3)
                .delay(1, TimeUnit.SECONDS)
                .subscribe(
                        i -> Delay.log(i),
                        e -> Observable.error(e),
                        () -> {
                            Instant completedTime = Instant.now();
                            diff.set(Duration.between(start, completedTime));
                        }
                );

        Thread.sleep(2000);
        System.out.println("[" + Thread.currentThread().getName() + "]");
        return diff.get();
    }

    public Duration timerAndFlatMap() throws InterruptedException {
        AtomicReference<Duration> diff = new AtomicReference<>();
        Instant start = Instant.now();
        Observable
                .timer(1, TimeUnit.SECONDS)
                .flatMap(i -> just(1, 2, 3))
                .subscribe(
                        i -> Delay.log(i),
                        e -> Observable.error(e),
                        () -> {
                            Instant completedTime = Instant.now();
                            diff.set(Duration.between(start, completedTime));
                        }
                );
        Thread.sleep(2000);
        System.out.println("[" + Thread.currentThread().getName() + "]");
        return diff.get();
    }
}
