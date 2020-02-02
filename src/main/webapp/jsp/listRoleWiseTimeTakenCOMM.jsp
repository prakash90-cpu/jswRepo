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
<h2 class="w3-center w3-xxlarge"><i>Commercial</i></h2>
					<h4 class="w3-text-blue">
						<b>Report 4- Role wise time taken to complete the process (End Time)
</b>
					</h4>
				



<form:form commandName="FunctionAppBean" action="/jsw-report/CommController/listRoleWiseTimeTakenCOMM">				
				
				
				
				
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
        <c:when test="${not empty listRoleWiseTimeTaken}">
        
	
  
    <br><br>
 

    <%String timeTaken[]={"Average Time(HOUR)","Number of Count "};

    int countTimeTaken=0;%>
  
    		<c:forEach var="listCasesCreated" items="${listRoleWiseTimeTaken}">
    		
    		
    			<br /><br />
    			<c:if test="${settingValue==0}"> 
    		
    	<div class="w3-left"><label>Tabular</label>	

		<input type="radio" onChange="$('#listCaseCreated<%=countTimeTaken %>').show();$(' #chartContainer<%=countTimeTaken %>').hide();" class="w3-radio" style="padding-right:20px" checked name="graph<%=countTimeTaken %>">
		<label>Graphical</label>	
 <input   onChange="$('#listCaseCreated<%=countTimeTaken %>').hide();$('#chartContainer<%=countTimeTaken %>').show();$('#table-btn').show();graphNumberOfCaseProcessComm($('#chartContainer<%=countTimeTaken %>'),<%=countTimeTaken %>);" class="w3-radio" type="radio" name="graph<%=countTimeTaken %>">
 
</div><br /><br />
    		
    		<center>
     	
		 <div id="chartContainer<%=countTimeTaken %>" class="w3-center" style="height:150%; width:120% ; display:none"></div>
	<!-- style="height:100%; width:80% -->
		</c:if>
  </center>	
    		
    		    <div id="listCaseCreated<%=countTimeTaken %>">
    		<h5 class="w3-left"><b> Role wise Total <%=timeTaken[countTimeTaken] %></b></h5>
    		  <p class="w3-right currentDate" id=""></p>
<table 
					class="w3-table-all w3-hoverable w3-striped w3-border w3-center roleTable" id='<%=timeTaken[countTimeTaken]%>'>
					
						<tr class="w3-text-white w3-center " style="background-color: #004d99" id="firstTr">
						
						
							<td class="w3-center">Role / User</td>
						
						
						
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
						
						<td class="w3-center">Total <%=timeTaken[countTimeTaken] %>
						
						
						<span  onclick="getCaseCreatedAll('<%=timeTaken[countTimeTaken++] %>', 'COMM  Role wise time taken to complete process')" class="w3-badge fa fa-download  w3-right w3-hover-blue"></span>
				
						</td>
							
						</tr>

<% String groupName=""; %>
	

						<c:forEach var="listCases" items="${listCasesCreated}">
						
						

						
						
						
	 <c:set var="groupName" value="${listCases.name}"/>
 <%
 
    if(groupName.equals((String)pageContext.getAttribute("groupName"))){}
    else{
    	
    
  %>
  			<tr class="" style="background-color: #d6e0f5">
  			
  			<td class="busi" aliasName="${listCases.alias}">${listCases.name}</td>
  			
  			
  			
  			
  			
  			
  			
  			
  				 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
       	<td class="w3-center"></td>
  			<td class="w3-center"></td>
  			<td class="w3-center"></td>
						<td class="w3-center"></td>
  			<td class="w3-center"></td>
  			<td class="w3-center"></td>
					<td class="w3-center"></td>		
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         	<td class="w3-center"></td>
  			<td class="w3-center"></td>
  			<td class="w3-center"></td>
  				<td class="w3-center"></td>
  			
  			
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
    	<td class="w3-center"></td>
  			<td class="w3-center"></td>
  			<td class="w3-center" ></td>
    </c:when>  
    
    
    </c:choose>
  			
  			
  			
  			
  			
  			
    			<td class="w3-center">
    			
    			<span  onclick="getCaseStudiedTableByRoleName(this,'getRoleWiseTimeTakenExcelCOMM',1)" class="fa fa-download generateXLS w3-right w3-hover-blue"></span>
								
    			</td>
  
  </tr>
  
  <%}
 
 groupName=(String)pageContext.getAttribute("groupName");%>
						<tr class="">
						
<td class="w3-center firstTd bus${listCases.name}" colorName="${listCases.color}">${listCases.location}</td>
								
								
								
								
								
							
											
											 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
		<td class="w3-center w3-text-hover-blue" style='cursor:pointer' onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.day1}</td>
										<td style='cursor:pointer' class="w3-center" onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.day2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.day3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.day4}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.day5}</td>
											<td class="w3-center" style='cursor:pointer' onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.day6}</td>
						
							<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.day7}</td>
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.week1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.week2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.week3}</td>
													<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.week4}</td>
											
											
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   		<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.period1}</td>
										<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.period2}</td>
											<td style='cursor:pointer' class="w3-center"  onClick="getCaseStudiedTableByUserNameRow(this,'getRoleWiseTimeTakenCOMM',0)">${listCases.period3}</td>
    </c:when>  
    
    
    </c:choose>
											
											
											
											
								<td  class="w3-center w3-text-blue">
								<u class="w3-hover-blue w3-hover-text-black" onClick="getCaseStudiedTableByUserName(this,'getRoleWiseTimeTakenCOMM',0)"
								 style="cursor:pointer"
								 >
								
								${listCases.count}
								</u>
								
								<span onClick="getCaseStudiedTableByUserName(this,'getRoleWiseTimeTakenExcelCOMM',1)"  class="fa fa-download w3-right"></span>
								
								
								</td>
								

							</tr>
						</c:forEach>
						
						
						<%-- <tr class="w3-text-white w3-black w3-center w3-medium">
						
						
							<td class="w3-center">Grand Total</td>
						
						
						
  			
  			
  			 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
          <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  			<td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  		       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  	       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  		       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  	       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  		       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
						
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
        <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  			       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  		       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  			       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  	       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
  		       <td class="w3-center" onClick="getCaseStudiedTableByTotalRow(this,'getRoleWiseTimeTakenCOMM',0)"></td>
    </c:when>  
    
    
    </c:choose>
  		
  			
  			
    			<td class="w3-center"  >Total of all column on totals 
    			 <span onClick="getCaseStudiedTableByTotal(this,'getRoleWiseTimeTakenExcelCOMM',1)"  class="fa fa-download  w3-right"></span></td>
							
						</tr> --%>
				</table>
				
				</div>
				<br><br>
				</c:forEach>
				
				
			</c:when>
		<c:when test="${listRoleWiseTimeTaken ne null}">
<br>
<div class='w3-container  w3-border w3-round-xxlarge '>

<br>
<h3 class='w3-center'>No output</h3>
<br><br>
</div>
</c:when>
</c:choose>
				</div><br><br>

	 
			<!-- End page content -->
		</div>
	
		
		
   
	<%@ include file="Footer.jsp"%>
	</div>
	 <c:set var="locationName" value="${FunctionAppBean.getLocation()}"/>
	<c:set var="businessName" value="${FunctionAppBean.getBusiness()}"/>
	
 <script>


 

/*  $(".firstTd").each(function(){

		var countTotal=0;
		$(this).parent().children().not(':first').not(':last').each(function(){
			countTotal+=parseFloat($(this).text()) ;
			
		});
		
		$(this).parent().children().last().find("u").text(countTotal.toFixed(2));



 }); */




 $(".busi").each(function(){
	var totalTd=$(this).parent().children("td").length;
	 var busiGroup=$(this).text();
	
	for(var count=1;count<totalTd;count++){
		
		var contEachTd=0;
		
		 $(this).parent().siblings().find(".bus"+busiGroup).each(function(){
			 //alert($(this).text());
			 contEachTd+= parseFloat($(this).parent().find('td:eq('+count+')').text());; 
			 
			 
		 });
		 
		 if(count==totalTd-1)
		 $(this).parent().find('td:eq('+count+')').append("<br><a style='cursor:pointer' onclick=\"getCaseStudiedTableByRoleName(this,'getRoleWiseTimeTakenCOMM',0)\" >"+contEachTd.toFixed(2)+"</a>");
		 else
			 $(this).parent().find('td:eq('+count+')').append("<br><a style='cursor:pointer' onclick=\"getCaseStudiedTableByRoleNameRow(this,'getRoleWiseTimeTakenCOMM',0)\" >"+contEachTd.toFixed(2)+"</a>");
			
	}	 
	
	 });
	 
 //alert($(".roleTable").first().attr("id"));
var trCount=1;
var tdCount=1;

$(".roleTable").first().find("tr").not(':first').each(function(){
tdCount=1;
$(this).find("td").not(':first').each(function(){

var Denominator=0;

if($(this).children('a').length > 0){


Denominator=$(".roleTable").last().find('tr:eq('+trCount+')').find('td:eq('+tdCount+')').find("a").html().trim();

if(Denominator==0)
Denominator=1;

$(this).find("a").html(($(this).find("a").html().trim()/Denominator).toFixed(2))


}else if($(this).children('u').length > 0){


Denominator=$(".roleTable").last().find('tr:eq('+trCount+')').find('td:eq('+tdCount+')').find("u").html().trim();

if(Denominator==0)
Denominator=1;
$(this).find("u").html(($(this).find("u").html().trim()/Denominator).toFixed(2))


}else{
Denominator=$(".roleTable").last().find('tr:eq('+trCount+')').find('td:eq('+tdCount+')').html().trim();
if(Denominator==0)
Denominator=1;
$(this).html(($(this).html().trim()/Denominator).toFixed(2))



}
tdCount++;
});


trCount++;
});


	/*  var totalTd=$("#firstTr").children("td").length;

	// alert(totalTd);
	 $(".roleTable").each(function(){

	for(var count=1;count<totalTd;count++){
		
		
		var contEachTd=0;
		  $(this).find(".busi").each(function(){
				
			// alert($(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,'')); 
		contEachTd+= parseFloat( $(this).parent().find('td:eq('+count+')').text());
		
		
		 });
		 $(this).find("tr").last().find('td:eq('+count+')').append("<br><a  style='cursor:pointer'>"+contEachTd.toFixed(2)+"</a>");
		// alert($("#firstTr").parent().find("tr").last().find('td:eq('+count+')').text())
		 
	}
	 
	

 });
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
 

function getCaseStudiedTableByUserName(thisVariable,ajaxUrl,download){
	$("#caseTable").html("");

	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	   // alert(firstColumnValue);
	    var roleName=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
	    //alert(busiGroup);
//	 var  secondColumnValue = $(thisVariable).closest("tr").find("td:eq(1)").text();
  

  var json="{";
  json+="'userName':'"+firstColumnValue+"','roleName':'"+roleName+"',";
  
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
  // document.getElementById('id01').style.display='block';
 
}





function getCaseStudiedTableByUserNameRow(thisVariable,ajaxUrl,download){
	$("#caseTable").html("");
	 // alert("table");
	 //alert( $(thisVariable).index())
	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	   // alert(firstColumnValue);
	    var roleName=$(thisVariable).closest('tr').children('td:first').attr("class").split("bus")[1];
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
  json+="'userName':'"+firstColumnValue+"','roleName':'"+roleName+"',";
  
 // //var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
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
	// document.getElementById('id01').style.display='block';
}












  function getCaseStudiedTableByRoleName(thisVariable,ajaxUrl,download){
	 
		$("#caseTable").html("");

		
		    var roleName=$(thisVariable).closest('tr').children('td:first').text();
		    //alert(busiGroup);
//		 var  secondColumnValue = $(thisVariable).closest("tr").find("td:eq(1)").text();
	  

	  var json="{";
	  json+="'roleName':'"+roleName+"',";
	  
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
	  // document.getElementById('id01').style.display='block';
	}
  
  
  function getCaseStudiedTableByRoleNameRow(thisVariable,ajaxUrl,download){
	 
	  $("#caseTable").html("");
		 // alert("table");
		 //alert( $(thisVariable).index())
		 var roleName=$(thisVariable).closest('tr').children('td:first').text();
		 
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
	  json+="'roleName':'"+roleName+"',";
	  
	 // //var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
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
		// document.getElementById('id01').style.display='block';
	}
  function getCaseStudiedTableByTotal(thisVariable,ajaxUrl,download){
	  var json="{";

	  
	  //var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
	  json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+$("#dateTo").val()+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
			var currentLocation=$(this).attr("name");
			json+="'"+$(this).attr("name")+"':"
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					
					 $(this).parent().children("option").not(':first').each(function(){
						if(dropdownsCount!=5)
						 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
						else{
							tempOptValue+="'"+$(this).val()+"',";
			
						}
				
					 })  
					
					 
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1);
					 
				 }
				 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=7) {
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',"
					 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else{
					 tempOptValue+=$(this).val();
					 
				 }				 
					
				 json+="\""+tempOptValue+"\",";
				
			})

			
			dropdownsCount++;
		});
		json=json.substring(0,json.length-1)
		json+="}";
	//alert(json);
	   generateTableByService(ajaxUrl,json,download);
	 //  document.getElementById('id01').style.display='block';
  
  }
  

  function getCaseStudiedTableByTotalRow(thisVariable,ajaxUrl,download){

  var currentDate=$(thisVariable).parent().parent().parent().find("tr:first").children('td:eq('+$(thisVariable).index()+')').text();
	var endDate,startDate="";
	
	 if(currentDate.substring(0,1)=='D'){
	startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	endDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length);
	
	 }else {
   startDate=currentDate.split("-")[2].trim()+"-"+currentDate.split("-")[1].trim()+"-"+currentDate.split("-")[0].substring(currentDate.split("-")[0].length-2,currentDate.split("-")[0].length).trim();
   endDate=currentDate.split("-")[5].trim()+"-"+currentDate.split("-")[4].trim()+"-"+currentDate.split("-")[3].trim();
	 
	 }
	 
  
	   var json="{";

	  
	  json+="'dateFrom':'"+endDate+"','dateTo':'"+startDate+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
			var currentLocation=$(this).attr("name");
			json+="'"+$(this).attr("name")+"':"
			$(this).children("option:selected").each(function(){
				var tempOptValue="";
				 if($(this).val()=="ALL"){ 
					
					 $(this).parent().children("option").not(':first').each(function(){
						if(dropdownsCount!=5)
						 tempOptValue+="'"+($(this).val().split("-"))[0]+"',";
						else{
							tempOptValue+="'"+$(this).val()+"',";
			
						}
				
					 })  
					
					 
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1);
					 
				 }
				 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=7) {
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',"
					 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else{
					 tempOptValue+=$(this).val();
					 
				 }				 
					
				 json+="\""+tempOptValue+"\",";
				
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
function countData(countTable){
	 var rolecount={};
	 var location=[];
	 
	 
	 
	   var uniqueNames=locationData();
	   
	  
	  
	   
	   for(var i=0;i<uniqueNames.length;i++){
		 
	 		 var jsonCount = [];
	 		 var jsonBusiness = [];
	 		
	 		
	 		var loc=uniqueNames[i];
	 		
	 		 if(countTable==0){
	 	
	 		 $(".roleTable").first().each(function(){		
	 			 
	 			 $(this).find(".busi").each(function(){
	 				 var count=0;
	 				  var busiGroup2=$(this).text();
	 				 
	 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
	 					 var temp2=$(this).text();
	 					
	 					
	 					
	 				  if(loc==temp2){
	 													
	 					 						 
	 					 jsonCount.push(parseFloat($(this).parent().children().last().text())); 						
	 						
	 						 
	 						 count++; 
	 						 
	 					 }
	 						 
	 					 
	 				 });//end of location
	 				
	 				 if(count==0){
	 							 
	 					jsonCount.push(0);
	 					
	 					  
	 				 }//end of if 
	 				 
	 				 
	 				 
	 			 });//end of businessGroup
	 		 });//end of roletable
	 		rolecount["serie"+i]=jsonCount;
	 		 }
	 		 
	 		 if(countTable==1){
	 		
             $(".roleTable").children().eq(1).each(function(){		
	 			 
            	 $(this).find(".busi").each(function(){
	 				 var count=0;
	 				  var busiGroup2=$(this).text();
	 				
	 				 
	 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
	 					 var temp2=$(this).text();
	 					
	 					
	 					
	 				  if(loc==temp2){
	 													
	 					 						 
	 					 jsonCount.push(parseFloat($(this).parent().children().last().text())); 						
	 						
	 						 
	 						 count++; 
	 						 
	 					 }
	 						 
	 					 
	 				 });//end of location
	 				
	 				 if(count==0){
	 							 
	 					jsonCount.push(0);
	 					
	 					  
	 				 }//end of if 
	 				 
	 				 	 				 
	 			 });//end of businessGroup
	 		 });//end of roletable
	 		rolecount["serie"+i]=jsonCount;
	   }
	 		 if(countTable==2){
               $(".roleTable").last().each(function(){		
	 			 
            	   $(this).find(".busi").each(function(){
	 				 var count=0;
	 				  var busiGroup2=$(this).text();
	 				 
	 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
	 					 var temp2=$(this).text();
	 					
	 					
	 					
	 				  if(loc==temp2){
	 													
	 					 						 
	 					 jsonCount.push(parseFloat($(this).parent().children().last().text())); 						
	 						
	 						 
	 						 count++; 
	 						 
	 					 }
	 						 
	 					 
	 				 });//end of location
	 				
	 				 if(count==0){
	 							 
	 					jsonCount.push(0);
	 					
	 					  
	 				 }//end of if 
	 				 
	 				 
	 				 
	 			 });//end of businessGroup
	 		 });//end of roletable
	 		 
	 		rolecount["serie"+i]=jsonCount;
	 			
	   }
	 			// alert("hhh"+JSON.stringify(jsonCount)); 
	 			
	 			 
	 			
	 	
	 		 
	   }
	   
	  
	  // alert("Main Json is"+JSON.stringify(mainJson)); 
	 	return rolecount;
	      
}

function businessdata(countTable){
	 var busiGroup=[];
	 if(countTable==0){
		 $(".roleTable").first().each(function(){	
	 
			 $(this).find(".busi").each(function(){
		 
		 busiGroup.push($(this).text());	  
	
	 }); 
		 });
	 }
	 if(countTable==1){
		 $(".roleTable").children().eq(1).each(function(){	
	 
			 $(this).find(".busi").each(function(){
			 
			 busiGroup.push($(this).text());	  
		
		 }); 
	 });
		 }
	 if(countTable==2){
		 
		 $(".roleTable").last().each(function(){	
			 $(this).find(".busi").each(function(){
			 
			 busiGroup.push($(this).text());	  
		
		 }); 
		 });
		 }
	 return busiGroup;
	 
}






function toolTipData(countTable){
	 
	 var mainToolTip={};
	 var location=[];
	 
	
	 
	 
	   
	  var uniqueNames = locationData();
	  
	  
	  
	   
	   for(var i=0;i<uniqueNames.length;i++){
	 		 var jsonToolTip = [];
	 		 var jsonBusiness = [];
	 		
	 		
	 		var loc=uniqueNames[i];
	 	
	 		 if(countTable==0){		
	 			 $(".roleTable").first().each(function(){
	 				 $(this).find(".busi").each(function(){
	 				 
	 		
	 				 var count=0;
	 				  var busiGroup2=$(this).text();
	 				 
	 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
	 					 
	 					// alert($(this).attr("colorName"));
	 					 var temp2=$(this).text();
	 					// alert("temo"+temp2);
	 					
	 				  if(loc==temp2){ 					
	 											
	 						
	 						 
	 					
	 					 jsonToolTip.push("<table><tr><td style='color:red;padding:0'>Average Time(Hr):"
	 							+parseInt($(this).parent().children().last().text())
								+ "</td></tr><tr><td style='color:#4897f1;padding:0'>USER:"
								+ temp2
								+ "</td></tr></table>");
	 						
	 						 
	 						 count++; 
	 					 }
	 						 
	 					 
	 				 });//end of location
	 				
	 				 if(count==0){
	 							 
	 					jsonToolTip.push(0+"");
	 					
	 					  
	 				 }//end of if
	 				 
	 				 
	 				 
	 			 });//end of businessGroup
	 			 
	 			 });	
	 		 }
	 			 
	 			 
	 			 if(countTable==1){		
		 			 $(".roleTable").children().eq(1).each(function(){
		 				 $(this).find(".busi").each(function(){
		 				 
		 		
		 				 var count=0;
		 				  var busiGroup2=$(this).text();
		 				 
		 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
		 					 
		 					// alert($(this).attr("colorName"));
		 					 var temp2=$(this).text();
		 					// alert("temo"+temp2);
		 					
		 				  if(loc==temp2){ 					
		 											
		 						
		 						 
		 					
		 					 jsonToolTip.push("<table><tr><td style='color:red;padding:0'>Count:"
		 							+parseInt($(this).parent().children().last().text())
									+ "</td></tr><tr><td style='color:#4897f1;padding:0'>USER:"
									+ temp2
									+ "</td></tr></table>");
		 						
		 						 
		 						 count++; 
		 					 }
		 						 
		 					 
		 				 });//end of location
		 				
		 				 if(count==0){
		 							 
		 					jsonToolTip.push(0+"");
		 					
		 					  
		 				 }//end of if
		 				 
		 				 
		 				 
		 			 });//end of businessGroup
		 			 
		 			 });
	 			 }
		 			 
		 			 
		 			 if(countTable==2){		
			 			 $(".roleTable").last().each(function(){
			 				 $(this).find(".busi").each(function(){
			 				 
			 		
			 				 var count=0;
			 				  var busiGroup2=$(this).text();
			 				 
			 				 $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
			 					 
			 					// alert($(this).attr("colorName"));
			 					 var temp2=$(this).text();
			 					// alert("temo"+temp2);
			 					
			 				  if(loc==temp2){ 					
			 											
			 						
			 						 
			 					
			 					 jsonToolTip.push("<table><tr><td style='color:red;padding:0'>Count:"
			 							+parseInt($(this).parent().children().last().text())
										+ "</td></tr><tr><td style='color:#4897f1;padding:0'>USER:"
										+ temp2
										+ "</td></tr></table>");
			 						
			 						 
			 						 count++; 
			 					 }
			 						 
			 					 
			 				 });//end of location
			 				
			 				 if(count==0){
			 							 
			 					jsonToolTip.push(0+"");
			 					
			 					  
			 				 }//end of if
			 				 
			 				 
			 				 
			 			 });//end of businessGroup
			 			 
			 			 });//end of roletable
	 			 
		 			 }
	 			
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
	 					
	 					
	 							
	 						
	 						//graphColor.push($(this).attr("colorName"));
	 						graphColor.push('#'+(Math.random()*0xFFFFFF<<0).toString(16));
	 						 					
	 						 count++;  
	 						 
	 						 
	 					 
	 				
	 					 
	 				 });//end of location
	 				
	 				
	 				 
	 			 });//end of businessGroup
	 			 
	 			
	 			
	  
	  /*  alert(JSON.stringify(graphColor)); */
	   
	 	return graphColor;
		 
}

function legendColor(){
	 var legendColorArr=[];
		
	var uniqueColor=barColor();
	  
	  
	  for(i=0;i<uniqueColor.length;i++){
		  var jsonColor={};
		  jsonColor['color']=uniqueColor[i];
		  legendColorArr.push(jsonColor);
		  
	  }
		  
	 
	   
	 	return legendColorArr;
	
}


function maxCount(countTable){
	    var count=0;
	    $(".roleTable").eq(countTable).each(function(){
		$(".busi").each(function(){
			  var busiGroup2=$(this).text();
			  $(this).parent().siblings().find(".bus"+busiGroup2).each(function(){
			var temp=parseFloat($(this).parent().children().last().text());
			if(temp>=count)
				count=temp;
			
		});
			  
		});
	    });
	 	return count;	
	 
}//end of maxCount


function aliasData(countTable){
	 var roleAlias=[];
	 
	 
		 
	 $(".roleTable").eq(countTable).each(function(){
		 roleAlias=[];
		 $(this).find(".busi").each(function(){
		 
			 roleAlias.push($(this).attr("aliasName"));	  
	
	 }); 
	 }); 
		 
	
	 
	
		 
     $(".roleTable").children().eq(1).each(function(){		
    	 roleAlias=[];
    	 $(this).find(".busi").each(function(){
				
													
					 						 
    		 roleAlias.push($(this).attr("aliasName"));	 						
						
						 
							 
			 });//end of businessGroup
		 });//end of roletable
   

		
			
       $(".roleTable").last().each(function(){		
    	   roleAlias=[];
    	   $(this).find(".busi").each(function(){
								
					 						 
    		  roleAlias.push($(this).attr("aliasName"));	 						
						
						 
					
				 
			 });//end of businessGroup
		 });//end of roletable
		 
       
			

	 
	 
	 
		 var uniqueNames = [];
		   $.each(roleAlias, function(i, el){
		       if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
		   });
		  // alert(JSON.stringify(uniqueNames))
		   return uniqueNames;
		
	}





var barColorData=barColor();
var legendColorData=legendColor();

function graphNumberOfCaseProcessComm(chartName,radioData){
	
	
	 var x;
	 var location;
	 var roles;
	 var thetooltips;
	 var title;
	 var aliasValue;
	 var maxiCount;
	if(radioData==0){
		
		x=countData(0);
		location=locationData(0);
		roles=businessdata(0);
		thetooltips=toolTipData(0);
		title="Total Time"
			aliasValue=aliasData(0);
		maxiCount= maxCount(0);
		//alert(maxiCount);
		
	}
	
	
	if(radioData==1){
		
		x=countData(1);
		location=locationData(1);
		roles=businessdata(1);
		thetooltips=toolTipData(1);
		title="Average Time"
			aliasValue=aliasData(1);
		maxiCount= maxCount(1);
	}
	
	if(radioData==2){
		
		x=countData(2);
		location=locationData(2);
		roles=businessdata(2);
		thetooltips=toolTipData(2);
		title="Total Count"
			aliasValue=aliasData(2);
		maxiCount= maxCount(2);
	}
      
	
           
            /*  alert(JSON.stringify(location));
            */
			  
           
			 
           
          
			    
          chartName.chart({
		 template : "line_basic_6",
		 tooltips :thetooltips,
		 
		 values : x,
		 labels : aliasValue,
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
			title : title
		},
		x : {title : 'Roles', titleDistance: 35},
		 }
		});
            $("#titleDiv").remove(); 
            $(chartName).prepend('<html><div id="titleDiv"> <h4 style="color:purple">Title : Role Wise Time Taken</h5></div></html>');
            
          var descData='';
    	 	
    	 	//alert(JSON.stringify(aliasValue));
    	 	
    	 	$('#locDesc').html();
    	 	
    	 	
    	 	for(i=0;i<roles.length;i++){
    	 		
    	 		
    	 		descData+='<tr><td width="100">' + roles[i] + '</td>'+'<td width="118">'+aliasValue[i]+'</td>'+'</tr>';
    	 	}
    	 	  	
    	 	
    	 	 $("#divDesc").remove();
    	 	chartName.append('<html><div id="divDesc"><table width="80%" border="1" id="locTable"  ><tr class="w3-blue"><td>' + " Role FullName" +'</td>' +'<td>'+" Role shortName"+ '</td></tr>'+descData+'</table></div></html>');	    
  			    
			    
			      
}


/* alert(JSON.stringify(legendColorData)); */


$.elycharts.templates['line_basic_6'] = {
		type : "line",
		
		 margins : [10,80, 60, 50],
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
				titleDistance : 35,
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
			   horizontal : false,
			  
			   width : 125,
			   height : 1500,
			   x : 1400,
			   y : 0,
			  
			   borderProps : {
			    "fill-opacity" : 0.3
			   }
			  }
		 },
		 barMargins : 50
		}               	






</script>
</body>
</html>