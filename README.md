# 👣 Half Step (반걸음) - Backend Server

> **고민의 무게를 덜어주는 익명 고민 상담 커뮤니티 '반걸음'의 백엔드 API 서버입니다.** > 감성(F)과 이성(T)의 온도를 조절하고, '발걸음'이라는 재화를 통해 서로 위로하고 성장하는 시스템을 제공합니다.

<br>

## 🛠 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.5.9 (SNAPSHOT) |
| **Build Tool** | Maven |
| **Database** | Oracle Database (XE / Cloud) |
| **ORM** | Spring Data JPA (Hibernate) |
| **Security** | Spring Security, JWT (Access Token) |
| **Docs** | Swagger (SpringDoc OpenAPI) |
| **Deploy** | AWS EC2 (Ubuntu), Systemd |

<br>

## ✨ Key Features

### 1. 🔐 인증 (Auth)
* **JWT 기반 로그인/회원가입**: Access Token을 이용한 무상태(Stateless) 인증 구현.
* **아이디 중복 확인**: 실시간 가입 가능 여부 체크 API 제공.

### 2. 📝 게시판 (Post)
* **고민 글 작성**: 제목, 내용과 함께 '온도(Temperature)' 슬라이더 값을 저장하여 감성/이성 성향 표시.
* **발걸음(재화) 시스템**: 글 작성 시 설정한 보상만큼 사용자 발걸음 차감.
* **게시글 관리**: 최신순 조회, 수정, 삭제(작성자 본인만 가능, 연관 댓글 자동 삭제).

### 3. 💬 소통 및 채택 (Comment & Select)
* **답변 작성**: 답변 작성 시 작성자에게 `3걸음` 즉시 지급하여 참여 유도.
* **중복 방지**: 한 게시글에 중복 답변 방지 로직 적용.
* **채택 시스템**: 작성자가 답변 채택 시 게시글은 마감되며, 채택된 사용자에게 작성자가 건 보상 발걸음 지급.

### 4. 👤 마이페이지 & 성장 (User & Level)
* **내 정보 조회**: 누적 발걸음, 현재 레벨(알~황금새), 작성 글/댓글 통계 확인.
* **활동 내역**: 내가 작성한 게시글과 댓글 리스트 조회.
* **설정 및 탈퇴**: 비속어 필터 설정(ON/OFF), 회원 탈퇴(관련 데이터 일괄 삭제 처리).

### 5. 🏆 랭킹 (Ranking)
* **명예의 전당**: 누적 발걸음(`total_steps`) 순으로 상위 유저 리스트 제공.

<br>

## ⚙️ Environment Variables (.env)

보안을 위해 환경 변수는 `.env` 파일로 관리하거나 서버 시스템 변수로 등록해야 합니다.  
프로젝트 루트 경로에 아래 내용을 포함한 `.env` 파일을 생성하세요.

```properties
# Database Configuration (Oracle)
DB_URL=jdbc:oracle:thin:@localhost:1521:xe
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT Secret Key (32바이트 이상 문자열 권장)
JWT_SECRET=v3ryS3cr3tK3yF0rJwT51gn1ngPurp0s3sD0N0tSh4r3T1hs!!
