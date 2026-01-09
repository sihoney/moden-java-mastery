좋다.
지금 지연은 **커리큘럼을 “소비”하는 게 아니라 “설계”하고 있다.**
이 단계부터 진짜 실력이 붙는다.

Week 3는 Week 1, 2 위에 얹히는 **네트워크 + 비동기 시스템 감각**이다.

---

# 🧭 Modern Java Mastery — **Week 3 계획**

### Theme: **Async I/O & Networked Systems**

> 목표
> **동기 세계 → 비동기 세계로 사고 전환**

---

## 🪵 Week 3 전체 구조

| Lab    | 주제                        | 배우는 핵심               |
| ------ | ------------------------- | -------------------- |
| Lab 01 | Modern HTTP Client        | non-blocking I/O     |
| Lab 02 | Async Pipelines           | CompletableFuture 설계 |
| Lab 03 | Timeout & Retry           | 안정성 패턴               |
| Lab 04 | Backpressure over Network | slow client 제어       |
| Lab 05 | Mini API Service          | HTTP 서버 실습           |

---

## 🧪 Lab 01 — Modern HTTP Client

**프로젝트:** `simple/03-http-client`

구현:

* Java `HttpClient`
* async requests
* connection pooling

---

## 🧪 Lab 02 — Async Pipelines

구현:

* CompletableFuture 체인
* 병렬 요청
* 결과 결합

---

## 🧪 Lab 03 — Timeout & Retry

구현:

* timeout 전략
* 재시도 정책
* exponential backoff

---

## 🧪 Lab 04 — Backpressure over Network

실험:

* 느린 서버
* 빠른 클라이언트
* 큐 제어, rate limit

---

## 🧪 Lab 05 — Mini API Service

**프로젝트:** `simple/04-rest-service`

구현:

* Javalin or Spring Boot
* REST endpoint
* async handler

---

## 🧠 Week 3 종료 상태

* HTTP 시스템이 머릿속에서 흐름으로 보이고
* blocking vs non-blocking을 구분하며 설계하고
* 서버의 병목을 예측할 수 있다.

---

Week 4부터는
**캐시, 스케줄링, 리소스 제어**로 들어간다.
