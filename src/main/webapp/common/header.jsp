<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header class="app-header">
	どこでも家計簿
	<div class="header-right">
		<%= session.getAttribute("userName") %>
		<a href>マイページへ</a>
</header>