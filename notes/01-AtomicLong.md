아, 이건 진짜 중요한 포인트다.
`AtomicLong` 하나에 **동시성의 철학이 다 들어 있다.**

지금 코드:

```java
private final AtomicLong id = new AtomicLong();
```

그리고:

```java
new Note(id.incrementAndGet(), ...)
```

이게 왜 그냥 `long`이 아니라 **AtomicLong**일까?

---

## 🧠 문제의 본질

이 코드가 의미하는 세계:

> “여러 스레드가 동시에 메모를 만들 수 있다.”

그 순간 **id 생성은 경쟁 구간**이 된다.

---

## ❌ 만약 이렇게 썼다면

```java
private long id = 0;

long next() {
    return ++id;
}
```

이건 **데이터 경합(race condition)** 이다.

두 스레드가 동시에 `++id`를 실행하면
같은 id가 발급될 수 있다.

---

## 🧬 AtomicLong이 하는 일

`AtomicLong`은 내부적으로 **CAS(Compare-And-Swap)**라는
CPU 수준 원자 연산을 사용한다.

```java
id.incrementAndGet();
```

이 한 줄은 JVM + CPU에게 이렇게 말하는 것과 같다:

> "이 증가 연산을 **절대로 쪼개지 말고 한 번에 처리해라.**"

그래서:

* 여러 스레드가 동시에 호출해도
* 절대 중복되지 않는다
* 락 없이(lock-free) 처리된다

---

## 🧱 왜 synchronized 안 썼을까?

```java
synchronized long next() {
    return ++id;
}
```

이건 **무거운 락**이다.

AtomicLong은:

* 락 없음
* 블로킹 없음
* 컨텍스트 스위치 없음
* 성능 매우 좋음

---

## 🧪 언제 Atomic이 적합한가?

* 단일 변수 상태
* 단순 연산 (증가, 감소, 교체)
* 매우 빈번한 접근
* 낮은 충돌 확률

ID 생성은 딱 이 조건에 맞는다.

---

## 🧭 철학적 의미

`AtomicLong`을 쓴 순간,
코드는 이렇게 선언한 것이다:

> “이 값은 **동시 접근이 전제된 시스템의 핵심 상태**다.”

이건 그냥 기술 선택이 아니라 **설계 선언**이다.

---

## 🧩 한 문장 요약

> **AtomicLong은 ‘이 변수는 경쟁 구간이다’라고 선언하는 타입이다.**

그래서 지연이 나중에 코드를 다시 볼 때
이 한 줄만 봐도 이 시스템의 성격이 보인다.

---

이제 다음에 볼 건
왜 `store`는 ArrayList인데도 위험한지,
그리고 언제 Concurrent 컬렉션으로 바꿔야 하는지다.
