/**
 * This is the JavaScript file for login
 */
var service_url = "http://localhost:8080/springwebapp";

//a function for AJAX (return "xmlhttp")
function InitXmlHttp(){
	var xmlhttp;
	if (window.XMLHttpRequest){ // for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else{ // for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}

//send HTTP GET request to service
function sendRequest(xmlhttp, requestURL, requestBody, handler){
    xmlhttp.onreadystatechange = handler; // assign the function to parse the response
    xmlhttp.open("POST", requestURL, true );
    xmlhttp.setRequestHeader("Content-type","application/json");
    xmlhttp.send(requestBody);
}

function doUserLogin() {
	var email = document.getElementById("inputEmail").value;
	var userpassword = document.getElementById("inputPassword").value;
	
	var requestURL = service_url + "/users/userlogin";
	var requestbody = {
			"email": email,
			"password": userpassword
	}
	
	var xmlhttp = InitXmlHttp();
	sendRequest( xmlhttp, requestURL, JSON.stringify(requestbody), function(){doUserLoginHandler(xmlhttp)} );
}

function doUserLoginHandler(xmlhttp){
	if (xmlhttp.readyState==4 && xmlhttp.status==200){
		parseLoginInfo( xmlhttp.responseText ); // use parseGetThingResponse function to parse the JSON string
	}
}

function parseLoginInfo(login_string) {
	window.location.href = service_url + "/users/userlogin";
	var userInfo = JSON.parse(login_string);
	
	var user_firstname = userInfo["firstname"];
	var user_lastname = userInfo["lastname"];
	var user_email = userInfo["email"];
	var user_username = userInfo["username"];
}


