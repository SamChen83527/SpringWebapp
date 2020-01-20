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

<!-- Custom styles for this template -->
<link href="<c:url value="/resources/css/dashboard.css" />"
	rel="stylesheet">

<title>Spring 4 MVC - User Home Page</title>

<!-- Gauge JavaScript
<link href="<c:url value="/resources/css/chart_style.css" />" rel="stylesheet">
<script src="<c:url value="http://mbostock.github.com/d3/d3.js" />" ></script>
<script src="<c:url value="/resources/javascript/chart.js" />" ></script> -->

<!-- STA JavaScript -->
<script src="<c:url value="/resources/javascript/$TA_JsLibrary.js" />"></script>
<script src="<c:url value="/resources/javascript/sta.js" />"></script>

<!-- 
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
</script> -->

</head>
<body>
	<nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
		<!-- <nav class="navbar navbar-expand-sm navbar-dark bg-dark"> --> <!-- "class .navbar-expand" will expand collapse list when client resolution is enough -->
		<a class="navbar-brand h1 col-sm-3 col-md-2 my-0 my-sm-0" href="#">SENZOR</a>
		
		<button
			class="btn btn-outline-light btn-login font-weight-bold my-0 my-sm-0 mr-2"
			onclick="location.href='/springwebapp/user/logout'" type="button">Logout</button>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<nav class="col-md-2 d-none d-md-block bg-light sidebar">
				<div class="sidebar-sticky">
					<ul class="nav flex-column">
						<li class="nav-item"><a class="nav-link active" href="#">
								<span data-feather="home"></span> Dashboard <span class="sr-only">(current)</span>
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="bar-chart-2"></span> TO-DO
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="layers"></span> TO-DO
						</a></li>
					</ul>
	
					<h6
						class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
						<span>TO-DO LIST</span> <a
							class="d-flex align-items-center text-muted" href="#"> <span
							data-feather="plus-circle"></span>
						</a>
					</h6>
					<ul class="nav flex-column mb-2">
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="file-text"></span> TO-DO
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="file-text"></span> TO-DO
						</a></li>
					</ul>
				</div>
			</nav>

			<main role="main" class="col-md-10 ml-sm-auto col-lg-10 pt-3 px-4">
				<div class="row">
					<div class="col" id="chartdiv"></div>
				</div>
				<div class="row">
					<div class="col" id="historial_chartdiv"></div>
				</div>
			</main>
		</div>

	</div>




	<!-- Gauge JavaScript -->
	<link href="<c:url value="/resources/css/chart_style.css" />"
		rel="stylesheet">
	<script src="https://www.amcharts.com/lib/4/core.js"></script>
	<script src="https://www.amcharts.com/lib/4/charts.js"></script>
	<script src="https://www.amcharts.com/lib/4/themes/frozen.js"></script>
	<script src="https://www.amcharts.com/lib/4/themes/animated.js"></script>
	<script src="<c:url value="/resources/javascript/chart.js" />"></script>

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