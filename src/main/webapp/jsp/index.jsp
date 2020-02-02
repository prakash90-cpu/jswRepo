<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>JSW</title>

<style>
.myMain {
	min-height: 100%;
	height: 200px;
	background-position: center;
	background-size: cover;
/* background-image: url("<c:url value='/img/jsw.jpg'/>");
 */	filter: alpha(opacity = 60);
	/* opacity: 0.09; */
}

#myForm {
	opacity: 1.0;
}

nav a {
	color: white;
}



</style>
</head>
<body class="w3-light-grey w3-content" style="max-width: 1600px"
	ng-app="myApp" ng-Controller="myCtrl">

	<!-- Sidenav/menu -->
	<nav class="w3-sidenav w3-collapse w3-Indigo w3-animate-left"
		style="z-index:3;width:300px;" id="mySidenav">
	<br>
	<div class="w3-container">
		<a href="#" onclick="w3_close()"
			class="w3-hide-large w3-right w3-jumbo w3-padding" title="close menu">
			<i class="fa fa-remove"></i>
		</a>
		<%--  <img src="<c:url value='/img/vw.jpg'/>" style="width:100%;height:25%" class="w3-round"><br><br>
   
   --%>
		<h2 class="w3-center">
			<b>JSW <h4>Global Business Solutions</h4></b>
		</h2>
		<br />
		<hr style="width: 100%; border: 3px solid white" class="w3-round">
		<br />
		<center><h4 class="w3-padding-0">
			<b>JSW-REPORTING</b>
		</h4></center>
		<p class="w3-text-grey"></p>
	</div>
	<a href="#search" onclick="w3_close()"
		class="w3-padding w3-border w3-round-xxlarge"><i
		class="fa fa-search fa-fw w3-margin-right"></i>SEARCH</a> <a href="#about"
		onclick="w3_close()" class="w3-padding w3-border w3-round-xxlarge"><i
		class="fa fa-male fa-fw w3-margin-right"></i>ABOUT</a> <a href="#contact"
		onclick="w3_close()" class="w3-padding w3-border w3-round-xxlarge"><i
		class="fa fa-envelope fa-fw w3-margin-right"></i>CONTACT</a> </nav>

	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large w3-animate-opacity"
		onclick="w3_close()" style="cursor: pointer" title="close side menu"
		id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
<div class="w3-main w3-border" style="margin-left: 340px; margin-right: 40px">
		<!-- Header -->
		<header class="w3-container" id="audiapp">

		<div class="w3-row">
			<br>

			<div class="w3-quarter w3-display-top">
				<span class="w3-opennav w3-hide-large w3-xxlarge w3-hover-text-grey"
					onclick="w3_open()"><i class="fa fa-bars"></i></span>
				<div class=" w3-hide-small">
					<img src="<c:url value='/img/logo.png'/>"
						style="width: 80%; height: 14%" class="w3-round">
						
					<h5 >
						JSW Global Business Solutions		
				</h5>
				
				</div>


			</div>
			<div class="w3-half">
				<center>
				<table><tr><td>	<h1 class="w3-text-Indigo w3-xxxlarge" ><i>
					<b>JSW</b></i>
				</h1></td><td><br>
				<h3 class="w3-text-blue"><b>Global Business Solutions</b></h3></td>
	</tr></table>
				</center>
			</div>

			<div class="w3-quarter w3-container">
				<img src="<c:url value='/img/logo.png'/>"
					style="width: 100%; height: 20%" class="w3-round w3-hide-small">
				<br /> <br />
				<p class="w3-center">
					<a title="LOGIN" class=" w3-hover-text-blue" onClick="showLogin()">Sign In</a>
				</p>
			</div>

		</div>



		</header>


		<div class="w3-row-padding myMain" id="myMain">

			<div class=" w3-container w3-margin-bottom myOpacity">
				<div class="w3-container ">
					<div class="w3-container w3-margin w3-text-blue w3-center" id="myHome">
						<h1 style="opacity:0.4">Welcome to DMS Reporting</h1>
						<span class="w3-text-red">${message}</span><br />
					</div>
					<form:form action="/jsw-report/login"
						class="w3-container w3-hide w3-white" method="post" id="loginForm"
						commandName="LoginBean">
						<h2 class="w3-text-blue">
							<b>Login</b>
						</h2>
						<br />
						<%--  <span class="w3-text-red">${message}</span><br/> --%>
						<form:input class="w3-input w3-text-blue" path="username"
							placeholder="Username" style="width:100%" />
						<br />


						<div class="w3-row">
							<div class="w3-col" style="width: 95%">
								<form:input type="{{inputType}}" class="w3-input w3-text-blue"
									path="password" placeholder="Password" id="passwordfield"
									style="width:100%" />
							</div>
							<div class="w3-col" style="width: 5%">
								<span class="glyphicon glyphicon-eye-open btn-lg w3-text-blue"
									id="myEye" ng-Click="hideShowPassword()"></span>
							</div>

						</div>
						<br>
						<input type="submit" value="Login" class="w3-btn">
						<br />	<br>
						<!-- <p class="message w3-hover-blue" onClick="myFunction()">
							Not registered? <a href="#">Create an account</a>
						</p> -->
					</form:form>
					
				</div>
			</div>


		</div>

			<%@ include file="Footer.jsp"%>
	</div>
	<script>
		function myFunction() {
			var x = document.getElementById("regisForm");
			if (x.className.indexOf("w3-show") == -1) {
				x.className += " w3-show";
			} else {
				x.className = x.className.replace(" w3-show", "");
			}

			var y = document.getElementById("loginForm");
			if (y.className.indexOf("w3-show") == -1) {
				y.className += " w3-show";
			} else {
				y.className = x.className.replace(" w3-show", "");
			}
		}
		function showLogin() {

			var y = document.getElementById("loginForm");
			if (y.className.indexOf("w3-show") == -1) {

				y.className += " w3-show";

			} else {

				y.className = y.className.replace(" w3-show", "");

			}
		
			var h = document.getElementById("myHome");

			if (h.className.indexOf("w3-hide") == -1) {

				h.className += " w3-hide";

			} else {

				h.className = h.className.replace(" w3-hide", "");

			}

		}
		function showRegis() {
			var x = document.getElementById("regisForm");
			if (x.className.indexOf("w3-show") == -1) {
				x.className += " w3-show";
			} else {
				x.className = x.className.replace(" w3-show", "");
			}

			var y = document.getElementById("loginForm");

			y.className = y.className.replace(" w3-show", "");

			var m = document.getElementById("myMain");

			m.className = m.className.replace(" myMain", "");
			var h = document.getElementById("myHome");

			h.className += " w3-hide";

		}
	</script>
	<script>
		var myApp = angular.module('myApp', []);
		myApp.controller('myCtrl', [ '$scope', function($scope) {

			var regis = '${regis}';
			if (regis == "yes") {
				var x = document.getElementById("regisForm");
				x.className = x.className.replace(" w3-hide", "");
				x.className += " w3-show";
				var y = document.getElementById("loginForm");
				y.className = x.className.replace(" w3-show", "");
				y.className += " w3-hide";
			}

			// Set the default value of inputType
			$scope.inputType = 'password';

			// Hide & show password function
			$scope.hideShowPassword = function() {
				if ($scope.inputType == 'password') {
					$scope.inputType = 'text';
					var x = document.getElementById("myEye");

					var y = document.getElementById("myEye2");

					x.className += " w3-green";
					y.className += " w3-green";

				} else {
					$scope.inputType = 'password';
					var x = document.getElementById("myEye");
					x.className = x.className.replace(" w3-green", "");
					var y = document.getElementById("myEye2");
					y.className = x.className.replace(" w3-green", "");

				}
			};

		} ]);
		
		
		if (window.chrome && window.chrome.webstore) {

}
else{
alert("Our Portal Support Only Google Chrome !!");
}
	</script>


</body>
</html>