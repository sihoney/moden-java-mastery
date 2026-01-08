좋아.
지금부터는 **Week 2**로 넘어간다.
Week 1에서 “시스템의 뼈대와 시간 감각”을 심었고,
Week 2는 그 위에 **데이터 흐름과 관측 능력**을 붙이는 단계다.

---

# 🧭 Modern Java Mastery — **Week 2 계획**

### Theme: **Data Pipeline & Functional Thinking**

> **목표**
> 대량 데이터를 “안전하게, 빠르게, 읽기 쉬운 코드”로 처리하는 감각 만들기

---

## 🪵 Week 2 전체 흐름

| Lab    | 주제              | 배울 핵심                      |
| ------ | --------------- | -------------------------- |
| Lab 01 | Log Analyzer 설계 | Stream 사고 전환               |
| Lab 02 | 스트림 파이프라인       | map/filter/reduce          |
| Lab 03 | Collector 설계    | grouping, custom collector |
| Lab 04 | 불변 데이터 모델       | side-effect 제거             |
| Lab 05 | Backpressure 실험 | 대용량 파일 안정 처리               |

---

## 🧪 Lab 01 — Log Analyzer 시스템 설계

**프로젝트**: `simple/02-log-analyzer`

### 만들 것

```
logs/
  app.log
```

분석 명령:

```bash
analyze --level ERROR
analyze --top-ip 10
analyze --response-time avg
```

### 학습 포인트

* 파일 → 데이터 스트림
* 파싱 → 객체 스트림
* 필터 → 집계 → 결과

---

## 🧪 Lab 02 — Stream Pipeline

학습:

* map / filter / flatMap
* lazy evaluation
* short-circuit

실험:

* 1GB 로그 파일 처리
* 메모리 폭주 관찰

---

## 🧪 Lab 03 — Collector Mastery

구현:

* `groupingBy`
* `partitioningBy`
* custom collector 구현

---

## 🧪 Lab 04 — Immutability Discipline

리팩토링:

* 모든 중간 결과 불변화
* side effect 제거
* 함수형 스타일로 변환

---

## 🧪 Lab 05 — Backpressure 실험

실험:

* `Files.lines()` vs `BufferedReader`
* 스트림 처리 속도 제한
* CPU / 메모리 관찰

---

## 🧠 Week 2 종료 시 능력

지연은 이 단계 끝나면:

* 스트림 파이프라인을 설계할 수 있고
* 대용량 데이터 처리 시 메모리 사고를 예방하고
* 코드를 **“읽히는 함수형 파이프라인”**으로 작성한다.

---

다음 메시지에서
**Week 2 — Lab 01 설계부터 바로 시작**한다.
