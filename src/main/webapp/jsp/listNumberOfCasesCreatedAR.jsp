<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@include file="include.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*" %>
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
<h2 class="w3-center w3-xxlarge"><i>AR</i></h2>
					<h4 class="w3-text-blue">
					<b>Report 1- Number of Cases Created</b>
			
					</h4>
				



<form:form commandName="FunctionAppBean" action="/jsw-report/ARController/listNumberOfCasesCreatedAR">				
				
				
				
				
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
         <option value="${value}" selected>${value} </option>   
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
<br /> <br />
		</div>

			
			<c:set var="fromDateVar" value="${FunctionAppBean.getFromDate()}"/>

		
                <c:choose>
        <c:when test="${not empty listCasesCreated}">
		<br /> <br />
			<c:if test="${settingValue==0}"> 
			<div class="w3-left"><label>Tabular</label>	
		<input type="radio" onChange="$('#listCaseCreated').show();$('#chartContainer').hide();" class="w3-radio" style="padding-right:20px" checked name="graph">
		<label>Graphical</label>	

		<input onChange="$('#listCaseCreated').hide();$('#chartContainer').show();graphNumberOfCase();" class="w3-radio" type="radio" name="graph"></div>
		
		
		</c:if>
	
		<center>
	    <p class="w3-right currentDate" id=""></p>
	    
		 <div id="chartContainer" class="w3-center" style="height: 500px; width:70%;display:none">


			</div>	
			<button class='w3-btn w3-right' id="save-btn" style="display:none" onClick="saveGraph('chartContainer')">SAVE</button>
			
			</center>
		
	

    <br>

  
    <div id="listCaseCreated">
<table 
					class="w3-table-all w3-hoverable w3-striped w3-border w3-center">
					
						<tr class="w3-center " id="firstTr" style="background-color:#372d87;color:white">
						
						
							<td class="w3-center">BusinessGroup / Location</td>
						
						
						
						<c:if test="${not empty listPeriod}"> 
						
						 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
    
					
					
					<c:forEach items="${listPeriod}" var="value">
					
						<td class="w3-center">${value}</td>
						</c:forEach>
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         				
					<c:forEach items="${listPeriod}" var="value">
					
						<td class="w3-center">${value}</td>
						</c:forEach>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   <c:forEach items="${listPeriod}" var="value">
					
						<td class="w3-center">${value}</td>
						</c:forEach>
    </c:when>  
    
    
    </c:choose>
    </c:if>
						
						<td class="w3-center"> Total Count
						
						
						<span  onclick="getCaseCreatedAll('listCaseCreated', 'AR Number of Case Created')" class="w3-badge fa fa-download  w3-right w3-hover-blue"></span>
				
						</td>
							
						</tr>

<% String groupName=""; %>




						<c:forEach var="listCases" items="${listCasesCreated}">
							<tr class="">
	 <c:set var="groupName" value="${listCases.name}"/>
 <%
 
    if(groupName.equals((String)pageContext.getAttribute("groupName"))){}
    else{
    	
    
  %>
  			<tr class="" style="background-color:#aabdf7">
  			<td class="busi">${listCases.name}</td>
  			
  			
  			
  			
  			
  			
  			
  			
  				 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
       	<td class="w3-center">Total of all ${listCases.name} Locations</td>
  			<td class="w3-center">Total of all ${listCases.name} Locations</td>
  			<td class="w3-center">Total of all ${listCases.name} Locations</td>
						<td class="w3-center">Total of all ${listCases.name} Locations</td>
  			<td class="w3-center">Total of all ${listCases.name} Locations</td>
  			<td class="w3-center">Total of all ${listCases.name} Locations</td>
					<td class="w3-center">Total of all ${listCases.name} Locations</td>		
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         	<td class="w3-center">Total of all ${listCases.name} Locations</td>
  			<td class="w3-center">Total of all ${listCases.name} Locations</td>
  			<td class="w3-center">Total of all ${listCases.name} Locations</td>
  				<td class="w3-center">Total of all ${listCases.name} Locations</td>
  			
  			
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
    	<td class="w3-center">Total of all ${listCases.name} Locations</td>
  			<td class="w3-center">Total of all ${listCases.name} Locations</td>
  			<td class="w3-center" >Total of all ${listCases.name} Locations</td>
    </c:when>  
    
    
    </c:choose>
  			
  			
  			
  			
  			
  			
    			<td class="w3-center" >Total of all period on Table
    			
    			<span  onclick="getCaseStudiedTableByBusinessGroup(this,'getCaseCreatedCompanyExcelAR',1)" class="fa fa-download w3-right w3-hover-blue"></span>
								
    			</td>
  
  </tr>
  
  <%}
 
 groupName=(String)pageContext.getAttribute("groupName");%>

					
						
<td class="w3-center firstTd bus${listCases.name}" colorName="${listCases.color}" aliasName="${listCases.alias}" >${listCases.location}</td>
								
								
								
								
								
							
											
											 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
		<td class="w3-center" style='cursor:pointer' onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.day1}</td>
										<td style='cursor:pointer' class="w3-center" onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.day2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.day3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.day4}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.day5}</td>
											<td class="w3-center" style='cursor:pointer' onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.day6}</td>
						
							<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.day7}</td>
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.week1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.week2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.week3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.week4}</td>
											
											
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.period1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.period2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCaseCreatedCompanyAR',0);">${listCases.period3}</td>
    </c:when>  
    
    
    </c:choose>
											
											
											
											
								<td  class="w3-center w3-text-blue">
								<u class="w3-hover-blue w3-hover-text-black"
								 style="cursor:pointer"  onClick="getCaseStudiedTableByLocation(this,'getCaseCreatedCompanyAR',0);"
								 >
								
								${listCases.count}
								</u>
								
								<span onClick="getCaseStudiedTableByLocation(this,'getCaseCreatedCompanyExcelAR',1)"  class="fa fa-download  w3-right"></span>
								
								
								</td>
								

							</tr>
						</c:forEach>
						
						
						<tr class=" w3-center w3-medium"  style="background-color:black;color:white">
						
						
							<td class="w3-center">Grand Total</td>
						
						
						
  			
  			
  			 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
          <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);" >Total of all Business Locations</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
  				<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>   
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
  				<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
						
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
  				<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
      <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCaseCreatedCompanyAR',0);">Total of all Business Locations</td>
    </c:when>  
    
    
    </c:choose>
  		
  			
  			
    			<td class="w3-center"  >Total of all column on totals  
    			
    			<span  onClick="getCaseStudiedTableByTotal(this,'getCaseCreatedCompanyExcelAR',1)"  class="fa fa-download w3-right"></span></td>
							
						</tr>
				</table></div><br><br>

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
	
 <script>


 

 $(".firstTd").each(function(){

		var countTotal=0;
		$(this).parent().children().not(':first').not(':last').each(function(){
			countTotal+=parseInt($(this).text()) ;
			
		});
		
		$(this).parent().children().last().find("u").text(countTotal);



 });

 $(".busi").each(function(){
	var totalTd=$(this).parent().children("td").length;
	 var busiGroup=$(this).text();
	
	for(var count=1;count<totalTd;count++){
		
		var contEachTd=0;
		
		 $(this).parent().siblings().find(".bus"+busiGroup).each(function(){
			 //alert($(this).text());
			 contEachTd+= parseInt($(this).parent().find('td:eq('+count+')').text());; 
			 
			 
		 });
		 
		 if(count==totalTd-1)
		 $(this).parent().find('td:eq('+count+')').append("<br><a style='cursor:pointer' onclick=\"getCaseStudiedTableByBusinessGroup(this,'getCaseCreatedCompanyAR',0);\" \" >"+contEachTd+"</a>");
		 else
			 $(this).parent().find('td:eq('+count+')').append("<br><a style='cursor:pointer' onclick=\"getCaseStudiedTableByBusinessGroupRow(this,'getCaseCreatedCompanyAR',0);\" >"+contEachTd+"</a>");
			
	}	 
	
	 });

 
	 var totalTd=$("#firstTr").children("td").length;

	// alert(totalTd);
	for(var count=1;count<totalTd;count++){
		
		
		var contEachTd=0;
		 $(".busi").each(function(){
				
			// alert($(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,'')); 
		contEachTd+= parseInt( $(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,''));
		
		
		 });
		 
		 
		 if(count==totalTd-1)
			 $("#firstTr").parent().find("tr").last().find('td:eq('+count+')').append("<br><a onClick=\"getCaseStudiedTableByTotal(this,'getCaseCreatedCompanyAR',0)\" style='cursor:pointer'>"+contEachTd+"</a>");	 
		 else
		 $("#firstTr").parent().find("tr").last().find('td:eq('+count+')').append("<br><a  style='cursor:pointer'>"+contEachTd+"</a>");
		
		 
	}
	 
	

 
 
 
 var datefield=document.createElement("input")
 datefield.setAttribute("type", "date")
 if ( datefield.type!="date"){ //if browser doesn't support input type="date", initialize date picker widget:

 	 $("#dateFrom").kendoDatePicker({  format: " dd-MM-yyyy", });

 }
 
 
</script> 

<%@include file="webservicesAjax.jsp"%>

<script>

 
 
  
function getCaseStudiedTableByLocation(thisVariable,ajaxUrl,download){
	$("#caseTable").html("");

	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	  
	var busiGroup=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
	   
  var json="{";
  json+="'Loc':'"+firstColumnValue+"','busiGrp':'"+busiGroup+"',";
  
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





function getCaseStudiedTableByLocationRow(thisVariable,ajaxUrl,download){
	$("#caseTable").html("");
	 // alert("table");
	 //alert( $(thisVariable).index())
	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	   // alert(firstColumnValue);
	    var busiGroup=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
	    //alert(busiGroup);
//	 var  secondColumnValue = $(thisVariable).closest("tr").find("td:eq(1)").text();
  
var currentDate=$(thisVariable).parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();

var endDate,startDate="";

if(currentDate.substring(0,1)=='D'){
startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);

}else {
startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();

}






var json="{";
  json+="'Loc':'"+firstColumnValue+"','busiGrp':'"+busiGroup+"',";
  
 //// var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
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
	 //document.getElementById('id01').style.display='block';
}












  function getCaseStudiedTableByBusinessGroup(thisVariable,ajaxUrl,download){
	 
		$("#caseTable").html("");
		
		    var busiGroup=$(thisVariable).closest('tr').children('td:first').text();
		 

	  var json="{";
	  json+="'busiGrp':'"+busiGroup+"',";
	  
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
  
  
  function getCaseStudiedTableByBusinessGroupRow(thisVariable,ajaxUrl,download){
	 
	  $("#caseTable").html("");
		 // alert("table");
		 //alert( $(thisVariable).index())
		 var busiGroup=$(thisVariable).closest('tr').children('td:first').text();
		 
//		 var  secondColumnValue = $(thisVariable).closest("tr").find("td:eq(1)").text();
	    
	var currentDate=$(thisVariable).parent().parent().parent().find("tr:first").children('td:eq('+$(thisVariable).parent().index()+')').text();
	var endDate,startDate="";
	
	 if(currentDate.substring(0,1)=='D'){
	startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	
	 }else {
   startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
   endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();
	 
	 }
	 

	var json="{";
	  json+="'busiGrp':'"+busiGroup+"',";
	  
	 //// var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
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
		 //document.getElementById('id01').style.display='block';
	}
  function getCaseStudiedTableByTotal(thisVariable,ajaxUrl,download){
	  var json="{";

	  
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
	   //document.getElementById('id01').style.display='block';
	  
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
				tempOptValue="";
				
				 }
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




</script>

<script>
<%
String templocationName=(String)pageContext.getAttribute("locationName");
templocationName=templocationName.replace("','" , "");
String tempbusinessName=(String)pageContext.getAttribute("businessName");
tempbusinessName=tempbusinessName.replace("','" , "");
%>
var loc=""+ <%=templocationName%>;
var busi=""+ <%=tempbusinessName%>;

callGroupByLocationService(loc,busi);

$('[name="businessGroup"]').change(function(){

callGroupByLocationService();
callLocationByBusinessService(loc);
});
	
$('[name="Business"]').change(function(){
		

		callLocationByBusinessService();

	});


$( "select[name='DisplayBy']" ).change(function() {

		setToDisplayDate(this);
});



//***********************Ely***********


 	
  
 
 function locationData(){
	 var location=[];
	 
	 $(".busi").each(function(){
		 
		   var busiGroup=$(this).text();
		
		   
		  	
	 $(this).parent().siblings().find(".bus"+busiGroup).each(function(){
		
		 
		 location.push($(this).text());
		 
		
		 });
	
	 
	
	 }); 
	 
	 
	 var uniqueNames = [];
	   $.each(location, function(i, el){
	       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
	   });
	  // alert(JSON.stringify(uniqueNames))
	   return uniqueNames;
	 
}
  function countData(){
	 var mainJson={};
	 var location=[];
	 
	 
	 
	   var uniqueNames=locationData();
	  
	   
	   for(var i=0;i<uniqueNames.length;i++){
		 
	 		 var jsonCount = [];
	 		 var jsonBusiness = [];
	 		
	 		
	 		var loc=uniqueNames[i];
	 	
	 				 			
	 			 
	 			 $(".busi").each(function(){
	 				 var count=0;
	 				  var busiGroup2=$(this).text();
	 				 
	 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
	 					 var temp2=$(this).text();
	 					
	 					
	 					
	 				  if(loc==temp2){
	 													
	 					 						 
	 					 jsonCount.push(parseInt($(this).parent().children().last().text())); 						
	 						
	 						 
	 						 count++; 
	 						 
	 					 }
	 						 
	 					 
	 				 });//end of location
	 				
	 				 if(count==0){
	 							 
	 					jsonCount.push(0);
	 					
	 					  
	 				 }//end of if 
	 				 
	 				 
	 				 
	 			 });//end of businessGroup
	 			 
	 			 
	 			// alert("hhh"+JSON.stringify(jsonCount)); 
	 			
	 			 
	 			mainJson["serie"+i]=jsonCount;
	 	}
	  
	  // alert("Main Json is"+JSON.stringify(mainJson)); 
	 	return mainJson;
	      
} 
 

function businessdata(){
	 var busiGroup=[];
	 
	 $(".busi").each(function(){
		 
		 busiGroup.push($(this).text());	  
	
	 }); 
	
	 return busiGroup;
	 
}






function toolTipData(){
	 
	 var mainToolTip={};
	 var location=[];
	 
	 
	 
	   $(".busi").each(function(){
		 
		   var busiGroup=$(this).text();
		
		   
		  	
	 $(this).parent().siblings().find(".bus"+busiGroup).each(function(){
		
		 
		 location.push($(this).text());
	
		
		 
		
		 });
	
	  
	
	 }); 
	   
	   
	  var uniqueNames = [];
	   $.each(location, function(i, el){
	       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
	   });
	  
	   
	   for(var i=0;i<uniqueNames.length;i++){
	 		 var jsonToolTip = [];
	 		 var jsonBusiness = [];
	 		
	 		
	 		var loc=uniqueNames[i];
	 	
	 				 			
	 			 
	 			 $(".busi").each(function(){
	 				 
	 		
	 				 var count=0;
	 				  var busiGroup2=$(this).text();
	 				 
	 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
	 					 
	 					// alert($(this).attr("colorName"));
	 					 var temp2=$(this).text();
	 					// alert("temo"+temp2);
	 					
	 				  if(loc==temp2){ 					
	 											
	 						
	 						 
	 					
	 					 jsonToolTip.push("<table><tr><td style='color:red;padding:0'>Count:"
	 							+parseInt($(this).parent().children().last().text())
								+ "</td></tr><tr><td style='color:#4897f1;padding:0'>Location:"
								+ temp2
								+ "</td></tr></table>");
	 						
	 						 
	 						 count++; 
	 					 }
	 						 
	 					 
	 				 });//end of location
	 				
	 				 if(count==0){
	 							 
	 					jsonToolTip.push(0+"");
	 					
	 					  
	 				 }//end of if
	 				 
	 				 
	 				 
	 			 });//end of businessGroup
	 			 
	 			 
	 			 
	 			
	 			/*  alert("hhh"+JSON.stringify(mainJsondataArray)); */
	 			mainToolTip["serie"+i]=jsonToolTip;
	 	}
	  
	   
	 	return mainToolTip;
	      
	 
}//end of toolTipData





function barColor(){
	var graphColor=[];
	 var location=locationData();
	 
	  	
	 			 
	 			 $(".busi").each(function(){
	 				 
	 		
	 				 var count=0;
	 				  var busiGroup2=$(this).text();
	 				 
	 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
	 					 
	 					
	 					 var temp2=$(this).text();
	 					
	 					
	 							
	 						
	 						graphColor.push($(this).attr("colorName"));
	 						 					
	 						 count++;  
	 						 
	 						 
	 					 
	 				
	 					 
	 				 });//end of location
	 				
	 				
	 				
	 				 
	 				 
	 				 
	 			 });//end of businessGroup
	 			 
	 			
	 			 
	 			 
	 			 
	 			var uniqueColor = [];
	 		   $.each(graphColor, function(i, el){
	 		       if($.inArray(el, uniqueColor) === -1) uniqueColor.push(el);
	 		   });
	 	
	  
	  /*  alert(JSON.stringify(graphColor)); */
	   
	 	return uniqueColor;
		 
}

function legendColor(){
	 var legendColorArr=[];
		
	 var location=locationData();
	 var tempColor=[];
	 
	  
	  // for(var i=0;i<location.length;i++){
	 		
	 		
	 		
	 		
	 	//	var loc=location[i];
	 	
	 				 			
	 			 
	 			 $(".busi").each(function(){
	 			
	 		
	 				 var count=0;
	 				  var busiGroup2=$(this).text();
	 				 
	 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
	 					 
	 					
	 					 var temp2=$(this).text();
	 					
	 					
	 				
	 						tempColor.push($(this).attr("colorName"));
	 						
	 						 					
	 						 count++;  
	 						 
	 						 
	 					// }
	 				
	 					 
	 				 });//end of location
	 				
	 			
	 				 
	 			 });//end of businessGroup
	 			 
	 			 
	 			  //alert(JSON.stringify(graphColor));
	 			
	 			/*  alert("hhh"+JSON.stringify(mainJsondataArray)); */
	 			
	 	//}
	  
	  
	 			var uniqueColor = [];
	 		   $.each(tempColor, function(i, el){
	 		       if($.inArray(el, uniqueColor) === -1) uniqueColor.push(el);
	 		   });
	  
	  
	  for(i=0;i<uniqueColor.length;i++){
		  var jsonColor={};
		  jsonColor['color']=uniqueColor[i];
		  legendColorArr.push(jsonColor);
		  
	  }
		  
	 
	   
	 	return legendColorArr;
	
}



function aliasData(){
	 var location=[];
		 
		 $(".busi").each(function(){
			 
			   var busiGroup=$(this).text();
			
			   
			  	
		 $(this).parent().siblings().find(".bus"+busiGroup).each(function(){
			
			 
			 location.push($(this).attr("aliasName"));
			 
			
			 });
		
		 
		
		 }); 
		 
		 
		 var uniqueNames = [];
		   $.each(location, function(i, el){
		       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
		   });
		  // alert(JSON.stringify(uniqueNames))
		   return uniqueNames;
		
	}



function maxCount(){
	    var count=0;
		$(".busi").each(function(){
			  var busiGroup2=$(this).text();
			  $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
			var temp=parseInt($(this).parent().children().last().text());
			if(temp>=count)
				count=temp;
			
		});
			  
		});
		
	 	return count;	
	 
}//end of maxCount

var barColorData=barColor();
var legendColorData=legendColor();




var count=0;

function graphNumberOfCase(){
	
	
	
	 var jsonString = JSON.stringify(barColorData);
	
	 var x=countData();
	
	 
			var location=locationData(); 
			   
			    var business=businessdata();
			   
			 var thetooltips = toolTipData();
			
          
           /*  alert(JSON.stringify(location));
           */
			  
			 
			 var maxiCount= maxCount();
			  
         
			    
	$("#chartContainer").chart({
		 template : "line_basic_6",
		 tooltips :thetooltips,
		 
		 values : x,
		 labels : business,
		  legend : location, 
		 defaultSeries : {
		  type : "bar",
		  stacked : true
		 },
		   seriesPalette :barColorData,  
		   
		   
		   
		 axis : {
		  r : {
		   max : maxiCount,
		   
		  },
		l : {
			title : "Case Created Count"
		},
		x : {title : 'Business Group', titleDistance: 32},
		 }
		});
	 if(count==0){
  		 $("#titleDiv").remove(); 
           $('#chartContainer').prepend('<html><div id="titleDiv"> <h4 style="color:purple">Title : Number of cases Created</h5></div></html>');
  			  }
	
	var descData='';
	var aliasValue=aliasData();
	//alert(JSON.stringify(aliasValue));
	
	$('#locDesc').html();
	
	
	for(i=0;i<location.length;i++){
		
		
		descData+='<tr><td width="100">' + location[i] + '</td>'+'<td width="118">'+aliasValue[i]+'</td>'+'</tr>';
	}
	  	
	
	 $("#divDesc").remove();
   $('#chartContainer').append('<html><div id="divDesc"><br /><table width="80%" border="1" id="locTable"  ><tr style=\'background-color:blue\'><td>' + " Location ShortName" +'</td>' +'<td>'+" Location FullName"+ '</td></tr>'+descData+'</table></div></html>');

		    
			      
}





$.elycharts.templates['line_basic_6'] = {
		 type : "line",
		
		 margins : [30, 200, 40, 50],
		
		 defaultSeries : {
		  highlight : {
		   newProps : {
		    r : 8,
		    opacity : 1
		   },
		   overlayProps : {
		    fill : "white",
		    opacity : 0.2
		   }
		  },
		  tooltip : {
				height : 90,
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
		   series : legendColorData,  
		 
		 bar:{
			 gap:2,
			width:undefined
		 },
		 
		 defaultAxis : {
		  labels : true
		 },
		 axis : {
			 x : {
					titleProps : {
						fill : "RED",
							"font-size" : "15px"
					},
					
					labelsMargin : 15,
					
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
				titleDistance : 30,
				labelsMargin : 0,
				
				labelsDistance : 0 
			}
		  
		 },
		 features : {
		  grid : {
		   draw : true,
		   forceBorder : true,
		   ny : 5
		  },
		  
		  
		  
		  legend : {
			   horizontal :false,
			  
			   itemWidth : "auto",
			    height : 250,
			   x :600,
			   y :0, 
			   
			  
			   dotProps: { "stroke-width": 0 },
		        textProps: { "fill": "#404040" },
		        dotWidth: 15,
		        dotHeight: 20,
		        dotR: 5
			  } 
		 },
		 barMargins : 50
		}               	
              	





</script>
</body>
</html>