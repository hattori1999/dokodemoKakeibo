<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこでも家計簿 - 削除確認</title>
<script>
function confirmDelete() {
    if (confirm("本当に削除しますか？")) {
        document.getElementById("deleteForm").submit();
    }
}
</script>
</head>
<body>
<%@ include file="/common/header.jsp" %>

<h1>削除データ確認</h1>


<!-- ここにエラーメッセージを追記 -->
<c:if test="${not empty errorMsg}">
    <p style="color:red">${errorMsg}</p>
</c:if>

<form action="DeleteEntryServlet" method="post" id="deleteForm">
    <label>記帳ID検索:
        <input type="number" name="entryId" min="0" step="1" value="${param.entryId}">
    </label>
    <button type="submit" formaction="EntrySearchServlet">検索する</button>
</form>

<c:if test="${not empty entry}">
    <h2>検索結果</h2>
    <table border="1">
        <thead>
            <tr>
                <th>ユーザーID</th>
                <th>日付</th>
                <th>支出内容</th>
                <th>分類ID</th>
                <th>決済方法</th>
                <th>目的</th>
                <th>金額</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>${entry.userId}</td>
                <td>${entry.date}</td>
                <td>${entry.name}</td>
                <td>${entry.categoryId}</td>
                <td><c:choose>
                        <c:when test="${entry.creditFlag}">クレジット</c:when>
                        <c:otherwise>現金</c:otherwise>
                    </c:choose></td>
                <td><c:choose>
                        <c:when test="${entry.purposeFlag}">生活費</c:when>
                        <c:otherwise>お小遣い</c:otherwise>
                    </c:choose></td>
                <td>${entry.price}</td>
            </tr>
        </tbody>
    </table>
    <br>
    <button type="button" onclick="confirmDelete()">削除する</button>
</c:if>

<c:if test="${empty entry}">
    <p>データが見つかりません。</p>
</c:if>

<%@ include file="/common/footer.jsp" %>
</body>
</html>
