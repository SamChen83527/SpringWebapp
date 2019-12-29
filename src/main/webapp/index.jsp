<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC - HelloWorld Index Page</title>

<!-- Required meta tags -->
<meta charset="UTF-8">

<!-- 響應式中繼標籤 Responsive meta tag -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">

<!-- Custom styles for this template-->
<link href="<c:url value="resources/theme1/css/login_style.css" />" rel="stylesheet">
</head>

<body>
	<div class="container-fluid">
		<div class="row no-gutter">
			<!-- left-side image div -->
			<div class="d-none d-md-flex col-md-4 col-lg-6 bg-image"></div>

			<!-- login div -->
			<div class="col-md-8 col-lg-6">
				<div class="login d-flex align-items-center py-5">
					<div class="container">
						<div class="row">
							<div class="col-md-9 col-lg-8 mx-auto">
								<h3 class="login-heading mb-4">Welcome back!</h3>
								<form>
									<div class="form-label-group">
										<input type="email" id="inputEmail" class="form-control"
											placeholder="Email address" required autofocus> <label
											for="inputEmail">Email address</label>
									</div>

									<div class="form-label-group">
										<input type="password" id="inputPassword" class="form-control"
											placeholder="Password" required> <label
											for="inputPassword">Password</label>
									</div>

									<div class="custom-control custom-checkbox mb-3">
										<input type="checkbox" class="custom-control-input"
											id="customCheck1"> <label
											class="custom-control-label" for="customCheck1">Remember
											password</label>
									</div>
									<button
										class="btn btn-lg btn-primary btn-block btn-login text-uppercase font-weight-bold mb-2"
										type="submit">Sign in</button>
									<div class="text-center">
										<a class="small" href="#">Forgot password?</a>
									</div>
								</form>
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
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>
</body>
</html>