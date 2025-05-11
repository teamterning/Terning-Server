# 💚 Team-Terning (터닝) 
> NOW SOPT 34 APPJAM DEMODAY 최우수상 수상작 

![image](https://github.com/user-attachments/assets/b5ba8653-c30b-4ad7-afa3-5083dc001581) 

- Behance URL : https://www.behance.net/gallery/209714965/terning-College-Internship-App
- 앱 소개 URL : https://terning.framer.website/

### 내 계획에 딱 맞는 대학생 인턴의 시작, 터닝 (terning) 
> 인턴은 취업 전 전공 관련 실무 경험을 쌓을 수 있을 뿐만 아니라 취업 시장에서 중요한 평가 요소로 자리하고 있습니다. <br/> 하지만 채용 플랫폼에는 졸업자와 경력자를 위한 공고만 가득할 뿐, 대학생을 대상으로 한 인턴 공고만을 모아 탐색하기에는 어렵고 복잡하기만 합니다. <br/> <br/> 터닝에서는 **대학생이 세운 계획에 딱 맞는 공고를 모아볼 수 있고, 인턴 지원 일정까지 효율적으로 관리할 수 있는 서비스**를 제공합니다. <br/> 나만의 인턴 계획이 있는 대학생이라면, 지금 바로 터닝으로 대학생 인턴을 시작해보세요.

<br/>

## 🪴 터닝의 핵심기능을 소개합니다

**Solution 01. 오직 대학생 인턴만 모아볼 수 있도록**
- 간편한 온보딩 : 세가지 질문에 대한 대답만으로 간단하게 내 인턴 계획을 세울 수 있어요
- 맞춤형 공고 확인 : 홈 화면에서 내 계획에 맞게 필터링된 공고만 쉽고 빠르게 확인할 수 있어요
- 마감 공고 리마인드 : 더 이상 지원 마감을 놓치지 않도록 일주일 내 마감되는 관심 공고를 알려드릴게요

**Solution 02. 캘린더에서 효율적으로 인턴 지원 일정을 관리할 수 있도록**
- 다양한 스크랩 색상 : 나만의 기준에 따라 공고를 스크랩하고 여러가지 색상으로 캘린더를 채워보세요
- 날짜별 리스트 제공 : 캘린더에 스크랩한 공고를 리스트 형식으로도 볼 수 있어요
- 공고 상세 페이지 : 캘린더에서도 깔끔하게 요약된 스크랩 공고 정보를 바로 확인할 수 있어요

<br/>

## 🪴 왜 터닝과 함께 인턴을 준비해야 할까요?

- 인턴에 대해 잘 모를 수 있는 대학생들을 위해 친절하고 따뜻한 문구로 상세하게 안내해줘요.
- 타 채용 플랫폼과는 달리 오로지 ‘대학생 인턴’만을 위한 공고를 선별해서 불러와요.
- 오로지 대학생만을 공략하여 세세하고 전문적인 정보를 제공해요.

<br/>



##  Back-end (Spring 🌱)

| <img src="https://github.com/user-attachments/assets/8e71a4f2-70e7-4a80-b658-de0eea90b0e0" width=300px alt="서버/정정교"/>  | <img src="https://github.com/user-attachments/assets/7cdc140f-5281-45b5-88ac-a952cc327247" width=300px alt="서버/신정윤"/>  | <img src="https://github.com/user-attachments/assets/54c89379-08b8-4737-8da0-5bfad27e49fe" width=300px alt="서버/권장순"/>
| :-----: | :-----: | :-----: |
| [정정교/Junggyo1020](https://github.com/junggyo1020) | [신정윤/JungYoonShin](https://github.com/JungYoonShin) | [권장순/jsoonworld](https://github.com/jsoonworld) |

<br/>

## 🪴 역할
| 분야 | 이름 | 포지션 |
| --- | --- | --- |
| 서버 개발 | [정정교/Junggyo1020](https://github.com/junggyo1020) | 서버 파트 리더, 팀 매니징 및 개발 인프라, 캘린더, 인턴 공고, 프로필 |
| 서버 개발 | [신정윤/JungYoonShin](https://github.com/JungYoonShin) | 스크랩, 공고 상세, 탐색, 필터링 |
| 서버 개발 | [권장순/jsoonworld](https://github.com/jsoonworld) | 회원가입, 로그인, 회원관리 |

<br/>

## 💡 Git Convention
> **Git Flow**
> 
- `main` : 배포 프랜치
- `develop` : 개발 브랜치
- `type/#이슈번호` : 세부 개발 브랜치
    - 생성한 이슈번호로 브랜치를 생성한다.
    - ex) feat/#20
- PR 머지 후 해당 브랜치는 삭제한다.
<br/>

## 📌 Commit Convention
### commit 메세지
```text
[type/#이슈번호]: 작업내용

ex) [feat/#20]: 검색 결과 필터링 기능 추가
```
<br/>

### branch 이름
```text
type/#이슈번호

ex) feat/#20
```
<br/>

### branch 종류
| Type | 의미 |
| --- | --- |
| ✨feat | 새로운 기능 추가 |
| 🔨fix | 버그, 오류 수정 |
| ✅chore | 동작에 영향 없는 코드 or 변경 없는 변경사항(주석 추가 등)  ex) .gitIgnore |
| 📝docs | README나 WIKI 등의 문서 수정 |
| ☁️db | DATABASE 및 더미데이터 수정 |
| ♻️refactor | 코드 리팩토링 ex) 형식변경 |
| ⚰️del | 쓸모없는 코드 삭제 |
| ✏️rename | 파일 이름 변경시 |
| 🔀merge | 다른 브랜치와 병합 |
| 💡test | 테스트 코드, 리팩토링 테스트 코드 추가  |
| ✒️comment | 필요한 주석 추가 및 변경 |
| 📂file | 파일 또는 폴더명 수정, 이동, 삭제 등의 작업만 수행한 경우 |
| 🔥!hotfix | 급하게 치명적인 버그를 고쳐야 하는 경우 |

<br/>

## 🌱 Architecture Diagram
<img width="750" alt="image" src="https://github.com/user-attachments/assets/0bf49d8a-bcfa-4810-87ca-734e2b3a2d71">

<br/>

## 🪴 IA
<img width="1024" alt="image" src="https://github.com/user-attachments/assets/0dac17fd-5950-4f47-95d1-d2aa261bdfa5">

<br/><br/>

## 📎 ERD
![image](https://github.com/user-attachments/assets/d4ef76d7-d1fb-41e2-9a53-10012fe797ea)


<br/><br/>

## [🍏 API 명세서](https://abundant-quiver-13f.notion.site/9b5cd91c634b4a2580ea8222736fdcab?v=e8692cde216c47559d714abc4231d9a7)
> 클릭 시 API 명세서를 확인하실 수 있습니다.

<br/><br/>

## 📂 Structure

```text
- 📁 common
    - 🛡️ security             // 보안 관련 설정 및 로직
        - 🔐 jwt             // JWT 기반 인증/인가 처리 모듈
    - ❗ exception            // 전역 공통 예외 처리 및 예외 클래스 정의
    - 🧰 util                 // 프로젝트 전반에서 사용되는 유틸리티 함수 및 클래스 모음
    - ⚙️ config               // Spring 설정 클래스 및 환경 설정 파일 관리
    - 📝 logging              // 요청/응답, 예외 등의 로깅 처리를 위한 설정 및 도구

- 🔐 auth                    // 로그인, 회원가입, 인증 관련 로직을 담당
- 🖼️ banner                  // 배너 및 홈 상단에 표시되는 홍보 영역을 담당
- 📆 calendar                // 유저가 스크랩한 일정(공고)을 캘린더 형태로 관리
- 🧾 filter                  // 공고 필터링 기능 및 필터 조건 관리
- 🏠 home                    // 홈 화면에서 보여지는 주요 콘텐츠 로직을 담당
- 💼 internshipAnnouncement  // 인턴십 공고 등록, 조회, 관리 기능
- 📌 scrap                   // 유저가 공고를 스크랩하고 관리하는 기능
- 🔍 search                  // 공고 및 관련 항목에 대한 검색 기능

- 🌐 external                // 외부 서비스 연동을 담당
    - 📲 pushNotification    // FCM 기반 푸시 알림 전송 및 설정 로직
    - 🧵 discord             // Discord Webhook 연동을 위한 기능을 담당

- 👤 user                    // 유저 정보, 온보딩, 설정 등 사용자 도메인 전반을 관리
```
