<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spring 4 MVC - Get user by full name</title>
</head>
<body>
	 <table border="1">
	 
        <tr>
            <th>User Id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>User Email</th>
            <th>User Account Name</th>
            <th>User Password</th>
        </tr>

		<tr>
			<td>${user.rid}</td>
			<td>${user.firstname}</td>
			<td>${user.lastname}</td>
			<td>${user.email}</td>
			<td>${user.username}</td>
			<td>${user.password}</td>
		</tr>

	</table>
</body>
</html>