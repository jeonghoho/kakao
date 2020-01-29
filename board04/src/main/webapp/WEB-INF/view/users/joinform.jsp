<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
  <head>
    <title>회원가입 폼</title>
  </head>
  <body>
    <h1>회원가입 폼</h1>

  <form method="post" action="/users/join">
    <div class="row">
      <div class="col-md-4 bg-info text-center">ID</div>
      <div class="col-md-8"><input type="text" name="userId" class="form-control"></div>
    </div>
    <div class="row">
      <div class="col-md-4 bg-info text-center">이름</div>
      <div class="col-md-8"><input type="text" name="name" class="form-control"></div>
    </div>
    <div class="row">
      <div class="col-md-4 bg-info text-center">암호</div>
      <div class="col-md-8"><input type="password" name="password" class="form-control"></div>
    </div>
    <div class="row">
      <div class="col-md-4 bg-info text-center"></div>
      <div class="col-md-8"><input type="submit" value="확인" class="form-control"></div>
    </div>
  </form>
  </body>
</html>
