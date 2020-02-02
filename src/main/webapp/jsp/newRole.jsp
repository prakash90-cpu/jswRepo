<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include.jsp"%>

<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>JSW</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
	
<script>

	$(document).ready(function() {

		var selectedRoles=','+'${screenRole.getScreenId()}'+',';
		
	//	alert(selectedRoles);
					 	var main = ${jsonArray};

					
						var checkString = "";

						$.each(main,function(key, value) {

											$.each(value,function(key1,value1) {

																$.each(value1,function(key2,value2) {

																					$.each(value2,function(key3,value3) {
																										var ab;
																										var ac;
																										$.each(value3,function(key4,value4) {

																															if (key4 == "1zid") {

																																checkString = "<ul><li class=il"
																																		+ value4
																																		+ " onClick=fun('il"
																																		+ value4
																																		+ "')>";
																																checkString += "<input type='checkbox' value="+value4+" name='screenId' id='cap"
																																		+ value4
																																		+ "cap'";
																															//	alert(selectedRoles.indexOf(value4));
																																if(selectedRoles.indexOf(","+value4+",") != -1)
																																{
																																	checkString+=" checked='checked' "
																																}
																																
																																ac = value4;
																															}

																															if (key4 == "2pid") {
																																checkString += " class='cap"+ value4+ "cap w3-check'";
																																ab = value4;

																															}

																															if (key4 == "3value") {
																																checkString += "/><label for='cap"+ac+"cap' class='w3-validate'>"
																																		+ value4;
																																checkString += "</label></li></ul>";
																																
																																$(".il"+ ab).append(checkString);
																														
																															}

																														});

																									});

																				});

															});

										});

						$('input[type="checkbox"]').change(
								function() {
									
						
						
									if ($(this).is(':checked')) {

										$(this).siblings('ul').find(
												"input[type='checkbox']").prop(
												'checked', this.checked);

										$(this).parent().parent().parent().children().prop(
												'checked', this.checked);
										$(this).parent().parent().parent().parent().parent().children().prop(
												'checked', this.checked);	
										
									} else {

										$(this).siblings('ul').find("input[type='checkbox']").prop(
												'checked', false);
										

									if($(this).parent().parent().parent().children().find("input[type='checkbox']:checked").length==0){
									$(this).parent().parent().parent().children().prop(
												'checked', false);}
									
									if($(this).parent().parent().parent().parent().parent().children().find("input[type='checkbox']:checked").length==0){
										$(this).parent().parent().parent().parent().parent().children().prop(
												'checked', false);
									
									}
									}
									
									
									

								});

					

						$('ul li').click(function() {

							
							$(this).children().show();

							

						});

						var showChar = 30;
						var ellipsestext = "...";
						var moretext = "more";
						var lesstext = "less";
						$('.more')
								.each(
										function() {
											var content = $(this).html();

											if (content.length > showChar) {

												var c = content.substr(0,
														showChar);
												var h = content.substr(
														showChar - 1,
														content.length
																- showChar + 1);

												var html = c
														+ '<span class="moreellipses">'
														+ ellipsestext
														+ '&nbsp;</span><span class="morecontent"><span>'
														+ h
														+ '</span>&nbsp;&nbsp;<a href="" class="morelink">'
														+ moretext
														+ '</a></span>';

												$(this).html(html);
											}

										});

						$(".morelink").click(function() {
							if ($(this).hasClass("less")) {
								$(this).removeClass("less");
								$(this).html(moretext);
							} else {
								$(this).addClass("less");
								$(this).html(lesstext);
							}
							$(this).parent().prev().toggle();
							$(this).prev().toggle();
							return false;
						});

					});

	function fun(x) {

		var temp = window.getComputedStyle(document.querySelector('.' + x),
				':before');

	}
	
	

	
	
	
</script>

<style>
ul {
	list-style: none;
}

ul li:before {
	content: "\27A4 \0020";
}
/* ul li:after {
		content: "\25bc \0020";
	     }	 	     
	      */
a.morelink {
	text-decoration: none;
	outline: none;
	color: green;
}

.morecontent span {
	display: none;
}

.comment {
	width: 400px;
	margin: 10px;
}
</style>
</head>
<body class="w3-light-grey w3-content" style="max-width: 1600px" ng-app="myApp" ng-controller="myCtrl">

	<!-- Sidenav/menu -->
	<%@include file="Navigation.jsp"%>

	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large w3-animate-opacity"
		onclick="w3_close()" style="cursor: pointer" title="close side menu"
		id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
<div class="w3-main w3-border" style="margin-left: 340px; margin-right: 40px">
		<!-- Header -->
		<%@include file="Header.jsp"%>

		<div class="w3-row-padding w3-border w3-border-blue" id="myMain">

		
					<div
						class="w3-container w3-padding">
						 <b>Add Role</b> <br /> <br />
						<form:form class="w3-container"
							action="/jsw-report/mainController/screenAssign" method="post"
							commandName="screenRole">

	<form:input path="id" type="hidden" />

							<form:input path="roleName" class="w3-input w3-border"
								placeholder="Role Name" style="width:100%" />
							<br />
							<form:textarea rows="5" cols="30" path="roleDescription"
								placeholder="Role Description" style="width:100%"></form:textarea>
							<br />
							<br />
							<p>
								<label class="w3-validate">is Active?</label>
								<form:checkbox class="w3-check" value="y" path="isActive"/>
							</p>
							<label><h4>Select screens</h4></label>
							<br />
							<br />
							<div class="ilnull"></div>
						
							<input type="submit" value="Submit" ng-Click="check()"
								class="w3-btn w3-hover-blue w3-right" >
							<br />
						</form:form>
					</div>
				</div>
		
		<!-- Footer -->
		<%@include file="Footer.jsp"%>

	</div>

</body>

</html>