<script>

 function callBusinessGroupByLocation(){

	 alert("called");
	var selectedBusinessGroup=$('[name="businessGroup"]').val();
	selectedBusinessGroup=selectedBusinessGroup.replace("," , "','");
alert(selectedBusinessGroup);

	$.ajax({                    
 	  url: '/jsw-report/NW/getLocationByGroup/'+selectedBusinessGroup,     
 	  type: 'GET',
 	async:false,
 	cache:false,
 	  success: function(data)         
 	  {

 	   var newJsonBusinessData=""+JSON.parse(data)[0];
 	 
 	   newJsonBusinessData=newJsonBusinessData.split(",");
 	    
 		 var optionString="<option>ALL</option>";
 		
 		 for (var countOp=0;countOp<newJsonBusinessData.length;countOp++){
 		  			optionString+="<option>"+newJsonBusinessData[countOp]+"</option>";
 		
 		 }

		 $('[name="Business"]').html(optionString);
 	
 
 	     var newJsonLocationData=""+JSON.parse(data)[1];

 	
 		newJsonLocationData=newJsonLocationData.split(",");
 	 
 		 optionString="<option>ALL</option>";
 		 for (var countOp=0;countOp<newJsonLocationData.length;countOp++){
 		  			optionString+="<option>"+newJsonLocationData[countOp]+"</option>";
 		
 		 }

		 $('[name="Location"]').html(optionString);
 			   
 	  }
	 
	 }); 
  
} 

function callGroupByLocationService(loc,busi){

	var selectedBusinessGroup=$('[name="businessGroup"]').val();	


	$.ajax({                    
 	  url: '/jsw-report/NW/getLocationByGroup/'+selectedBusinessGroup,     
 	  type: 'GET',
 	async:false,
 	cache:false,
 	  success: function(data)         
 	  {

 	   var newJsonBusinessData=""+JSON.parse(data)[0];
 	 
 	   newJsonBusinessData=newJsonBusinessData.split(",");
 	    
 	    var optionString="";
 	  if( newJsonBusinessData.length>1)
 		optionString+="<option >ALL</option>";
 		
 		 for (var countOp=0;countOp<newJsonBusinessData.length;countOp++){
 		  if(newJsonBusinessData[countOp].indexOf(busi) !=-1)
 		  			optionString+="<option selected>"+newJsonBusinessData[countOp]+"</option>";
 		else
 		optionString+="<option>"+newJsonBusinessData[countOp]+"</option>";
 	
 		 }

		 $('[name="Business"]').html(optionString);
 	
 
 	     var newJsonLocationData=""+JSON.parse(data)[1];

 	
 		newJsonLocationData=newJsonLocationData.split(",");
 	 
 		
 		 
 		  
 		 var optionString="";
 	  if( newJsonLocationData.length>1)
 		optionString+="<option >ALL</option>";
 		 
 		 for (var countOp=0;countOp<newJsonLocationData.length;countOp++){
 		 
 		 if(newJsonLocationData[countOp].indexOf(loc) !=-1)
 			optionString+="<option selected>"+newJsonLocationData[countOp]+"</option>";
 			else
 			optionString+="<option>"+newJsonLocationData[countOp]+"</option>";
 			
 		
 		 }

		 $('[name="Location"]').html(optionString);
 			   
 	  }
	 
	 }); 
	 
	

	  
} 

function callLocationByBusinessService(loc){
	var selectedLocation=($('[name="Business"]').val().split("-")[0]);
	var selectedBusinessGroup=$('[name="businessGroup"]').val();	

	  $.ajax({                    
 	  url: '/jsw-report/NW/getBusinessByLocation/'+selectedLocation+"@"+selectedBusinessGroup,     
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
 		 
 			 if(newJsonresultData[roleDataKey].indexOf(loc) !=-1)
 			optionString+="<option selected>"+newJsonresultData[roleDataKey]+"</option>";
 		 else
 		 optionString+="<option>"+newJsonresultData[roleDataKey]+"</option>";
 		 
 		 }
 
		 $('[name="Location"]').html(optionString);
}
else
	  $('[name="Location"]').html("");
 	  }
	 
	
	 }); 
 
}




</script>