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
#chartContainer0 {
   
     overflow-y:scroll; 
    position:relative;
   
}
#chartContainer1 {
   
     overflow-y:scroll; 
    position:relative;
   
}
#chartContainer2 {
   
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
<h2 class="w3-center w3-xxlarge"><i>AP</i></h2>
					<h4 class="w3-text-blue">
							<b>Report 5- Total cycle time taken to complete the process (End Time)
</b>
					</h4>
<form:form commandName="FunctionAppBean" action="/jsw-report/ApController/listTotalCycleTimeAP">				
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
   <c:when test="${functionName.name=='CaseType'}">
  <tr>
  <td><label>Bar Code</label></td><td><form:input path="barCode" id="barcode" class="w3-input w3-border" style="width:50%"/>
		
			</td>
  </tr>
     </c:when>
</c:choose>
			</c:forEach>	
				
				</table>
				<br />
				<input type="submit" onClick="checkAllSelectedOption()" class="w3-btn w3-right" style="width:15%" value="Go">
</form:form> 
		

			<br /> <br />	</div>
			
			
                <c:choose>
        <c:when test="${not empty listTotalTimeTaken}">
			
  
    <br><br>
 

    <%String timeTaken[]={"Time(HOUR)","Average Time(HOUR)","Number of Count "};

    int countTimeTaken=0;%>
  
    		<c:forEach var="listCasesCreated" items="${listTotalTimeTaken}">
    		
    		
    			<br /><br />
    		
    	<div class="w3-left"><label>Tabular</label>	

		<input type="radio" onChange="$('#listCaseCreated<%=countTimeTaken %>').show();$(' #chartContainer<%=countTimeTaken %>').hide();" class="w3-radio" style="padding-right:20px" checked name="graph<%=countTimeTaken %>">
		<label>Graphical</label>	
 <input onChange="$('#listCaseCreated<%=countTimeTaken %>').hide();$('#chartContainer<%=countTimeTaken %>').show();$('#table-btn').show();graphOfTotalTimeTaken($('#chartContainer<%=countTimeTaken %>'),<%=countTimeTaken %>);" class="w3-radio" type="radio" name="graph<%=countTimeTaken %>">
 
</div><br /><br />
    		
    		<center>
     	<c:if test="${settingValue==0}"> 
		 <div id="chartContainer<%=countTimeTaken %>" class="w3-center" style="height: 500px; width:80%;display:none"></div>
	
		</c:if>
  </center>	
    		
    		    <div id="listCaseCreated<%=countTimeTaken %>">
    		<h5 class="w3-left"><b> Total <%=timeTaken[countTimeTaken] %> taken</b></h5>
    		  <p class="w3-right currentDate" id=""></p>
<table 
					class="w3-table-all w3-hoverable w3-striped w3-border w3-small w3-center totalTimeTable" id='<%=timeTaken[countTimeTaken] %>'>
					<thead>
						<tr class="w3-center w3-medium" style="background-color: #004d99;color:white" id="firstTr">
						
						
							<th class="w3-center">Stage Name</th>
						
						
						
						<c:if test="${not empty listPeriod}"> 
						
						 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
    
					
					
					<c:forEach items="${listPeriod}" var="value">
				
					
					
						<th class="w3-center">${value}</th>
						</c:forEach>
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         				
					<c:forEach items="${listPeriod}" var="value">
					
						<th class="w3-center">${value}</th>
						</c:forEach>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   <c:forEach items="${listPeriod}" var="value">
					
						<th class="w3-center">${value}</th>
						</c:forEach>
    </c:when>  
    
    
    </c:choose>
    </c:if>
						
						<th class="w3-center">Total <%=timeTaken[countTimeTaken] %>
						
						
						<span  onclick="getCaseCreatedAll('<%=timeTaken[countTimeTaken++] %>', 'AP Total Cycle time taken to complete process')" class="w3-badge fa fa-download  w3-right w3-hover-blue"></span>
				
						</th>
							
						</tr>
</thead><tbody>
<% String groupName=""; %>
	

						<c:forEach var="listCases" items="${listCasesCreated}">
						
						

						
						
							<tr class="roleRow" aliasName="${listCases.alias}">

					
						
<td class="w3-center firstTd bus${listCases.name}" >${listCases.name}</td>
								
								
								
								
								
							
											
											 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
		<td class="w3-center" style='cursor:pointer' onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.day1}</td>
										<td style='cursor:pointer' class="w3-center" onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.day2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.day3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.day4}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.day5}</td>
											<td class="w3-center" style='cursor:pointer' onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.day6}</td>
						
							<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.day7}</td>
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.week1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.week2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.week3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.week4}</td>
											
											
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.period1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.period2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByStageRow(this,'getTotalCycleTimeAP',0);">${listCases.period3}</td>
    </c:when>  
    
    
    </c:choose>
											
											
											
											
								<td  class="w3-center w3-text-blue">
								<u class="w3-hover-blue w3-hover-text-black"  onClick="getCaseStudiedTableByStage(this,'getTotalCycleTimeAP',0);"
								 style="cursor:pointer"
								 >
								
								${listCases.count}
								</u>
								
								<span onClick="getCaseStudiedTableByStage(this,'getTotalCycleTimeExcelAP',1);"  class="fa fa-download generateXLS w3-right"></span>
								
								
								</td>
								

							</tr>
						</c:forEach>
						
						</tbody>
					<%-- 	<tr class="w3-text-white w3-black w3-center w3-medium">
						
						
						
							<td class="w3-center" style="background-color: black;color:white">Grand Total</td>
						
						
						
  			
  			
  			 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
          <td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Day1</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Day2</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Day3</td>
  				<td class="w3-center"  style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Day4</td>   
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Day5</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Day6</td>
  				<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Day7</td>
						
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         <td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Week1</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Week2</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Week3</td>
  				<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Week4</td>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
      <td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Month1</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Month2</td>
  			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');">Total of all Month1</td>
    </c:when>  
    
    
    </c:choose>
  			
  			
  			
    			<td class="w3-center" style="background-color: black;color:white" onClick="getCaseStudiedTableByTotal(this,'getTotalCycleTimeCOMM');">Total of all column on totals
    			
    			<span onClick="getCaseStudiedTableByTotalRow(this,'getTotalCycleTimeCOMM');" class="fa fa-download generateXLS w3-right"></span>
    			</td>																
						</tr> --%>
				</table>
				
				</div>
				<br><br>
				</c:forEach>
				
	</c:when>
<c:when test="${listTotalTimeTaken ne null}">
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


$('.totalTimeTable').DataTable( {
    "paging":   false,
    "ordering": false,
    "info":     false,
    "searching": false
} );
 
 /*  $(function(){

	    $('.generateXLS').click(function(){
	    
	    	alert("xls");
	  	//  alert("generate");
	        var url='data:application/vnd.ms-excel,' + encodeURIComponent($('#caseTable').html()) 
	       // alert(url);
	        location.href=url
	        return false
	    })
	})
  */


   function getCaseStudiedTableByStage(thisVariable,ajaxUrl,download){
		$("#caseTable").html("");
		 // alert("table");
		var stepName=$(thisVariable).closest('tr').children('td:first').text();

	  var json="{";
	  json+="'StageName':'"+stepName+"',";
	  
	  if($("#barcode").val().length>0){
		  
		  json+="'barCode':'"+$("#barcode").val()+"',";
	  }
	  // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
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
	///   document.getElementById('id01').style.display='block';
	 
	}
	  
	function getCaseStudiedTableByStageRow(thisVariable,ajaxUrl,download){
		$("#caseTable").html("");
		 // alert("table");

	 	var stepName=$(thisVariable).closest('tr').children('td:first').text();
		   
	
	

	  var json="{";
	  json+="'StageName':'"+stepName+"',";
	  var currentDate=$(thisVariable).parent().parent().parent().find("thead").find("tr:first").children('th:eq('+$(thisVariable).index()+')').text();
		
	  
	  if($("#barcode").val().length>0){
		  
		  json+="'barCode':'"+$("#barcode").val()+"',";
	  }
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
	  // document.getElementById('id01').style.display='block';
	 
	}


function getCaseStudiedTableByTotal(thisVariable,ajaxUrl,download){
	  var json="{";

	  
	  if($("#barcode").val().length>0){
		  
		  json+="'barCode':'"+$("#barcode").val()+"',";
	  }
	  // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
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
	   //document.getElementById('id01').style.display='block';
  
  }
  
  
  
  
  function getCaseStudiedTableByTotalRow(thisVariable,ajaxUrl,download){
	    var json="{";

	    
		  if($("#barcode").val().length>0){
			  
			  json+="'barCode':'"+$("#barcode").val()+"',";
		  }
		  var currentDate=$(thisVariable).parent().parent().parent().find("thead").find("tr:first").children('th:eq('+$(thisVariable).index()+')').text();
			
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
	 //  document.getElementById('id01').style.display='block';
	  
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
				 else if(dropdownsCount==1&&dropdownsCount==5){
					 tempOptValue+="'"+$(this).val()+"'";
					 
				 }
				 else
					 tempOptValue+=$(this).val();			 
					
				 $(this).val(tempOptValue);
				
			})
	
			
			dropdownsCount++;
		});
	
	}






	

//***********************Cycle Time Graph Generation***********




	     
	     
	    function countData(countTable){

	
	 var stageCount=[];
	

	 		
	 	
	 		 $(".totalTimeTable").eq(countTable).each(function(){	
	 			stageCount=[];
	 			 
	 			 $(this).find(".roleRow").each(function(){
	 				
	 				 							
	 					 						 
	 				stageCount.push(parseFloat($(this).children().last().text())); 						
	 						

	 						 
	 						 
	 						 

	 				 
	 			 });//end of businessGroup
	 		 });//end of roletable
	 		
	 		
	 		
	 		 
	 			
	 	
	  
	  // alert("Main Json is"+JSON.stringify(mainJson)); 
	 	return stageCount;
	      
}

function stageData(countTable){
	 var stages=[];
	
		 
		 $(".totalTimeTable").eq(countTable).each(function(){	
			 stages=[];
			 $(this).find(".roleRow").each(function(){
		 
				 stages.push($(this).children().first().text());	  
	
	 }); 
		 });
	 
		 var uniqueNames = [];
		   $.each(stages, function(i, el){
		       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
		   });
		
	 return uniqueNames;
	 
}






/* function toolTipData(countTable){
	
	 var thetooltips = [];
	 
	
	 
		var stageCount=countData(countTable);
		var  stageName=stageData(countTable);
		
		thetooltips = new Array(stageCount.length);
		
	for ( var i = 0; i < stageCount.length; i++) {
		
		  thetooltips[i] = "<table><tr><td style='color:red;padding:0'>"
								+ stageName[i]
								+ " : </td></tr><tr><td><span  style='color:#4897f1;padding:0'> Count : </span><span><b>"
								+ stageCount[i]
								+ " </b></span></td></tr></table>";
	      }
	  

	 
	
	
	 	return thetooltips;
	      
	 
	 
	 		 	 
	 
}//end of toolTipData
 */




function maxCount(){
	    var count=0;
		$(".tableRow").each(function(){
			
			 
			var temp=parseInt($(this).parent().children().last().text());
			if(temp>=count)
				count=temp;
			
		
			  
		});
		
	 	return count;	
	 
}//end of maxCount




function aliasData(countTable){
	 var roleAlias=[];
	 
	
		
	 $(".totalTimeTable").eq(countTable).each(function(){	
		 roleAlias=[];
		 $(this).find(".roleRow").each(function(){
			
			 roleAlias.push($(this).attr("aliasName"));	  
	
	 }); 
	 }); 
		 
	
	 
	
	 
	 
	 
		 var uniqueNames = [];
		   $.each(roleAlias, function(i, el){
		       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
		   });
		  // alert(JSON.stringify(uniqueNames))
		   return uniqueNames;
		
	}

var count=0;

	     function graphOfTotalTimeTaken(chartName,radioData){
	    	 
	    
	  var stageValue=stageData(radioData);
	    var countValue= countData(radioData);
	    var stageAlias=aliasData(radioData);
	   
	      
	     
	    var thetooltips = new Array(countValue.length);
	    var thelabels = new Array(countValue.length); 
	    var thevalues = new Array(countValue.length); 
	   
	     
	    for ( var i = 0; i < countValue.length; i++) {
	    	  thelabels[i] = stageValue[i];
	    	  
	    	  thevalues[i] = countValue[i];
	    	  
	    	  if(radioData==0 || radioData==1){
	    		  
	    	  thetooltips[i] = "<table><tr><td style='color:red;padding:0'>"
	    							+ stageValue[i]
	    							+ " : </td></tr><tr><td><span  style='color:#4897f1;padding:0'> Total Time(Hr) </span><span><b>"
	    							+ countValue[i]
	    							+"</b></span></td></tr></table>";
	         }
	    	  else{
	    		  thetooltips[i] = "<table><tr><td style='color:red;padding:0'>"
						+ stageValue[i]
						+ " : </td></tr><tr><td><span  style='color:#4897f1;padding:0'> Count </span><span><b>"
						+ countValue[i]
						+"</b></span></td></tr></table>";
	    	  }
	    	  
	    }
	    
	    if(radioData==0){
	    	title="Total Time";
	    }
	    if(radioData==1){
	    	title="Average Time";
	    }
	    if(radioData==2){
	    	title="Total Count";
	    }
	    
	    chartName.chart({
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
	    							title : title
	    						},
	    						x : {title : 'Stages', titleDistance: 35},
	    					},
	    					 
	    					
	    					barMargins : 50,
	    					labels : stageAlias
	    				});
	    if(count==0){
      		 $("#titleDiv").remove(); 
               $(chartName).prepend('<html><div id="titleDiv"> <h4 style="color:purple">Title :Total Cycle Time Taken To Complete Process</h5></div></html>');
      			  }
	        
	        
	        var descData='';
	    	//alert(JSON.stringify(aliasValue));
	    	
	    	$('#locDesc').html();
	    	
	    	
	    	for(i=0;i<stageValue.length;i++){
	    		
	    		
	    		descData+='<tr><td width="100">' + stageValue[i] + '</td>'+'<td width="118">'+stageAlias[i]+'</td>'+'</tr>';
	    	}
	    	  	
	    	
	    	 $("#divDesc").remove();
	       $('#chartContainer'+radioData).append('<html><div id="divDesc"><table width="80%" border="1" id="locTable"  ><tr class="w3-blue"><td>' + " Stage FullName" +'</td>' +'<td>'+" Stage shortName"+ '</td></tr>'+descData+'</table></div></html>');

	        

	    }
     

	    $.elycharts.templates['line_basic_5'] = {
	    		  type : "line",
	    				margins : [10, 65, 50, 70],
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

	    					l : {
	    						titleProps : {
	    							fill : "RED",
	    							"font-size" : "15px"
	    							
	    						},
	    						titleDistance : 45,
	    						labelsMargin : 0,
	    						
	    						labelsDistance : 30
	    					},
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