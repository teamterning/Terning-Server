# 💚 Team-Terning (터닝) 
> NOW SOPT 34 APPJAM DEMODAY 최우수상 수상작

![image](https://github.com/user-attachments/assets/b5ba8653-c30b-4ad7-afa3-5083dc001581) 


### 내 계획에 딱 맞는 대학생 인턴의 시작, 터닝 (terning) 

> 취업을 위한 필수 관문이자 대학생으로서 쌓을 수 있는 최고의 스펙, 인턴. <br/> 팀 터닝포인트는 대학생이 세운 계획에 딱 맞는 인턴 공고를 추천해주고, 인턴 지원 일정을 효율적으로 관리하는 방안에 대해 고민합니다. <br/> 터닝에서 나만의 인턴 계획을 등록하고, 딱 맞는 인턴 공고를 추천 받아보세요!

<br/>

## 🪴 터닝의 핵심기능을 소개합니다

1. 터닝은 간단한 세가지 문답만으로 나만의 대학생 인턴 계획을 입력할 수 있어요. 
2. 내 학년에 맞는 공고와 내가 희망하는 근무 기간에 맞는 인턴 공고가 무엇인지 추천 받으세요.
3. 내 계획에 딱 맞는 인턴 공고를 나만의 커스텀 캘린더로 스크랩하고 관리해보세요.
4. 내가 관심 있는 공고 중 오늘 마감되는 공고를 리마인드 받아보세요.

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
![터닝 AD](https://github.com/user-attachments/assets/966b11f8-f8d9-4a1e-b9db-19bf20c2fa82)


<br/>

## 📎 ERD
![image](https://github.com/user-attachments/assets/d4ef76d7-d1fb-41e2-9a53-10012fe797ea)


<br/><br/>

## [🍏 API 명세서](https://abundant-quiver-13f.notion.site/9b5cd91c634b4a2580ea8222736fdcab?v=e8692cde216c47559d714abc4231d9a7)
> 클릭 시 API 명세서를 확인하실 수 있습니다.

<br/><br/>

## 📂 Structure
```text
├── build.gradle
├── 📂 src/main/java/org/terning/terningserver
│     ├── 📂 config
│     ├── 📂 controller
│     ├── 📂 repository
│     ├── 📂 domain
│     │     ├── 📂 common // ex) BaseEntity.java
│     │     ├── 📂 enums 
│     │     └── 📂 mapping // 매핑 테이블 구현
│     ├── 📂 service // 인터페이스와 구현체(Impl) 구분
│     ├── 📂 dto // 도메인 별로 dto 구분
│     │   ├── 📂 user // 도메인(1)
│     │   │       ├── 📂 request
│     │   │       └── 📂 response
│     │   │
│     │   └── 📂 post // 도메인(2)
│     │           ├── 📂 request
│     │           └── 📂 response
│     └── 📂 exception
│                 ├── 📂 dto // ex) ErrorResponse, SuccessResponse 
│                 ├── 📂 enums // ex) ErrorMessage, SuccessMessage
│                 └── CustomException.java, GlobalExceptionHandler.java
└── ServerApplication.java
```
