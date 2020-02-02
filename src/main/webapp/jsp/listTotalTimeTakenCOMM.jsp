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
#chartContainer {
   
     overflow-y:scroll; 
    position:relative;
   
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
		
<div class="w3-container w3-border w3-round-xxlarge">
<h2 class="w3-center w3-xxlarge"><i>Commercial</i></h2>
					<h4 class="w3-text-blue">
							<b>Report 5- Total cycle time taken to complete the process (End Time)
</b>
					</h4>
<form:form commandName="FunctionAppBean" action="/jsw-report/CommController/listTotalCycleTimeCOMM">				
					<table
					class="w3-table w3-hoverable w3-striped w3-bordeorange ">
					
					<tr >
					<td><lable><b>Business Group</b></lable>
					</td><td>
					<select id="businessGroup" name="businessGroup"  style="width:50%">
					<c:if test = "${fn:length(groupList)>1}">
					 <option value="ALL" selected>ALL</option>  
					 </c:if>
				<c:forEach items="${groupList}" var="value">
				
               
                <c:choose>
       <c:when test="${ FunctionAppBean.getBusinessGroup()==value }">
         <option value="${value}" selected>${value}</option>   
    </c:when> 
   
    <c:otherwise>
         <option value="${value}">${value}</option>
   
    </c:otherwise>
</c:choose>
        
                
				</c:forEach>
				</select>
					</td>
				</tr>	
				
					<%int count=0; %>
					<c:forEach var="functionName" items="${functionTable}">
			 			<%
						
						
						if(count%2==0){%> 
	<tr class="">
	
 <% }%> 
	<td><label>${functionName.name}</label></td>
	
	
	<td>
<select name="${functionName.name}"  style="width:50%">
<c:choose>
    <c:when test="${functionName.name!='DisplayBy'}">
 <option value="ALL">ALL</option>
     </c:when>    
  
</c:choose>
    <c:forEach items="${functionName.value}" var="value">
    
    <c:choose>
        <c:when test="${FunctionAppBean.getBusiness()=='null'}">
        <option value="${value.value}" >${value.value}</option>
    
    </c:when> 
  <c:when test="${value.value==functionName.selectedValue}">
        <option value="${value.value}" selected="selected"  >${value.value}  </option>
    
    </c:when>    
    <c:otherwise>
        <option value="${value.value}" >    ${value.value}</option>
    
    
    </c:otherwise>
</c:choose>
       
    </c:forEach>
</select>
					</td>		
				
		 	<% if(count%2==0){}
			else{%>
			
			</tr>
			
		<%} 	count++; %> 
	
<c:choose>
   <c:when test="${functionName.name=='Location'}">
        <tr><td><label>Date From </label></td><td><form:input  style="width:50%" type="date" id="dateFrom"  onChange="setToDate(this)" path="fromDate" /> (mm/dd/yyyy)</td>
       <td><label>Date To </label></td><td><form:input  style="width:50%" type="date"  path="toDate" id="dateTo" readonly="true"/> (mm/dd/yyyy)</td></tr>
    </c:when>    
  
</c:choose>
			</c:forEach>	
				
				</table>
				<br />
				<input type="submit" onClick="checkAllSelectedOption()" class="w3-btn w3-right" style="width:15%" value="Go">
</form:form> 
		

			<br /> <br />	</div>
			
	   
		                 <c:choose>
        <c:when test="${not empty listCasesCreated}">
        
		   
		    <p class="w3-right currentDate">${currentDate}</p>
		    
		    		<c:if test="${settingValue==0}"> 
		    <div class="w3-left"><label>Tabular</label>	
		<input type="radio" onChange="$('#listCaseCreated').show();$(' #chartContainer').hide();" class="w3-radio" style="padding-right:20px" checked name="graph">
		<label>Graphical</label>	
		<input   onChange="$('#listCaseCreated').hide();$('#chartContainer').show();graphTotalCycleTimeTaken();" class="w3-radio" type="radio" name="graph">	</div>	
		</c:if>
		
		
		<br /> <br /> <br />
		<center> 
<!-- 		 <h5 style="color:green">Title : Total Time Taken</h5>
 -->		<div id="chartContainer" style="height: 80%; width: 70%;display:none">
  </div></center>
		    
		    
		  
		    
 <table id="listCaseCreated"
					class="w3-table-all w3-hoverable w3-striped w3-border  w3-center">
					<thead>
						<b><tr class="w3-center " >
						
						
							<td class="w3-center" style="background-color: #004d99;color:white">Stage Name</td>
						
						
						<c:if test="${not empty listPeriod}"> 
						
						 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
    
					
					
					<c:forEach items="${listPeriod}" var="value">
					
						<td class="w3-center" style="background-color: #004d99;color:white">${value}</td>
						</c:forEach>
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         				
					<c:forEach items="${listPeriod}" var="value">
					
						<td class="w3-center" style="background-color: #004d99;color:white">${value}</td>
						</c:forEach>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   <c:forEach items="${listPeriod}" var="value">
					
						<td class="w3-center" style="background-color: #004d99;color:white">${value}</td>
						</c:forEach>
    </c:when>  
    
    
    </c:choose>
    </c:if>
								
						<td class="w3-center" style="background-color: #004d99;color:white">Total Time (HOUR)
						
						<span  onclick="getCaseCreatedAll('listCaseCreated', 'Total Cycle time to complete the process')" class="w3-badge fa fa-download  w3-right w3-hover-blue"></span>
				
						
						
						</td>
							
						</tr></b>


						<c:forEach var="listCases" items="${listCasesCreated}">
							<tr class="roleRow" aliasName="${listCases.alias}">

					
							<td class="" >${listCases.name}</td>
							
								 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
		<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.day1}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.day2}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.day3}</a></td>
													<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.day4}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.day5}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.day6}</a></td>
						
							<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.day7}</a></td>
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
   		<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.week1}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.week2}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.week3}</a></td>
													<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.week4}</a></td>
											
											
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   		<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.period1}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.period2}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getTotalCycleTimeCOMM',0);"><a>${listCases.period3}</a></td>
    </c:when>  
    
    
    </c:choose>
					
								<td class="w3-center w3-text-blue">
								
								<u class="w3-hover-blue w3-hover-text-black"  onclick="getCaseStudiedTableByRole(this,'getTotalCycleTimeCOMM',0);">${listCases.count}</u>
								
								<span onclick="getCaseStudiedTableByRole(this,'getTotalCycleTimeExcelCOMM',1);" class="fa fa-download generateXLS w3-right"></span>
								</td>
								

							</tr>
						</c:forEach>
						
					<%-- 	<tr class=" w3-center w3-medium" >
						
						
							<td class="w3-center" style="background-color: black;color:white">Grand Total</td>
						
						
						
  			
  			
  			 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
          <td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Day1</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Day2</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Day3</td>
  				<td class="w3-center"  style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Day4</td>   
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Day5</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Day6</td>
  				<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Day7</td>
						
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         <td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Week1</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Week2</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Week3</td>
  				<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Week4</td>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
      <td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Month1</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Month2</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);">Total of all Month1</td>
    </c:when>  
    
    
    </c:choose>
  			
  			
  			
    			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotal(this,'getTotalCycleTimeCOMM',0);">Total of all column on totals
    			
    			<span onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM',0);" class="fa fa-download generateXLS w3-right"></span>
    			</td>
							
						</tr> --%>
						
				</table><br><br>

</c:when>
<c:when test="${listCasesCreated ne null}">
<br>
<div class='w3-container  w3-border w3-round-xxlarge '>

<br>
<h3 class='w3-center'>No output</h3>
<br><br>
</div>
</c:when>
</c:choose> 
			<!-- End page content -->
		</div>
		
		
	<%@ include file="Footer.jsp"%>
	</div>
<c:set var="locationName" value="${FunctionAppBean.getLocation()}"/>
	<c:set var="businessName" value="${FunctionAppBean.getBusiness()}"/>
	
<%@include file="webservicesAjax.jsp"%>
<script>

//$('[name="CaseType"]').html("<option>COM_PRTOPO</option>");


 
 
  $(function(){

	    $('.generateXLS').click(function(){
	    
	    	//alert("xls");
	  	//  alert("generate");
	        var url='data:application/vnd.ms-excel,' + encodeURIComponent($('#caseTable').html()) 
	       // alert(url);
	        location.href=url
	        return false
	    })
	})
 


  
  function getCaseStudiedTableByRole(thisVariable,ajaxUrl,download){
		$("#caseTable").html("");
		 // alert("table");
		var stepName=$(thisVariable).closest('tr').children('td:first').text();

	  var json="{";
	  json+="'StageName':'"+stepName+"',";
	  
	  //var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
	  json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
		var jsonKeyName=$(this).attr("name");
		$(this).children("option:selected").each(function(){
			var tempOptValue="";
			 if($(this).val()=="ALL"){ 
			 
				if(dropdownsCount==2||dropdownsCount==3){
				 $(this).parent().children("option").not(':first').each(function(){
					if(dropdownsCount!=5)
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
					else{
						tempOptValue+="'"+$(this).val()+"',";
		
					}
				
				 })  
		 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
		 
		 }
		 else
		 tempOptValue="";
			 }
			 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=7) {
				 tempOptValue+="'"+($(this).val().split("-"))[0]+"',"
				 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
			 }
			 else{
				 tempOptValue+=$(this).val();
				 
			 }				 
				
			if(tempOptValue.length==0){}
				else
				json+="'"+jsonKeyName+"':\""+tempOptValue+"\",";
			
		})

		
			dropdownsCount++;
		});
		json=json.substring(0,json.length-1)
		json+="}";
	//alert(json);
	generateTableByService(ajaxUrl,json,download);
	 //  document.getElementById('id01').style.display='block';
	 
	}
	  
	function getCaseStudiedTableByRoleRow(thisVariable,ajaxUrl,download){
		$("#caseTable").html("");
		 // alert("table");

	 	var stepName=$(thisVariable).closest('tr').children('td:first').text();
		   
	
	

	  var json="{";
	  json+="'StageName':'"+stepName+"',";
	  var currentDate=$(thisVariable).parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();
		
		 
	  var endDate,startDate="";
		
		 if(currentDate.substring(0,1)=='D'){
		startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
		endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
		
		 }else {
	  startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
	  endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();
		 
		 }	  json+="'dateFrom':'"+startDate+"','dateTo':'"+endDate+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
		
			var jsonKeyName=$(this).attr("name");
		$(this).children("option:selected").each(function(){
			var tempOptValue="";
			 if($(this).val()=="ALL"){ 
			 
				if(dropdownsCount==2||dropdownsCount==3){
				 $(this).parent().children("option").not(':first').each(function(){
					if(dropdownsCount!=5)
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
					else{
						tempOptValue+="'"+$(this).val()+"',";
		
					}
				
				 })  
		 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
		 
		 }
		 else
		 tempOptValue="";
			 }
			 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=7) {
				 tempOptValue+="'"+($(this).val().split("-"))[0]+"',"
				 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
			 }
			 else{
				 tempOptValue+=$(this).val();
				 
			 }				 
				
			if(tempOptValue.length==0){}
				else
				json+="'"+jsonKeyName+"':\""+tempOptValue+"\",";
			
		})

		
			dropdownsCount++;
		});
		json=json.substring(0,json.length-1)
		json+="}";
	//alert(json);
	generateTableByService(ajaxUrl,json,download);
	//   document.getElementById('id01').style.display='block';
	 
	}


function getCaseStudiedTableByTotal(thisVariable,ajaxUrl,download){
	  var json="{";

	  
	  //var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
	  json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
			
			var jsonKeyName=$(this).attr("name");
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					
					if(dropdownsCount==2||dropdownsCount==3){
					 $(this).parent().children("option").not(':first').each(function(){
						if(dropdownsCount!=5)
						 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
						else{
							tempOptValue+="'"+$(this).val()+"',";
			
						}
				
					 })  
					
					 	
				 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1);
					 }
					 else
					 tempOptValue="";
				 }
				 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=7) {
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',"
					 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else{
					 tempOptValue+=$(this).val();
					 
				 }				 
					
				 if(tempOptValue.length==0){}
				else
				json+="'"+jsonKeyName+"':\""+tempOptValue+"\",";
				
			})

			
			dropdownsCount++;
		});
		json=json.substring(0,json.length-1)
		json+="}";
	//alert(json);
	   generateTableByService(ajaxUrl,json,download);
	   document.getElementById('id01').style.display='block';
  
  }
  
  
  
  
  function getCaseStudiedTableByTotalRow(thisVariable,ajaxUrl,download){
	    var json="{";

	  
var currentDate=$(thisVariable).parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();

	var endDate,startDate="";
	
	 if(currentDate.substring(0,1)=='D'){
	startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	
	 }else {
   startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
   endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();
	 
	 }
	 
	   json+="'dateFrom':'"+startDate+"','dateTo':'"+endDate+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
			
			var jsonKeyName=$(this).attr("name");
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					
					if(dropdownsCount==2||dropdownsCount==3){
					 $(this).parent().children("option").not(':first').each(function(){
						if(dropdownsCount!=5)
						 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
						else{
							tempOptValue+="'"+$(this).val()+"',";
			
						}
				
					 })  
					
					 	
				 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1);
					 }
					 else
					 tempOptValue="";
				 }
				 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=7) {
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',"
					 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else{
					 tempOptValue+=$(this).val();
					 
				 }				 
					
				 if(tempOptValue.length==0){}
				else
				json+="'"+jsonKeyName+"':\""+tempOptValue+"\",";
				
			})

			
			dropdownsCount++;
		});
		json=json.substring(0,json.length-1)
		json+="}";
	//alert(json);
	   generateTableByService(ajaxUrl,json,download);
	//   document.getElementById('id01').style.display='block';
	  
  }







  function checkAllSelectedOption(){

		var dropdownsCount=1;
		$("select").each(function(){
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					
					 if(dropdownsCount==2||dropdownsCount==3){
					 $(this).parent().children("option").not(':first').each(function(){
						if(dropdownsCount!=5)
						 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
						else{
							tempOptValue+="'"+$(this).val()+"',";
			
						}
					
					 })  
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				}
				else
				tempOptValue="";		 }
				 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=7) {
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',"
					 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else if(dropdownsCount==5){
					 tempOptValue+="'"+$(this).val()+"'";
				 }
				 else
					 tempOptValue+=$(this).val();
					
				 $(this).val(tempOptValue);
				
			})
	
			
			dropdownsCount++;
		});
	
	}



	

/* $(".roleRow").each(function(){
	
	var countTotal=0;
	$(this).children().not(':first').not(':last').each(function(){
		countTotal+=parseFloat($(this).text()) ;
		
	});
	
	$(this).children().last().find("u").text(countTotal.toFixed(2));
	
	
}); */

/* var totalTd=$(".roleRow").first().find("td").length;

//alert(totalTd);
for(var count=1;count<totalTd;count++){
	
	
	var contEachTd=0;
	 $(".roleRow").each(function(){
			
		// alert($(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,'')); 
	contEachTd+= parseFloat( $(this).find('td:eq('+count+')').text());
	
	
	 });
	 $(".roleRow").parent().find("tr").last().find('td:eq('+count+')').append("<br><a  style='cursor:pointer'>"+contEachTd.toFixed(2)+"</a>");
	// alert($("#firstTr").parent().find("tr").last().find('td:eq('+count+')').text())
	 
} */
//***********************Cycle Time Graph Generation***********

//***********************Ely Chart***********


	     
	     
	      function stepNamedata(){
	    	 var jsonData = [];
	    					
	    		$(".roleRow").each(function(){
	    			
	    					  
	    			jsonData .push($(this).children().first().text());
	    			
	    			  
	    			
	    			});
	    		
	    		console.log("jsonData: " + jsonData);
	    		return jsonData;
	    }
	    
	      function aliasData(){
	    		 var stage=[];
	    			 
	    		 $(".roleRow").each(function(){
		    			
					  
	    			 stage .push($(this).attr("aliasName"));
		    			
		    			  
		    			
		    			});
		    		
		    		console.log("jsonData: " + stage);
		    		return stage;
	    			
	    		}

	    
	    
	    function countdata(){
	    	 var jsonData = [];
	    		
	    		$(".roleRow").each(function(){
	    			
	    					  
	    			jsonData .push(parseInt($(this).children().last().find("u").text()));
	    			   	  
	    			   			  
	    			
	    			
	    			});
	    		
	    		console.log("jsonData: " + jsonData);
	    		return jsonData;
	    } 
		

	     function graphTotalCycleTimeTaken(){
	    	 
	    	
	    var x=stepNamedata();
	    var countData= countdata();
	    var stageAlias=aliasData();
	      
	     
	    var thetooltips = new Array(countData.length);
	    var thelabels = new Array(countData.length); 
	    var thevalues = new Array(countData.length); 
	   
	     
	    for ( var i = 0; i < countData.length; i++) {
	    	  thelabels[i] = x[i];
	    	  
	    	  thevalues[i] = countData[i];
	    	  thetooltips[i] = "<table><tr><td style='color:red;padding:0'>"
	    							+ x[i]
	    							+ " : </td></tr><tr><td><span  style='color:#4897f1;padding:0'> count: </span><span><b>"
	    							+ countData[i]
	    							+"</b></span></td></tr></table>";
	         }
	       
	        $("#chartContainer").chart({
	    					template : "line_basic_5",
	    					tooltips : thetooltips,
	    					values : {
	    						serie1 : thevalues
	    					},
	    					defaultSeries : {
	    						type : "bar"
	    					},
	    					axis : {
	    						l : {
	    							title : "Total Time"
	    						},
	    						x : {title : 'Stages', titleDistance: 35},
	    					},
	    					 
	    					
	    					barMargins : 20,
	    					labels : stageAlias
	    				});
	        $("#titleDiv").remove(); 
            $("#chartContainer").prepend('<html><div id="titleDiv"> <h4 style="color:purple">Title :Total Cycle Time Taken To Complete Process</h5></div></html>');
	        
	        var descData='';
	    	//alert(JSON.stringify(aliasValue));
	    	
	    	$('#locDesc').html();
	    	
	    	
	    	for(i=0;i<x.length;i++){
	    		
	    		
	    		descData+='<tr><td width="100">' + x[i] + '</td>'+'<td width="118">'+stageAlias[i]+'</td>'+'</tr>';
	    	}
	    	  	
	    	
	    	 $("#divDesc").remove();
	       $('#chartContainer').append('<html><div id="divDesc"><table width="80%" border="1" id="locTable"  ><tr class="w3-blue"><td>' + " Stage FullName" +'</td>' +'<td>'+" Stage shortName"+ '</td></tr>'+descData+'</table></div></html>');

	        

	    }
     

	    $.elycharts.templates['line_basic_5'] = {
	    		  type : "line",
	    				margins : [10, 65, 70, 70],
	    		        style : {
	    		          background : "url(http://images2.layoutsparks.com/1/214768/brown-plain-wall-paper-1.jpg)"
	    		         },
	    				defaultSeries : {
	    					plotProps : {
	    						opacity : 1
	    								},
	    					startAnimation : {
	    						active : true,
	    						type : 'grow'
	    					},
	    					highlight : { 
	    						overlayProps : {
	    							fill : "#0276FA",
	    							opacity : 1
	    						}
	    					},
	    					tooltip : {
	    						height : 100,
	    						width : 150,
	    						padding : [ 7, 10 ],
	    						offset : [ -10, 0 ],
	    						frameProps : {
	    							opacity : 0.95,
	    							fill : "white",
	    							stroke : "#000"
	    						}
	    					}
	    				},
	    				series : {
	    					serie1 : {
	    						color :"#4897f1"
	    						/* color : "#A3E4D7" */
	    					}
	    					
	    				},
	    				defaultAxis : {
	    					labels : true,
	    					labelsProps : {
	    						fill : "#000",
	    						"font-size" : "13px"
	    					},
	    					labelsAnchor : "start",
	    					labelsRotate : 0,
	    					labelsMargin : 0,
	    					labelsDistance : 30,
	    					labelsHideCovered : false
	    				},
	    				axis : {
	    					x : {
	    						titleProps : {
	    							fill : "RED",
	    								"font-size" : "15px"
	    						},
	    						
	    						labelsMargin : 18,
	    						
	    						labelsDistance : 10 ,
	    						labelsRotate : 0,
	    						   
	    						   labelsProps : {
	    						    font : "15px Verdana"
	    						   },
	    					},

	    					l : {
	    						titleProps : {
	    							fill : "RED",
	    							"font-size" : "15px"
	    							
	    						},
	    						titleDistance : 45,
	    						labelsMargin : 0,
	    						
	    						labelsDistance : 30
	    					}
	    		      
	    		      
	    				},
	    				features : {
	    					tooltip : {
	    						positionHandler : function(env, tooltipConf, mouseAreaData,suggestedX, suggestedY) {
	    							return [ mouseAreaData.event.pageX,	mouseAreaData.event.pageY, true ]
	    						}
	    					},
	    					mousearea : {
	    						type : 'axis'
	    					},
	    					grid : {
	    						draw : true, 
	    						ny : 7,
	    						nx : "auto",
	    						evenHProps : {
	    							fill : "#FFFFFF"
	    						},
	    						oddHProps : {
	    							fill : "#FAF5F7"
	    						},
	    						ticks : {
	    							active : [ true, true, false ],
	    							size : [ 5, 5 ],
	    							props : {
	    								stroke : "#CCC"
	    							}
	    						}
	    					}
	    				}
	    		};
	     
//***************End*********************

</script>
<script>
 <%
 String templocationName=(String)pageContext.getAttribute("locationName");
 templocationName=templocationName.replace("','" , "");
 String tempbusinessName=(String)pageContext.getAttribute("businessName");
 tempbusinessName=tempbusinessName.replace("','" , "");
 %>
 var loc= <%=templocationName%>;
 var busi= <%=tempbusinessName%>;
callGroupByLocationService(loc,busi);
		
callLocationByBusinessService(loc);
$('[name="businessGroup"]').change(function(){

callGroupByLocationService();

});
	
$('[name="Business"]').change(function(){
		
callLocationByBusinessService();
});
 
 $( "select[name='DisplayBy']" ).change(function() {

		setToDisplayDate(this);
});
 

</script> 
</body>
</html>