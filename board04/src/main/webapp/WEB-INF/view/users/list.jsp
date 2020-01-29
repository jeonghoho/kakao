<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>사용자 목록</title>
<link rel="stylesheet" href="/resources/css/board_list.css">
</head>
<body>
	<div class="userList">
		<div class="userListHead">
			<div>id</div>
			<div>이름</div>
			<div>가입일</div>
		</div>

		<c:forEach var="user" items="${list}" varStatus="status">
			<div class="userInfo">
				<div>${user.userId}</div>
				<div>${user.name}</div>
				<div>
					<fmt:formatDate value="${user.joinDate}" pattern="yyyy.MM.dd" />
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>
