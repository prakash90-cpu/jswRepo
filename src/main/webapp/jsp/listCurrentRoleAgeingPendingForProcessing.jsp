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
						<b>Current Role Ageing Pending for Processing</b>
					</h3>
				</div>
 
<form:form commandName="FunctionAppBean" action="/jsw-report/mainController/submitCurrentRoleAgeingPendingForProcessing">				
					<table
					class="w3-table w3-hoverable w3-striped w3-bordeorange ">
					
					<tr >
					<td><lable><b>Business Group</b></lable>
					</td><td>
					<select id="businessGroup" name="businessGroup"  style="width:50%">
					 <option value="ALL" selected>ALL</option>  
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
    <c:when test="${fn:contains(value.value, functionName.selectedValue)}">
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
    <c:when test="${functionName.name=='Business'}">
        <tr><td><label>Date From </label></td><td><form:input  style="width:50%" type="date" id="dateFrom"  onChange="setToDate(this)" path="fromDate" /></td>
       <td><label>Date To </label></td><td><form:input  style="width:50%" type="text"  path="toDate" id="dateTo" readonly="true"/></td></tr>
    </c:when>    
  
</c:choose>
			</c:forEach>	
			<%-- <tr><td><label>Bar Code</label></td><td><form:input path="barCode" class="w3-input w3-border" style="width:50%"/>
			<c:if test="${not empty barCode}"> 	
			<span class="w3-text-red">${barCode}</span>
			</c:if>
			</td></tr>	 --%>	
				</table>
				<br />
				<input type="submit" onClick="checkAllSelectedOption()" class="w3-btn w3-right" style="width:15%" value="Go">
</form:form> 
			</div>

			<br /> <br />
			
			
			
  
  
  
  
		<c:if test="${not empty listRoleWiseTimeTaken}"> 
        <p class="w3-right currentDate">${currentDate}</p>
        
        		<c:if test="${settingValue==0}"> 
        
				<div class="w3-left"><label>Tabular</label>	
		<input type="radio" onChange="$('#listCaseCreated').show();$(' #chartContainer').hide();" class="w3-radio" style="padding-right:20px" checked name="graph">
		<label>Graphical</label>	
		<input   onChange="$('#listCaseCreated').hide();$('#chartContainer').show();graphRoleWiseTimeTaken();" class="w3-radio" type="radio" name="graph">	</div>	
		</c:if><br /> <br />
		
		<center> 
		<div id="chartContainer" style="height:300px; width: 70%;display:none">
  </div></center>
        <div id="listCaseCreated" class="w3-container" style="background-color:#F0F8FF">
        
     
        	
       <h4 class="w3-text-blue"><b>Role Pending for Processing</b></h4>
<table
					class="w3-table-all w3-hoverable w3-striped w3-border w3-small w3-center roleWiseTime">
					<thead>
						<tr class="w3-text-white w3-blue w3-center w3-medium">
						
						
							<th class="w3-center">Role</th>
						
						
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
						<th class="w3-center"> count</th>
							
						</tr></thead>

<tbody>
						<c:forEach var="listCases" items="${listRoleWiseTimeTaken}">
							<tr class="roleRow" alias="${listCases.alias}">

					
							<td class="">${listCases.name}</td>
							
								<c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
		<td class="w3-center"  onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.day1}</a></a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.day2}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.day3}</a></td>
													<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.day4}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.day5}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.day6}</a></td>
						
							<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.day7}</a></td>
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
   		<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.week1}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.week2}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.week3}</a></td>
													<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.week4}</a></td>
											
											
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
   		<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.period1}</a></td>
										<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.period2}</a></td>
											<td class="w3-center" onclick="getCaseStudiedTableByRoleRow(this,'getRoleWiseTimeTaken')"><a>${listCases.period3}</a></td>
    </c:when>  
    
    
    </c:choose>
								<td onclick="getCaseStudiedTableByRole(this,'getRoleWiseTimeTaken')" class="w3-center w3-text-blue"><u class="w3-hover-blue w3-hover-text-black">${listCases.count}</u></td>
								

							</tr>
						</c:forEach>
						</tbody><tfoot>
						<tr class="w3-text-white w3-black w3-center w3-medium">
						
						
							<td class="w3-center">Grand Total</td>
						
						
						
  			
  			
  			 <c:choose>
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '7')}">
          <td class="w3-center">Total of all Day1</td>
  			<td class="w3-center">Total of all Day2</td>
  			<td class="w3-center">Total of all Day3</td>
  				<td class="w3-center">Total of all Day4</td>   
  			<td class="w3-center">Total of all Day5</td>
  			<td class="w3-center">Total of all Day6</td>
  				<td class="w3-center">Total of all Day7</td>
						
						
						
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '4')}">
         <td class="w3-center">Total of all Week1</td>
  			<td class="w3-center">Total of all Week2</td>
  			<td class="w3-center">Total of all Week3</td>
  				<td class="w3-center">Total of all Week4</td>
    </c:when>  
        <c:when test="${fn:contains(FunctionAppBean.getDisplayBy(), '3')}">
      <td class="w3-center">Total of all Month1</td>
  			<td class="w3-center">Total of all Month2</td>
  			<td class="w3-center">Total of all Month1</td>
    </c:when>  
    
    
    </c:choose>
  			
  			
  			
    			<td class="w3-center">Total of all column on totals</td>
							
						</tr></tfoot>
						
				</table><br><br>
			
				</div><br><br>

</c:if>	 
			<!-- End page content -->
		</div>
		
		
		<div id="id01" class="w3-modal" style="width:100%">
    <div class="w3-modal-content"  style="width:100%">
      <header class="w3-container "> 
        <span onclick="document.getElementById('id01').style.display='none'" 
        class="w3-button w3-display-topright">&times;</span>
        <h2>Case Detail</h2>
      </header>
      <div class="w3-container" id="caseTable"   style="width:100%">
     
      </div>
      <footer class="w3-container ">
        <p></p>
      </footer>
    </div>
  </div>
	<%@ include file="Footer.jsp"%>
	</div>
<c:set var="locationName" value="${FunctionAppBean.getLocation()}"/>
	<c:set var="businessName" value="${FunctionAppBean.getBusiness()}"/>
	
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
 $('[name="businessGroup"]').change(function(){

 	callGroupByLocationService(loc,busi);


 });
	
 $('[name="Location"]').change(function(){
		

		callLocationByBusinessService();

	});
 $( "select[name='DisplayBy']" ).change(function() {

		setToDate(this);
});
 
 function checkAllSelectedOption(){

		var dropdownsCount=1;
		$("select").each(function(){
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
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=5) {
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

 
 
 
 
 
 
</script> 
<script>

function callGroupByLocationService(loc,busi){

	var selectedBusinessGroup=$('[name="businessGroup"]').val();	
	  $.ajax({                    
 	  url: '/jsw-report/NW/getLocationByGroup/'+selectedBusinessGroup,     
 	  type: 'GET',
 	async:false,
 	cache:false,
 	  success: function(data)         
 	  {

 		 if(data!="[]"){
 		 var newJsonresultData=JSON.parse(data);
 	 
 		 var optionString="<option>ALL</option>";
 		 for (var roleDataKey in newJsonresultData){
 		 
 			 
 			 if(newJsonresultData[roleDataKey].indexOf(loc) !=-1)
 				optionString+="<option selected>"+newJsonresultData[roleDataKey]+"</option>";
 			 else
 			optionString+="<option>"+newJsonresultData[roleDataKey]+"</option>";
 		 
 		 }
 
		 $('[name="Location"]').html(optionString);
 		 }
 		 
	   
 	  }
	 
	
	 }); 
	  
	  
	 
	  
	  var selectLocation=($('[name="Location"]').val().split("-")[0]);
		
	
	  $.ajax({                    
 	  url: '/jsw-report/NW/getBusinessByLocation/'+selectLocation,     
 	  type: 'GET',
 	async:false,
 	cache:false,
 	  success: function(data)         
 	  {

		 if(data!="[]"){
			 var newJsonresultData=JSON.parse(data);
		 	 
	 		 var optionString="<option>ALL</option>";
	 		 for (var roleDataKey in newJsonresultData){
	 		 
	 			 
	 			 if(newJsonresultData[roleDataKey].indexOf(busi) !=-1)
	  				optionString+="<option selected>"+newJsonresultData[roleDataKey]+"</option>";
	  			 else
	  			optionString+="<option>"+newJsonresultData[roleDataKey]+"</option>";
	 			
	 		 
	 		 }
	 
			 $('[name="Business"]').html(optionString);
		 }
		 else
		 	  $('[name="Business"]').html("");
		  	
	
		 
 	  }
	 
	
	 }); 
	
	
	
	
	  
}

function callLocationByBusinessService(){
	var selectedLocation=($('[name="Location"]').val().split("-")[0]);
	
	  $.ajax({                    
 	  url: '/jsw-report/NW/getBusinessByLocation/'+selectedLocation,     
 	  type: 'GET',
 	async:false,
 	cache:false,
 	  success: function(data)         
 	  {
 		 
if(data!="[]"){
 		 var newJsonresultData=JSON.parse(data);
 	 
 		 var optionString="<option>ALL</option>";
 		 for (var roleDataKey in newJsonresultData){
 		 
 			 
 			optionString+="<option>"+newJsonresultData[roleDataKey]+"</option>";
 		 
 		 }
 
		 $('[name="Business"]').html(optionString);
}
else
	  $('[name="Business"]').html("");
 	  }
	 
	
	 }); 
 
}

 
 
 
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
 
 function getCaseCreatedAll(){
	  
	  var url='data:application/vnd.ms-excel,' + encodeURIComponent($('#listCaseCreated').html()) 
      // alert(url);
       location.href=url
       return false
	  
  }

  
  
  function getCaseStudiedTableByRole(thisVariable,ajaxUrl){
		$("#caseTable").html("");
		 // alert("table");
		var Role=$(thisVariable).closest('tr').children('td:first').text();
		   
	
	

	  var json="{";
	  json+="'CurrentRole':'"+Role+"',";
	  
	  var dateTo=$("#dateTo").val().split("-")[2]+"-"+$("#dateTo").val().split("-")[1]+"-"+$("#dateTo").val().split("-")[0];
	  json+="'dateFrom':'"+$("#dateFrom").val()+"','dateTo':'"+dateTo+"',";
	  var dropdownsCount=1;
		$("select").each(function(){
		
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
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=5) {
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
	generateTableByService(ajaxUrl,json);
	   document.getElementById('id01').style.display='block';
	 
	}
	  
	function getCaseStudiedTableByRoleRow(thisVariable,ajaxUrl){
		$("#caseTable").html("");
		 // alert("table");

	 	var Role=$(thisVariable).closest('tr').children('td:first').text();
		   
	
	

	  var json="{";
	  json+="'CurrentRole':'"+Role+"',";
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
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=5) {
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
	generateTableByService(ajaxUrl,json);
	   document.getElementById('id01').style.display='block';
	 
	}


function  generateTableByService(AjaxUrl,jsonData){
		//  alert(jsonData);
		  
		  $.ajax({                    
	    	  url: '/jsw-report/NW/'+AjaxUrl,     
	    	  type: 'POST',
	    	async:false,
	    	cache:false,
	    	data:jsonData,
	    	contentType:"application/json",
	    	  success: function(data)         
	    	  {
	    	
	    	   var newJsonresultData=JSON.parse(data);
	    	   var tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border '>";
	    	
	       		tableString+="<tr class='w3-blue w3-text-white'><td>FCaseFolderID</td><td class='w3-light-blue w3-text-white'>Username</td><td>Date Created</td><td class='w3-light-blue w3-text-white'>Location</td><td>BarCodeDC</td><td class='w3-light-blue w3-text-white'>Document Source</td>"
	    	   

	        		tableString+="<td>Amount</td><td class='w3-light-blue w3-text-white'>Step Name</td><td>Start Time</td><td class='w3-light-blue w3-text-white'>End Time</td></tr>";
	    	   
	    	   for (var jsonOuterKey in newJsonresultData){
	    		   tableString+="<tr>";
	    		
	    		   var newInnerJsonresultData=(newJsonresultData[jsonOuterKey]).toString();
	    		
	    		   var newInnerJsonresultDataArray = new Array();
	    		 
	    		   newInnerJsonresultDataArray = newInnerJsonresultData.split(",");
	    		   
	    		   for (a in newInnerJsonresultDataArray ) {
	    		
	    			  
	    			  tableString+="<td>"+newInnerJsonresultDataArray[a]+"</td>";
	    			}
	    		   
	    		     
	    		     tableString+="</tr>";
	    		    
	    	   }
	    	   tableString+="</table>";
	    	   
	    	
	    	   $(tableString).appendTo("#caseTable");
	    	  } 
	    	});
	    
	    
	  
		  
	  }
	  




  function checkAllSelectedOption(){

		var dropdownsCount=1;
		$("select").each(function(){
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
			 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else if(dropdownsCount!=4&&dropdownsCount!=1&&dropdownsCount!=5) {
					 tempOptValue+="'"+($(this).val().split("-"))[0]+"',"
					 tempOptValue=tempOptValue.substring(0, tempOptValue.length-1)
				 }
				 else{
					 tempOptValue+=$(this).val();
					 
				 }				 
					
				 $(this).val(tempOptValue);
				
			})
	
			
			dropdownsCount++;
		});
	
	}




//Set Date Day Month Year


function setToDate(thisVariable){
	
	
	var selectedDisplayBy=$('select[name="DisplayBy"]').find(":selected").index();

		var result = new Date($("#dateFrom").val());

 if(selectedDisplayBy==0)
	{
	
	    result.setDate(result.getDate() + 6);
	 
	
	}
 else if(selectedDisplayBy==1)
		{
	 result.setDate(result.getDate() + 27);
	 
		}
 else{
	 result.setDate(result.getDate() + 89);
	 
		
	}	
 if(result.getDate()<10&&result.getMonth()<10)
 $("#dateTo").val("0"+result.getDate()+"-"+"0"+(result.getMonth()+1)+"-"+(result.getYear()+1900));
 else if(result.getDate()<10)
	 $("#dateTo").val("0"+result.getDate()+"-"+(result.getMonth()+1)+"-"+(result.getYear()+1900));
 else if(result.getMonth()<10)
	 $("#dateTo").val(result.getDate()+"-"+"0"+(result.getMonth()+1)+"-"+(result.getYear()+1900));
 else
	 $("#dateTo").val(result.getDate()+"-"+(result.getMonth()+1)+"-"+(result.getYear()+1900));

}


	
$(".roleRow").each(function(){
	
	var countTotal=0;
	$(this).children().not(':first').not(':last').each(function(){
		countTotal+=parseFloat($(this).text()) ;
		
	});
	
	$(this).children().last().find("u").text(countTotal);
	
	
});

var totalTd=$(".roleRow").first().find("td").length;

//alert(totalTd);
$(".roleWiseTime").each(function(){

for(var count=1;count<totalTd;count++){
	
	
	var contEachTd=0;
	 $(this).find(".roleRow").each(function(){
			
		// alert($(this).parent().find('td:eq('+count+')').text().replace(/[^0-9]/g,'')); 
	contEachTd+= parseFloat( $(this).find('td:eq('+count+')').text());
	
	
	 });
	 $(this).find("tfoot").find("tr").first().find('td:eq('+count+')').append("<br><a  style='cursor:pointer'>"+contEachTd+"</a>");
	// alert($("#firstTr").parent().find("tr").last().find('td:eq('+count+')').text())
	 
}

});
  $(".roleWiseTime").DataTable();
//***********************Graph Generation***********
//***********************DUMMY***********

function aliasNamedata(){
	    	 var jsonData = [];
	    					
	    		$(".roleRow").each(function(){
	    			
	    					  
	    			jsonData .push($(this).attr("alias"));
	    			   	  
	    			//  alert($(this).attr("color"))
	    			
	    			
	    			});
	    		
	    		console.log("jsonData: " + jsonData);
	    		return jsonData;
	    }
	     
	     
	      function stepNamedata(){
	    	 var jsonData = [];
	    					
	    		$(".roleRow").each(function(){
	    			
	    					  
	    			jsonData .push($(this).children().first().text());
	    			   	  
	    			  //.split("-")[1]
	    			
	    			
	    			});
	    		
	    		console.log("jsonData: " + jsonData);
	    		return jsonData;
	    }
	    function countdata(){
	    	 var jsonData = [];
	    		
	    		$(".roleRow").each(function(){
	    			
	    					  
	    			jsonData .push(parseFloat($(this).children().last().find("u").text()));
	    			   	  
	    			   			  
	    			
	    			
	    			});
	    		
	    		console.log("jsonData: " + jsonData);
	    		return jsonData;
	    } 
		

	     function graphRoleWiseTimeTaken(){
	    	 
	    	var aliasData=aliasNamedata();
	    var x=stepNamedata();
	    var countData= countdata();
	      
	     
	    var thetooltips = new Array(countData.length);
	    var thelabels = new Array(countData.length); 
	    var thevalues = new Array(countData.length); 
	    var theColor = new Array(countData.length); 
	     
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
	    							title : "Numbe OF Count"
	    						}
	    					},
	    					 
	    					
	    					barMargins : 10,
	    					labels : aliasData
	    				});

	    }
     

	    $.elycharts.templates['line_basic_5'] = {
	    		  type : "line",
	    				margins : [10, 35, 70, 70],
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
	    						height : 105,
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
	    					labelsRotate : 45,
	    					labelsMargin : 0,
	    					labelsDistance : 15,
	    					labelsHideCovered : false
	    				},
	    				axis : {

	    					l : {
	    						titleProps : {
	    							fill : "RED",
	    							"font-size" : "15px"
	    							
	    						},
	    						titleDistance : 35,
	    						labelsMargin : 4,
	    						
	    						labelsDistance : 15 
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

//****************Fuzzy Chart*****************


	 /*     
	     function maxCount(){
	    var count=0;
		$(".roleRow").each(function(){
			var temp=parseInt($(this).children().last().find("u").text());
			if(temp>=count)
				count=temp;
			
		});
		
	 	return count;	
	 
 }//end of maxCount

	     
	     function graphCountData(){
	    		
	  		
	    		var jsondata={};
	    		var mainArrayData=[];
	    		var mainTopArrayData=[]; 		
	    			
	    		

	    				$(".roleRow").each(function(){
	    			
	    			alert($(this).attr("alias"));
	    			var countData = [];
	    			
	    			
	    			countData.push(parseInt($(this).children().last().find("u").text()));
	    			mainArrayData.push(countData)
	    			mainTopArrayData.push("Count:"+parseInt($(this).children().last().find("u").text()));
	    			  	
	    			
	    			});		
	    		
	    		
	    		jsondata['bar']=mainArrayData;
	    		var topArrayData=[];
	    		topArrayData.push(mainTopArrayData);
	    		jsondata['top']=topArrayData;
	    		
	    		return jsondata;
	    		
	    		
	    	}

	    	function graphXAxisData(){
	    		
	    		var jsondata={};
	    		 var xAxisData = [];			
	    			    		
	    			 
	    				 $(".roleRow").each(function(){
	    				var xData = [];
	    				
	    				
	    				xData.push($(this).children().first().text().split(":")[0]);
	    				xAxisData.push(xData);	  
	    				
	    				});
	    			 			 		    		 	  	 
	    		 	 	
	    			var activeLine={};
	    			jsondata['x']=xAxisData;
	    			jsondata['fontSize']= 15;                                   
	    			jsondata['fontFamily']='Microsoft YaHei';                 
	    			jsondata['color'] ='#666666';                          
	    			jsondata['lineColor'] ='#EEEEEE';                         
	    			jsondata['lineWidth'] = 1;                               
	    			jsondata['textMarginTopX'] = 5; 
	    			activeLine['width']=1;
	    			activeLine['color']='#999999';
	    			jsondata['activeLine']=activeLine;
	    			return jsondata;
	    		
	    		
	    	}
	     
	     
function graphRoleWiseTimeTaken(){
	
	 var yData=graphCountData();
	 var xData=graphXAxisData();
	 var maxElement=maxCount();
	 
	 var canvas_mix = $('#chartContainer');
	 
	 
	//**************Start OF Graph Plotting ***************

		canvas_mix.css({
		  width: 1500,
		  height: 450
		}).LineMixBar({
			  data: yData,
			  
			  
		
		  units: {
		      bar: []
		  },
		  colors: {
		      bar: ['#98D0FF', '#FFFFFF', '#98D0FF'],
		  },  
		  background: 'transparent',  
		  isDebug: false,     
		  axis: xData,
		  bar: {
		      gap: 1,
		       max: maxElement, 
		      min: 0,
		     
		      defaultColor:'#CCCCCC',
		      width:undefined,
		      type: 0      // 0 or 1
		  },
		  top: {
		      fontSize: 18,
		     
		      fontFamily: 'Roboto',
		      color: '#666666', 
		      textMarginBottom: 5
		  }
		}).draw();

	
	
}

 */



//*****************End*********************



</script>
<script type="text/javascript">



 
  
  var today = new Date();
	 today=today.toString();
	 var now=(today.split("GMT"));

	$(".currentDate").html(now[0]); 
	
	
	
  </script>
</body>
</html>