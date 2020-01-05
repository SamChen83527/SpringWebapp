<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC - Registration Page</title>

<!-- Required meta tags -->
<meta charset="UTF-8">

<!-- 響應式中繼標籤 Responsive meta tag -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<link href="<c:url value="/resources/css/registration_style.css" />"
	rel="stylesheet">
	
</head>
<body>
	<div class="container-fluid">
		<div class="row no-gutter">
			<!-- left-side image div -->
			<div class="d-none d-md-flex col-md-4 col-lg-6 bg-image"></div>

			<!-- registration div -->
			<div class="col-md-8 col-lg-6">
				<div class="registration d-flex align-items-center py-5">
					<div class="container">
						<div class="row">
							<div class="col-md-9 col-lg-8 mx-auto">
								<h3 class="registration-title mb-4">Sign Up</h3>
								
								<form:form class="form-signin" id="registrationForm" modelAttribute="user_registration" action="registration" method="POST">
									<div class="form-label-group">
										<form:input path="firstname" type="text" id="inputFirstName" class="form-control" placeholder="First Name" required="required" autofocus="autofocus" />
										<form:label path="firstname" for="inputFirstName">First name</form:label>
									</div>

									<div class="form-label-group">
										<form:input path="lastname" type="text" id="inputLastName" class="form-control" placeholder="Last Name" required="required" />
										<form:label path="lastname" for="inputLastName">Last name</form:label>
									</div>

									<div class="form-label-group">
										<form:input path="email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required="required" />
										<form:label path="email" for="inputEmail">Email address</form:label>
									</div>

									<div class="form-label-group">
										<form:input path="username" type="username" id="inputUserName" class="form-control" placeholder="User name" required="required" />
										<form:label path="username" for="inputUserName">User name</form:label>
									</div>

									<div class="form-label-group">
										<form:input path="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required="required" />
										<form:label path="password" for="inputPassword">Password</form:label>
									</div>

									<div class="form-label-group">
										<input type="password" id="inputConfirmPassword" class="form-control" placeholder="Comfirm password" required="required" />
										<label for="inputConfirmPassword">Comfirm password</label>
									</div>

									<hr class="my-4">
									<form:button class="btn btn-lg btn-primary btn-block btn-registration text-uppercase" type="submit">
										Sign up
									</form:button>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>
</body>
</html>