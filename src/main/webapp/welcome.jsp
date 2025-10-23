<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>どこでも家計簿</title>
  <link rel="stylesheet" type="text/css" href="css/common.css">

</head>
<body>

<%@ include file="/common/headerTop.jsp" %>

<main class="main">
	<h1 class="title">どこでも家計簿</h1>
	
	<div class="button-area">
		<a href="WelcomeServlet" class="action-btn" method="post">ログインする</a>
	</div>
</main>


 <%@ include file="/common/footer.jsp" %>
</body>
</html>