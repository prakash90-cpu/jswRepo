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
			<b>Edit Business Group Location </b>
		</h3>
		<br />
		
	

	
		
		<form:form class="w3-container" commandName="BusinessGroupLocBeanByID" method="POST"  action="/jsw-report/master/updateBusinessGR" >
			


			
			<p>
<b>
				 Business Group</b>
				
			
				
					
                  <select class=" w3-input w3-select w3-border" style="width:500px" name="businessId" >
					<option value="" disabled selected>---Select---</option>
					
           <c:forEach items="${BusinessGroupBean}" var="value">
               <c:choose>
            <c:when test="${BusinessGroupLocById.getBusinessGroup()==value.businessGroup}">
        <option value="${value.id}" selected>${value.businessGroup}</option>
          </c:when>
           <c:otherwise>
            <option value="${value.id}">${value.businessGroup}</option>
            </c:otherwise>
            </c:choose>
          </c:forEach>
        
          
          
          </select> 
         
			</p>
            <p>
       
				<c:forEach items="${LocationList}" var="value">
				
		 <form:checkbox path="locId" value="${value.locationId}" class="w3-check" />${value.locationName} <br /> 
				</c:forEach>
			</p>  
			
					<p>
			<input type="submit" value="${btn}" class="w3-btn w3-right">
			</p>
				
		</form:form>
		
       


		<br />
		<br />
		<%@ include file="Footer.jsp"%>

		<!-- End page content -->


	</div>
</body>



</body>
</html>