<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>


<html>
<head>
<meta charset="UTF-8">
<title>會員資料</title>

<style>
table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>
</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td><h3>Member : Home</h3>
				<h4></h4></td>
		</tr>
	</table>


	<h3>資料查詢:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<ul>
		<li><a href='listAllMem.jsp'>List</a> all Member. <br> <br></li>


		<li>
			<FORM METHOD="post" ACTION="/molife/memberController">
				<b>輸入會員編號 (如 1、2):</b> <input type="text" name="memId"> <input
					type="hidden" name="action" value="member_View"> <input
					type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />

		<li>
			<FORM METHOD="post" ACTION="/molife/memberController">
				<b>選擇會員編號:</b> <select size="1" name="memId">
					<c:forEach var="memVO" items="${memSvc.all}">
						<option value="${memVO.memId}">${memVO.memId}
					</c:forEach>
				</select> <input type="hidden" name="action" value="member_View">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post" ACTION="/molife/memberController">
				<b>選擇會員姓名:</b> <select size="1" name="memId">
					<c:forEach var="memVO" items="${memSvc.all}">
						<option value="${memVO.memId}">${memVO.memLname}
					</c:forEach>
				</select> <input type="hidden" name="action" value="member_View">
				<input type="submit" value="送出">
			</FORM>
		</li>


	</ul>

	<h3>會員登入</h3>
	<ul>
		<li><a href='loginMem.jsp'>登入</a></li>
	</ul>

	<h3>會員註冊</h3>

	<ul>
		<li><a href='addMem.jsp'>Add</a> a new Mem.</li>
	</ul>

</body>
</html>