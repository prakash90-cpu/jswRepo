<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include.jsp"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>JSW</title>



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

	<!-- Top menu on small screens -->

	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large" onclick="w3_close()"
		style="cursor: pointer" title="close side menu" id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
	<div class="w3-main" style="margin-left: 340px; margin-right: 40px">
	<%@ include file="Header.jsp"%>
		<!-- Header -->
		<div class="w3-container" style="margin-top: 0px" id="showcase">
			<br />

		</div>

		<!-- Photo grid (modal) -->
		<div class="w3-row-padding">
		
				<div class="w3-container">

					<h3 class="w3-text-blue w3-center">
						<b>Select Display By</b>
					</h3>
				</div>
		
		
			
  			 <c:choose>
        <c:when test="${not empty insert}">
        <span> Inserted Successfully. <br><br><br>
        
        <a href="/jsw-report/mainController/displayBy"><button class="w3-btn w3-left">Add</button></a><br><br><br><br>
            </c:when>    <c:otherwise>
				<form:form action="/jsw-report/mainController/displayBy" method="POST" commandName="displayBy">
				<div>
			
	<p>

			 <label>Setting Name</label>	<form:input path="settingName" class=" w3-border"
					 type="text" placeholder="" />
			</p>			
				
  <div><label >Display By :</label></div>
  <div style="margin-left:10%">
  <p><input type="radio" name="settingValue" value="0" class="w3-radio">  <label>Both</label></p>  
  <p><input type="radio" name="settingValue" value="1" class="w3-radio">  <label>Statistical</label></p>
  <p><input type="radio"  name="settingValue" value="2" class="w3-radio">   <label>Visual Graph</label><br>
  </p>
  
  
  </div>

 

	  <p><label style="width:10%">From Date</label><form:input path="fromDate" class=" w3-border"
					 type="date" placeholder="" />
			</p>

			
			<p>

			<label style="width:10%">To Date</label>		<form:input path="toDate" class=" w3-border"
					 type="date" placeholder="" />
			</p>
  
		<p>
         

     
      <input type="checkbox" name="isActive" value="Y" class="w3-check"
				 /> 	 
				 isActive
			</p>
  
</div>
  
  <input type="submit" class="w3-btn w3-right" value="Go"><br><br>
  </form:form></c:otherwise></c:choose>
			<!-- End page content -->

		
		
		
	<%@ include file="Footer.jsp"%>
	</div>

	

</body>
</html>