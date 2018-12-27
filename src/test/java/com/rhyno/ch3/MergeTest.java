package com.rhyno.ch3;

import com.rhyno.rx.ch3.Merge;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeTest {
    private Merge subject;

    @Before
    public void setUp() throws Exception {
        subject = new Merge();
    }

    @Test
    public void whenMergeWithObservables_then() throws InterruptedException {
        Observable<String> all = Observable.merge(
                subject.preciseAlgo(),
                subject.exprementalAlgo(),
                subject.fastAlgo()
        );

        TestObserver<String> testObservable = new TestObserver<>();
        all.subscribe(testObservable);

        TimeUnit.SECONDS.sleep(8);

        testObservable.assertComplete();

        List<String> emittedEvents = testObservable.getEvents().get(0)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        System.out.println("merge is not ");
        System.out.println("[emitted events] " + emittedEvents);

        assertThat(emittedEvents).isNotEqualTo(Lists.newArrayList(
                "precise-1", "precise-2", "precise-3",
                "exp-1", "exp-2", "exp-3",
                "fast-1", "fast-2", "fast-3"
        ));
    }


}
