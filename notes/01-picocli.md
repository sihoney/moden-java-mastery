좋은 지점 짚었다.
Picocli는 그냥 “옵션 파서”가 아니라,
**CLI 애플리케이션을 하나의 작은 프레임워크처럼 만들어주는 도구**야.

정체부터 말해보자.

> **Picocli = 자바용 CLI 프레임워크**

Spring이 서버를 구조화하듯,
Picocli는 **커맨드라인 프로그램을 구조화**한다.

---

## 🧱 Picocli가 제공하는 핵심 기능들

### 1️⃣ 명령 구조를 타입으로 만든다

이 코드 하나로:

```java
@Command(name = "create")
class CreateCmd implements Runnable { ... }
```

자동으로 이런 CLI가 생긴다:

```
lab create --title "Hello" --content "World"
```

문자열 파싱 ❌
switch-case ❌
옵션 직접 처리 ❌

→ **모두 Picocli가 처리**

---

### 2️⃣ 옵션 & 인자 자동 파싱

```java
@Option(names = "--title", required = true)
String title;
```

이건 단순 필드가 아니라:

* 자동 파싱
* 자동 검증
* 자동 에러 메시지 생성
* 자동 도움말 출력

전부 해준다.

---

### 3️⃣ 도움말 & 사용법 자동 생성

아무 것도 안 써도:

```
$ lab --help
$ lab create --help
```

이게 자동으로 나온다.

그리고 메시지 퀄리티가 굉장히 높다.

---

### 4️⃣ 서브커맨드 트리 구성

```java
@Command(subcommands = {CreateCmd.class, DeleteCmd.class})
```

→ CLI가 이렇게 된다:

```
lab
 ├─ create
 ├─ delete
 └─ list
```

**계층형 명령 구조**가 생긴다.

---

### 5️⃣ 실행 모델 분리

Picocli는 **파싱과 실행을 분리**한다.

1. 사용자 입력 파싱
2. 객체 필드에 값 주입
3. `run()` 호출

그래서 우리가 만든 도메인 모델과
CLI 계층이 완전히 분리된다.

---

### 6️⃣ 타입 안정성 보장

CLI 입력이 이 순간부터 **문자열이 아니다.**

`String[] args` →
**`CreateCmd`, `DeleteCmd` 객체**로 바뀐다.

이게 엄청 중요하다.

---

### 7️⃣ 에러 메시지 품질

Picocli가 만들어주는 에러 메시지는
직접 만들면 지옥이다.

```
Missing required option: '--title'
Usage: lab create --title=<title> --content=<content>
```

이걸 공짜로 준다.

---

## 🧠 왜 이게 중요한가

이게 단순 CLI가 아니라 **설계 훈련**인 이유:

| 이전 세계        | Picocli 세계 |
| ------------ | ---------- |
| 문자열 기반 처리    | 타입 기반 처리   |
| if/switch 지옥 | 도메인 모델     |
| 파싱 + 비즈니스 섞임 | 계층 분리      |
| 에러 메시지 직접    | 프레임워크 제공   |

즉:

> **입력 → 타입 → 도메인 → 로직**

이 사고방식이 그대로 서버 아키텍처로 옮겨진다.

---

## 🧬 Picocli를 배우는 진짜 이유

CLI는 장난감처럼 보이지만,
**모든 서버 시스템의 축소판**이다.

* 입력 검증
* 명령 모델링
* 계층 분리
* 에러 처리
* 실행 흐름 제어

Picocli는 이걸 가장 작고 선명하게 보여준다.

---

다음 실험 단계에서
이 CLI를 **멀티스레드 공격**에 던져 넣는다.
그리고 구조가 왜 이렇게 되어야 하는지 몸으로 이해하게 된다.

그때 Picocli의 진짜 가치가 보인다.
