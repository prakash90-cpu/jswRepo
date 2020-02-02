<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    	
<%@include file="include.jsp"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Case Detail</title>
</head>
<body>
 <div  class="w3-container w3-left" id="export">
     
     <a href="${downloadLink}" title="Download Details as Excel"> <button class="w3-large">Excel</button></a>
      </div>
      
<div class="w3-container"  style="height:100%; width:100%; overflow:auto;">
${detailTable}
</div>
</body>

<script>
 $("#dataTableId").DataTable( );

var table=  $("#dataTableId");
/* $('#export').html("");
	 var buttons = new $.fn.dataTable.Buttons(table, {
  buttons: [
    'copyHtml5',
    'excelHtml5',
    'csvHtml5'
 ]
}).container().appendTo($('#export'));
  */
</script>
</html>