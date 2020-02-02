<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>




<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery-1.11.3.min"></script>
<script src="/jsw-report/js/jquery-1.9.1.js"></script>
<link rel="stylesheet"
	href="/jsw-report/css/bootstrap.min.css">
<script
	src="/jsw-report/js/bootstrap.min.js"></script>
	
	
	
<link rel="stylesheet" href="/jsw-report/css/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet"
	href="/jsw-report/font-awesome/css/font-awesome.min.css">


<script src="/jsw-report/js/angular.min.js"></script>


 <link rel="stylesheet" href="/jsw-report/css/kendo.common-material.min.css" />
    <link rel="stylesheet" href="/jsw-report/css/kendo.material.min.css" />
    <link rel="stylesheet" href="/jsw-report/css/kendo.material.mobile.min.css" />

    <script src="/jsw-report/js/jquery.min.js"></script>
    <script src="/jsw-report/js/kendo.all.min.js">
    
     </script>

<script src="/jsw-report/js/raphael.js"></script>

<script src="/jsw-report/js/elycharts.js"></script>


<script src="/jsw-report/js/util.js"></script>
<script src="/jsw-report/js/jquery.charts.js"></script>

<script src="/jsw-report/js/elycharts_manager_legend.js"></script>










<link rel="stylesheet"
	href="/jsw-report/css/jquery.dataTables.min.css">
	<link rel="stylesheet"
	href="/jsw-report/css/buttons.dataTables.min.css">
	
	<script src="/jsw-report/js/jquery.dataTables.min.js"></script>
      <script src="/jsw-report/js/dataTables.buttons.min.js"></script>
     <script src="/jsw-report/js/buttons.flash.min.js"></script>
          <script src="/jsw-report/js/jszip.min.js"></script>
               <script src="/jsw-report/js/pdfmake.min.js"></script>
                    <script src="/jsw-report/js/vfs_fonts.js"></script>
                         <script src="/jsw-report/js/buttons.html5.min.js"></script>
 <script src="/jsw-report/js/buttons.print.min.js"></script>


 <script src="/jsw-report/js/html2canvas.js"></script>





<style>
a{
cursor:pointer;
}
nav a {
	color: white
}

.center {
  position: absolute;

  /*
  Nope =(
  margin-left: -25%;
  margin-top: -25%;
  */

  /* 
  Yep!
  */

  
  /*
  Not even necessary really. 
  e.g. Height could be left out!
  */
  width: 100%;
  height: 100%;
}


body {
  background-color:#f4f6f7;
	min-height: 100%;
	height: 600px;
	background-position: center;
	background-size: cover;

}

</style>

<%@ page isELIgnored="false"%>



	



<script>
 

 
function saveGraph(DivId){

  var graphDOM = $("#"+DivId);
            html2canvas(graphDOM, {
                onrendered: function (canvas) {
                    theCanvas = canvas;
                    var image = new Image();
                    image.id = "pic"
                    image.src = theCanvas.toDataURL();
               image.height = graphDOM.clientHeight
                 
                    window.open(image.src, 'Chart')
                }
            })
/*    setTimeout(function(){
 location.reload();
}, 1000);   */     




} 



 var tableToExcel = (function () {
            var uri = 'data:application/vnd.ms-excel;base64,'
                , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
                , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
                , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
            return function (table, name) {
                if (!table.nodeType) table = document.getElementById(table)
                var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
                window.location.href = uri + base64(format(template, ctx))
            }
        })()




	function abcd() {

		var x = document.getElementById("k1");
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}
	}

	function investment() {
		var x = document.getElementById("k3");
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}

	}


 function getCaseCreatedAll(tableID,SheetName){
	  
	 
       
       tableToExcel(tableID, SheetName)
       return false
	  
  }





  function  generateTableByService(AjaxUrl,selectedJSON,download){
		
	  
	
	  var selectedJSONfinal = window.btoa(selectedJSON);
	  
	
	 
	
	  if(download==1){
		  //alert("download");
		  window.open('/jsw-report/NW/'+AjaxUrl+"/"+selectedJSONfinal);
		  
	 /*  $.ajax({                    
    	  url: '/jsw-report/NW/'+AjaxUrl+"/"+selectedJSONfinal,     
    	  type: 'GET',
    	async:false,
    	cache:false,
    	  success: function(data)         
    	  {
    		  
    		  
   	  }
    	}); */
    
	  }
	  else{
		 // alert("new window");
		  window.open('/jsw-report/NW/'+AjaxUrl+"/"+selectedJSONfinal,'_blank');
		  
	  }


   
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
	 result.setMonth(result.getMonth() + 3);
	     result.setDate(result.getDate() -1);
		
	}	
	
 if(result.getDate()<10&&(result.getMonth()+1)<10) {
var dateToVar=(result.getYear()+1900)  +"-"+"0"+(result.getMonth()+1)+"-"+"0"+result.getDate();
 }
 else if(result.getDate()<10){
	var dateToVar= (result.getYear()+1900)+"-"+(result.getMonth()+1)+"-"+ "0"+result.getDate();
	 
	 }
 else if((result.getMonth()+1)<10){
	 var dateToVar=(result.getYear()+1900)+"-"+"0"+(result.getMonth()+1)+"-"+ result.getDate();
	 
	 }
 else{
	 var dateToVar=(result.getYear()+1900)+"-"+(result.getMonth()+1)+"-"+ result.getDate();
	 }
	
	  $("#dateTo").val(dateToVar);

}



function setToDisplayDate(thisVariable){
	
	
	var selectedDisplayBy=$('select[name="DisplayBy"]').find(":selected").index();

		var result = new Date($("#dateTo").val());
var maxDate=new Date();
 if(selectedDisplayBy==0)
	{
	
	    result.setDate(result.getDate()-6);
	 maxDate.setDate(maxDate.getDate()-6);
	
	}
 else if(selectedDisplayBy==1)
		{
	 result.setDate(result.getDate() - 27);
	  maxDate.setDate(maxDate.getDate()-27);
		}
 else{
	 result.setMonth(result.getMonth() - 3);
	
	     result.setDate(result.getDate() +1);
	 maxDate.setMonth(maxDate.getMonth() - 3);
	   maxDate.setDate(maxDate.getDate() +1);
	}	
	
 if(result.getDate()<10&&(result.getMonth()+1)<10) {
var dateToVar=(result.getYear()+1900)  +"-"+"0"+(result.getMonth()+1)+"-"+"0"+result.getDate();
 }
 else if(result.getDate()<10){
	var dateToVar= (result.getYear()+1900)+"-"+(result.getMonth()+1)+"-"+ "0"+result.getDate();

	 }
 else if((result.getMonth()+1)<10){
	 var dateToVar=(result.getYear()+1900)+"-"+"0"+(result.getMonth()+1)+"-"+ result.getDate();
	
	 }
 else{
	 var dateToVar=(result.getYear()+1900)+"-"+(result.getMonth()+1)+"-"+ result.getDate();
	 
	 }

 
	  $("#dateFrom").val(dateToVar);
	  
	 if(maxDate.getDate()<10&&(maxDate.getMonth()+1)<10) {
var dateToVarmaxDate=(maxDate.getYear()+1900)  +"-"+"0"+(maxDate.getMonth()+1)+"-"+"0"+maxDate.getDate();
 }
 else if(maxDate.getDate()<10){
	var dateToVarmaxDate= (maxDate.getYear()+1900)+"-"+(maxDate.getMonth()+1)+"-"+ "0"+maxDate.getDate();

	 }
 else if((maxDate.getMonth()+1)<10){
	 var dateToVarmaxDate=(maxDate.getYear()+1900)+"-"+"0"+(maxDate.getMonth()+1)+"-"+ maxDate.getDate();
	
	 }
 else{
	 var dateToVarmaxDate=(maxDate.getYear()+1900)+"-"+(maxDate.getMonth()+1)+"-"+ maxDate.getDate();
	 
	 }  
	  
 
	   $("#dateFrom").attr("max",dateToVarmaxDate);

}





function setToBucketFromDate(){


	var dateFrom = new Date($("#dateFrom").val());
	var dateTo = new Date($("#dateTo").val());
	
	var timeDiff = Math.abs(dateTo.getTime() - dateFrom.getTime());
	var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
	if(diffDays>90){
		
		dateFrom.setDate(dateFrom.getDate()+90);
		
		 if(dateFrom.getDate()<10&&(dateFrom.getMonth()+1)<10) {
			 var dateToVar=(dateFrom.getYear()+1900)  +"-"+"0"+(dateFrom.getMonth()+1)+"-"+"0"+dateFrom.getDate();
			  }
			  else if(dateFrom.getDate()<10){
			 	var dateToVar= (dateFrom.getYear()+1900)+"-"+(dateFrom.getMonth()+1)+"-"+ "0"+dateFrom.getDate();
			 	 
			 	 }
			  else if((dateFrom.getMonth()+1)<10){
			 	 var dateToVar=(dateFrom.getYear()+1900)+"-"+"0"+(dateFrom.getMonth()+1)+"-"+ dateFrom.getDate();
			 	 
			 	 }
			  else{
			 	 var dateToVar=(dateFrom.getYear()+1900)+"-"+(dateFrom.getMonth()+1)+"-"+ dateFrom.getDate();
			 	 }
			 	
			 	  $("#dateTo").val(dateToVar);
		
	}
	
}






function setToBucketToDate(){


	var dateFrom = new Date($("#dateFrom").val());
	var dateTo = new Date($("#dateTo").val());
	
	var timeDiff = Math.abs(dateTo.getTime() - dateFrom.getTime());
	var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
	if(diffDays>90){
		
		dateTo.setDate(dateTo.getDate()-90);
		
		 if(dateTo.getDate()<10&&(dateTo.getMonth()+1)<10) {
			 var dateToVar=(dateTo.getYear()+1900)  +"-"+"0"+(dateTo.getMonth()+1)+"-"+"0"+dateTo.getDate();
			  }
			  else if(dateTo.getDate()<10){
			 	var dateToVar= (dateTo.getYear()+1900)+"-"+(dateTo.getMonth()+1)+"-"+ "0"+dateTo.getDate();
			 	 
			 	 }
			  else if((dateTo.getMonth()+1)<10){
			 	 var dateToVar=(dateTo.getYear()+1900)+"-"+"0"+(dateTo.getMonth()+1)+"-"+ dateTo.getDate();
			 	 
			 	 }
			  else{
			 	 var dateToVar=(dateTo.getYear()+1900)+"-"+(dateTo.getMonth()+1)+"-"+ dateTo.getDate();
			 	 }
			 	
			 	  $("#dateFrom").val(dateToVar);
		
	}
	
}



$(function(){

	    $('.generateXLS').click(function(){
	    	 document.getElementById('id01').style.display='none'
       tableToExcel('caseTable', 'COMM Number of Case Created')
       return false
	     
	    })
	})



 $( "select").change(function() {

	 alert($(this).val())
	 
	 
 });
	
	



	// Script to open and close sidenav
	function w3_open() {
		document.getElementById("mySidenav").style.display = "block";
		document.getElementById("myOverlay").style.display = "block";
	}

	function w3_close() {
		document.getElementById("mySidenav").style.display = "none";
		document.getElementById("myOverlay").style.display = "none";
	}

	var id = '${sid}';
	
	
	

</script>



	