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
<!-- <meta http-equiv="refresh" content="15"> -->
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
						<b>Extraction Progress</b>
					</h3>

				</div>
				<br>
				
		<form:form  commandName="DateCreationBean" method="POST"  action="/jsw-report/externalSystems/submitDateCreated" >		

         
         	
          
			 <label>Date Created
      <select class=" w3-input w3-select w3-border" style="width:500px" name="dateCreated" id="dateCreated" >
					<option value="" disabled selected>---Select---</option>
					 <option value="All">All</option>
           <c:forEach items="${ProgressDetail}" var="value">
        <option value="${value.startDate}">${value.startDate}</option>
          </c:forEach>
          </select> 
         </label>
			
			<input type=Button  value="GO" class="w3-btn w3-right" onClick='getTime()' />
			
			</form:form>
			<br /><br />
				
				<table
					class="w3-table-all w3-hoverable w3-striped w3-bordeorange w3-border w3-small">
					<thead>
						<tr class="w3-pale-orange">
							<td>PROGRESS ID</td>
							<!-- <td>PROCESS NAME</td> -->
							<td>FUNCTION</td>
							<!-- <td>SYSTEM</td> -->
							
							
							<td>PROCESS START DATE</td>
							<!-- <td>PROCESS START TIME</td> -->
							<td>PROCESS COMPLEATED DATE</td>
							<!-- <td>PROCESS COMPLEATED TIME</td> -->
							<td>PROCESS STATUS</td>
							<!-- <td>PERCENTAGE COMPLEATED......</td> -->
							
						</tr>

  
</thead><tbody>

						<c:forEach var="list" items="${ProgressDetail}">
							<tr class="">

								<td>${list.progressId}</td>

								<td>${list.moduleName}</td>
								

								<%-- <td>${list.shortName}</td> --%>

								<%-- <td>${list.shortName}</td> --%>

								<td>${list.startDate}</td>
								<%-- <td>${list.startTime}</td> --%>
								<td>${list.endDate}</td>
								<%-- <td>${list.endTime}</td> --%>
								<td>${list.status}</td>
								<%-- <td>${list.processName}</td> --%>
							 <%-- <td>${list.triggeredDO.function}</td> 
								<td>${list.triggeredDO.systems}</td> 
								
								<td>${list.processStartDate}</td>
								<td>${list.processCompleatedDate}</td>
								<td>${list.processStatus}</td>
								<td>${list.percentageCompleated}</td> --%>
								

							</tr>
						</c:forEach>
						</tbody>
				</table>

				<br />
				<!-- <center>
					<a href="#">
						<button class="w3-btn w3-right" onclick="myForm3()"
							style="width: 25%">Back</button>
					</a>
				</center> -->
			</div>



			<br />
			<br />
<script type="text/javascript">

	
 //response.setIntHeader("Refresh", 60);
 
 setInterval(function(){window.location="/jsw-report/externalSystems/viewExtraction"; }, 8000);
 

</script>


<script>
		function getTime(){
			
			var dateSelected=$("#dateCreated").prop('selectedIndex');
			
			
			
			
			$('table > tbody  > tr').each(function() {
				$(this).show();
				
				 if(dateSelected==1){
						$(this).show();} 
				
				 else{
				if($(this).index()==dateSelected-2)
					{}
				else
					$(this).hide();
				}
				
});
			
			
		}
		</script>


		</div>
	<%@include file="Footer.jsp"%>
	</div>




</body>
</html>