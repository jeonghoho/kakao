<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
  <!-- title에 게시물의 제목이 나오도록 해서 구글 검색 봇이 잘 가져갈 수 있도록 한다. -->
  <title>${board.title} - 게시판</title>
  <jsp:include page="../common_lib.jsp"></jsp:include>
</head>
<body>

  <div class="container">
    <div class="jumbotron mt-3">
      <h1><a href="/boards">게시판</a> : ${board.title}</h1>
      <p class="lead"><a href="/users/loginform">로그인</a> 후 글을 작성할 수 있는 게시판입니다.</p>
    </div>

    <div class="row">
      <div class="col-md-2 col-md-offset-2 bg-info text-center">ID</div>
      <div class="col-md-6">${board.id}</div>
    </div>
    <div class="row">
      <div class="col-md-2 col-md-offset-2 bg-info text-center">이름</div>
      <div class="col-md-6">${board.userName}</div>
    </div>
    <div class="row">
      <div class="col-md-2 col-md-offset-2 bg-info text-center">작성자ID</div>
      <div class="col-md-6">${board.userId}</div>
    </div>
    <div class="row">
      <div class="col-md-2 col-md-offset-2 bg-info text-center">제목</div>
      <div class="col-md-6">${board.escapeHtmlTitle}</div>
    </div>
    <div class="row">
      <div class="col-md-2 col-md-offset-2 bg-info text-center">내용</div>
      <div class="col-md-6">
        ${board.escapeHtmlContent}
      </div>
    </div>
    <div class="row">
      <div class="col-md-2 col-md-offset-2 bg-info text-center">writeDate</div>
      <div class="col-md-6"><fmt:formatDate value="${board.writeDate}" pattern="yyyy.MM.dd HH:mm:ss"/></div>
    </div>

    <c:forEach var="boardFile" items="${boardFiles}" varStatus="status">
      <div class="row">
        <div class="col-md-2 col-md-offset-2 bg-info text-center">file</div>
        <div class="col-md-6"><a href="/boards/download/${boardFile.id}">${boardFile.fileName}</a></div>
      </div>
    </c:forEach>

    <div class="row">
      <div class="col-md-8"></div>
      <div class="col-md-4 text-center">
        <c:if test="${sessionScope.login != null}">
          <a href="/boards/deleteform?id=${board.id}" class="btn btn-primary" role="button">삭제</a>
          <a href="/boards/updateform?id=${board.id}" class="btn btn-info" role="button">수정</a>
        </c:if>
      </div>
    </div>
  </div> <!-- container -->
  </body>
</html>
