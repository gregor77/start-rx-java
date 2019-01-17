# ch3. 연산자와 변환
RxJava의 Observable 연산자의 기초에 대해서 접할 수 있는 챕터이다.
각 연산자들 별로 실습을 진행할 수 있는 즐거움을 느낄 수 있지만, 그만큼 각 연산자에 대한 자세한 설명들 덕분에(?)
지루할 수도 있는 챕터이다.
하지만 앞으로 RxJava를 실제로 적용하려면 코드를 직접 짜보면서 이해해보는 방법을 추천한다. 시간이 오래 걸릴 수 있지만 가장 확실한 방법이다

*  Java 8의 stream에 대해서 알고 있다면 이해하기 쉽다.


#### filter
![filter()](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/filter.png)

* 변환을 재사용하거나 다른 방식으로 사용하려면? **작은 단위로 쪼개라**


#### amb
![amb()](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/amb.png)

* amb()는 자신을 깨운 첫번째 Observable의 이벤트만 전달한다. 나머지 ObservableSource는 구독을 해지한다
* ambArray()는 amb()와 쌍대이다. 단 입력파라미터의 타입만 다르다
* ex) AmbTest 참조

## scan, reduce
event가 downstream으로 흐르는 동안의 값을 보관하거나, 누적하는 경우 외부 전역변수를 두고 상태를 저장하는 행위는
안티패턴이다. 전역상태는 쓰레드에 안전해야 하는데 Observable 연산자의 람다식은 임의에 쓰레드에 수행될 수 있기때문에
동시성 문제가 발생한다. 이를 해결하기 위해 Rx에서는 scan, reduce라는 연산자를 제공한다. 

#### scan
![scan()](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/scan.png)

* scan(), 중간에 누적된 event들을 매번 방출한다
* 예: chunk 파일의 진행률을 계속 보여줘야 하는 경우 적합

#### reduce
![reduce()](https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/reduce.2.png)

* reduce(), 최종 누적된 결과만 한번 방출한다
* 예: chunk 파일 진행의 최종 결과만 관심있는 경우
* reduce(initialValue, Biconsumer) 의 'eventCount = 1(initialValue) + upstream size' 이다.

#### compose
compose()는 몇몇 연산자를 연결하여 업스트림 Observable을 변환하는 함수를 인자로 받는다.

#### lift
사용자 정의 연산자를 만들기는 까다롭다. backpressure 문제와 구독 원리를 고려해야한다. 최대한 기존 연산자를 활용하여 목적을 달성하자

* 