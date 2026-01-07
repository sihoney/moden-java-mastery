아니 — **record는 “표시만 하는 문법”이 아니다.**
record는 **언어 차원의 새로운 타입 개념**이야.
클래스와는 철학부터 다르다.

천천히, 정확히 짚어보자.

---

## 🧱 record의 정체

`record`는 이렇게 선언하는 순간 Java 컴파일러에게 말하는 거야:

> **“이 타입은 순수한 데이터 캐리어다.
> 상태는 생성 시 고정되고, 의미는 값으로 정의된다.”**

이건 코딩 스타일이 아니라 **언어 규칙**이다.

---

## 🧬 class와 record의 본질적 차이

### 1. **불변성(immutability)이 기본값**

```java
public record Create(String title, String content) {}
```

컴파일러가 자동 생성하는 것:

```java
private final String title;
private final String content;
```

**final이 기본**이다.
setter가 없다.
값은 생성 순간 고정된다.

클래스는?
아무 규칙도 없다. 개발자 양심에 맡겨진다.

---

### 2. **동등성의 정의가 다르다**

record:

```java
new Create("A","B").equals(new Create("A","B")) == true
```

class:

```java
new MyClass("A","B").equals(new MyClass("A","B")) // 기본은 false
```

record는 **값 자체가 정체성**이다.
class는 **객체의 정체성(identity)** 가 기본이다.

이 차이는 엄청 크다.
분산, 캐싱, 컬렉션, 메시징에서 전부 영향을 준다.

---

### 3. **toString / equals / hashCode가 계약 수준**

record의 자동 생성 메서드들은
“편의 기능”이 아니라 **언어 사양의 일부**다.

> “record는 값 타입처럼 행동해야 한다.”

이게 Java가 record를 만든 이유다.

---

### 4. **상속이 봉인됨**

record는 암묵적으로 `final`.

> 데이터 모델을 **행동 확장의 수단으로 쓰지 말라**는 의도다.

---

### 5. **패턴 매칭과의 결합**

```java
if (cmd instanceof Create(var t, var c)) { ... }
```

record는 구조 분해가 된다.
이건 class로 흉내만 낼 수 있을 뿐 **동등하지 않다.**

---

### 6. **JVM 최적화 대상**

record는 JVM에게 이렇게 알려준다:

> “이 객체는 데이터 묶음이다.
> 동작이나 복잡한 정체성 관리 필요 없다.”

그래서 escape analysis, scalar replacement 같은 최적화에서
**record는 특별 대우**를 받는다.

---

## 🧪 생성자 검증이 왜 핵심인가

record는 **생성 시점이 곧 유일한 상태 변경 시점**이다.

그래서:

```java
public Create {
    if (title == null || title.isBlank())
        throw ...
}
```

이 한 줄이 시스템 전체의 안정성을 바꾼다.

이 이후부터:

> “잘못된 Create는 이 우주에 존재하지 않는다.”

class로는 이 계약을 강제할 방법이 없다.

---

## 🧭 한 문장 정리

> **class는 ‘객체’를 만들고
> record는 ‘값’을 만든다.**

record는 “표기법”이 아니라
**Java 타입 시스템의 새로운 계층**이다.

---

이제 지연이 작성하는 코드에서
“이건 class일까, record일까?” 라는 질문이 생기기 시작하면
이미 설계 레벨이 달라진 거다.

다음에 설명할 건
왜 record + sealed + pattern matching이 함께 쓰일 때
Java가 갑자기 언어처럼 보이기 시작하는지다.
