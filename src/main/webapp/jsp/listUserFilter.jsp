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
			<div id="myTableList">
			
					<h3 class="w3-text-blue">
						<b>User Filter List</b>
					</h3>
			
 
<table
					class="w3-table-all w3-hoverable w3-striped w3-bordeorange w3-border w3-small" id="table">
					<thead>
						<tr class="w3-pale-orange">
							<th>Id</th>
							
							<th>Username</th>
						<!-- 	<td>Functions</td> -->
							<th>Business</th>
								<th>Location</th>
							
							<th>Personal Area</th>
								<th>Payroll Area</th>
								
							
							<th>Action</th>
						</tr>

</thead><tbody>


						<c:forEach var="list" items="${userFilterList}">
							<tr class="">

								<td>${list.id}</td>
								
								<td>${list.userId}</td>
							<%-- 	<td>${list.functionFilter}</td> --%>
								<td>	<div  style='overflow:scroll; width:200px;height:100px;'>${list.businessFilter}</div></td>
								<td>	<div  style='overflow:scroll; width:200px;height:100px;'>${list.locationFilter}</div></td>
						
								<td>	<div  style='overflow:scroll; width:200px;height:100px;'>${list.personalAreaFilter}</div></td>
								<td>	<div  style='overflow:scroll; width:200px;height:100px;'>${list.payrollAreaFilter}</div></td>
								

								<td><a href="/jsw-report/mainController/editUserFilter/${list.id}"
									class="w3-margin-right"><i
										class="fa fa-edit fa-fw fa-2x w3-hover-text-orange"></i></a><%--  <a
									href="/jsw-report/mainController/deleteUserFilter/${list.id}"
									class=" w3-margin-right"><i
										class="fa fa-minus fa-fw fa-2x w3-hover-text-red"></i></a> --%></td>

							</tr>
						</c:forEach>
				</tbody></table>
<br />
				<!-- <center>
					<a href="/jsw-report/mainController/submitUserFilter"><button
							class="w3-btn w3-right" style="width: 20%">New</button></a>
				</center> -->
			</div>
<script>

  $("#table").DataTable();
</script>
			<br /> <br />
	
			<!-- End page content -->
		</div>
		
		
		
	<%@ include file="Footer.jsp"%>
	</div>
 
</body>
</html>