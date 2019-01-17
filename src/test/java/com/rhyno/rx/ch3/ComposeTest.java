package com.rhyno.rx.ch3;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ComposeTest {
    @Test
    public void whenCompose_thenTransformObservableType() throws InterruptedException {
        Observable<String> observable = Observable.just("A", "B", "C", "D")
                .compose(upstream -> {
                    Observable<Boolean> repeat = Observable.just(true, false).repeat();
                    return upstream.zipWith(repeat, (c, b) -> {
                        if (b) {
                            return c.concat("1");
                        } else {
                            return c.concat("0");
                        }
                    });
                })
                .doOnNext(System.out::println);

        TestObserver testObserver = new TestObserver();
        observable.subscribeWith(testObserver);

        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void whenBuffer_thenReturnObservableList() throws InterruptedException {
        Observable
                .range(1, 9)
                .buffer(1, 2)
                .concatMapIterable(x -> x.stream().filter(v -> v % 3 == 0).collect(Collectors.toList()))
                .map(Object::toString)
                .subscribe(x -> System.out.println(x));
    }
}
