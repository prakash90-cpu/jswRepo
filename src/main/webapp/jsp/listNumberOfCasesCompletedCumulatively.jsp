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
<h2 class="w3-center w3-xxlarge"><i>Commercial</i></h2>
					<h4 class="w3-text-blue">
						<b>Report 7- Number of cases completed cumulatively (End Time)
</b>
					</h4>
				
				



<form:form commandName="FunctionAppBean" action="/jsw-report/CommController/listCasesCompletedCumulativelyCOMM">				
				
				
				
				
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
        <tr><td><label>Date From </label></td><td><form:input onchange="setToBucketFromDate()" style="width:50%" type="date" id="dateFrom" path="fromDate" /> (mm/dd/yyyy)</td>
       <td><label>Date To </label></td><td><form:input  onchange="setToBucketToDate()" style="width:50%" type="date"  path="toDate" id="dateTo" /> (mm/dd/yyyy)</td></tr>
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
			<c:if test="${settingValue==0}"> 
			<br>
   
    
    <div class="w3-left"><label>Tabular</label>	
		<input type="radio" onChange="$('#listCaseCreated').show();$(' #chartContainer').hide();" class="w3-radio"  checked name="graph">
		<label>Graphical</label>	

		<input onChange="$('#listCaseCreated').hide();$('#chartContainer').show();graphNumberOfCase();" class="w3-radio" type="radio" name="graph">
		
		 </div>
		<center> 
		<!-- <h5 style="color:green">Title : Number of cases completed Cumulative</h5> -->
		<div id="chartContainer" style="height:500px; width: 80%;display:none">
  </div></center>

 
		
		</c:if>
		<%-- 	<c:if test="${settingValue==2}"> 
			<script>
			
			
			$('#listCaseCreated').hide();
			$('#chartContainer').show();
			
			
			
			</script>
			</c:if> --%>
    <p class="w3-right currentDate" id=""></p>
    <br>
    <div id="listCaseCreated">
<table 
					class="w3-table-all w3-hoverable w3-striped w3-border w3-small w3-center">
					
						<tr class="w3-center w3-medium" id="firstTr"  style="background-color:#372d87;color:white">
						
						
							<td class="w3-center">BusinessGroup / Location</td>
						
						
						
						
						 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3/7/10/15/15+')}">
    
					
					
					
					
						<td class="w3-center">Bucket 1
						<br> within 3 Days</td>
							<td class="w3-center">Bucket 2
						<br> within 7 Days</td>
							<td class="w3-center">Bucket 3
						<br> within 10 Days</td>
							<td class="w3-center">Bucket 4
						<br> within 15 Days</td>
							<td class="w3-center">Bucket 5
						<br> More_than 15 Days
					<span  onclick="getCaseCreatedAll('listCaseCreated', 'COMM Number of Case Completed Cumulatively')" class="w3-badge fa fa-download  w3-right w3-hover-blue"></span>
					
						</td>
						
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '15/30/45/45+')}">
         				
					
					
						<td class="w3-center">Bucket 1
						<br> within 15 Days</td>
							<td class="w3-center">Bucket 2
						<br> within 30 Days</td>
							<td class="w3-center">Bucket 3
						<br> within 45 Days</td>
							<td class="w3-center">Bucket 4
						<br> More than 45 Days</td>
							
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '1/2/3/4/4+')}">
			
						<td class="w3-center">bucket 1
						<br>within 1 Week</td>
							<td class="w3-center">bucket 2
						<br> within 2 Week</td>
							<td class="w3-center">bucket 3
						<br> within 3 Week</td>
							<td class="w3-center">bucket 4
						<br> within 4 Week </td>
							<td class="w3-center">bucket 5
						<br> More than 4 Week
						
						</td>
						
    </c:when>  
    
    
    </c:choose>
  
						
					<!-- 	<td class="w3-center">Total
						
						
						
				
						</td> -->
							
						</tr>

<% String groupName=""; %>


						<c:forEach var="listCases" items="${listCasesCreated}">
							<tr class="">
	 <c:set var="groupName" value="${listCases.name}"/>
 <%
 
    if(groupName.equals((String)pageContext.getAttribute("groupName"))){}
    else{
    	
    
  %>
  			<tr class=" w3-medium"  style="background-color:#aabdf7">
  			<td class="busi" >${listCases.name}</td>
  			
  			
  			
  			
  			
  			
  			
  			
  				 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3/7/10/15/15+')}">
       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
					       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '15/30/45/45+')}">
         	       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			
  			
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '1/2/3/4/4+')}">
    	       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       		<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			       	<td class="w3-center" title="Total of all ${listCases.name} Role"></td>
  			
    </c:when>  
    
    
    </c:choose>
  			
  			
  			
  			
  			
  			<%-- 
    			<td class="w3-center" onclick="getCaseStudiedTableByBusinessGroup(this,'getCasesCompletedCumulativelyCOMM',0)"
    			
    			title="Total of all period on ${listCases.name} Role"
    			>
    			<span  onclick="getCaseStudiedTableByBusinessGroup(this,'getCasesCompletedCumulativelyCOMM',0)" class="fa fa-download generateXLS w3-right w3-hover-blue"></span>
								
    			</td> --%>
  
  </tr>
  
  <%}
 
 groupName=(String)pageContext.getAttribute("groupName");%>
					
						
<td class="w3-center firstTd bus${listCases.name}" colorName="${listCases.color}" aliasName="${listCases.alias}">${listCases.stepName}</td>
								
								
								
								
								
							
											
											 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3/7/10/15/15+')}">
		<td class="w3-center" style='cursor:pointer' onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket1}</td>
										<td style='cursor:pointer' class="w3-center" onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket4}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket5}</td>
										
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '15/30/45/45+')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket4}</td>
											
											
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '1/2/3/4/4+')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket3}</td>
 
 	<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket4}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByLocationRow(this,'getCasesCompletedCumulativelyCOMM',0);">${listCases.bucket5}</td>
 
    </c:when>  
    
    
    </c:choose>
											
											
											
											
								<%-- <td  class="w3-center w3-text-blue" onClick="getCaseStudiedTableByLocation(this,'getCasesCompletedCumulativelyCOMM',0);">
								<u class="w3-hover-blue w3-hover-text-black"
								 style="cursor:pointer"
								 >
								
								${listCases.count}
								</u>
								
								<span onClick="getCaseStudiedTableByLocation(this,'getCasesCompletedCumulativelyCOMM',0)"  class="fa fa-download generateXLS w3-right"></span>
								
								
								</td> --%>
								

							</tr>
						</c:forEach>
						
						
					<%-- 	<tr class="w3-center w3-medium"   style="background-color:black;color:white">
						
						
							<td class="w3-center">Grand Total</td>
						
						
						
  			
  			
  			
  			 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3/7/10/15/15+')}">
          <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  				<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>   
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  			
						
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '15/30/45/45+')}">
         <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  				<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '1/2/3/4/4+')}">
      <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
  				<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getCasesCompletedCumulativelyCOMM',0);">Total of all Role StepName</td>
    </c:when>  
  			
  			</c:choose>
    			<td class="w3-center" onClick="getCaseStudiedTableByTotal(this,'getCasesCompletedCumulativelyCOMM',0);" >Total of all column on totals  
    			
    			<span   class="fa fa-download generateXLS w3-right"></span></td>
							
						</tr> --%>
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


 /* 

 $(".firstTd").each(function(){

		var countTotal=0;
		$(this).parent().children().not(':first').not(':last').each(function(){
			countTotal+=parseInt($(this).text()) ;
			
		});
		
		$(this).parent().children().last().find("u").text(countTotal);



 }); */

 $(".busi").each(function(){
	var totalTd=$(this).parent().children("td").length;
	 var busiGroup=$(this).text();
	
	for(var count=1;count<totalTd;count++){
		
		var contEachTd=0;
		
		 $(this).parent().siblings().find(".bus"+busiGroup).each(function(){
			 //alert($(this).text());
			 contEachTd+= parseInt($(this).parent().find('td:eq('+count+')').text());; 
			 
			 
		 });
		 
		/*  if(count==totalTd-1)
		 $(this).parent().find('td:eq('+count+')').append("<br><a style='cursor:pointer' \" onclick=\"getCaseStudiedTableByBusinessGroup(this,'getCasesCompletedCumulativelyCOMM',0);\">"+contEachTd+"</a>");
		 else */
			 $(this).parent().find('td:eq('+count+')').append("<br><a style='cursor:pointer' onclick=\"getCaseStudiedTableByBusinessGroupRow(this,'getCasesCompletedCumulativelyCOMM',0);\" >"+contEachTd+"</a>");
			
	}	 
	
	 });

 
	/*  var totalTd=$("#firstTr").children("td").length;

	// alert(totalTd);
	for(var count=1;count<totalTd;count++){
		
		
		var contEachTd=0;
		 $(".busi").each(function(){
				
			// alert($(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,'')); 
		contEachTd+= parseInt( $(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,''));
		
		
		 });
		 $("#firstTr").parent().find("tr").last().find('td:eq('+count+')').append("<br><a  style='cursor:pointer'>"+contEachTd+"</a>");
		// alert($("#firstTr").parent().find("tr").last().find('td:eq('+count+')').text())
		 
	}
	 
	 */

 
 
 
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

 
  $(function(){

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
 
function getCaseStudiedTableByLocation(thisVariable,ajaxUrl,download){
	$("#caseTable").html("");

	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	  
	var busiGroup=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
	   
  var json="{";
  json+="'Loc':'"+firstColumnValue+"','busiGroup':'"+busiGroup+"',";
  
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
  // document.getElementById('id01').style.display='block';
 
}





function getCaseStudiedTableByLocationRow(thisVariable,ajaxUrl,download){
	$("#caseTable").html("");

	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	  
	    var busiGroup=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
	
/*	  
var currentDate=$(thisVariable).parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();

 var endDate,startDate="";



if(currentDate.substring(0,1)=='D'){
startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);

}else {
startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();

} */



var bucketValue=$(thisVariable).parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();

var bucketFrom=0;
var bucketTo=bucketValue.split(" ")[3];

var json="{";
json+="'bucketFrom':'"+bucketFrom+"',";

if($(thisVariable).index()!=5)
json+="'bucketTo':'"+bucketTo+"',";

 json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
   
  json+="'Loc':'"+firstColumnValue+"','busiGroup':'"+busiGroup+"',";
  
 // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
 // json+="'dateFrom':'"+startDate+"','dateTo':'"+endDate+"',";
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












  function getCaseStudiedTableByBusinessGroup(thisVariable,ajaxUrl,download){
	 
		$("#caseTable").html("");
		
		    var busiGroup=$(thisVariable).closest('tr').children('td:first').text();
		 

	  var json="{";
	  json+="'busiGroup':'"+busiGroup+"',";
	  
//	  var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
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
	    
/* 	var currentDate=$(thisVariable).parent().parent().parent().find("tr:first").children('td:eq('+$(thisVariable).parent().index()+')').text();
	var endDate,startDate="";
	
	 if(currentDate.substring(0,1)=='D'){
	startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	
	 }else {
   startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
   endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();
	 
	 } */
	 
	 

var bucketValue=$(thisVariable).parent().parent().parent().find("tr:first").children('td:eq('+$(thisVariable).parent().index()+')').text();

var bucketFrom=0;
var bucketTo=bucketValue.split(" ")[3];


	var json="{";
	  json+="'busiGroup':'"+busiGroup+"',";
	    json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
	
	  
	    json+="'bucketFrom':'"+bucketFrom+"',";

	    if($(thisVariable).index()!=5)
	    json+="'bucketTo':'"+bucketTo+"',";
	 // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
	//  json+="'dateFrom':'"+startDate+"','dateTo':'"+endDate+"',";
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
		// document.getElementById('id01').style.display='block';
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
	  // document.getElementById('id01').style.display='block';
  
  }
  
  
  
  
  function getCaseStudiedTableByTotalRow(thisVariable,ajaxUrl,download){
	    var json="{";

/* 	  
var currentDate=$(thisVariable).parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();

	var endDate,startDate=""; */
	
	
	var bucketValue=$(thisVariable).parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();

var bucketFrom=0;
var bucketTo=bucketValue.split(" ")[3];
	var json="{";
	   
	json+="'bucketFrom':'"+bucketFrom+"',";

	if($(thisVariable).index()!=5)
	json+="'bucketTo':'"+bucketTo+"',";
	  	    json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
	
	/* 
	 if(currentDate.substring(0,1)=='D'){
	startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	
	 }else {
   startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
   endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();
	 
	 }
	 
	   json+="'dateFrom':'"+startDate+"','dateTo':'"+endDate+"',"; */
	   
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


/* $( "select[name='DisplayBy']" ).change(function() {

		setToDate(this);
}); */



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





 	
  
 
  //******************Ely**************
 function countData(){
	 var mainJson=[];
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
	 		 var jsonCount = [];
	 		 var jsonBusiness = [];
	 		
	 		
	 		var loc=uniqueNames[i];
	 	
	 				 			
	 			 
	 			 $(".busi").each(function(){
	 				 var count=0;
	 				  var busiGroup2=$(this).text();
	 				 
	 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
	 					 var temp2=$(this).text();
	 					// alert("temo"+temp2);
	 					
	 				  if(loc==temp2){ 					
	 											
	 						
	 						 
	 					 jsonCount.push(parseInt($(this).parent().children().last().text())); 						
	 						
	 						 
	 						 count++; 
	 					 }
	 						 
	 					 
	 				 });//end of location
	 				
	 				 if(count==0){
	 							 
	 					jsonCount.push(0);
	 					
	 					  
	 				 }//end of if
	 				 
	 				 
	 				 
	 			 });//end of businessGroup
	 			 
	 			 
	 			 
	 			
	 			/*  alert("hhh"+JSON.stringify(mainJsondataArray)); */
	 			mainJson["serie"+i]=jsonCount;
	 	}
	  
	   
	 	return mainJson;
	      
 }
 
 function businessdata(){
	 var busiGroup=[];
	 
	 $(".busi").each(function(){
 		 
		 busiGroup.push($(this).text());	  
	
	 }); 
	 return busiGroup;
	 
 }
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
	   return uniqueNames;
	 
 }
 
 
 
 
 
 function toolTipData(){
	 
	 var mainToolTip=[];
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
 
 
 function aliasData(){
	 var locationAlias=[];
	 
	 $(".busi").each(function(){
		 var busiGroup2=$(this).text();
		 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
		 locationAlias.push($(this).attr("aliasName"));	  
	
	 }); 
	 }); 
	 return locationAlias;
	 
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
 

 function graphNumberOfCase(){
	
 	 
 	
	 var jsonString = JSON.stringify(countData());
 	 var x=countData();
 	var aliasValue=aliasData();
 	
 	 
 	
 	
 	
 	
 	
 			    
 			var location=locationData(); 
 			
 			   
 			    var business=businessdata();
 			   
 			   
 			 
 			 // alert("hhh"+jsonString1);
 			  
 			 var thetooltips = toolTipData();
 			
            
             /*  alert(JSON.stringify(colorValue));
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
			title : "Count "
		},
		x : {title : 'Business Group', titleDistance: 35},
 		 }
 		});
 			
 	$("#titleDiv").remove(); 
    $('#chartContainer').prepend('<html><div id="titleDiv"> <h4 style="color:purple">Title : Number of cases Completed Cumulatively</h5></div></html>');
 	
 	var descData='';

	//alert(JSON.stringify(aliasValue));
	
	$('#locDesc').html();
	
	
	for(i=0;i<location.length;i++){
		
		
		descData+='<tr><td width="100">' + location[i] + '</td>'+'<td width="118">'+aliasValue[i]+'</td>'+'</tr>';
	}
	  	
	
	 $("#divDesc").remove();
   $('#chartContainer').append('<html><div id="divDesc"><table width="80%" border="1" id="locTable"  ><tr class="w3-blue"><td>' + " Location FullName" +'</td>' +'<td>'+" Location shortName"+ '</td></tr>'+descData+'</table></div></html>');

 	
 	
 			    
 			      
 }
 

 
 $.elycharts.templates['line_basic_6'] = {
		 type : "line",
		
		 margins : [10, 100, 50, 60],
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
					
					height : 90,
					width : 150,
					padding : [ 7, 10 ],
					offset : [ -10, 0 ],
					frameProps : {
						opacity :1,
						fill : "white",
						stroke : "#000"
					}
				}
			},
			 series : legendColorData, 
		 defaultAxis : {
		  labels : true
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
				titleDistance : 26,
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
				  
				   itemWidth : "fixed",
				   height : 250,
				   x :860,
				   y : 10,
				  
				   dotProps: { "stroke-width": 0 },
			        textProps: { "fill": "#404040" },
			        dotWidth: 15,
			        dotHeight: 20,
			        dotR: 5
				  } 
			 },
		 barMargins : 40
		}               	
	    
 

</script>
</body>
</html>