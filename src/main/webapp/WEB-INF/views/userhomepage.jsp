<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.JSONObject"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC - User Home Page</title>
</head>
<body>
	<table border="1">

		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>User Email</th>
			<th>User Account Name</th>
		</tr>

		<tr>
			<td>${userInfo.firstname}</td>
			<td>${userInfo.lastname}</td>
			<td>${userInfo.email}</td>
			<td>${userInfo.username}</td>
		</tr>

	</table>
</body>
</html>