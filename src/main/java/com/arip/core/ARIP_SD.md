# 🚀 ARIP (Adaptive Risk Intelligence Platform)

## 📌 Overview

ARIP is a real-time fraud detection microservice that evaluates payment risk before authorization and improves decisions using asynchronous learning.

---

## 🎯 Goals

* Real-time fraud decisioning (100–300ms)
* Event-driven learning system
* Scalable & fault-tolerant architecture
* Production-grade observability

---

## 🧱 Architecture

### ✅ ARIP is a separate microservice

**Why:**

* Independent deployment
* Independent scaling
* Fault isolation
* Clean architecture boundaries

---

## 🔁 Core Flows

### 🔴 1. Sync Flow (Real-Time Decision)

**Use case:** Payment authorization

```
PSP → /risk/evaluate → ARIP → Decision → PSP
```

**Goal:** Return decision within 100–300ms

---

### 🔵 2. Async Flow (Learning Pipeline)

**Use case:** Post-transaction processing

```
Payment Service → Kafka → ARIP Consumer → DB/Redis
```

**Goal:** Improve future decisions

---

## 📡 API CONTRACT

### ✅ Request: `/risk/evaluate`

```json
{
  "transactionId": "...",
  "userId": "...",
  "amount": 1000,
  "currency": "INR",
  "paymentMethod": "UPI",
  "merchantCategory": "5411",
  "timestamp": "...",
  "geoMetadata": "...",
  "deviceFingerprintHash": "...",
  "deviceTrustFlags": "...",
  "channel": "MOBILE"
}
```

### ❌ Not Included:

* No PII
* No external risk score

---

### ✅ Response

```json
{
  "decision": "ALLOW | BLOCK | REVIEW",
  "actionRequired": "NONE | OTP_REQUIRED | STEP_UP_AUTH",
  "reasonCodes": ["HIGH_VELOCITY", "DEVICE_MISMATCH"]
}
```

### ❌ Not Returned:

* ❌ riskScore (internal only)

---

## 🧠 Internal Processing Flow

### 1️⃣ Validation Layer

* Request validation
* Idempotency check
* Auth verification

---

### 2️⃣ Context Aggregation

* Redis → fast counters (future)
* DB → user profile
* Cache → recent behavior

---

### 3️⃣ Risk Evaluation Engine

* Velocity rules
* Amount anomaly
* Device mismatch

**Output:**

* riskScore (internal)
* decision
* reasonCodes

---

### 4️⃣ State Update

#### 🔴 Sync (Critical)

* Update counters
* Update timestamps

#### 🔵 Async (Non-Critical)

* Kafka publish
* Analytics write
* ML data

---

### 5️⃣ Response Mapping

* Return decision to PSP

---

## ⚡ Risk Score

* Range: 0–100
* Used internally only

**Used for:**

* Decision thresholds
* Monitoring
* ML models

---

## ⚠️ Failure & Degraded Mode

### 🧯 ARIP must ALWAYS return a decision

---

### Degraded Strategy

#### 🟡 Phase 1 (0–5 min)

* Slightly stricter rules

#### 🟠 Phase 2 (5–20 min)

* Step-up auth increases

#### 🔴 Phase 3 (20+ min)

* Only low-risk allowed

---

### ❌ Never:

* Return "service unavailable"
* Block all traffic blindly

---

## ⏱ Circuit Breakers

* Stop calling failing dependencies
* Switch to fallback immediately
* Prevent cascading failures

---

## 📈 Observability (Implemented ✅)

### Stack:

```
Spring Boot → Micrometer → Prometheus → Grafana
```

### Metrics:

* HTTP request count
* Custom user metrics
* DB connection metrics
* JVM metrics

---

## 🐳 Docker Setup (Implemented ✅)

**Running services:**

* PostgreSQL (container)
* Prometheus
* Grafana

---

## 📊 Current Capabilities

✅ REST APIs (User Service)
✅ PostgreSQL integration (Docker)
✅ Prometheus metrics endpoint
✅ Grafana dashboards
✅ Custom metrics (user counters)

---

## ❗ Known Learnings

* Docker cache can cause stale builds
* Micrometer metric names must be unique
* DB downtime causes connection delays (fixed via timeout tuning)
* Observability is critical from day 1

---

## 🔜 Roadmap

### 🔹 Phase 1 (Next)

* Improve custom metrics

    * decision counters
    * endpoint hits
    * latency

---

### 🔹 Phase 2

* Redis integration

    * user velocity tracking
    * real-time counters

---

### 🔹 Phase 3

* Kafka integration

    * publish events
    * consume transaction events

---

### 🔹 Phase 4

* Event-driven architecture
* Async learning pipeline

---

### 🔹 Phase 5 (Advanced)

* ML / anomaly detection
* Dynamic risk scoring

---

## 🧠 Key Principles

* Microservices over monolith
* Sync vs Async separation
* Event-driven systems
* Internal vs external contracts
* Graceful degradation
* Observability-first design

---

## ⚔️ Final Mental Model

ARIP is:

👉 A real-time decision engine (sync)
➕
👉 A learning system (async)

---

## 📌 Resume Point

Continue from:

👉 Custom metrics refinement → Redis integration → Kafka integration
