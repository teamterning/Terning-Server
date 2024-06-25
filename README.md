# 💙 Team-Terning (터닝포인트)

> NOW SOPT 34th Appjam , 터닝포인트 팀입니다.
<br/>


##  Back-end (Spring 🌱)
| <img src="https://avatars.githubusercontent.com/u/150939763?v=4" width=300px alt="서버/정정교"/>  | <img src="https://avatars.githubusercontent.com/u/63058347?v=4" width=300px alt="서버/신정윤"/>  | <img src="https://avatars.githubusercontent.com/u/89952042?v=4" width=300px alt="서버/권장순"/>
| :-----: | :-----: | :-----: |
| [정정교/Junggyo1020](https://github.com/junggyo1020) | [신정윤/JungYoonShin](https://github.com/JungYoonShin) | [권장순/jsoonworld](https://github.com/jsoonworld) |

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
