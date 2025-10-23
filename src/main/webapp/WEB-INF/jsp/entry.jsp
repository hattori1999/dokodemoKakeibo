<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>どこでも家計簿</title>
<style>
    .error { color: red; }
</style>
</head>
<body>
<%@ include file="/common/header.jsp" %>

<main>
    <h1>記帳ページ</h1>

    <!-- 成功・エラーメッセージ -->
    <c:if test="${not empty successMessage}">
        <p style="color:green">${successMessage}</p>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <p class="error">${errorMessage}</p>
    </c:if>

    <!-- フォーム開始 -->
    <form id="entryForm" action="EntryServlet" method="post" onsubmit="return validateForm()">
        <!-- 日付 -->
        <label for="entryDate">日付:</label>
        <input type="date" id="entryDate" name="entryDate">
        <span id="dateError" class="error"></span><br><br>

        <!-- 支出内容 -->
        <label for="entryName">支出内容:</label>
        <input type="text" id="entryName" name="entryName">
        <span id="nameError" class="error"></span><br><br>

        <!-- 分類 -->
        <label for="categoryId">分類:</label>
        <select id="categoryId" name="categoryId">
            <option value="">--選択してください--</option>
            <%
                List<Category> list = (List<Category>) request.getAttribute("categoryList");
                if (list != null) {
                    for (Category cat : list) {
            %>
                        <option value="<%= cat.getCategoryId() %>"><%= cat.getCategoryName() %></option>
            <%
                    }
                }
            %>
        </select>
        <span id="categoryError" class="error"></span><br><br>

        <!-- 決済方法 -->
        <label for="paymentMethod">決済方法:</label>
        <select id="paymentMethod" name="paymentMethod">
            <option value="">--選択してください--</option>
            <option value="normal">通常決済</option>
            <option value="credit">クレジット決済</option>
        </select>
        <span id="paymentError" class="error"></span><br><br>

        <!-- 目的 -->
        <label for="purpose">目的:</label>
        <select id="purpose" name="purpose">
            <option value="">--選択してください--</option>
            <option value="living">生活費</option>
            <option value="allowance">お小遣い</option>
        </select>
        <span id="purposeError" class="error"></span><br><br>

        <!-- 金額 -->
        <label for="amount">金額:</label>
        <input type="number" id="amount" name="amount" placeholder="金額を入力してください" min="0">
        <span id="amountError" class="error"></span><br><br>

        <!-- 送信ボタン -->
        <button type="submit">記帳する</button>
    </form>

    <!-- バリデーションスクリプト -->
    <script>
    function validateForm() {
        let valid = true;

        // 各エラー初期化
        document.getElementById("dateError").textContent = "";
        document.getElementById("nameError").textContent = "";
        document.getElementById("categoryError").textContent = "";
        document.getElementById("paymentError").textContent = "";
        document.getElementById("purposeError").textContent = "";
        document.getElementById("amountError").textContent = "";

        // 日付
        const entryDate = document.getElementById("entryDate");
        if(entryDate.value === "") {
            document.getElementById("dateError").textContent = "日付を入力してください";
            valid = false;
        }

        // 支出内容
        const entryName = document.getElementById("entryName");
        if(entryName.value.trim() === "") {
            document.getElementById("nameError").textContent = "支出内容を入力してください";
            valid = false;
        }

        // 分類
        const categoryId = document.getElementById("categoryId");
        if(categoryId.value === "") {
            document.getElementById("categoryError").textContent = "分類を選択してください";
            valid = false;
        }

        // 決済方法
        const paymentMethod = document.getElementById("paymentMethod");
        if(paymentMethod.value === "") {
            document.getElementById("paymentError").textContent = "決済方法を選択してください";
            valid = false;
        }

        // 目的
        const purpose = document.getElementById("purpose");
        if(purpose.value === "") {
            document.getElementById("purposeError").textContent = "目的を選択してください";
            valid = false;
        }

        // 金額
        const amount = document.getElementById("amount");
        if(amount.value === "" || parseInt(amount.value) <= 0) {
            document.getElementById("amountError").textContent = "金額を正しく入力してください";
            valid = false;
        }

        return valid; // trueなら送信、falseなら停止
    }
    </script>

</main>

<%@ include file="/common/footer.jsp" %>
</body>
</html>
