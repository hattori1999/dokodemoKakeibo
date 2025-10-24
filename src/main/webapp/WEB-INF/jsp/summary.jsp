<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこでも家計簿 - 集計</title>
</head>
<body>

<h1>集計機能</h1>

<form action="SummaryServlet" method="post">
    ユーザーID：
    <input type="text" name="userId" value="${param.userId}" placeholder="例: 1"><br><br>

    期間：
    <input type="date" name="startDate" value="${param.startDate}">
    〜
    <input type="date" name="endDate" value="${param.endDate}"><br><br>

    カテゴリー：
    <select name="categoryId">
        <option value="">-- 選択 --</option>
        <c:forEach var="category" items="${categoryList}">
            <option value="${category.categoryId}" 
                <c:if test="${param.categoryId == category.categoryId}">selected</c:if>>
                ${category.categoryName}
            </option>
        </c:forEach>
    </select>

    決済方法：
    <select name="creditFlag">
        <option value="">-- 選択 --</option>
        <option value="0" ${param.creditFlag == '0' ? 'selected' : ''}>通常決済</option>
        <option value="1" ${param.creditFlag == '1' ? 'selected' : ''}>クレジット決済</option>
    </select>

    目的：
    <select name="purposeFlag">
        <option value="">-- 選択 --</option>
        <option value="0" ${param.purposeFlag == '0' ? 'selected' : ''}>生活費</option>
        <option value="1" ${param.purposeFlag == '1' ? 'selected' : ''}>お小遣い</option>
    </select>

    <input type="submit" value="集計する">
</form>

<c:if test="${not empty summaryList}">
    <c:set var="grandTotal" value="0" />
    <c:forEach var="s" items="${summaryList}">
        <c:set var="grandTotal" value="${grandTotal + s.total}" />
    </c:forEach>

    <h2>集計結果</h2>
    <p style="font-weight:bold;">合計金額：￥${grandTotal}</p>

    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>日付</th>
                <th>記帳ID</th>
                <th>ユーザーID</th>
                <th>ユーザー名</th>
                <th>カテゴリー</th>
                <th>決済方法</th>
                <th>目的</th>
                <th>合計金額</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="s" items="${summaryList}">
                <c:set var="entryList" value="${fn:split(s.entryIds, ',')}"/>
                <c:forEach var="eid" items="${entryList}">
                    <tr>
                        <td>${s.date}</td>
                        <td>${eid}</td>
                        <td>${s.userId}</td>
                        <td>${userNameMap[s.userId]}</td>
                        <td>
                            <c:forEach var="c" items="${categoryList}">
                                <c:if test="${c.categoryId == s.categoryId}">
                                    ${c.categoryName}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${s.creditFlag == 1}">クレジット決済</c:when>
                                <c:otherwise>通常決済</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${s.purposeFlag == 1}">お小遣い</c:when>
                                <c:otherwise>生活費</c:otherwise>
                            </c:choose>
                        </td>
                        <td>￥${s.total}</td>
                    </tr>
                </c:forEach>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty summaryList and not empty param.userId}">
    <p>該当するデータがありません。</p>
</c:if>

</body>
</html>
