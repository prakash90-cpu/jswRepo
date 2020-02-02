<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="include.jsp"%>

<%@ page isELIgnored="false"%>



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
						<b>DocumentType List</b>
					</h3>

				</div>
				<table
					class="w3-table-all w3-hoverable w3-striped w3-bordeorange w3-border w3-small">
					<thead>
						<tr class="w3-pale-orange">
							<td>Id</td>
							<td>Name</td>
							<td>IsActive</td>
							<td>Action</td>
						</tr>





						<c:forEach var="list" items="${documentTypelist}">
							<tr class="">

								<td>${list.id}</td>
								<td>${list.name}</td>
								<td>${list.isActive}</td>


								<td><a href="#" class="w3-margin-right"><i
										class="fa fa-edit fa-fw fa-2x w3-hover-text-orange"></i></a> <a
									href="#" class=" w3-margin-right"><i
										class="fa fa-minus fa-fw fa-2x w3-hover-text-red"></i></a></td>

							</tr>
						</c:forEach>
				</table>

				<br />
				<center>
					<a href="/jsw-report/mainController/newDocumentType">
						<button class="w3-btn w3-right" onclick="myForm3()"
							style="width: 25%">New</button>
					</a>
				</center>
			</div>



			<br />
			<br />



		</div>

	</div>



	<%@include file="Footer.jsp"%>
</body>
</html>