<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>員工資料表</title>
</head>
<body>
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
			<td><h3>emp : Home</h3>
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
		<li><a href='listAllEmp.jsp'>List</a> all Emp. <br>
		<br></li>


		<li>
			<FORM METHOD="post" ACTION="/molife/adminController">
				<b>輸入會員編號 (如 1、2):</b> <input type="text" name="adminId"> <input
					type="hidden" name="action" value="getOne_For_Display"> <input
					type="submit" value="送出">
			</FORM>
		</li>

		<jsp:useBean id="adminSvc" scope="page" class="com.admin.service.AdminService" />

		<li>
			<FORM METHOD="post" ACTION="/molife/adminController">
				<b>選擇員工編號:</b> <select size="1" name="adminId">
					<c:forEach var="adminVO" items="${adminSvc.all}">
						<option value="${adminVO.adminId}">${adminVO.adminId}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post" ACTION="/molife/adminController">
				<b>選擇會員姓名:</b> <select size="1" name="adminId">
					<c:forEach var="adminVO" items="${adminSvc.all}">
						<option value="${adminVO.adminId}">${adminVO.empName}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>


	</ul>
	
	<h3>員工登入</h3>
	<ul>
		<li><a href='loginEmp.jsp'>登入</a></li>
	</ul>


	<h3>員工註冊</h3>

	<ul>
		<li><a href='addEmp.jsp'>Add</a> a new emp.</li>
	</ul>
</body>
</html>