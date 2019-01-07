# start rx-java
RxJava를 활용한 리액티브 프로그래밍 실습과 테스트 프로젝트

* stack
  - jdk v1.8.0
  - spring-boot v2.1.1

## preparation
* java 8 stream, lambda

## terms
* reactive stream
* upstream vs downstream
* Bubble diagram
* backpressure


### delay vs timer vs interval 
http://reactivex.io/documentation/ko/operators  Observable 연산자 결정트리 참조

* delay vs timer vs interval
* delay: Observable이 항목을 배출하기 전에 항목의 배출 시간을 지연
* interval: 특정 시간 간격별로 항목을 배출해야 한다면
* timer: 지정된 시간 이후에 항목을 배출해야 한다면

### Observable의 Error Handlig
1. Action on Error
  * .doOnError() AtomicBoolean 값으로, onError의 경우 값을 세팅 후, assert로 비교
  * RxJava는 event를 emit하는 동안 발생한 exception는 CompositionException으로 wrap한다
2. Resume with Default Items
  * doOnError를 사용하여 작업을 호출 할 수 있지만 오류로 인해 표준 시퀀스 플로우가 여전히 손상됩니다. 
  때로는 onErrorReturnItem이하는 것과 같이 기본 옵션을 사용하여 시퀀스를 다시 시작하고자 할 수 있습니다.
  * .onErrorReturnItem("singleValue"): object return
  * .onErrorReturn(Throwable::getMessage): supplier 
3. Resume with Another Sequence
  * 단일 항목으로 폴백하는 대신 오류가 발생할 경우 onErrorResumeNext를 사용하여 폴백 데이터 시퀀스를 제공 할 수 있습니다. 
   이렇게하면 오류 확산을 방지하는 데 도움이됩니다.
  * .onErrorResumeNext(Observable.just("one", "two"))
  * 대체 시퀀스가 특정 예외 유형에 따라 다르거 나 함수에서 시퀀스를 생성해야하는 경우이 함수를 onErrorResumeNext에 전달할 수 있습니다.
  * .onErrorResumeNext(throwable -> Observable.just(throwable.getMessage(), "nextValue"))
4. Handle Exception Only
  * 또한 RxJava는 오류 (예외는 발생하지 않음)가 발생할 때 제공된 Observable을 사용하여 시퀀스를 계속할 수있는 대체 방법을 제공합니다.
  * onErrorResumeNext()는 오류가 발생하면 시퀀스를 다시 시작하지 않는다.
   
#### 참조
  - [RxJava2를 도입하며](https://medium.com/rainist-engineering/migrate-from-rxjava1-to-rxjava2-3aea3ff9051c), RxJava1과 비교하여 변경되는 부분을 확인하려면 반드시 읽어볼것
  - [Observable의 연산자 결정트리](http://reactivex.io/documentation/ko/operators)
  - https://rxjava-doc.readthedocs.io/en/latest/What's-different-in-2.0/
  - [RxJava2 Error Handling](https://www.baeldung.com/rxjava-error-handling)