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
| `*.spec.ts` 코로케이션 | `src/test/java`에 별도 트리, 패키지 미러링 | classpath가 test→main 단방향. `testImplementation` 의존성은 main에서 import 자체가 불가능하고, 배포 jar에 test 클래스가 안 들어간다 | 0 |
| 상대 경로 import (`../payment/service`) | `package` 선언 + FQN import | 패키지명과 디렉터리 구조가 일치해야 컴파일된다. 접근 제한자를 안 쓰면 package-private(같은 패키지 한정)이라 패키지가 캡슐화 경계 역할을 한다 | 0 |
| `pnpm start` (tsx·swc 트랜스파일) | `./gradlew bootRun` (javac 컴파일) | 컴파일 생략 불가. JVM은 바이트코드만 실행한다. 타입 체크를 건너뛰는 선택지가 없다 | 0 |
| `package.json` + corepack | Gradle Wrapper (`./gradlew`) | 저장소가 빌드 도구 버전을 고정한다. 로컬에 Gradle 설치 불필요 | 0 |
| Prisma lazy connect (첫 쿼리 때 연결) | 부팅 시 커넥션 풀 초기화 | Spring은 fail-fast. DB에 못 붙거나 `@Value` 프로퍼티가 없으면 기동 자체가 실패한다. 그래서 `contextLoads()` 빈 테스트 하나가 설정·DI·매핑·DB 연결을 한 번에 검증한다 | 0 |
| Jest `globalSetup`에서 컨테이너 띄우고 env 덮어쓰기 | `@ServiceConnection` | 컨테이너의 랜덤 포트·계정을 `spring.datasource.*`로 자동 주입. 테스트용 접속 정보를 어디에도 안 적는다 | 0 |
| Prisma `include` / `select` 명시 로딩 | JPA lazy loading + fetch join | 기본값 `open-in-view=true`면 트랜잭션 밖에서도 lazy 쿼리가 나가 N+1이 숨는다. `false`로 두면 Prisma와 같은 명시 로딩 모델이 된다 → ADR 0001 | 0 |
