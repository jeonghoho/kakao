<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
  <title>게시판 글쓰기 폼</title>
  <jsp:include page="../common_lib.jsp"></jsp:include>
</head>
<body>

  <div class="container">
    <div class="jumbotron mt-3">
      <h1><a href="/boards">게시판</a> 글쓰기 폼</h1>
      <p class="lead">${sessionScope.login.userId} 님이 글을 쓰고 있습니다.</p>
    </div>

    <form method="post" action="/boards" role="form" enctype="multipart/form-data">
      <div class="form-group">
        <label>제목</label>
        <input type="text" name="title" class="form-control">
      </div>
      <div class="form-group">
        <label>내용</label>
        <textarea rows="10" name="content" class="form-control"></textarea>
      </div>
      <!-- name이 같을 경우 배열로 전달된다. -->
      <div class="form-group">
        <label>파일 1</label>
        <input type="file" name="uploadFile" class="form-control">
      </div>
      <div class="form-group">
        <label>파일 2</label>
        <input type="file" name="uploadFile" class="form-control">
      </div>
      <div class="form-group">
        <input type="submit" value="등록" class="btn-primary form-control">
      </div>
    </form>
  </div> <!-- container -->
  </body>
</html>
