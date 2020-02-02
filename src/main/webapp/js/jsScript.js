
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
function getCaseStudiedTableByLocation(thisVariable,ajaxUrl){
	$("#caseTable").html("");
	 // alert("table");
	var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
	    
	 var  secondColumnValue = $(thisVariable).closest("tr").find("td:eq(1)").text();
  
	 generateTableByService(ajaxUrl,firstColumnValue,secondColumnValue);
 
}

  function getCaseStudiedTableByBusiness(thisVariable,ajaxUrl){
		$("#caseTable").html("");
		
		var firstColumnValue=$(thisVariable).closest('tr').children('td:first').text();
		 var  secondColumnValue="";
		var count=0;
		 $(".loc"+firstColumnValue).each(function(){
			 
			 if(count==0)
				 secondColumnValue+=$(this).text();
			 else
				 secondColumnValue+=","+$(this).text(); 
			 
			 
			   count++;
			})
			
			
		 generateTableByService(ajaxUrl,firstColumnValue,secondColumnValue);
		
	 
	}
  
  function getCaseStudiedTableByAll(thisVariable,ajaxUrl){
		$("#caseTable").html("");
		
		var firstColumnValue="";
		 var  secondColumnValue="";
		var count=0;
		$(".busi").each(function(){
			 
			 if(count==0)
				 firstColumnValue+=$(this).text();
			 else
				 firstColumnValue+=","+$(this).text(); 
			 
			 
			   count++;
			})
count=0;
		
		 $(".loc"+firstColumnValue).each(function(){
			 
			 if(count==0)
				 secondColumnValue+=$(this).text();
			 else
				 secondColumnValue+=","+$(this).text(); 
			 
			 
			   count++;
			})
		 generateTableByService(ajaxUrl,firstColumnValue,secondColumnValue);
		
	 
	}
  
  
  function  generateTableByService(AjaxUrl,business,location){
	 // alert("table");
	  
	  $.ajax({                    
    	  url: '/jsw-report/NW/'+AjaxUrl+'/'+location+"@"+business,     
    	  type: 'GET',
    	async:false,
    	cache:false,
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


	