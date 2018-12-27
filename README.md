# start rx-java
RxJava를 활용한 리액티브 프로그래밍 실습과 테스트 프로젝트

* stack
  - jdk v1.8.0
  - spring-boot v2.1.1

#### delay vs timer vs interval 
http://reactivex.io/documentation/ko/operators  Observable 연산자 결정트리 참조

* delay vs timer vs interval
* delay: Observable이 항목을 배출하기 전에 항목의 배출 시간을 지연
* interval: 특정 시간 간격별로 항목을 배출해야 한다면
* timer: 지정된 시간 이후에 항목을 배출해야 한다면
  
#### 참조
  - [Observable의 연산자 결정트리](http://reactivex.io/documentation/ko/operators)
  - https://rxjava-doc.readthedocs.io/en/latest/What's-different-in-2.0/
