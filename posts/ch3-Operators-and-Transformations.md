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