<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@include file="include.jsp"%>
   
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<script src="/jsw-report/js/manualExtraction.js"></script>

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
			<b>Extraction Process </b>
		</h3>
		<br />
		
		
		
<form:form class="w3-container" commandName="ExtractionBean" method="POST"  action="/jsw-report/externalSystems/submitExtraction">
	
	
	
			
<table class="w3-table w3-hoverable w3-striped  ">
          <%-- <form:input type="hidden" path="id"/> --%>
          <tr>
          
          
			 <td><label>MODULE
          <form:select class=" w3-input w3-select w3-border" path="module"
					id="moduleName"  style="width:300px" >
					<!-- onchange="getCaseType()" -->
					
					<option value="" disabled selected>---Select---</option>
           <c:forEach items="${FunctionList}" var="value">
        <option value="${value.functionName}">${value.shortName}</option>
          </c:forEach>
          </form:select>
         </label></td>
         
         
         <%-- <td><label>CASE_TYPE
          <form:select class=" w3-input w3-select w3-border" path="caseType"
					name="option"  style="width:300px">
					<option value="" disabled selected>---Select---</option>
           <c:forEach items="${caseTypes}" var="value">
        
          </c:forEach>
          </form:select>
         </label></td> --%>
         
          <td><label>PROGRESS_TYPE
          <form:select class=" w3-input w3-select w3-border" path="progressType"
					name="option"  style="width:300px">
					<option value="" disabled selected>---Select---</option>
          
        <option value="All">ALL</option>
         <option value="WIP">WIP</option>
          <option value="Completed">Completed</option>
         
          </form:select>
         </label></td>
         
         </tr>
			
			<tr>
			
         <td>
         <label>
Start Date</p>
				<form:input path="startDate" class=" w3-input w3-border"
					 type="date" placeholder="Start Date" style="width:300px"  />
		 </label></td>
         
         
			
			 <td><label>
Start Time
				<form:input path="startTime" class="w3-input w3-border"
					 type="time" placeholder=" Start Date"  style="width:300px" />
			</label></td>
         
			 </tr>
			 <tr>
			 
			  <td><label>
End Date
				<form:input path="endDate" class="w3-input w3-border"
					 type="date" placeholder=" End Date"  style="width:300px" />
			</label></td>
			
			 
			 <td><label>
End Time
				<form:input path="endTime" class="w3-input w3-border"
					 type="time" placeholder="End Time"   style="width:300px" />
			</label></td>
		


		 </tr>
		
		
		
			</table>	
			<input type="submit"  value="GO" class="w3-btn w3-right">
		</form:form>
		
       
		

		<br />
		<br />
		<%@ include file="Footer.jsp"%>

		<!-- End page content -->


	</div>
</body>



</body>
</html>