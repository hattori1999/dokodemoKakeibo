<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    int userId = (int) session.getAttribute("userId");
    int currentAmount = (request.getAttribute("currentAmount") != null)
                        ? (int) request.getAttribute("currentAmount")
                        : 0;
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>お小遣い設定</title>
</head>
<body>
    <h2>お小遣い設定</h2>

    <form action="${pageContext.request.contextPath}/allowance" method="post">
        <label>お小遣い額：</label>
        <input type="number" name="amount" min="0" step="1" value="<%= currentAmount %>" required> 円
        <input type="hidden" name="userId" value="<%= userId %>">
        <input type="submit" value="更新">
    </form>

    <p>現在のお小遣い金額：<%= currentAmount %> 円</p>

    <p><a href="${pageContext.request.contextPath}/mypage.jsp">マイページへ戻る</a></p>
</body>
</html>
