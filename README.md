# board
SpringBoot JPA, board

## 프로젝트 소개

---

쌍용교육센터 도중 진행한 개인 프로젝트입니다. SpringBoot와 Data JPA를 활용하여 회원들끼리 소통할 수 있는 커뮤니티인 회원 게시판을 만들었습니다.

## 개발환경

---

## 핵심업무

---

- 게시판 : CRUD 기능, 조회수, 페이징 및 검색
- 사용자 : Security 회원가입 및 로그인
- 댓글 : CRUD 기능

## 데이터 구조(ERD)
![Untitled](https://user-images.githubusercontent.com/73234708/187394365-e1f05b50-91b3-4241-af6a-fe8a0498b58b.png)
---

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9899a58b-c24d-459d-bea6-116c40139ce4/Untitled.png)

- Entity 클래스를 사용하여 JPA가 관리하게 해주었다.

## 구현화면 및 설명

---

### 회원가입 및 로그인

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d0a09927-7e0c-40fd-a259-2dc49d8c7173/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/fa5daa5e-c584-45e2-9966-7feef3edd7eb/Untitled.png)

- 회원가입 및 로그인
    - SecurityConfig에서 WebSecurityConfigurerAdaper를 상속받은 클래스에서 메소드를 오버라이딩 하여 조정한다.
    - 사용자 정보를 가져오기 위해UserDetailsService 인터페이스를 사용한다.

### 리스트, 페이징, 조회수

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b9861dd7-1bd8-463a-884f-0cfa554d62e4/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/18b5e754-cd82-4d90-9ed7-e80b5cc48ca3/Untitled.png)

- 리스트, 페이징
    - jpa findAll()과 pageable를 사용한다.
- 조회수
    - BoardRepository에서 jpql 쿼리를 생성하고 view컨트롤러를 안에 service에서 조회수 메소드를 넣어줘서 증가시킨다

### 글쓰기, 수정, 삭제

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/620f57e7-7af8-43c7-9cf6-cc0dcca62bb1/Untitled.png)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2eed1bab-ed16-4ab0-9d57-e7ff2c8fc7c0/Untitled.png)

- 글쓰기
    - GetMapping -> 글쓰기 폼.html -> PostMapping하여 작성
    - 컨트롤러에서 model에 객체를 담아 전해준다.
- 글수정
    - GetMapping -> 수정 -> PutMapping -> 수정된글 업데이트 하여 수정
    - @PathVariable 통해 게시글 아이디를 가져온다.
- 글삭제
    - deletePost를 사용해서 게시글 id로 삭제한다.
    - @PathVariable 통해 게시글 아이디를 가져온다.

** 수정, 삭제 시 security authentication principal를 사용하여 로그인한 정보와 게시글 정보가 일치하는지 확인시켜 본인 게시글만 수정 삭제 가능하다.

### 검색 및 검색 페이징

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cede5049-a55a-4ea0-9ebe-d41087989c70/Untitled.png)

- 검색 및 검색 페이징
    - repository에서 findByTitleContaining 이처럼 By 이후에 containing을 붙혀 Like 검색이 가능하게 해줘서 검색한다.
    - 모델에 객체를 담아 전해준다.

### 상세보기 및 댓글작성

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9255fa6b-4cc4-461f-b948-e571ebe4f4e8/Untitled.png)

- 상세보기
    - 게시글 제목을 누르면 컨트롤러에서 Getmapping해서 후 보여준다.
    - @PathVariable를 통해 게시글 아이디를 가져온다.
    - model에서 객체를 담아 전해준다.
- 댓글작성
    - PostMapping 하고 @PathVariable를 사용하여 게시글 아이디를 가져와 작성한다.
    - commentRepository에서 게시판 번호와 댓글 번호를 비교하는 jpql 쿼리를 짜서 게시글 상세보기 할 때 댓글도 가져온다.

** 수정, 삭제 시 security authentication principal를 사용하여 로그인한 정보와 댓글정보가 일치하는지 확인시켜 본인 댓글만 수정 삭제 가능하다.
