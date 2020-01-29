<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <title>게시물 목록</title>
    <jsp:include page="../common_lib.jsp"></jsp:include>
  </head>
  <body>

  <div class="container">
    <div class="jumbotron mt-3">
      <a href="/boards"><h1>게시판</h1></a>
      <p class="lead"><a href="/users/loginform">로그인</a> 후 글을 작성할 수 있는 게시판입니다.</p>
    </div>

    <div class="row">
      <div class="col-md-8"></div>
      <div class="col-md-4 text-center">
        <c:if test="${sessionScope.login != null}">
          ${sessionScope.login.userId} 님 환영합니다.
          <a href="/users/logout" class="btn btn-info" role="button">로그아웃</a>
          <a href="/boards/writeform" class="btn btn-primary" role="button">글쓰기</a>
        </c:if>
        <c:if test="${sessionScope.login == null}">
          <a href="/users/loginform" class="btn btn-primary" role="button">로그인</a>
          <a href="/users/joinform" class="btn btn-info" role="button">회원가입</a>
        </c:if>
      </div>
    </div>

    <table class="table table-striped table-hover">
      <thead>
        <tr>
          <th class="col-md-2">ID</th>
          <th class="col-md-6">제목</th>
          <th class="col-md-2">조회수</th>
          <th class="col-md-2">작성일</th>
        </tr>
      </thead>

      <c:forEach var="board" items="${list}" varStatus="status">
        <tr>
          <td>${board.id}</td>
          <td><a href="/boards/${board.id}">${board.escapeHtmlTitle}</a></td>
          <td>${board.readCount}</td>
          <td><fmt:formatDate value="${board.writeDate}" pattern="yyyy.MM.dd"/></td>
        </tr>
      </c:forEach>
    </table>

    <!-- 페이지 네이션 -->
    <div class="text-center">
      <ul class="pagination">
        <c:forEach var="pageLink" items="${pageNavi}" varStatus="status">
          <li><a href="/boards?${pageLink}">${pageLink.page}</a></li>
        </c:forEach>
      </ul>
    </div>

    <form method="get" action="/boards" role="form" class="form-inline text-center">
      <div class="row">
        <div class="form-group">
          <select name="kind"class="form-control">
            <option value="name">이름</option>
            <option value="title">제목</option>
            <option value="content">내용</option>
          </select>
        </div>
        <div class="form-group">
          <input type="text" name="value"class="form-control">
        </div>
        <div class="form-group">
          <input type="submit" value="검색"class="form-control">
        </div>
      </div>
    </form>
  </div> <!-- 컨테이너 -->

  </body>
</html>
