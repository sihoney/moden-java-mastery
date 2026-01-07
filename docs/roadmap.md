# Modern Java Mastery Roadmap

현재 단계: Week 1 / Lab 01

완료:
- [x] CLI Tool Skeleton
- [ ] Config Loader
- [ ] Validation Layer

---

하루 1.5~2시간 기준.
주말은 복습 + 기록 + 리팩토링.

---

# 🧭 Modern Java Mastery — 16주 커리큘럼

> 목표:
> **현대 Java 설계 사고 + 동시성 내공 + 시스템 감각 완성**

---

## 🪵 Phase 1 — Language & Core Mastery (1–4주)

### Week 1 — Modern Java Foundations

**Projects**

* CLI Tool (Picocli)
* Config Loader

**Concepts**

* records, sealed classes
* immutability
* validation

**Deliverable**

* config parser with validation
* CLI command executor

---

### Week 2 — Streams & Data Pipelines

**Projects**

* Log Analyzer
* File Processor

**Concepts**

* Streams API
* collectors
* NIO
* backpressure awareness

**Deliverable**

* large log processor with streaming pipeline

---

### Week 3 — Concurrency Basics

**Projects**

* Thread Pool Wrapper
* In-memory Cache

**Concepts**

* Executors
* CompletableFuture
* ConcurrentHashMap
* TTL & schedulers

**Deliverable**

* cache with eviction & expiry

---

### Week 4 — Networking & HTTP

**Projects**

* HTTP Client
* Basic REST Service

**Concepts**

* async HttpClient
* timeouts
* controllers

**Deliverable**

* async client + REST endpoint

---

## ⚙️ Phase 2 — System Design & Async (5–9주)

### Week 5 — Clean Architecture

**Projects**

* Layered REST API

**Concepts**

* DTOs
* mappers
* records in API

---

### Week 6 — Async Systems

**Projects**

* Async Service

**Concepts**

* CompletableFuture
* structured concurrency

---

### Week 7 — Resource Control

**Projects**

* Rate Limiter

**Concepts**

* atomic primitives
* concurrency control

---

### Week 8 — Scheduling & Virtual Threads

**Projects**

* Scheduler

**Concepts**

* virtual threads
* cancellation
* retries

---

### Week 9 — Security Core

**Projects**

* Auth Service

**Concepts**

* JWT
* filters
* functional validation

> 🔁 **Midpoint Integration**
> Zero Market 일부 리팩토링 적용

---

## 🧬 Phase 3 — Distributed & Advanced (10–13주)

### Week 10 — Event Systems

**Projects**

* Kafka Event Pipeline

---

### Week 11 — Stream Processing

**Projects**

* Kafka Streams App

---

### Week 12 — High Throughput

**Projects**

* Virtual Thread Service

---

### Week 13 — Reactive Systems

**Projects**

* Reactive Service (Reactor)

---

## 🧪 Phase 4 — JVM & Platform Mastery (14–16주)

### Week 14 — JVM Performance Lab

* GC tuning
* JFR
* async profiling

---

### Week 15 — Distributed Coordination

* Saga Orchestration
* State machines

---

### Week 16 — Final Platform Build

**Project**

* Production-grade Platform

    * API
    * async jobs
    * caching
    * observability
    * fault tolerance

---

## 🧠 학습 규칙 (이게 핵심)

* 매 프로젝트마다 `notes/` 작성
* 리팩토링 전/후 비교 기록
* 장애 시뮬레이션 필수
* 설계 이유 문서화

---

## 🎯 완주 후 상태

* 동시성 설계 가능
* 비동기 시스템 설계 가능
* JVM 성능 분석 가능
* 대규모 시스템 구조 판단 가능
* **“Java로 시스템을 만든다”는 감각 보유**
