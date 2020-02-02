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
				<div class="w3-container">

					<h3 class="w3-text-blue">
						<b>User Filters</b>
					</h3>
				</div>
				
 <form:form commandName="FunctionAppBean" action="/jsw-report/mainController/submitUserFilter">				
	

<select  required name="userId" style="width:100%;height:7%" class="w3-select w3-border w3-hover-grayscale">

								
								<c:choose>
        <c:when test="${FunctionAppBean.getFunctions()=='n'}">
       <option disabled selected>Select Username</option>
    </c:when>  
  
    <c:otherwise>
        <option disabled>Select Username</option>
    </c:otherwise>
</c:choose> 
								
								<c:forEach items="${userList}" var="username">
								 <c:choose>
        <c:when test="${username==FunctionAppBean.getUserId()}">
       <option value="${username}" selected>${username}</option>
    </c:when>  
  
    <c:otherwise>
        <option value="${username}">${username}</option>
    </c:otherwise>
</c:choose> 
									
								
								</c:forEach>
							</select>
							<br />
							<br />
							
							
							
							
								<table class="w3-table w3-hoverable w3-striped w3-bordeorange ">
				
					<%int count=0; %>
					<c:forEach var="functionName" items="${functionTableUserFilter}">
			 			<%
						
			 		
						if(count%2==0){%> 
	<tr class="">
	
 <% }%> 
  
	<td><label>${functionName.name}</label></td>
	<td>
	<div  style='overflow:scroll; width:400px;height:200px;'>
	 <input type="checkbox" class="w3-check" onChange="checkAll(this)" value="ALL" name="ALL"> <label>ALL</label><br>
	 
	  <c:forEach items="${functionName.value}" var="value">
	  
	  
  <c:choose>
        <c:when test="${value.isActive=='y'}">
        <input type="checkbox" class="w3-check" value="${value.id}" name="${functionName.name}" checked> <label>${value.value}</label><br>

    </c:when>  
  
    <c:otherwise>
        <input type="checkbox" class="w3-check" value="${value.id}" name="${functionName.name}"> <label>${value.value}</label><br>

    </c:otherwise>
</c:choose> 
    
	 </c:forEach>
	 
	 </div>  

					</td>		
				    
		 	<% if(count%2==0){}
			else{%>
			
			</tr>
			
		<%} 	count++; %> 
	

			</c:forEach>		
				</table>
				<br />
	<br />
				<input type="submit"  class="w3-btn w3-right" style="width:15%" value="Submit">


	</form:form>						
							
							
							
							
			</div>

			<!-- End page content -->
		</div>
		
		
	<%@ include file="Footer.jsp"%>
	</div>
	<script type="text/javascript">
	function checkAll(thisVariable){
		
		if ($(thisVariable).is(':checked')) {

			
			$(thisVariable).siblings("input").prop('checked', thisVariable.checked);

			
			
		} else {

			$(thisVariable).siblings("input").prop('checked', false);

		}
	
	}
	
var countTD=1;
	$("table").find('td').each (function() {

		if(countTD%2==0){
			var checkVar=$(this).find("input[type='checkbox']:checked:not(:first-child)").length;
			var notCheckVar=$(this).find("input[type='checkbox']:not(:first-child)").length;
			//alert($(this).find("input[type='checkbox']:checked:not(:first-child)").length==$(this).find("input[type='checkbox'](:first-child)").length);
			  	
			if(checkVar==notCheckVar)
			$(this).find("input[type='checkbox']:first-child").prop('checked', true);
			
			
			
		}
		
		
		countTD++;
  	
  	
  
}); 
	
	</script>
 
</body>
</html>