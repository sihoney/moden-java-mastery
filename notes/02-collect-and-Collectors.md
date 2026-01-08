좋다.
이건 **Stream API의 심장**이다.
`collect()`를 이해하면 스트림이 왜 강력한지 한 번에 연결된다.

---

# 🧠 `collect()`의 정체

한 문장으로 말하면:

> **스트림을 원하는 자료구조나 결과값으로 “수렴”시키는 연산**

스트림은 **흐름**이다.
`collect()`는 그 흐름을 **그릇에 담는 행위**다.

---

## 🧱 스트림 파이프라인 구조

```
source → intermediate ops → terminal op
```

`collect()`는 **terminal operation**이다.
여기서 스트림이 **실제로 실행**된다.

---

# 🧩 `Collector`는 무엇인가

`Collector`는:

> **스트림의 요소들을 어떻게 모을지 정의한 설계도**

이 설계도는 4가지 부품으로 이루어져 있다:

| 단계          | 역할             |
| ----------- | -------------- |
| supplier    | 결과 그릇 생성       |
| accumulator | 요소를 그릇에 넣는 법   |
| combiner    | 병렬 처리 시 결과 합치기 |
| finisher    | 최종 결과 변환       |

---

## 🧪 예제: `groupingBy`

```java
Map<String, Long> counts =
    logs.stream()
        .collect(Collectors.groupingBy(
            LogEntry::level,
            Collectors.counting()
        ));
```

이 코드가 하는 일:

1. Map 하나 생성 (supplier)
2. 로그 하나씩 읽으며 해당 key에 count 누적 (accumulator)
3. 병렬이면 부분 결과 병합 (combiner)
4. Map 반환 (finisher)

---

# 🧠 왜 이게 중요한가

명령형 코드로 쓰면:

```java
Map<String, Long> map = new HashMap<>();
for (LogEntry log : logs) {
    map.put(log.level(), map.getOrDefault(log.level(), 0L) + 1);
}
```

스트림은 이 과정을 **한 문장**으로 추상화한다.

---

# 🧬 우리가 만든 커스텀 Collector

우리가 만들었던 `ResponseStatsCollector`:

```java
public class ResponseStatsCollector
  implements Collector<LogEntry, long[], ResponseStats>
```

이건:

> “LogEntry들을 모아서 ResponseStats로 만들어줘”

라는 **수집 규칙 정의서**다.

---

# 🧩 `Collectors`는 무엇인가

`Collectors`는:

> **자주 쓰는 Collector 공장**

미리 만들어진 설계도 모음집이다.

* toList()
* toSet()
* groupingBy()
* counting()
* mapping()
* joining()
* partitioningBy()

---

# 🏁 한 문장 요약

> `collect()`는 **스트림을 값으로 바꾸는 순간**이고,
> `Collector`는 **그 값을 만드는 방법의 설계도**다.

이걸 이해하면 스트림은
그냥 문법이 아니라 **데이터 공장**이 된다.
