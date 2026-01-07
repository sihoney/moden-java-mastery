# 왜 명령을 타입으로 만들어?

> “명령을 타입으로 만든다는 건,
시스템의 가능한 행동을 언어 수준에서 고정시키는 것이다.”

---

우리가 보통 이렇게 코드를 짠다:
```
service.create(title, content);
service.delete(id);
service.list();
```

이 방식의 진짜 정체는 이거다:
> “아무 데서나 아무 행동이나 부를 수 있는 세계”

컴파일러는 전혀 모른다.
- 지금 이 호출이 합법적인지
- 이 시스템에 이런 행동이 존재하는지
- 이 행동들이 서로 어떤 관계인지
모든 규칙이 개발자 머릿속에만 있다.

---

이제 이 방식으로 바꾼다:
```
service.handle(new Create("Hello", "World"));
service.handle(new Delete(1));
```

여기서 시스템은 이렇게 변한다:
> “시스템에 제출할 수 있는 행동의 종류가 타입으로 고정됨”

```
sealed interface Command permits Create, Delete, ListAll
```

이 한 줄로,
시스템의 행동 우주가 닫힌다.

---

이게 왜 중요한가?

## 1. 시스템의 가능한 행동을 “지도”로 만든다

Command 타입이 곧 시스템 사용 설명서다.

```
permits Create, Delete, ListAll
```

이 줄만 봐도
이 시스템이 뭘 할 수 있는지 한눈에 보인다.

문서보다 정확하고, 절대 틀리지 않는다.

---

## 2. 불법 상태가 아예 표현되지 않는다

```
new Create("", null);   // 객체 생성 단계에서 검증 -> 실패하면 객체 자체가 존재하지 않음
```

이제 시스템에는
“잘못된 Create 명령”이라는 개념이 존재하지 않는다.

> 유효하지 않은 상태는 코드로 표현조차 할 수 없다

이건 타입 시스템의 최고급 사용법이다.

---

## 3. 컴파일러가 설계 경찰이 된다

```
switch(cmd) {
    case Create ...
    case Delete ...
}
```

나중에 Update를 추가하면?

```
public sealed interface Command permits Create, Delete, ListAll, Update {}
```

컴파일러가 즉시 말한다:
> “야, 모든 switch 문 고쳐.”

설계 누락이 런타임이 아니라 컴파일 타임에 터진다.

### sealed + switch

Java 17+의 패턴 매칭 switch는
sealed 타입을 만나면 이렇게 생각한다:
> “Command의 모든 가능한 하위 타입을 내가 안다.”

지금 permits에 있는 타입은:
- Create
- Delete
- ListAll
- Update

그런데 switch는 Update를 처리하지 않았다.
그래서 컴파일러가 바로 말한다:
> ❌ 컴파일 오류: switch is not exhaustive

즉,
> "Update 케이스 처리 안 했어. 고쳐."

---

## 4. 시스템이 스스로 문서가 된다

메서드 호출 방식은 흩어진다.
Command 타입은 한 군데 모인다.

그래서 이 구조를 쓰면
대형 시스템이 갑자기 읽히기 시작한다.

---

## 5. 이게 왜 ‘현대 Java’인가

Java 17+의 핵심 도구:
- sealed
- record
- pattern matching
- switch exhaustiveness
이 모든 게 바로 **이 패턴**을 위해 존재한다.

---

## 느낀점

sealed + record 사용 소감

validation 위치가 왜 생성자에 있는지

switch 패턴 매칭 느낌