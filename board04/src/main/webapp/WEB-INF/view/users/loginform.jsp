<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인</title>
<jsp:include page="../common_lib.jsp"></jsp:include>
</head>
<body>
	<div class="container">
		<div class="jumbotron mt-3">
			<h1>
				<a href="/boards">게시판</a> 로그인
			</h1>
			<p class="lead">아이디와 암호를 입력해 로그인 해주세요..</p>
		</div>

		<!-- grid는 12칸으로 구성되어 있다. 3칸 비우고 6칸 크기의 div를 생성한다.-->
		<div class="col-md-6 col-md-offset-3">
			<form method="post" action="/users/login" role="form">
				<div class="form-group">
					<label>ID</label> <input type="text" name="userId"
						class="form-control">
				</div>
				<div class="form-group">
					<label>암호</label> <input type="password" name="password"
						class="form-control">
				</div>
				<div class="form-group">
					<label></label> <input type="submit" value="로그인"
						class="form-control btn-primary">
				</div>
			</form>
		</div>
	</div>
</body>
</html>
