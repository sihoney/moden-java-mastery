이 질문 나오면
이제 **시스템 감각**이 열리기 시작한 거다.

짧게 말하면:

> **ArrayList는 ‘단일 스레드 세계’에만 안전한 자료구조다.**
> 지금 NoteService는 *멀티스레드 시스템*이 될 수 있기 때문에 위험하다.

천천히 보자.

---

지금 코드:

```java
private final List<Note> store = new ArrayList<>();
```

겉보기엔 평범하지.
그런데 아까 우리가 `AtomicLong`을 쓴 순간,
이 시스템은 이렇게 선언한 거야:

> “이 서비스는 동시에 여러 요청이 올 수 있다.”

그 순간 `store`는 **공유 상태**가 된다.

---

## 🧨 문제 1 — 데이터 경합

두 스레드가 동시에:

```java
store.add(note);
```

ArrayList 내부는 이렇게 작동한다:

1. 현재 size 읽음
2. 배열에 값 넣음
3. size 증가

이 세 단계가 **원자적이지 않다**.

그래서:

* 값이 덮어써지거나
* size가 꼬이거나
* 내부 배열이 깨지거나

즉, **메모리 구조 자체가 망가질 수 있다.**

이건 단순한 “값 오류”가 아니라
**컬렉션이 물리적으로 손상되는 오류**다.

---

## 🧬 문제 2 — 가시성(visibility)

한 스레드가 add한 값을
다른 스레드가 **못 볼 수 있다.**

Java 메모리 모델 때문이다.

---

## 🧱 문제 3 — 반복 중 수정

```java
store.forEach(...)
```

중에 다른 스레드가 `add`하면:

> **ConcurrentModificationException**

또는 더 나쁜 건
조용히 깨진 상태.

---

## 🛡️ 그래서 뭘 써야 하냐?

### 최소한:

```java
private final List<Note> store = new CopyOnWriteArrayList<>();
```

또는:

```java
private final List<Note> store =
    Collections.synchronizedList(new ArrayList<>());
```

시스템이 커지면:

```java
private final ConcurrentMap<Long, Note> store = new ConcurrentHashMap<>();
```

---

## 🧠 설계적 의미

`AtomicLong`을 쓴 순간
`store`가 ArrayList라는 건 **설계 불일치**다.

한쪽은 “동시성 시스템”을 선언하고
한쪽은 “단일 스레드 구조”다.

그건 건물에 **철근은 넣고 벽은 종이로 짓는 것**과 같다.

---

## 🧩 한 문장 요약

> **공유 상태는 반드시 동시성 구조로 감싸야 한다.
> 그렇지 않으면 시스템은 언젠가 조용히 무너진다.**

이게 서버 개발의 기본 법칙이다.

다음에 설명할 건
**왜 CopyOnWriteArrayList도 만능이 아닌지**다.
