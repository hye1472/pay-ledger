# pay-ledger

결제 완료 이벤트 → 누적 결제액 집계 → 임계 도달 → 선착순 쿠폰 드롭 → 쿠폰 적용 결제 → 정산 배치·대사.
쿠폰 드롭부터 정산 원장까지 한 흐름으로 만드는 Java/Spring 학습 프로젝트입니다.

목적은 금융 도메인 설계와 Java 습득입니다. 빠른 완성이 목적이 아니며, 단계마다 실측 수치와 결정 기록(ADR)을 남깁니다.

## 모티브

무신사 "무진장" 카운트업 이벤트. 누적 판매액이 일정 단위에 도달할 때마다 선착순 N명에게 쿠폰을 지급하고(1인 1회), 누적액을 실시간으로 노출합니다.

## 범위

**In**
- 결제 승인·취소·부분 환불 API, 멱등성 키, 상태 머신
- 토스페이먼츠 테스트 키 연동. 웹훅 중복·지연·역순 처리, 타임아웃 후 상태 조회 복구
- Transactional Outbox → Kafka
- 누적 결제액 집계, 임계 도달 감지, 선착순 쿠폰 드롭(초과 발급 0, 1인 1회)
- Spring Batch 일 정산(정산금 = 결제액 − 수수료 − 쿠폰 부담), 스냅샷 고정, 재실행 멱등
- 결제 원장 vs PG 거래 원장 대사, 불일치 리포트
- Prometheus + Grafana, k6 부하 테스트, 장애 주입 실험

**Out (의도적으로 제외)**
- 실제 결제·송금·지급. "지급 지시 파일 생성"에서 멈춤
- 세금계산서, 회계 전표, 부가세
- 회원·상품 관리 UI
- 실제 트래픽 규모 재현. 목표는 단계별 비교 수치

## 기술 스택

Java 21 · Spring Boot 3 · Spring Data JPA · Spring Batch · Spring Kafka · MySQL 8 · Redis 7 · Kafka · JUnit5 + Testcontainers · k6 · Docker Compose · Kubernetes · Prometheus/Grafana

## 모듈 (예정)

| 모듈 | 역할 |
|---|---|
| `common-contracts` | 이벤트 스키마, 공용 타입(금액·통화) |
| `payment-service` | 결제 상태 머신, PG 연동, outbox |
| `coupon-service` | 누적 집계, 임계 감지, 선착순 드롭, 쿠폰 원장 |
| `settlement-batch` | 일 정산 배치, 대사, 지급 지시 파일 |

## 단계별 계획 (주 10시간 기준, 총 12~14주)

| Phase | 기간 | 완료 기준 |
|---|---|---|
| 0. 세팅 · Java 워밍업 | 1~2주 | 빈 서비스가 Testcontainers 테스트를 통과 |
| 1. 카운트업 · 쿠폰 드롭 | 3~4주 | 2,000 VU 부하에서 발급 = N장 정확, 초과 발급 0, 단계별 p99 표 |
| 2. 결제 상태 머신 · 웹훅 | 3주 | 웹훅을 드롭·중복·역순 전달해도 최종 상태가 PG와 일치 |
| 3. 정산 배치 · 대사 | 3주 | 배치 재실행 멱등. 떨어뜨린 웹훅 1건을 대사가 MISSING_IN_LEDGER로 검출 |
| 4. 관측 · 배포 · 문서 | 2주 | README에서 설계·ADR·벤치·대시보드를 한 번에 확인 |

완료 기준 미달이면 다음 Phase로 넘어가지 않습니다. Phase마다 AI 없이 핵심 기능을 다시 쓰는 재현 시험을 통과해야 끝납니다.

## 문서

- [docs/adr](docs/adr) — 설계 결정 기록. AI 제안을 기각한 경우도 사유와 함께 남깁니다.
- [docs/nest-to-spring.md](docs/nest-to-spring.md) — Nest → Spring 대응표. 기능마다 누적합니다.
- [CLAUDE.md](CLAUDE.md) — AI 협업 규칙.

## AI 활용 명시

Java 관용구·보일러플레이트·테스트 스캐폴딩은 AI 도구로 생성했습니다. 설계, 벤치 설계, 실측, 결정은 직접 했고 근거는 ADR에 있습니다. 규칙은 [CLAUDE.md](CLAUDE.md)를 따릅니다.
