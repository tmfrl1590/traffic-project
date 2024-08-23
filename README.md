# 광주 버스

---
### 소개
버스를 타고 출,퇴근을 하면서 사용하기 위해 만들어 본 광주 버스 앱

---
### 기술스택
언어 : kotlin 2.0

안드로이드 : Jetpack Compose, Hilt, Retrofit2, Room, DataStore, Navigation

멀티모듈 : app, presentation, domain, data, di, navigation, common

---
### 기능
- 버스 도착정보 조회
- 버스 노선정보 조회
- 버스 정류장 정보 조회
- 정류장 즐겨찾기 등록/삭제

---
### 설명
###### SplashScreen

- 앱 실행이 처음이라면 presentation 모듈의 assets 에 있는 station.json, line.json 파일을 읽어와서 Room 데이터베이스에 저장한다.
- 이후에는 홈화면 이동한다.

###### HomeScreen

- 즐겨찾기(Room) 등록한 정류장 정보 리스트를 보여준다.
- 즐겨찾기를 취소할 수 있다.

###### StationScreen

- 검색어를 통해서 Room 데이터베이스에 저장되있는 정류장 리스트를 보여준다.
- 정류장을 클릭하면 해당 정류장의 버스 도착정보 화면으로 이동한다.

###### BusArriveScreen

- API 통신을 이용해서 해당 정류장의 버스 도착정보를 보여준다.
---
### 스토어 정보
스토어 링크 : https://play.google.com/store/apps/details?id=com.system.traffic

---
### 참고 공공데이터 API
버스 도착정보 : https://www.data.go.kr/data/15001106/openapi.do

노선-정류소 정보 : https://www.data.go.kr/data/15001056/openapi.do


