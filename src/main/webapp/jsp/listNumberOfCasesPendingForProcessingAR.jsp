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
						<b>Report 8-Number of Cases pending for processing from the last role start time
</b>
					</h4>
				



<form:form commandName="FunctionAppBean" action="/jsw-report/ARController/listCasesPendingForProcessingAR">				
				
				
				
				
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
	
<%-- <c:choose>
    <c:when test="${functionName.name=='Location'}">
        <tr><td><label>Date From </label></td><td><form:input  onchange='setToBucketFromDate()' style="width:50%" type="date" id="dateFrom"  path="fromDate" /></td>
       <td><label>Date To </label></td><td><form:input  onchange='setToBucketToDate()' style="width:50%" type="date"  path="toDate" id="dateTo" /></td></tr>
    </c:when>    
  
</c:choose>
 --%>			</c:forEach>		
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

		<input onChange="$('#listCaseCreated').hide();$('#chartContainer').show();graphNumberOfCasePendProcess();" class="w3-radio" type="radio" name="graph">
		
		 </div>
		<center> <div id="chartContainer" style="height:500px; width: 100%;display:none">
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
					class="w3-table-all w3-hoverable w3-striped w3-border  w3-center">
					
						<tr class="w3-text-white w3-blue w3-center " id="firstTr">
						
						
							<td class="w3-center">Role</td>
						
						
						
			<%-- 			
						 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3/7/11/15/15+')}">
     --%>
					
					
					
					
						<td class="w3-center">Bucket 1
						<br> 0 - 1</td>
							<td class="w3-center">Bucket 2
						<br> 1 - 3</td>
							<td class="w3-center">Bucket 3
						<br> 3 - 7</td>
							
							<td class="w3-center">Bucket 4
						<br> 7 +</td>
						
						
	<%-- 					
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '15/30/45/45+')}">
         				
					
					
						<td class="w3-center">Bucket 1
						<br> 0 - 15</td>
							<td class="w3-center">Bucket 2
						<br> 16 - 30</td>
							<td class="w3-center">Bucket 3
						<br> 31 - 45</td>
							<td class="w3-center">Bucket 4
						<br> 45+</td>
							
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '1/2/3/4/4+')}">
			
						<td class="w3-center">Bucket 1
						<br> 0 - 1 week</td>
							<td class="w3-center">Bucket 2
						<br> 1 - 2 week</td>
							<td class="w3-center">Bucket 3
						<br> 2- 3 week</td>
							<td class="w3-center">Bucket 4
						<br> 3 - 4 week </td>
							<td class="w3-center">Bucket 5
						<br> 4+ week</td>
						
    </c:when>  
    
    
    </c:choose> --%>
  
						
						<td class="w3-center">Total
						
						
						<span  onclick="getCaseCreatedAll('listCaseCreated', 'AR Number of Case Pending for Processing from last Role start time')" class="w3-badge fa fa-download  w3-right w3-hover-blue"></span>
				
						</td>
							
						</tr>

<% String groupName=""; %>

						<c:forEach var="listCases" items="${listCasesCreated}">
							<tr class="busi" aliasName="${listCases.alias}">
	
						
<td class="w3-center firstTd bus${listCases.name}" colorName="${listCases.color}">${listCases.name}</td>
								
								
								
								
							<%-- 	
							
											
											 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}"> --%>
		<td class="w3-center" style='cursor:pointer' onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket1}</td>
										<td style='cursor:pointer' class="w3-center" onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket4}</td>
										<%-- <td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket5}</td>
										 --%>
						
  <%--   </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket4}</td>
											
											
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket3}</td>
 
 	<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket4}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByRoleRow(this,'getCasesPendingForProcessingAR',0)">${listCases.bucket5}</td>
 
    </c:when>  
    
    
    </c:choose>
											
								 --%>			
											
											
								<td  class="w3-center w3-text-blue">
								<u class="w3-hover-blue w3-hover-text-black" onClick="getCaseStudiedTableByRole(this,'getCasesPendingForProcessingAR',0)"
								 style="cursor:pointer"
								 >
								
								${listCases.count}
								</u>
								
								<span onClick="getCaseStudiedTableByRole(this,'getCasesPendingForProcessingExcelAR',1)"  class="fa fa-download w3-right"></span>
								
								
								</td>
								

							</tr>
						</c:forEach>
						
						 <%--
						<tr class="w3-text-white w3-black w3-center w3-medium">
						
						
							<td class="w3-center">Grand Total</td>
						
						
						
  			
  			
  			 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}"> 
          <td class="w3-center">Total of all Role StepName</td>
  			<td class="w3-center">Total of all Role StepName</td>
  			<td class="w3-center">Total of all Role StepName</td>
  				<td class="w3-center">Total of all Role StepName</td>   
  			<!-- <td class="w3-center">Total of all Role StepName</td>
  			 -->
						
						
					
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         <td class="w3-center">Total of all Role StepName</td>
  			<td class="w3-center">Total of all Role StepName</td>
  			<td class="w3-center">Total of all Role StepName</td>
  				<td class="w3-center">Total of all Role StepName</td>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
      <td class="w3-center">Total of all Role StepName</td>
  			<td class="w3-center">Total of all Role StepName</td>
  			<td class="w3-center">Total of all Role StepName</td>
  			<td class="w3-center">Total of all Role StepName</td>
  				<td class="w3-center">Total of all Role StepName</td>
    </c:when>  
    
    
    </c:choose>
  		
  			
  			
    			<td class="w3-center" onClick="getCaseStudiedTableByTotal(this,'getCasesPendingForProcessingAR',0)" >Total of all column on totals  <span   class="fa fa-download generateXLS w3-right"></span></td>
							
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
		 $(this).parent().find('td:eq('+count+')').append("<br><a style='cursor:pointer' \" >"+contEachTd+"</a>");
		 else
			 $(this).parent().find('td:eq('+count+')').append("<br><a style='cursor:pointer' onclick=\"getCaseStudiedTableByBusinessGroupRow(this,'getCasesPendingForProcessingAR',0)\" >"+contEachTd+"</a>");
			
	}	 
	
	 });

 
	/*  var totalTd=$("#firstTr").children("td").length;

	// alert(totalTd);
	for(var count=1;count<totalTd;count++){
		
		
		var contEachTd=0;
		 $(".busi").each(function(){
				
			// alert($(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,'')); 
		contEachTd+= parseInt( $(this).find('td:eq('+count+')').text().replace(/[^0-9]/g,''));
		
		
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
 

  
function getCaseStudiedTableByRole(thisVariable,ajaxUrl){
	$("#caseTable").html("");

	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	  
	   
  var json="{";
  json+="'currentRole':'"+firstColumnValue+"',";
  
 // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
  //json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
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
			 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=7&&dropdownsCount!=8) {
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



  function getCaseStudiedTableByRoleRow(thisVariable,ajaxUrl){
  	$("#caseTable").html("");
  	 
  	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
  	  

  	  
  var currentBucket=$(thisVariable).parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();




  var json="{";
    json+="'currentRole':'"+firstColumnValue+"',";
     json+="'bucketFrom':'"+currentBucket.split(" ")[2]+"',";
     
     
     var bucketTo=currentBucket.split(" ")[4];
     if(bucketTo !== undefined)
        json+="'bucketTo':'"+bucketTo+"',";

  //  var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
   // json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
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
  			 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=7&&dropdownsCount!=8) {
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
  	
  	
    
   
     generateTableByService(ajaxUrl,json);
  	 document.getElementById('id01').style.display='block';
  }

  
  
function getCaseStudiedTableByTotal(thisVariable,ajaxUrl){
	  var json="{";

	  
	 // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
	 // json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
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
	   generateTableByService(ajaxUrl,json);
	   document.getElementById('id01').style.display='block';
  
  }
  
  
  
  
  function getCaseStudiedTableByTotalRow(thisVariable,ajaxUrl){
	    var json="{";

 
	 var currentBucket=$(thisVariable).parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();

	 
	    json+="'bucketFrom':'"+currentBucket.split("-")[0].trim().split(" ")[2]+"',";
      json+="'bucketTo':'"+currentBucket.split("-")[1].trim()+"',";
 // var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
  //json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
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
	   generateTableByService(ajaxUrl,json);
	   document.getElementById('id01').style.display='block';
	  
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


/* $( "select[name='DisplayBy']" ).change(function() {

		setToDate(this);
}); */






 	
  
 
  //******************Ely**************
    
	  function countData(){
	
 	 var count=[];
 	 
 	 
 	 
 	   $(".busi").each(function(){
 		 
 		 
 		
 		count.push($(this).children().last().find("u").text());
 	
 		
 	
 	 }); 
 	   
 	   
 	return count;
	      
 }
 
 function stageData(){
	 var stages=[];
	 
	 $(".busi").each(function(){
 		 
		 stages.push($(this).children().first().text());	  
	
	 }); 
	 
	 var uniqueNames = [];
	   $.each(stages, function(i, el){
	       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
	   });
	   
	 return uniqueNames;
	 
 }
 
 

 
 
 
 
 
 function toolTipData(){
	 var countValue = countData();
	 var stageName=stageData();
	 
	 var thetooltips = new Array(countValue.length);
		
	 var thevalues = new Array(countValue.length);
	 
	 var aliasValue=aliasData();
	  
	for ( var i = 0; i < countValue.length; i++) {
		 
		  thevalues[i] = countValue[i];
		  thetooltips[i] = "<table><tr><td style='color:red;padding:0'>"
								+ stageName[i]
								+ " : </td></tr><tr><td><span  style='color:#4897f1;padding:0'> Count : </span><span><b>"
								+ countValue[i]
								+ " </b></span></td></tr></table>";
	      }
	   
	 	return thetooltips;
	      
	 
	 
	 
	 
 }//end of toolTipData
 
 

 
 

 
 function aliasData(){
	 var roleAlias=[];
	 
	 $(".busi").each(function(){
 		 
		 roleAlias.push($(this).attr("aliasName"));	  
	
	 }); 
	 return roleAlias;
	 
 }
 
 
 
/*  function maxCount(){
	    var count=0;
		$(".roleName").each(function(){
			 
			 
			var temp=parseInt((this).last().find("u").text());
			if(temp>=count)
				count=temp;
			
	
			  
		});
		
	 	return count;	
	 
}//end of maxCount
  */
 

 

 function graphNumberOfCasePendProcess(){
	
 	
 	
 	 var jsonString = JSON.stringify(countData());
 	 var x=countData();
 	var aliasValue=aliasData();
 	
 	 var stageName=stageData();
 	
 	
 			 
 			 // alert("hhh"+jsonString1);
 			  
 			 var thetooltips = toolTipData();
 			
            
            // alert(JSON.stringify(x));
             
 			  
 			 
 			/*  var maxiCount= maxCount(); */
 			  
 			 
 			    
 	$("#chartContainer").chart({
 		 template : "line_basic_6",
 		 tooltips :thetooltips,
 		 
 		values : {
			serie1 : x
		},
 		 labels : aliasValue,
 	      
 		 defaultSeries : {
 		  type : "bar",
 		  stacked : true
 		 },
 		
 		 axis : {
 		  /* r : {
 		   max : maxiCount,
 		   
 		  }, */
 		l : {
			title : "Case Pending Count"
		},
		x : {title : 'Roles', titleDistance: 35},
 		 }
 		});
 	 $("#titleDiv").remove(); 
     $('#chartContainer').prepend('<html><div id="titleDiv"> <h4 style="color:purple">Title : Number of cases Pending For Processing </h5></div></html>');    
 	var descData='';

	//alert(JSON.stringify(aliasValue));
	
	$('#locDesc').html();
	
	
	for(i=0;i<stageName.length;i++){
		
		
		descData+='<tr><td width="100">' + stageName[i] + '</td>'+'<td width="118">'+aliasValue[i]+'</td>'+'</tr>';
	}
	  	
	
	 $("#divDesc").remove();
   $('#chartContainer').append('<html><div id="divDesc"><table width="80%" border="1" id="locTable"  ><tr class="w3-blue"><td>' + " Role FullName" +'</td>' +'<td>'+" Role shortName"+ '</td></tr>'+descData+'</table></div></html>');

 	
 	
 			    
 			      
 }
 

 
 $.elycharts.templates['line_basic_6'] = {
		 type : "line",
		
		 margins : [30, 200, 50, 60],
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
				labelsRotate : 0,
				labelsMargin : 20,
				labelsDistance : 15,
				labelsHideCovered : false
			},
			axis : {
				x : {
					titleProps : {
						fill : "RED",
							"font-size" : "15px"
					},
					
					labelsMargin : 10,
					
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
					titleDistance : 50,
					labelsMargin : 10,
					labelsDistance : 35
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
			 barMargins : 30
	};
 
	    
 

</script>
</body>
</html>