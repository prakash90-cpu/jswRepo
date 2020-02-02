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
			<b>${head} Business </b>
		</h3>
		<br />
		
	
		
		<form:form class="w3-container" commandName="BusinessGroupBean" method="POST"  action="/jsw-report/master/submitBgGroup" >
			


			
			<%-- <p>

				 Business Group
                  <select class=" w3-input w3-select w3-border" style="width:500px" name="businessGroup" >
					<option value="" disabled selected>---Select---</option>
           <c:forEach items="${ListBusiness}" var="value">
        <option value="${value}">${value}</option>
          </c:forEach>
          </select> 
         
			</p>
            <p>

				<c:forEach items="${Location}" var="value">
				
				 <input type="checkbox" name="location" value=" " class="w3-check" />${value} 
				</c:forEach>
			</p> --%>
			<p>
			<form:input type="hidden" path="id"  /></p>
            
			<p>

				<form:input path="businessGroup" class="w3-input w3-border"
					name="approver" type="text" placeholder="BusinessGroup Name" />
			</p>
          
            
			 <p>

				<form:input path="description" class="w3-input w3-border"
					name="approver" type="text" placeholder=" Description" />
			</p>
			<p>
                <c:choose>
    <c:when test="${BusinessGroupBean.getIsActive()=='Y'}">
 <input type="checkbox" name="isActive" value="Y" class="w3-check" checked
				 /> 
     </c:when> 
     <c:otherwise>
     
      <input type="checkbox" name="isActive" value="Y" class="w3-check"
				 /> 
     </c:otherwise>
        
  
</c:choose>
		      
				 
				 
				 isActive
			</p>
		
			
			<p>
			<input type="submit" value="${btn}" class="w3-btn w3-right">
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