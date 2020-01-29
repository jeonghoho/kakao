<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
  <title>게시물 삭제</title>
  <jsp:include page="../common_lib.jsp"></jsp:include>
</head>
<body>

  <div class="container">
    <div class="jumbotron mt-3">
      <h1><a href="/boards">게시판</a> : ${board.title}</h1>
      <p class="lead">해당 글을 삭제하시겠습니까?</p>
    </div>

    <form method="post" action="/boards/delete" role="form">
      <input type="hidden" name="id" value="${board.id}">
      <div class="form-group">
        <input type="submit" value="삭제" class="btn-primary form-control">
      </div>
    </form>
  </div> <!-- container -->
  </body>
</html>
