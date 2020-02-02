<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="include.jsp"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>JSW</title>



<style>
table tr:not(:first-child) td:not(:first-child) {
cursor:pointer;
}
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
#chartContainer {
   
     overflow-y:scroll; 
      
    position:relative;
   
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
<h2 class="w3-center w3-xxlarge"><i>HRM</i></h2>
					<h4 class="w3-text-blue">
					<b>Report 2- Number of cases completed upto a step (End Time)</b>
					</h4>	
				
				
				
 
<form:form commandName="FunctionAppBean" action="/jsw-report/HRController/listNumberOfCasesCompletedHR">				
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
     <c:when test="${functionName.name=='Roles'}">
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
		    <p class="w3-right currentDate"></p>
				<c:if test="${settingValue==0}"> 
		<div class="w3-left"><label>Tabular</label>	
		<input type="radio" onChange="$('#listCaseCreated').show();$(' #chartContainer').hide();" class="w3-radio" style="padding-right:20px" checked name="graph">
		<label>Graphical</label>	
		<input   onChange="$('#listCaseCreated').hide();$('#chartContainer').show();graphNumberCaseComp();" class="w3-radio" type="radio" name="graph">	</div>	
</c:if>		<br>
	<center>	 <div id="chartContainer" style="height: 500px; width: 100%;display:none">
  </div></center>
		
 <table class="w3-table-all w3-hoverable w3-striped w3-border  w3-center" id="listCaseCreated">
					<thead>
						<tr class=" w3-center w3-medium" >
						
						
							<td class="w3-center"  style="background-color:#372d87;color:white">Stage Name</td>
						
						
						<c:if test="${not empty listPeriod}"> 
						
						 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
    
					
					
					<c:forEach items="${listPeriod}" var="value">
					
						<td class="w3-center"  style="background-color:#372d87;color:white">${value}</td>
						</c:forEach>
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         				
					<c:forEach items="${listPeriod}" var="value">
					
						<td class="w3-center"  style="background-color:#372d87;color:white">${value}</td>
						</c:forEach>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   <c:forEach items="${listPeriod}" var="value">
					
						<td class="w3-center"  style="background-color:#372d87;color:white"> ${value}</td>
						</c:forEach>
    </c:when>  
    
    
    </c:choose>
    </c:if>
						<td class="w3-center"  style="background-color:#372d87;color:white">Count
						
						
							<span title="download table"  onclick="getCaseCreatedAll('listCaseCreated', 'HRM Number of Case Completed')" class="w3-badge fa fa-download w3-blue  w3-right w3-hover-green"></span>
				
					
						
						</td>
							
						</tr>


						<c:forEach var="listCases" items="${listCasesCreated}">
							<tr class="stepName" aliasName="${listCases.alias}">

					
							<td class="">${listCases.name}</td>
							
									 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
		<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.day1}</td>
										<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.day2}</td>
											<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.day3}</td>
													<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.day4}</td>
										<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.day5}</td>
											<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.day6}</td>
						
							<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.day7}</td>
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
   		<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.week1}</td>
										<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.week2}</td>
											<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.week3}</td>
													<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.week4}</td>
											
											
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   		<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.period1}</td>
										<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.period2}</td>
											<td onclick="getCaseStudiedTableByStepnameRow(this,'getCaseCreatedCompanyCompletedHR',0);" class="w3-center">${listCases.period3}</td>
    </c:when>  
    
    
    </c:choose>
											
								<td  class="w3-center w3-text-blue"><u class="w3-hover-blue w3-hover-text-black" onclick="getCaseStudiedTableByStepname(this,'getCaseCreatedCompanyCompletedHR',0);">${listCases.count}</u>
								
								
								
								<span  onclick="getCaseStudiedTableByStepname(this,'getCaseCreatedCompanyCompletedExcelHR',1)" class="fa fa-download w3-right w3-hover-blue"></span>
				
							
								
								</td>
								

							</tr>
						</c:forEach>
						
						
					<%-- 	<tr class="w3-center w3-medium" >
						
						
							<td class="w3-center"  style="background-color:black;color:white">Grand Total</td>
						
						
						
  			
  			
  			 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
          <td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Day1</td>
  			<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Day2</td>
  			<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Day3</td>
  				<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Day4</td>   
  			<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Day5</td>
  			<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Day6</td>
  				<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Day7</td>
						
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         <td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Week1</td>
  			<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Week2</td>
  			<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Week3</td>
  				<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Week4</td>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
      <td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Month1</td>
  			<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Month2</td>
  			<td class="w3-center" style="background-color:black;color:white" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyCompletedHR',0);">Total of all Month1</td>
    </c:when>  
    
    
    </c:choose>
  			
  			
  			
    			<td class="w3-center" onClick="getCaseStudiedTableByTotal(this,'getCaseCreatedCompanyCompletedHR',0);" style="background-color:black;color:white">Total of all column on totals
    			
    			
    			
    			<span  onclick="getCaseStudiedTableByTotal(this,'getCaseCreatedCompanyCompletedExcelHR')" class="fa fa-download w3-right w3-hover-blue"></span>
				
    			</td>
							
						</tr>
						
						 --%>
						
						
						
						
						
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

	<c:set var="businessName" value="${FunctionAppBean.getBusiness()}"/>
	<c:set var="personnelName" value="${FunctionAppBean.getPersonnelArea()}"/>
	<c:set var="payrollName" value="${FunctionAppBean.getPayrollArea()}"/>
	
<%@include file="webservicesAjax.jsp"%>
<script>



$(".stepName").each(function(){

			var countTotal=0;
			$(this).children().not(':first').not(':last').each(function(){
				//alert($(this).text());
				countTotal+=parseInt($(this).text()) ;
			
			});
			
			$(this).children().last().find("u").text(countTotal);



	});
/* var totalTd=$(".stepName").first().find("td").length;

 //alert(totalTd);
for(var count=1;count<totalTd;count++){
	
	
	var contEachTd=0;
	 $(".stepName").each(function(){
			
		// alert($(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,'')); 
	contEachTd+= parseInt( $(this).find('td:eq('+count+')').text().replace(/[^0-9]/g,''));
	
	
	 });
	 $(".stepName").parent().find("tr").last().find('td:eq('+count+')').append("<br><a  style='cursor:pointer'>"+contEachTd+"</a>");
	// alert($("#firstTr").parent().find("tr").last().find('td:eq('+count+')').text())
	 
}
 */


 function callGroupByPersonnelPayrollService(payroll,personnel,busi){

		var selectedBusinessGroup=$('[name="businessGroup"]').val();	


		$.ajax({                    
	 	  url: '/jsw-report/NW/getPersoneelAndPayrollByGrp/'+selectedBusinessGroup,     
	 	  type: 'GET',
	 	async:false,
	 	cache:false,
	 	  success: function(data)         
	 	  {

	 		  
	 		
	 		  
	 		 var newJsonBusinessData=""+JSON.parse(data)[0];
	 	 	 
	 	 	   newJsonBusinessData=newJsonBusinessData.split(",");
	 	 	    
	 		  if( newJsonBusinessData.length>1)
	 	 		 var optionString="<option>ALL</option>";
	 	 		
	 	 		 for (var countOp=0;countOp<newJsonBusinessData.length;countOp++){
	 	 			 if(newJsonBusinessData[countOp].indexOf(busi) !=-1)
	  		  			optionString+="<option selected>"+newJsonBusinessData[countOp]+"</option>";
	  		else
	  		optionString+="<option>"+newJsonBusinessData[countOp]+"</option>";
	  	 	 		
	 	 		 }

	 			 $('[name="Business"]').html(optionString);
	 	 	
	 	 
	 		  
	 	   
	 
	 	     var newJsonLocationData=""+JSON.parse(data)[1];

	 	
	 		newJsonLocationData=newJsonLocationData.split(", ").join("$").split(",");
	 	 
	 		
	 		 
	 		  
	 		 optionString="";
	 	  if( newJsonLocationData.length>1)
	 		optionString+="<option >ALL</option>";
	 		 
	 		 for (var countOp=0;countOp<newJsonLocationData.length;countOp++){
	 		 
	 		 if(newJsonLocationData[countOp].indexOf(payroll) !=-1)
	 			optionString+="<option selected>"+newJsonLocationData[countOp].split("$").join(", ")+"</option>";
	 			else
	 			optionString+="<option>"+newJsonLocationData[countOp].split("$").join(", ")+"</option>";
	 			
	 		
	 		 }

			 $('[name="PersonnelArea"]').html(optionString);
	 			   
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 

	 	     var newJsonPayrollData=""+JSON.parse(data)[2];

	 	
	 	    newJsonPayrollData=newJsonPayrollData.split(",");
	 	 
	 		
	 		 
	 		  
	 		  optionString="";
	 	  if( newJsonPayrollData.length>1)
	 		optionString+="<option >ALL</option>";
	 		 
	 		 for (var countOp=0;countOp<newJsonPayrollData.length;countOp++){
	 		 
	 		 if(newJsonPayrollData[countOp].indexOf(payroll) !=-1)
	 			optionString+="<option selected>"+newJsonPayrollData[countOp]+"</option>";
	 			else
	 			optionString+="<option>"+newJsonPayrollData[countOp]+"</option>";
	 			
	 		
	 		 }

			 $('[name="PayrollArea"]').html(optionString);
	 	  }
		 
		 }); 
		 
		

		  
	} 



	function callBusinessByPersonnelPayrollService(payroll,personnel){

		var selectedBusinessGroup=$('[name="businessGroup"]').val();
		var selectedBusiness=($('[name="Business"]').val().split("-")[0]);	
		$.ajax({                    
	 	  url: '/jsw-report/NW/getPersoneelAndPayrollByBusiness/'+selectedBusinessGroup+'/'+selectedBusiness,     
	 	  type: 'GET',
	 	async:false,
	 	cache:false,
	 	  success: function(data)         
	 	  {
	//alert(data);
	 	   var newJsonBusinessData=""+JSON.parse(data)[0];
	 	 
	 	   newJsonBusinessData=newJsonBusinessData.split(", ").join("$").split(",");
	 	 
	  	 
	 	    var optionString="";
	 	  if( newJsonBusinessData.length>1)
	 		optionString+="<option >ALL</option>";
	 		
	 		 for (var countOp=0;countOp<newJsonBusinessData.length;countOp++){
	 		  if(newJsonBusinessData[countOp].indexOf(personnel) !=-1)
	 		  			optionString+="<option selected>"+newJsonBusinessData[countOp].split("$").join(", ")+"</option>";
	 		else
	 		optionString+="<option>"+newJsonBusinessData[countOp].split("$").join(", ")+"</option>";
	 	
	 		 }

			 $('[name="PersonnelArea"]').html(optionString);
	 	
	 
	 	     var newJsonLocationData=""+JSON.parse(data)[1];

	 	
	 		newJsonLocationData=newJsonLocationData.split(",");
	 	 
	 		
	 		 
	 		  
	 		 var optionString="";
	 	  if( newJsonLocationData.length>1)
	 		optionString+="<option >ALL</option>";
	 		 
	 		 for (var countOp=0;countOp<newJsonLocationData.length;countOp++){
	 		 
	 		 if(newJsonLocationData[countOp].indexOf(payroll) !=-1)
	 			optionString+="<option selected>"+newJsonLocationData[countOp]+"</option>";
	 			else
	 			optionString+="<option>"+newJsonLocationData[countOp]+"</option>";
	 			
	 		
	 		 }

			 $('[name="PayrollArea"]').html(optionString);
	 			   
	 	  }
		 
		 }); 
		 
		

		  
	} 

 function callPayrollByPersonnelService(payroll){
 	var selectedBusiness=($('[name="Business"]').val().split("-")[0]);
 	var selectedBusinessGroup=$('[name="businessGroup"]').val();	
 	var selectedPersonnelArea=$('[name="PersonnelArea"]').val();
 	//alert(selectedPersonnelArea)
 	  $.ajax({                    
  	  url: '/jsw-report/NW/getPayrollByPersonnel/'+selectedBusiness+"@"+selectedBusinessGroup+"@"+selectedPersonnelArea,     
  	  type: 'GET',
  	async:false,
  	cache:false,
  	  success: function(data)         
  	  {
  	
 if(data!="[]"){
  		 var newJsonresultData=JSON.parse(data);
  		 
  	 var optionString="";
  	  if( newJsonresultData.length>1)
  		optionString+="<option >ALL</option>";
  		 
  		 for (var roleDataKey in newJsonresultData){
  		 
  			 if(newJsonresultData[roleDataKey].indexOf(payroll) !=-1)
  			optionString+="<option selected>"+newJsonresultData[roleDataKey]+"</option>";
  		 else
  		 optionString+="<option>"+newJsonresultData[roleDataKey]+"</option>";
  		 
  		 }
  
 		 $('[name="PayrollArea"]').html(optionString);
 }
 else
 	  $('[name="PayrollArea"]').html("");
  	  }
 	 
 	
 	 }); 
  
 }




 </script>
 <script>

 <%

 String tempbusinessName=(String)pageContext.getAttribute("businessName");
 tempbusinessName=tempbusinessName.replace("','" , "");

  String temppersonnelName=(String)pageContext.getAttribute("personnelName");
 temppersonnelName=temppersonnelName.replace("','" , ""); 
 String temppayrollName=(String)pageContext.getAttribute("payrollName");
 temppayrollName=temppayrollName.replace("','" , "");

 %>

 var busi=""+ <%=tempbusinessName%>;
  var personnel= ""+ <%=temppersonnelName%>;/* "null"; */
 var payroll=""+ <%=temppayrollName%>; 

 //callGroupByLocationService(loc,busi); 
 callGroupByPersonnelPayrollService(payroll,personnel,busi); 
 callBusinessByPersonnelPayrollService(payroll,personnel);
 callPayrollByPersonnelService(payroll); 


 $('[name="businessGroup"]').change(function(){

 //callGroupByLocationService();
 //callLocationByBusinessService(loc);

  callGroupByPersonnelPayrollService(payroll,personnel,busi); 
 });
 	
 $('[name="Business"]').change(function(){
 		

 		//callLocationByBusinessService();
 		 callBusinessByPersonnelPayrollService(payroll,personnel); 
 	});

 $('[name="PersonnelArea"]').change(function(){
 	

 	callPayrollByPersonnelService(payroll);

 });
 $( "select[name='DisplayBy']" ).change(function() {

 		setToDisplayDate(this);
 });






function getCaseStudiedTableByStepname(thisVariable,ajaxUrl){
	$("#caseTable").html("");
	 // alert("table");
	var stepname=$(thisVariable).closest('tr').children('td:first').text();
	   // alert(firstColumnValue);
	   // var busiGroup=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
	    //alert(busiGroup);
//	 var  secondColumnValue = $(thisVariable).closest("tr").find("td:eq(1)").text();
  

  var json="{";
  json+="'StageName':'"+stepname+"',";
  
  // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
  json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
  var dropdownsCount=1;
	$("select").each(function(){
	
		var jsonKeyName=$(this).attr("name");
		$(this).children("option:selected").each(function(){
			var tempOptValue="";
			 if($(this).val()=="ALL"){ 
				
				 if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==4){
						
					 $(this).parent().children("option").not(':first').each(function(){
						 if(dropdownsCount==3||dropdownsCount==4)
							 tempOptValue+="'"+$(this).val()+"',";
						 else
							 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
					
					 })  
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1);
				}
				else
				tempOptValue="";
				
				 }
				 else if(dropdownsCount!=6&&dropdownsCount!=1&&dropdownsCount!=8) {
					 
					 if(dropdownsCount==3||dropdownsCount==4)
						 tempOptValue+="'"+$(this).val()+"',";
					 else
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
					 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else
					 tempOptValue+=$(this).val();				 
				
			 if(tempOptValue.length==0){}
			else
			json+="'"+jsonKeyName+"':\""+tempOptValue+"\",";
			
		})

		
		
	
		dropdownsCount++;
	});
	json=json.substring(0,json.length-1)
	json+="}";

generateTableByService(ajaxUrl,json);
   document.getElementById('id01').style.display='block';
 
}
  
function getCaseStudiedTableByStepnameRow(thisVariable,ajaxUrl){
	$("#caseTable").html("");
	 // alert("table");
	var stepname=$(thisVariable).closest('tr').children('td:first').text();
	   // alert(firstColumnValue);
	   // var busiGroup=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
	    //alert(busiGroup);
//	 var  secondColumnValue = $(thisVariable).closest("tr").find("td:eq(1)").text();
  

  var json="{";
  json+="'StageName':'"+stepname+"',";

  var currentDate=$(thisVariable).parent().parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();
	
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
				
				 if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==4){
						
					 $(this).parent().children("option").not(':first').each(function(){
						 if(dropdownsCount==3||dropdownsCount==4)
							 tempOptValue+="'"+$(this).val()+"',";
						 else
							 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
					
					 })  
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1);
				}
				else
				tempOptValue="";
				
				 }
				 else if(dropdownsCount!=6&&dropdownsCount!=1&&dropdownsCount!=8) {
					 
					 if(dropdownsCount==3||dropdownsCount==4)
						 tempOptValue+="'"+$(this).val()+"',";
					 else
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
					 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else
					 tempOptValue+=$(this).val();				 
				
			 if(tempOptValue.length==0){}
			else
			json+="'"+jsonKeyName+"':\""+tempOptValue+"\",";
			
		})

		
	
		dropdownsCount++;
	});
	json=json.substring(0,json.length-1)
	json+="}";
//alert(json);
generateTableByService(ajaxUrl,json);
   document.getElementById('id01').style.display='block';
 
}
  



 function getCaseStudiedTableByTotal(thisVariable,ajaxUrl){
	  var json="{";

	  
	  // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
	  json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
			var jsonKeyName=$(this).attr("name");
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					
					 if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==4){
							
						 $(this).parent().children("option").not(':first').each(function(){
							 if(dropdownsCount==3||dropdownsCount==4)
								 tempOptValue+="'"+$(this).val()+"',";
							 else
								 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
						
						 })  
				 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1);
					}
					else
					tempOptValue="";
					
					 }
					 else if(dropdownsCount!=6&&dropdownsCount!=1&&dropdownsCount!=8) {
						 
						 if(dropdownsCount==3||dropdownsCount==4)
							 tempOptValue+="'"+$(this).val()+"',";
						 else
						 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
						 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
					 }
					 else
						 tempOptValue+=$(this).val();				 
					
				 if(tempOptValue.length==0){}
				else
				json+="'"+jsonKeyName+"':\""+tempOptValue+"\",";
				
			})

			

			
			dropdownsCount++;
		});
		json=json.substring(0,json.length-1)
		json+="}";
	//alert(json);
	   generateTableByService(ajaxUrl,json);
	   document.getElementById('id01').style.display='block';
  
  }
  
  
  
  
  function getCaseStudiedTableByTotalRow(thisVariable,ajaxUrl){
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
					
					 if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==4){
							
						 $(this).parent().children("option").not(':first').each(function(){
							 if(dropdownsCount==3||dropdownsCount==4)
								 tempOptValue+="'"+$(this).val()+"',";
							 else
								 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
						
						 })  
				 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1);
					}
					else
					tempOptValue="";
					
					 }
					 else if(dropdownsCount!=6&&dropdownsCount!=1&&dropdownsCount!=8) {
						 
						 if(dropdownsCount==3||dropdownsCount==4)
							 tempOptValue+="'"+$(this).val()+"',";
						 else
						 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
						 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
					 }
					 else
						 tempOptValue+=$(this).val();			 
					
				 if(tempOptValue.length==0){}
				else
				json+="'"+jsonKeyName+"':\""+tempOptValue+"\",";
				
			})

			
			dropdownsCount++;
		});
		json=json.substring(0,json.length-1)
		json+="}";
	//alert(json);
	   generateTableByService(ajaxUrl,json);
	   document.getElementById('id01').style.display='block';
	  
  }












  function checkAllSelectedOption(){

		var dropdownsCount=1;
		$("select").each(function(){
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					
					if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==4){
						
					 $(this).parent().children("option").not(':first').each(function(){
						 if(dropdownsCount==3||dropdownsCount==4)
							 tempOptValue+="'"+$(this).val()+"',";
						 else
							 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
					
					 })  
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1);
				}
				else
				tempOptValue="";
				
				 }
				 else if(dropdownsCount!=6&&dropdownsCount!=1&&dropdownsCount!=8) {
					 
					 if(dropdownsCount==3||dropdownsCount==4)
						 tempOptValue+="'"+$(this).val()+"',";
					 else
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
					 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else
					 tempOptValue+=$(this).val();
						//alert(tempOptValue);
				 $(this).val(tempOptValue);
				
			})
	
		
			dropdownsCount++;
		});
	
	}




  function aliasData(){
		 var stage=[];
			 
			 $(".stepName").each(function(){
				 
				  
				
				   
				  	
			
				
				 
				 stage.push($(this).attr("aliasName"));
				 
				
				
			
			 
			
			 }); 
			 
			 
			 var uniqueNames = [];
			   $.each(stage, function(i, el){
			       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
			   });
			  // alert(JSON.stringify(uniqueNames))
			   return uniqueNames;
			
		}

                   	
                   
	 	





function graphNumberCaseComp(){

	
	var stepName = [ ];
    
	var countValue = [ ];
	    
			var countLength=0;
	$(".stepName").each(function(){
		
		
	
		stepName.push($(this).children().first().text());
		countValue.push($(this).children().last().find("u").text());

		
		
		});
	
	var uniqueNames = [];
	   $.each(stepName, function(i, el){
	       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
	   });
	
	
	
	 var thetooltips = new Array(countValue.length);
	
	 var thevalues = new Array(countValue.length);
	 
	 var aliasValue=aliasData();
	  
	for ( var i = 0; i < countValue.length; i++) {
		 
		  thevalues[i] = countValue[i];
		  thetooltips[i] = "<table><tr><td style='color:red;padding:0'>"
								+ stepName[i]
								+ " : </td></tr><tr><td><span  style='color:#4897f1;padding:0'> Count : </span><span><b>"
								+ countValue[i]
								+ " </b></span></td></tr></table>";
	      }
	    
	     $("#chartContainer").chart({
						template : "line_basic_6",
						tooltips : thetooltips,
						values : {
							serie1 : thevalues
						},
						defaultSeries : {
							type : "bar"
						},
						axis : {
							l : {
								title : "Case Compleated Count"
							},
							x : {title : 'Stage Name', titleDistance: 35},
						},
						
						labels : aliasValue
					});
	     $("#titleDiv").remove(); 
         $('#chartContainer').prepend('<html><div id="titleDiv"> <h4 style="color:purple">Title : Number of cases Completed</h5></div></html>');
	     var descData='';
	 	
	 	//alert(JSON.stringify(aliasValue));
	 	
	 	$('#locDesc').html();
	 	
	 	
	 	for(i=0;i<uniqueNames.length;i++){
	 		
	 		
	 		descData+='<tr><td width="100">' + uniqueNames[i] + '</td>'+'<td width="118">'+aliasValue[i]+'</td>'+'</tr>';
	 	}
	 	  	
	 	
	 	 $("#divDesc").remove();
	    $('#chartContainer').append('<html><div id="divDesc"><table width="80%" border="1" id="locTable"  ><tr class="w3-blue"><td>' + " Stage FullName" +'</td>' +'<td>'+" Stage shortName"+ '</td></tr>'+descData+'</table></div></html>');

}
	    


$.elycharts.templates['line_basic_6'] = {
		  type : "line",
				margins : [10, 150, 70, 70],
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
						
						height : 80,
						width : 250,
						padding : [ 7, 10 ],
						offset : [ -10, 0 ],
						frameProps : {
							opacity :1,
							fill : "white",
							stroke : "#000"
						}
					}
				},
				series : {
					serie1 : {
						color : "#4897f1"
					}
				},
				defaultAxis : {
					labels : true,
					labelsProps : {
						fill : "#000",
						"font-size" : "12px"
					},
					labelsAnchor : "start",
					labelsRotate :0.1,
					
					labelsHideCovered : false
				},
				axis : {

					l : {
						titleProps : {
							fill : "RED",
								"font-size" : "15px"
						},

						titleDistance :30,
						labelsMargin :10,
						labelsDistance :18

						

					},
					x : {
						titleProps : {
							fill : "RED",
								"font-size" : "15px"
						},
						
						labelsMargin : 9,
						
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
						forceBorder : [ true, false, true, false ],
						ny : 7,
						nx : "auto",
						plotProps : {
							"stroke-width" : 4
						},
						props : {
							stroke : "#FAF5F7"
						},
						evenHProps : {
							fill : "#FFFFFF",
						},
						oddHProps : {
							fill : "#FAF5F7",
						},
						ticks : {
							active : [ true, true, false ],
							size : [ 5, 5 ],
							props : {
								stroke : "black"
							}
						}
					}
				},
				 barMargins : 50
		};


</script>


<script>



 
 
 $(".stepName").each(function(){

		var countTotal=0;
		$(this).children().not(':first').not(':last').each(function(){
			countTotal+=parseInt($(this).text()) ;
			
		});
		
		$(this).children().last().find("u").text(countTotal);



});
</script> 


</body>
</html>