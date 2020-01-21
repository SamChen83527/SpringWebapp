var serverDomain = document.getElementById("dashboard").getAttribute("server_domain");
var datastream_id = document.getElementById("dashboard").getAttribute("datastream_id");
var latestObs;

window.onload = onloadFunctions(serverDomain, datastream_id);

function onloadFunctions(serverDomain, datastream_id){
	// Refresh latest observation
	latestObs = getLatestObservation(createServerConnection(serverDomain), datastream_id);
	setInterval(function(){
			latestObs = getLatestObservation(createServerConnection(serverDomain), 609);
		},5000);
	
	createPM25Gauge("chartdiv", serverDomain, datastream_id);
	drawHistoricalData("historial_chartdiv", serverDomain, datastream_id);
	drawMap("mapdiv", serverDomain, datastream_id);
}

function drawHistoricalData(div_id, serverDomain, datastream_id){
	let Model = $TA.Model('Datastream',{id:datastream_id})
						.$expand(
								$TA.Model('Observation')
									.$filter("phenomenonTime ge 2019-11-10T12:00:00.000Z and phenomenonTime le 2020-12-12T12:00:00.000Z")
						);
	
	var sta_connection = createServerConnection(serverDomain);
	sta_connection.getFormatData(Model,function(ResultSet){
	    let divID = div_id; //設定圖表繪製的位置
	    let Display = $TA.Display() //建立Display()物件
	    Display.drawChart(divID,ResultSet); //用drawChart()繪製圖表、並回傳chart物件
	});
}

function drawMap(div_id, serverDomain, datastream_id){
	let Model = $TA.Model('Thing')
						.$filter("Datastream/id eq "+ datastream_id)
						.$expand($TA.Model('Location'));
	
	var sta_connection = createServerConnection(serverDomain);
	sta_connection.getFormatData(Model,function(ResultSet){
	    let divID = div_id; //設定地圖繪製的位置
	    let Display = $TA.Display(); //建立一個Display()物件
	    Display.drawMap(divID,ResultSet) //用drawMap()繪製地圖、並回傳map物件   
	});
}

function createPM25Gauge(div_id, serverDomain, datastream_id){
	// Themes begin
	am4core.useTheme(am4themes_frozen);
	am4core.useTheme(am4themes_animated);
	// Themes end
	
	// create chart
	var chart = am4core.create(div_id, am4charts.GaugeChart);
	chart.innerRadius = -10;
	
	// create axis
	var axis = chart.xAxes.push(new am4charts.ValueAxis());
	axis.min = 0;
	axis.max = 500;
	axis.strictMinMax = true;	
	
	// fill axis color
	var range1 = axis.axisRanges.create();
	range1.value = 0;
	range1.endValue = 15.4;
	range1.axisFill.fillOpacity = 1;
	range1.axisFill.fill = am4core.color("#00FF00");
	range1.axisFill.zIndex = -1;
	
	var range2 = axis.axisRanges.create();
	range2.value = 15.5;
	range2.endValue = 35.4;
	range2.axisFill.fillOpacity = 1;
	range2.axisFill.fill = am4core.color("yellow");
	range2.axisFill.zIndex = -1;
	
	var range3 = axis.axisRanges.create();
	range3.value = 35.5;
	range3.endValue = 54.4;
	range3.axisFill.fillOpacity = 1;
	range3.axisFill.fill = am4core.color("orange");
	range3.axisFill.zIndex = -1;
	
	var range4 = axis.axisRanges.create();
	range4.value = 54.5;
	range4.endValue = 150.4;
	range4.axisFill.fillOpacity = 1;
	range4.axisFill.fill = am4core.color("red");
	range4.axisFill.zIndex = -1
	
	var range5 = axis.axisRanges.create();
	range5.value = 150.5;
	range5.endValue = 250.4;
	range5.axisFill.fillOpacity = 1;
	range5.axisFill.fill = am4core.color("purple");
	range5.axisFill.zIndex = -1
	
	var range6 = axis.axisRanges.create();
	range6.value = 250.5;
	range6.endValue = 500;
	range6.axisFill.fillOpacity = 1;
	range6.axisFill.fill = am4core.color("#A52A2A");
	range6.axisFill.zIndex = -1
	
	// create clock hand
	var hand = chart.hands.push(new am4charts.ClockHand());
	hand.radius = am4core.percent(99);
	
	var sta_connection = createServerConnection(serverDomain);
	
	setInterval(function () {
	    hand.showValue(
	    		latestObs.result, 
	    		2000, 
	    		am4core.ease.cubicOut);
	}, 2*1000);
}
