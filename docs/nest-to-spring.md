# Nest → Spring 대응표

기능을 만들 때마다 한 줄씩 누적한다. "개념은 이미 아는 것, 모르는 건 어휘"라는 전제로 쓴다.

| Nest / TypeScript | Spring / Java | 차이·주의 | 추가 Phase |
|---|---|---|---|
| `@Module()` | 패키지 + `@Configuration` | | 0 |
| `@Injectable()` Provider | `@Service` / `@Component` Bean | | 0 |
| Guard / Interceptor | Filter / `HandlerInterceptor` / Spring Security | | |
| Prisma `$transaction` | `@Transactional` (전파 옵션) | | |
| `@Cron()` | `@Scheduled` / Spring Batch | | |
| Jest + Docker Compose | JUnit5 + Testcontainers | | 0 |
