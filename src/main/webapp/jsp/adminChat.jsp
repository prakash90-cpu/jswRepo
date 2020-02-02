<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<title>Simple Chat</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
body, h1 {
	font-family: "Raleway", Arial, sans-serif
}

h1 {
	letter-spacing: 6px
}

.w3-row-padding img {
	margin-bottom: 12px
}
/* .popup-box {
  
    display: none;
} */
.popup-box-on {
	display: block !important;
}

.popup-box {
	display: none;
}

.chat {
	list-style: none;
	margin: 0;
	padding: 0;
}

.chat li {
	margin-bottom: 10px;
	padding-bottom: 5px;
	border-bottom: 1px dotted #B3A9A9;
}

.chat li.left .chat-body {
	margin-left: 60px;
}

.chat li.right .chat-body {
	margin-right: 60px;
}

.chat li .chat-body p {
	margin: 0;
	color: #777777;
}

.panel .slidedown .glyphicon, .chat .glyphicon {
	margin-right: 5px;
}

.panel-body {
	overflow-y: scroll;
	height: 250px;
}

::-webkit-scrollbar-track {
	-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
	background-color: #F5F5F5;
}

::-webkit-scrollbar {
	width: 12px;
	background-color: #F5F5F5;
}

::-webkit-scrollbar-thumb {
	-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
	background-color: #555;
}

.panel_footer {
	display: absolute;
}
</style>

<link rel="stylesheet" type="text/css" href="css/style.css">


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Karma">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="script.js"></script>
<script src="js.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css"
	rel="stylesheet" type="text/css" />


<script>
	$(document).ready(function() {

		$(function() {
			$("#divResize").draggable().resizable({
				grid : [ 1, 10000 ]
			});
		});

		var app = angular.module("myApp", [])

		addMainController(app);

	});
</script>
</head>

<body ng-app="myApp" ng-Controller="myCtrl">
	<%@ include file="Navigation.jsp"%>




	<!-- Top menu on small screens -->
	<%@ include file="Header.jsp"%>
	<br />
	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large" onclick="w3_close()"
		style="cursor: pointer" title="close side menu" id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
	<div class="w3-main" style="margin-left: 340px; margin-right: 40px">

		<div class="w3-container" style="" id="showcase"></div>

		<!-- Photo grid (modal) -->
		<div class="w3-row-padding">


			<div class="w3-btn-bar w3-border w3-show-inline-block" id="chatRooms">

			</div>

			</header>



			<div id="myChatWindows"></div>



			<!-- End Page Content -->
		</div>
	</div>
	<!-- Footer -->
	<%@include file="Footer.jsp"%>

	<script>
		function addMainController(app) {

			var stringNext;

			app.controller('myCtrl',function($scope, $http, $interval, $compile,$injector, $q) {

								var deferred = $q.defer();
								$scope.addClass = function(aId) {

									var qnimate = angular.element(document
											.querySelector("#qnimateid" + aId));
									qnimate.toggleClass('popup-box-on');
								};

								function getChatWindow() {

									$http
											.get(
													"/LAPP_Rest_App/rest/lappChat/getChatWindow")
											.success(
													function(data, status,
															headers, config) {

														var newChatWin = "";
														stringNext = data;
														var ChatRoomWin = "";
														for (var i = 0; i < data.length; i++) {

															newChatWin += "<my-directive temp='id"+data[i][0]+"' id='"+data[i][0]+"' name='"+data[i][1]+"'></my-directive>";

															ChatRoomWin += "<a ng-model='anchor"
																	+ data[i][0]
																	+ "' href='#' class='w3-btn w3-text-blue w3-hover-purple w3-circle' ng-click='addClass("
																	+ data[i][0]
																	+ ")'>Chat Room<p class='w3-tiny'>"
																	+ data[i][1]
																	+ "</p></a>";

														}

														var myEl1 = angular
																.element(document
																		.querySelector("#myChatWindows"));

														var compiledString = $compile(
																angular
																		.element(newChatWin))
																($scope);
														myEl1
																.append(compiledString);

														var myChRm = angular
																.element(document
																		.querySelector("#chatRooms"));
														var compiledString2 = $compile(
																angular
																		.element(ChatRoomWin))
																($scope);
														myChRm
																.append(compiledString2);

													});

									$http
											.get(
													"/LAPP_Rest_App/rest/lappChat/getDeActivated")
											.success(
													function(data, status,
															headers, config) {

														for (var k = 0; k < data.length; k++) {

															$(
																	'[ng-model="anchor'
																			+ data[k]
																			+ '"]')
																	.addClass(
																			'w3-hide');

														}

													});

								}

								$scope.callMe = function() {
									getChatWindow();

								}
								$interval(function() {
									$scope.callMe();
								}, 3000);

							});

			app
					.directive(
							'myDirective',
							function() {

								return {
									restrict : 'E',
									scope : {
										temp : '@',
										id : '@',
										name : '@'
									},
									template : "<div class='container popup-box chat-popup' id='qnimate{{temp}}'>"
											+ "<div class='row'>"
											+ "<div class='col-md-5'>"
											+ "<div class='panel panel-primary' ><!--   id='divResize' --> "
											+ "<div class='panel-heading' >"
											+ "<span class='glyphicon glyphicon-comment'></span> Customer Chat <span class='w3-text-green'>username:{{name}}</span>"
											+ "<div class='btn-group pull-right'>"
											+ "<button type='button' class='btn btn-default pull-right btn-xs dropdown-toggle' data-toggle='dropdown'>"
											+ "<span class='glyphicon glyphicon-chevron-down'></span>"
											+ "</button>"
											+ " <ul class='dropdown-menu slidedown'>"
											+ "  <li><a href='#'><span class='glyphicon glyphicon-refresh'>"
											+ " </span>Refresh</a></li>"
											+ " <li><a href='#'><span class='glyphicon glyphicon-ok-sign'>"
											+ " </span>Available</a></li>"
											+ " <li><a href='#'><span class='glyphicon glyphicon-remove'>"
											+ " </span>Busy</a></li>"
											+ " <li><a href='#'><span class='glyphicon glyphicon-time'></span>"
											+ "Away</a></li>"
											+ "<li class='divider'></li>"
											+ " <li><a><span class='glyphicon glyphicon-off'></span>"
											+ "Close chat</a></li>"
											+ " </ul>"
											+ " </div>"
											+ "</div>"
											+ " <div class='panel-body'>"
											+ "<ul class='chat' id='{{temp}}'>"
											+

											"</ul>"
											+ " </div>"
											+ " <div class='panel-footer'>"
											+ "<div class='input-group'>"
											+ " <input id='abcdef' type='text' ng-model='msg' class='form-control input-sm' placeholder='Type your message here...' />"
											+ "<span class='input-group-btn'>"
											+ "<button class='btn btn-warning btn-sm' id='btn-chat' ng-click='sendMessage()'>"
											+ " Send</button>"
											+ "</span>"
											+ "</div>"
											+ "</div>"
											+ "</div>"
											+ "</div>" + "</div></div>",
									controller : [
											'$scope',
											'$http',
											'$interval',
											function($scope, $http, $interval) {

												$scope.sendMessage = function() {

													var temp = " "
															+ new Date()
																	.getHours()
															+ ":"
															+ new Date()
																	.getMinutes();

													var newMssage = "<li class='left clearfix bg-success w3-round-xlarge' style='margin-left:30%'><span class='chat-img pull-left'></span>"
															+ "<div class='chat-body clearfix'>"
															+ "<div class='header'>"
															+ " <small class='pull-right text-muted'>"
															+ "<span class='glyphicon glyphicon-time'>"
															+ temp
															+ " </span></small>"
															+ "</div><br/>"
															+ " <p>"
															+ $scope.msg
															+ "</p>"
															+ "</div>"
															+ "</li>";

													$http
															.get(
																	"/LAPP_Rest_App/rest/lappChat/setCustomerMessage/"
																			+ $scope.msg
																			+ "/"
																			+ $scope.id)
															.success(
																	function(
																			data,
																			status,
																			headers,
																			config) {

																	});

													var myEl1 = angular
															.element(document
																	.querySelector("#id"
																			+ $scope.id));
													myEl1.append(newMssage);

													$scope.msg = "";
												};

												var myIsActive = "1";

												$scope.callMe = function() {

													$http
															.get(
																	"/LAPP_Rest_App/rest/lappChat/getMessage/"
																			+ $scope.id)
															.success(
																	function(
																			data,
																			status,
																			headers,
																			config) {

																		for (var i = 0; i < data.length; i++) {

																			var temp = " "
																					+ new Date()
																							.getHours()
																					+ ":"
																					+ new Date()
																							.getMinutes();

																			var Message = "<li class='right clearfix bg-danger w3-round-xlarge' style='margin-right:30%'><span class='chat-img pull-right'></span>"
																					+ "<div class='chat-body clearfix'>"
																					+ "<div class='header'>"
																					+ " <small class='text-muted'>"
																					+ "<span class='glyphicon glyphicon-time'>"
																					+ temp
																					+ "</span></small>"
																					+ "</div>"
																					+ " <p>"
																					+ data[i][0]
																					+ "</p>"
																					+ "</div>"
																					+ "</li>";

																			var myEl1 = angular
																					.element(document
																							.querySelector("#id"
																									+ $scope.id));
																			myEl1
																					.append(Message);

																		}

																	});

													$http
															.get(
																	"/LAPP_Rest_App/rest/lappChat/getDeActivated/"
																			+ $scope.id)
															.success(
																	function(
																			data,
																			status,
																			headers,
																			config) {

																		myIsActive = data;

																	});

												}

												$interval(function() {
													if (myIsActive == "1")
														$scope.callMe();

												}, 3000);

											} ]

								}
							});

		}
	</script>
</body>
</html>
