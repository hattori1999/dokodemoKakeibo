<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこでも家計簿</title>
<link rel="stylesheet" type="text/css" href="css/common.css">
</head>
<body>
	<%@ include file="/common/headerTop.jsp" %>

<div class="container">
	<h1 class="title">ログイン</h1>
	<form action="LoginServlet" method="post">
		<div class="form-group">
			<label for="userId">ユーザーID (user_id)</label>
			<input type="text" id="userId" name="userId" required>
		</div>
		<div class="form-group">
			<label for="password">パスワード</label>
			<input type="password" id="password" name="password" required>
		</div>
		
		<!-- エラーメッセージ（サーブレットから渡された時だけ表示） -->
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <div class="error"><%= errorMessage %></div>
        <%
            }
        %>
		
		<div class="btn-area">
            <button type="button" class="action-btn" onclick="location.href='welcome.jsp'">キャンセル</button>
            <button type="submit" class="action-btn">ログイン</button>
        </div>
		
	</form>
</div>

	 <%@ include file="/common/footer.jsp" %>
</body>
</html>