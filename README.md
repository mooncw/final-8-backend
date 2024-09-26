# BE-final
광고 심의를 위한 인공지능 웹페이지 솔루션 -> [웹사이트](https://ai-kars.netlify.app)

<br>

팀 프로젝트를 진행한 레포지토리 -> [BE-final Repository](https://github.com/final-team8/BE-final)
<br>

## 프로젝트 개요
뉴로플로우의 AI 서비스를 활용하여 한국광고자율심의기구의 심의 사업에 더 나은 솔루션을 제공하고자 함
<br>
특히, 수작업에 기반한 광고 심의로 소요 시간은 늘어나고 업무 효율이 낮아지는 문제를 해결하고자 함
<br>

## 프로젝트 소개
| 회원가입 | 로그인 |
| --- | --- |
| ![signup](https://github.com/user-attachments/assets/4a3a0ea8-1e19-41a9-9960-0d9621772e96) | ![login](https://github.com/user-attachments/assets/527f1335-39af-423b-a0d1-83f04ed6c20f) |
| 조건에 맞게 입력하고 연락처 인증과 아이디 중복 확인이 되면 회원가입할 수 있습니다. | 회원가입 요청이 승인된 계정으로 로그인할 수 있습니다. |

| 대시보드 | 동일 광고 |
| --- | --- |
| ![대시보드_gif](https://github.com/user-attachments/assets/eed4db68-e494-422e-afc1-c32a5a9fa872) | ![동일광고_gif](https://github.com/user-attachments/assets/00fee7cf-51c2-403b-8346-cf0e98f8e2f4) |
| 본인 또는 전체 작업 상태를 한 눈에 볼 수 있습니다. | 광고 목록을 볼 수 있고, 선택한 광고가 이전 광고들과 동일한 문장이 있는지 볼 수 있습니다. |

| 지적 광고 | 나의 작업 |
| --- | --- |
| ![지적광고_gif](https://github.com/user-attachments/assets/b0435548-e4a9-41e2-b2b2-eebc624c1d7a) | ![나의작업_gif](https://github.com/user-attachments/assets/658f6e36-c354-4361-973c-ab4b46239a5f) |
| 광고 목록을 볼 수 있고, 선택한 광고에서 지적 내용을 볼 수 있습니다. | 나에게 배분된 광고에 대한 검수를 할 수 있습니다. |

| 마이페이지 | 관리자 전용 |
| --- | --- |
| ![마이페이지_gif](https://github.com/user-attachments/assets/f4561f30-0add-4093-a94b-fca8b1eb66a4) | ![관리자 전용_gif](https://github.com/user-attachments/assets/19199538-7ca4-45ea-b400-50f6058111e4) |
| 연락처, 이메일, 비밀번호를 변경할 수 있습니다. | 관리자는 가입 요청, 회원 정보에 대해 관리할 수 있고 작업자들에 대한 작업을 볼 수 있고, 미배분된 광고를 선택한 작업자들에게 배분할 수 있습니다. |
<br>

## 기술스택
#### 언어
<div>
  <img src="https://img.shields.io/badge/openjdk-437291?style=flat&logo=openjdk&logoColor=white"/>
</div>
<br>

#### 웹 프레임워크
<div>
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat&logo=springboot&logoColor=white"/>
</div>
<br>

#### 데이터
<div>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=flat&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/redis-DC382D?style=flat&logo=redis&logoColor=white"/>
  <img src="https://img.shields.io/badge/flyway-CC0200?style=flat&logo=flyway&logoColor=white"/>
</div>
<br>

#### 클라우드
<div>
  <img src="https://img.shields.io/badge/amazonec2-FF9900?style=flat&logo=amazonec2&logoColor=white"/>
  <img src="https://img.shields.io/badge/docker-2496ED?style=flat&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/nginx-009639?style=flat&logo=nginx&logoColor=white"/>
</div>
<br>

#### CI/CD
<div>
  <img src="https://img.shields.io/badge/githubactions-2088FF?style=flat&logo=githubactions&logoColor=white"/>
</div>
<br>

#### 모니터링
<div>
  <img src="https://img.shields.io/badge/prometheus-E6522C?style=flat&logo=prometheus&logoColor=white"/>
  <img src="https://img.shields.io/badge/grafana-F46800?style=flat&logo=grafana&logoColor=white"/>
</div>
<br>

## 인프라
![인프라](https://github.com/user-attachments/assets/43122621-08dd-413a-b1c2-875c0014bd39)
<br>

## 성능 개선
### 1. 쿼리 최적화를 통한 작업 배분 실행 속도 개선
**기존 로직:** JPA로 조건에 맞는 모든 엔티티를 불러와 1개씩 더티체킹으로 Update
<br>
**개선 로직:** QueryDsl로 조건에 맞는 엔티티의 id(PK)만을 select한 후, 해당 id들에 대해 한번에 Update
<br>
**상황:** 광고 데이터 6만개를 6명의 작업자에게 1만개씩 배분
<br>
**측정 방식:** RestTemplate를 이용한 컨트롤러를 호출하는 test 코드를 짜서 측정
<br>
![쿼리최적화 성능 비교표](https://github.com/user-attachments/assets/ac2821e6-7494-4fb2-8d75-f8544e159e83)


## 함께한 동료들
| **PM 팀** | **UX/UI 팀** | **FE 팀** | **BE 팀** |
| --- | --- | --- | --- |
| 👑 **장민우**<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;김민정<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;최하영 | 👑 **박현아**<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;권예진<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;하예진 | 👑 **김여진**<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;박성현<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;송재원 | 👑 **문찬욱**<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;김강민<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;박현준 |
