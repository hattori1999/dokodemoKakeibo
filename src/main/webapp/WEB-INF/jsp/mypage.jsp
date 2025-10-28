<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこでも家計簿</title>
</head>
<body>
	<%@ include file="/common/headerMypage.jsp" %>

<div class="container">
	<h1>マイページ</h1>
	<div class="summary">
		ようこそ<%= session.getAttribute("userName") %>様<br>
		今月の残りのお小遣いは${remainingAllowance}円です
	</div>
	
	<div class="menu">
		＜マイメニュー＞
		<ul>
			<li><a href="EntryServlet">記帳する</a></li>
			<li><a href="EntryListServlet">削除する</a></li>
			<li><a href="SummaryServlet">集計する</a></li>
			<li><a href="${pageContext.request.contextPath}/allowance">お小遣い設定</a></li>
			<li><a href>分類の編集</a></li>
		</ul>
	</div>
</div>



	<%@ include file="/common/footer.jsp" %>
</body>
</html>