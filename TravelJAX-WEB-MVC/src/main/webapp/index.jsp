<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="cssIndex.css">
</head>
<body class="loginPage">

<div class= "content">
	<h1>LOGIN PAGE</h1>
	<form class="login_form" action="Putnik" method="post">
		<ul>
			<li>
				<label>Username:</label>
				<input type="text" name="username" value="${param.username}">
			</li>
			<li>
				<label>Password:</label>
				<input type="password" name="password" value="${param.password}">
			</li>
			<li>
				<input class="dugme_input" type="submit" name="action" value="Login">
			</li>
		</ul>
	</form>
	${requestScope.msg}<br>
	</div>
</body>
</html>