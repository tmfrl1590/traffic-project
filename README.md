# 광주 버스

---
### 소개
버스를 타고 출,퇴근을 하면서 사용하기 위해 개발한 광주 버스 앱

---
### 기술스택
언어 : Kotlin 2.2.20

UI : Jetpack Compose, Navigation3, Compose Material3

아키텍처 : 멀티모듈, 단방향 상태 관리(State/Action), Repository 패턴

DI : Hilt

비동기 : Coroutines, Flow

네트워크 : Retrofit2, OkHttp, kotlinx.serialization

로컬 저장소 : Room, DataStore

지도 : 네이버 지도 SDK, naver-map-compose

코드 품질 : GitHub Actions CI, ktlint, Android Lint

기타 : Firebase(Analytics, Cloud Messaging - FID 기반 등록), AdMob, Core SplashScreen

---
### 모듈 구조

| 모듈 | 역할 |
|---|---|
| app | 앱 진입점(MainActivity, Application), Firebase 서비스, 권한 처리 |
| presentation | 화면(Compose)·ViewModel, 단방향 상태 관리 |
| navigation | Navigation3 기반 화면 전환 (NavDisplay 백스택 직접 관리), 하단 탭, 뒤로가기 처리 |
| domain | UseCase, Repository 인터페이스, 도메인 모델 |
| data | Repository 구현, DataSource 인터페이스, DTO 매핑 |
| remote | Retrofit 기반 API 통신, 네트워크 상태 감지 |
| local | Room DB, DataStore, assets 파일 읽기 |
| design | 공통 컴포넌트, 테마(색상/타이포그래피), 문자열 리소스 |
| core | 공통 enum, Result/Error 타입, 네트워크 유틸 |

---
### 기능
- 버스 도착정보 조회 (30초 자동 새로고침)
- 지도에서 실시간 버스 위치 마커 표시
- 노선별 경유 정류장 조회
- 정류장 검색 및 최근 검색어 관리
- 정류장 즐겨찾기 등록/삭제
- 자주 타는 버스 핀 고정 (도착 목록 상단 정렬)
- 앱 테마(라이트/다크/시스템)·글자 크기 설정
- FCM 푸시 알림
- 네트워크 오프라인 안내 배너

---
### CI / 코드 품질

`main`, `develop` 브랜치 푸시 및 모든 PR에서 GitHub Actions가 자동 실행된다. (`.github/workflows/ci.yml`)

검사 순서 : ktlint(미사용 import 검출) → 단위 테스트 → Android Lint → 디버그 빌드

- ktlint 룰은 루트 `.editorconfig`에서 관리한다. 현재는 `no-unused-imports`만 활성화 상태이며, 룰 추가는 `ktlint_standard_룰이름 = enabled` 한 줄로 가능하다.
- 각 모듈은 자신이 사용하는 권한을 모듈 manifest에 직접 선언한다. (lint MissingPermission 대응, 앱 빌드 시 자동 병합)
- CI 실행에는 저장소 Secret `LOCAL_PROPERTIES`(로컬 `local.properties` 내용)가 필요하다.

##### 로컬 검증 명령어

```bash
# 푸시 전 CI와 동일한 검사를 한 번에 (전 모듈 끝까지 검사)
./gradlew ktlintCheck lintDebug --continue

# 미사용 import 등 ktlint 위반 자동 수정
./gradlew ktlintFormat

# 단위 테스트 (Android 모듈 + core JVM 모듈)
./gradlew testDebugUnitTest :core:test

# 디버그 빌드
./gradlew assembleDebug
```

---
### 성능/메모리 최적화
- 개발 중 LeakCanary로 AdMob AdView 미해제로 인한 Activity 메모리 누수 검출, AndroidView onRelease에서 destroy 처리로 해결
- 초기 데이터(asset JSON 1.2MB) 파싱을 스트리밍 방식(decodeFromStream)으로 전환해 최초 실행 시 순간 메모리 피크 감소 및 스트림 close 보장
- 지도 마커 좌표 등 데이터에서 파생되는 상태의 불필요한 savedState(Bundle) 저장 제거
- Android Studio Profiler(힙 덤프, allocation tracking)로 수정 전후 검증

---
### 화면 설명
###### SplashScreen

- 앱 최초 실행 시 presentation 모듈 assets 에 있는 station.json, line.json 파일을 읽어와서 Room 데이터베이스에 저장한다.
- 초기화 진행률을 표시하고, 실패 시 재시도할 수 있다.
- 이후에는 초기화를 건너뛰고 홈화면으로 이동한다.

###### HomeScreen

- 즐겨찾기(Room) 등록한 정류장 정보 리스트를 보여준다.
- 즐겨찾기를 취소할 수 있다.

###### StationScreen

- 검색어를 통해서 Room 데이터베이스에 저장되어 있는 정류장 리스트를 보여준다.
- 최근 검색어를 저장하고 개별/전체 삭제할 수 있다.
- 정류장을 클릭하면 해당 정류장의 버스 도착정보 화면으로 이동한다.

###### BusArriveScreen

- API 통신을 이용해서 해당 정류장의 버스 도착정보를 보여주고, 30초마다 자동 새로고침한다.
- 네이버 지도에 정류장 위치와 접근 중인 버스 위치를 마커로 표시한다.
- 자주 타는 버스를 핀으로 고정해 목록 상단에 정렬할 수 있다.

###### LineStationScreen

- 선택한 노선이 경유하는 정류장 목록을 타임라인으로 보여준다.
- 상행/하행 방향을 전환할 수 있다.

###### SettingScreen

- 앱 테마(라이트/다크/시스템)와 글자 크기를 설정한다.
- 저장된 핀 목록 초기화, 문의(이메일), 오픈소스 라이선스, 앱 버전 정보를 제공한다.

---
### 스토어 정보
스토어 링크 : https://play.google.com/store/apps/details?id=com.system.traffic

---
### 참고 공공데이터 API
광주버스 : https://www.data.go.kr/data/15157923/openapi.do
