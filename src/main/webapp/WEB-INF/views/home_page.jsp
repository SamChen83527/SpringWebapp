<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="org.json.JSONObject"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 響應式中繼標籤 Responsive meta tag -->
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
	
<title>Spring 4 MVC - User Home Page</title>

<!-- Gauge JavaScript -->
<link href="<c:url value="/resources/css/chart_style.css" />" rel="stylesheet">
<script src="<c:url value="http://mbostock.github.com/d3/d3.js" />" ></script>
<script src="<c:url value="/resources/javascript/chart.js" />" ></script>

<!-- STA JavaScript -->
<script src="<c:url value="/resources/javascript/$TA_JsLibrary.js" />" ></script>
<script src="<c:url value="/resources/javascript/sta.js" />" ></script>

<script>
	var serverDomain = "https://sta.ci.taiwan.gov.tw/STA_AirQuality_v2/v1.0/";
	var datastream_id = 10;
	
	var gauges = [];

	function createGauge(name, label, min, max) {
		var config = {
			size : 200,
			label : label,
			min : undefined != min ? min : 0,
			max : undefined != max ? max : 100,
			minorTicks : 5
		}

		var range = config.max - config.min;
		config.yellowZones = [ {
			from : config.min + range * 0.75,
			to : config.min + range * 0.9
		} ];
		config.redZones = [ {
			from : config.min + range * 0.9,
			to : config.max
		} ];

		gauges[name] = new Gauge(name + "GaugeContainer", config);
		gauges[name].render();
	}

	function createGauges() {
		createGauge("temperature", "Temperature (°C)", -20, 120);
		createGauge("humidity", "Humidity (%)", 0, 100);
	}

	function updateGauges() {
		for ( var key in gauges) {

			var latestObsRS = getLatestObservation(createServerConnection(serverDomain), datastream_id);
			var latestObs = latestObsRS.result;
			
			var value = latestObs; // input observation
			gauges[key].redraw(value);
		}
	}

	function initialize(observedProperty, tag, min, max) {
		createGauge(observedProperty, tag, min, max);
		setInterval(updateGauges, 5000);
	}
</script>

</head>
<body>
	<nav class="navbar navbar-expand-sm navbar-dark bg-dark"> <!-- "class .navbar-expand" will expand collapse list when client resolution is enough -->
		<a class="navbar-brand mb-0 h1" href="#">SENZOR</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
	
		<div class="collapse navbar-collapse" id="navbarNavDropdown">
			<!-- "class .navbar-collapse" will turn <ul> into a collapse list when client resolution decrease -->
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="#">Home
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Features</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Pricing</a></li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" 
						href="#"
						id="navbarDropdownMenuLink" 
						data-toggle="dropdown"
						aria-haspopup="true" 
						aria-expanded="false">Dropdown link</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
						<a class="dropdown-item" href="#">Action</a> 
						<a class="dropdown-item" href="#">Another action</a> 
						<a class="dropdown-item" href="#">Something else here</a>
					</div>
				</li>
			</ul>
			
			<form id="registerForm" class="form-inline my-2 my-lg-0">
				<button
					class="btn btn-outline-light btn-login font-weight-bold my-2 my-sm-0"
					onclick="location.href='/springwebapp/user/logout'"
					type="button">Logout</button>
			</form>
		</div>
	</nav>


	<div class="fluid-container">
		<body onload="initialize('pm2_5', 'PM 2.5', 0, 500)">
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-sm-6 col-md-6">
						<span id="pm2_5GaugeContainer">
					</div>
				</div>
			</div>
		</body>
		
	</div>
	<div class="fluid-container">
		<body onload="initialize('pm2_5', 'PM 2.5', 0, 500)">
			<div class="container">
				<div class="row justify-content-center">
					<div class="col-sm-6 col-md-6">
						<span id="pm2_5GaugeContainer">
					</div>
				</div>
			</div>
		</body>
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