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
<h2 class="w3-center w3-xxlarge"><i>TAX</i></h2>
					<h4 class="w3-text-blue">
						<b>Report 3- Number of Cases Processed by User (End Time)</b>
					</h4>
				



<form:form commandName="FunctionAppBean" action="/jsw-report/TAXController/listNumberOfCasesProcessedTax">				
				
				
				
				
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
<br /> <br />
		</div>

			
			<c:set var="fromDateVar" value="${FunctionAppBean.getFromDate()}"/>
			
			

                <c:choose>
        <c:when test="${not empty listCasesCreated}">	 		

		   <br />
		 
	
<div class='w3-container w3-round-xxlarge' style="background-color:#e3ecf9">
  		<%int countGraphId=0; %>
			
			<c:forEach var="listOuterCases" items="${listCasesCreated}">	
		
    			<c:if test="${settingValue==0}"> 
    	<div class="w3-left"><label>Tabular</label>	

		<input type="radio" onChange="$('#listCaseCreated<%=countGraphId %>').show();$(' #chartContainer<%=countGraphId %>').hide();" class="w3-radio" style="padding-right:20px" checked name="graph<%=countGraphId %>">
		<label>Graphical</label>	
 <input   onChange="$('#listCaseCreated<%=countGraphId %>').hide();$('#chartContainer<%=countGraphId %>').show();$('#table-btn').show();graphNumberOfCaseProcess($('#chartContainer<%=countGraphId %>'),<%=countGraphId %>);" class="w3-radio" type="radio" name="graph<%=countGraphId %>">
 
</div><br />
    			</c:if>
    		<center>
     
		 <div id="chartContainer<%=countGraphId %>" class="w3-center" style="height: 500px; width:100%;display:none"></div>
	
	
  </center>	
		
		
		
					
					<br><br>
				<div id="listCaseCreated<%=countGraphId %>">	
			
					<c:if test="${not empty listOuterCases}"> 
		    <p class="w3-right currentDate"></p>
					Number cases ${listOuterCases.name}<%-- (step <%=(countGraphId++)+1 %>) --%>  by the users
 <table  class="w3-table-all w3-hoverable w3-striped w3-border  w3-center processTable process" stepname="${listOuterCases.selectedValue}">
					<thead>
						<tr class="w3-center " style="background-color:#372d87;color:white">
						
						
							<th class="w3-center">User Name</th>
						
						
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
								
						<th class="w3-center">Grand Total
						<span title="download table"  onclick="getCaseCreatedAll('listCaseCreated<%=countGraphId++ %>', 'Tax Number of Cases Processed by user')" class="w3-badge fa fa-download w3-blue  w3-right w3-hover-green"></span>
				
						
						</th>
							
						</tr>
						</thead>
<tbody>


						<c:forEach var="listCases" items="${listOuterCases.value}">
							
<tr class="busi">
			   <c:choose>

  <c:when test="${not empty listCases.name}">
   <td class="w3-center firstTd">${listCases.name}</td>
  </c:when>
  <c:otherwise>
    <td class="w3-center firstTd">-</td>
  </c:otherwise>
</c:choose> 
							
										 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
		<td class="w3-center stepName" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.day1}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.day2}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.day3}</a></td>
													<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.day4}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.day5}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.day6}</a></td>
						
							<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.day7}</a></td>
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
   		<td class="w3-center stepName" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.week1}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.week2}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.week3}<a></td>
													<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.week4}<a></td>
											
											
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   		<td class="w3-center stepName" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.period1}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.period2}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByUserNameRow(this,'getCasesProcessedTax',0);"><a>${listCases.period3}</a></td>
    </c:when>  
    
    
    </c:choose>
											
												<%-- <td class="w3-center">${listCases.period4}</td> --%>
								<td  class="w3-center w3-text-blue">
								
								<u onclick="getCaseStudiedTableByUserName(this,'getCasesProcessedTax',0);" class="w3-hover-blue w3-hover-text-black">${listCases.count}</u>
								
								<span  onclick="getCaseStudiedTableByUserName(this,'getCasesProcessedExcelTax',1);" class="fa fa-download  w3-right w3-text-blue w3-hover-blue">
								
								</span>
				
								</td>
								
								
							
								 </tr>

						
						</c:forEach>
							
							</tbody> <%-- <tfoot>
							
							<tr class="w3-text-white w3-black w3-center w3-medium">
						
						
							<td class="w3-center">Grand Total</td>
						
						
						
  			
  			
  			 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
          <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Day1</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Day2</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Day3</td>
  				<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Day4</td>   
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Day5</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Day6</td>
  				<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Day7</td>
						
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Week1</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Week2</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Week3</td>
  				<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Week4</td>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
      <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Month1</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Month2</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesProcessedTax',0);">Total of all Month1</td>
    </c:when>  
    
    
    </c:choose>
  			
  			
  			
    			<td class="w3-center" >Total of all column on totals
    			
    				<span  onclick="getCaseStudiedTableByTotal(this,'getCasesProcessedTax',0);" class="fa fa-download w3-right w3-hover-blue"></span>
				
							
    			
    			</td>
							
						</tr>
						
							</tfoot> 
						 --%>
				</table>
					</div>
				<br><br>
				</c:if>
				</c:forEach>
</div>
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

 /*  $(".processTable").each(function(){
			 
			  var totalTd=$(this).find("tr").first().children().length;


			 
	for(var count=1;count<totalTd;count++){
	
		
		var contEachTd=0;
		 $(this).find(".busi").each(function(){
				
			// alert($(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,'')); 
			
			
			var textOfCell=$(this).find('td:eq('+count+')').text();
			
		contEachTd+= parseInt(textOfCell);
		
		
		 });
		 
		 
		 
		 $(this).find("tr").last().find('td:eq('+count+')').append("<br><a  style='cursor:pointer'>"+contEachTd+"</a>");
		// alert($("#firstTr").parent().find("tr").last().find('td:eq('+count+')').t

		 
	}
	 });
	  */
	

 

  $('.process').DataTable( {
          "paging":   false,
          "ordering": false,
          "info":     false,
          "searching": false
      } );
 
 var datefield=document.createElement("input")
 datefield.setAttribute("type", "date")
 if ( datefield.type!="date"){ //if browser doesn't support input type="date", initialize date picker widget:

 	 $("#dateFrom").kendoDatePicker({  format: " dd-MM-yyyy", });

 }
 
 
</script> 

<script type="text/javascript">



 
  
  var today = new Date();
	 today=today.toString();
	 var now=(today.split("GMT"));

	$(".currentDate").html(now[0]); 
	
	
	
  </script>
<%@include file="webservicesAjax.jsp"%>

<script>

 
 
/*   $(function(){

	    $('.generateXLS').click(function(){
	    	document.getElementById('id01').style.display='none';
	    	//alert("xls");
	  	//  alert("generate");
	        var url='data:application/vnd.ms-excel,' + encodeURIComponent($('#caseTable').html()) 
	       // alert(url);
	        location.href=url
	        return false
	    })
	})
  */
/*  function getCaseCreatedAll(){
	  
	  var url='data:application/vnd.ms-excel,' + encodeURIComponent($('#listCaseCreated').html()) 
      // alert(url);
       location.href=url
       return false
	  
  } */
/* function getCaseStudiedTableByStageName(thisVariable,ajaxUrl){
	$("#caseTable").html("");

	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	   // alert(firstColumnValue);
	    var userName=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
	    //alert(busiGroup);
//	 var  secondColumnValue = $(thisVariable).closest("tr").find("td:eq(1)").text();
  

  var json="{";
  json+="'stageName':'"+firstColumnValue+"','userName':'"+userName+"',";
  
  // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
  json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
  var dropdownsCount=1;
	$("select").each(function(){

	
		var jsonKeyName =$(this).attr("name");
		$(this).children("option:selected").each(function(){
			var tempOptValue="";
			 if($(this).val()=="ALL"){ 
				
				
				if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==8){
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
   generateTableByService(ajaxUrl,json);
   document.getElementById('id01').style.display='block';
 
}





function getCaseStudiedTableByStageNameRow(thisVariable,ajaxUrl){
	$("#caseTable").html("");
	 // alert("table");
	 //alert( $(thisVariable).index())
	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	   // alert(firstColumnValue);
	    var userName=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
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
  json+="'stageName':'"+firstColumnValue+"','userName':'"+userName+"',";
  
 // // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
  json+="'dateFrom':'"+startDate+"','dateTo':'"+endDate+"',";
  var dropdownsCount=1;
	$("select").each(function(){
		
	
		var jsonKeyName=$(this).attr("name");
		$(this).children("option:selected").each(function(){
			var tempOptValue="";
			 if($(this).val()=="ALL"){ 
			 
				if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==8){
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
   generateTableByService(ajaxUrl,json);
	 document.getElementById('id01').style.display='block';
}


 */









  function getCaseStudiedTableByUserName(thisVariable,ajaxUrl,download){
	 
		$("#caseTable").html("");
	//alert("table");
		
		    var userName=$(thisVariable).closest('tr').children('td:first').text();
		   // alert(userName);
		 var  stageNameProcess = $(thisVariable).closest("table").attr("stepname");
	 // alert(stepName);

	  var json="{";
	  json+="'userName':'"+userName+"',";
	  
	  
	    json+="'stageName':'"+stageNameProcess+"',";
	    
	
	  //  json+="'process':'"+stageNameProcess.split("$")[1].trim()+"',";
	  // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
	  json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
			var jsonKeyName=$(this).attr("name");
		
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==8){
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
	 //  document.getElementById('id01').style.display='block';
	}
  
 
  function getCaseStudiedTableByUserNameRow(thisVariable,ajaxUrl,download){
	 
	  $("#caseTable").html("");
		 // alert("table");
		 //alert( $(thisVariable).index())
		 var userName=$(thisVariable).closest('tr').children('td:first').text();
		 
//		 var  secondColumnValue = $(thisVariable).closest("tr").find("td:eq(1)").text();
	
	var currentDate=$(thisVariable).parent().parent().parent().find("tr:first").children('th:eq('+$(thisVariable).index()+')').text();
	var endDate,startDate="";

	 if(currentDate.substring(0,1)=='D'){
	startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	
	 }else {
   startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
   endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();
	 
	 }
	 	 var  stageNameProcess = $(thisVariable).closest("table").attr("stepname");

	var json="{";
	  json+="'userName':'"+userName+"',";
	   json+="'stageName':'"+stageNameProcess.trim()+"',";
	    
		
	//    json+="'process':'"+stageNameProcess.split("$")[1].trim()+"',";
	 // // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
	  json+="'dateFrom':'"+startDate+"','dateTo':'"+endDate+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
			var jsonKeyName=$(this).attr("name");
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					
					if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==8){
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
		
		
	//  alert(json);
	   generateTableByService(ajaxUrl,json,download);
	//	 document.getElementById('id01').style.display='block';
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
					
					if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==8){
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
	  // document.getElementById('id01').style.display='block';
  
  }
  
  
  
  function getCaseStudiedTableByTotalRow(thisVariable,ajaxUrl,download){
 
  var currentDate=$(thisVariable).parent().parent().parent().parent().find("tr:first").children('th:eq('+$(thisVariable).index()+')').text();
	var endDate,startDate="";
	
	 if(currentDate.substring(0,1)=='D'){
	startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	
	 }else {
   startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
   endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();
	 
	 }
	 

	   var json="{";

	   
	 json+="'dateFrom':'"+startDate+"','dateTo':'"+endDate+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
		var jsonKeyName=$(this).attr("name");
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					
					if(dropdownsCount==2||dropdownsCount==3||dropdownsCount==8){
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
	  // document.getElementById('id01').style.display='block';
	  
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



//******************Ely**************
function locationData(){
	 var location=[];
	 
	 $(".tableRow").each(function(){
		 
		
		 location.push($(this).text());
		 
		
	
	 }); 
	 
	 
	 var uniqueNames = [];
	   $.each(location, function(i, el){
	       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
	   });
	  // alert(JSON.stringify(uniqueNames))
	   return uniqueNames;
	 
}

 

 
 


 

 

function countData(countTable){

	
	 var roleCount=[];
	

	 		
	 	
	 		 $(".processTable").eq(countTable).each(function(){	
	 			roleCount=[];
	 			 
	 			 $(this).find(".busi").each(function(){
	 				
	 				//alert($(this).children().last().text());			
	 					 						 
	 				roleCount.push(parseFloat($(this).children().last().text())); 						
	 						

	 						 
	 						 
	 						 

	 				 
	 			 });//end of businessGroup
	 		 });//end of roletable
	 		
	 		
	 		
	 		 
	 		
	 			
	 	
	  
	  // alert("Main Json is"+JSON.stringify(mainJson)); 
	 	return roleCount;
	      
}

function rolesData(countTable){
	 var roles=[];
	
		
		 $(".processTable").eq(countTable).each(function(){	
			 roles=[];
			 $(this).find(".busi").each(function(){
		 
				 roles.push($(this).children().first().text());	  
	
	 }); 
		 });
	 
	
	 return roles;
	 
}






function toolTipData(countTable){
	
	 var thetooltips = [];
	 
	
	 
		var roleCount=countData(countTable);
		var  roleName=rolesData(countTable);
		
		
		thetooltips = new Array(roleCount.length);
		
	for ( var i = 0; i < roleName.length; i++) {
		
		  thetooltips[i] = "<table><tr><td style='color:red;padding:0'>"
								+ roleName[i]
								+ " : </td></tr><tr><td><span  style='color:#4897f1;padding:0'> Count : </span><span><b>"
								+ roleCount[i]
								+ " </b></span></td></tr></table>";
	      }
	  
	 
	/* if(countTable==1){
		 
		var roleCount=countData(1);
		var  roleName=rolesData(1);
		
		thetooltips = new Array(roleCount.length);
		
	for ( var i = 0; i < roleCount.length; i++) {
		
		  thetooltips[i] = "<table><tr><td style='color:red;padding:0'>"
								+ roleName[i]
								+ " : </td></tr><tr><td><span  style='color:#4897f1;padding:0'> Count : </span><span><b>"
								+ roleCount[i]
								+ " </b></span></td></tr></table>";
	      }
	  
}
	
	if(countTable==2){
		 
		var roleCount=countData(2);
		var  roleName=rolesData(2);
		
		thetooltips = new Array(roleCount.length);
		
	for ( var i = 0; i < roleCount.length; i++) {
		
		  thetooltips[i] = "<table><tr><td style='color:red;padding:0'>"
								+ roleName[i]
								+ " : </td></tr><tr><td><span  style='color:#4897f1;padding:0'> Count : </span><span><b>"
								+ roleCount[i]
								+ " </b></span></td></tr></table>";
	      }
	  
}
	 */
	
	
	 	return thetooltips;
	      
	 
	 
	 		 	 
	 
}//end of toolTipData





function maxCount(countTable){
	
	    var count=0;
	    $(".processTable").eq(countTable).each(function(){	
		$(".busi").each(function(){
		
			 
			var temp=parseInt($(this).children().last().text());
			if(temp>=count)
				count=temp;
			
		
			  
		});
	    });
	 	return count;	
	 
}//end of maxCount







function graphNumberOfCaseProcess(chartName,radioData){
	
	
	 var x;

	
	 var roles;


	 var location;
	 var roles;

	 var thetooltips;
	 var title;
	
	
	

		
		x=countData(radioData);

		
			
		roles=rolesData(radioData);
		
		

		
		//location=locationData(0);
		

		thetooltips=toolTipData(radioData);
		title="Total Count"
		 
	
	
	
			  
           
			 var maxiCount= maxCount(radioData);
           
           
          
			    
          chartName.chart({
		 template : "line_basic_6",
		 tooltips :thetooltips,
		 

		 values : {
				serie1 : x
			},
		 labels : roles,
		 



		 defaultSeries : {
		  type : "bar"
		 
		 },
		 
		 axis : {
		  r : {
		   max : maxiCount,
		   
		  },
		l : {
			title : title
		},
		x : {
			title : "User Name"
		}
		 }
		});
          
          $("#titleDiv").remove();
          $(chartName).prepend('<html><div id="titleDiv"> <h4 style="color:purple">Title : Number of cases Processed By User</h5></div></html>');
			    
			      
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
					x : {
						  titleProps : {
								fill : "red",
								"font-size" : "18px",
								
							},
					 titleDistance : 32,
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
						titleDistance : 30,
						labelsMargin : 10,
						labelsDistance : 18
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
</body>
</html>