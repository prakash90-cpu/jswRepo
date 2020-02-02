<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:javascript="http://www.w3.org/1999/xhtml">
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


	<br />
	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large" onclick="w3_close()"
		style="cursor: pointer" title="close side menu" id="myOverlay"></div>

	<!-- !PAGE CONTENT! -->
	<div class="w3-main" style="margin-left: 340px; margin-right: 40px">

	<!-- Top menu on small screens -->
	<%@ include file="Header.jsp"%>



	<!-- Photo grid (modal) -->
	<div class="w3-row-padding">


		<div id="myTableList">
			<div class="w3-container">
				<h3>
					<b>File Successfully Uploaded </b>
				</h3>
			</div>


			<br />
			<center>
				<a href="/jsw-report/mainController/fileList">
					<button class="w3-btn w3-left"
						style="width: 9%; height: 5%">Import</button>
				</a>
			</center>
		</div>



		<br />
		<br />

		<!-- End page content -->
	</div>

	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>