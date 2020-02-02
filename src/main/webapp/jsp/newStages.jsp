<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@include file="include.jsp"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSW REPORT</title>
<style>
body, h1, h2, h3, h4, h5 {
	font-family: "Poppins", sans-serif
}

body {
	font-size: 16px;
}

.w3-half img {
	margin: 25px;
	opacity: 0.8;
	cursor: pointer;
	height: 180px;
	width: 50px;
}

.w3-half img:hover {
	opacity: 1
}

.myheader {
	margin-left: 300px;
}
</style>
</head>
<body ng-App="myApp" ng-Controller="myCtrl">


	<!-- Sidenav/menu -->
	<%@ include file="Navigation.jsp"%>



	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large" onclick="w3_close()"
		style="cursor: pointer" title="close side menu" id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
	<div class="w3-main" style="margin-left: 300px;">

	<!-- Top menu on small screens -->
	<%@ include file="Header.jsp"%>

		<!-- Photo grid (modal) -->


		<h3>
			<b>${head} Stages </b>
		</h3>
		<br />
		<form:form class="w3-container" commandName="StagesBean" method="POST"  action="/jsw-report/master/submitStage" >
			
<p>
 <form:input type="hidden" path="id"/></p>
			
			<p>
			Function:

				<form:input path="function" class="w3-input w3-border"
					 type="text" placeholder="Function"  />
			</p><br />
           

				<p>
  Process:
				<form:input path="process" class="w3-input w3-border"
					 type="text" placeholder=" Process"  />
			
			</p><br />
            
		

				<p>
StageName:
				<form:input path="stageName" class="w3-input w3-border"
					 type="text" placeholder=" StageName"  />
			</p><br />
			<p>
Stage No:
				<form:input path="stage" class="w3-input w3-border"
					 type="number" placeholder=" Stage No"  />
			</p><br />	
		
                
		      <p>
QueName:
				<form:input path="queName" class="w3-input w3-border"
					 type="text" placeholder=" QueName"  />
			</p><br />
			
		
		
	
		
		<p>
StepName:
				<form:input path="stepName" class="w3-input w3-border"
					 type="text" placeholder=" StepName"  />
			</p><br />
		
		<p>
		
		<p>
Response:
				<form:input path="response" class="w3-input w3-border"
					 type="text" placeholder=" AliasName"  />
			</p><br />
AliasName:
				<form:input path="aliasName" class="w3-input w3-border"
					 type="text" placeholder=" AliasName"  />
			</p><br />
		
		<p>
			  Select your favorite color:
  <form:input type="color" path="color"  value="" />
			</p>
		
			
			<p>
			<input type="submit"  value="Submit" class="w3-btn w3-right">
			</p>
   
				
				
		</form:form>
		
       
		<!-- Modal for full size images on click-->

		<br />
		<br />
		<%@ include file="Footer.jsp"%>

		<!-- End page content -->


	</div>
</body>



</body>
</html>