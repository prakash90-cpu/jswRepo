
function getCaseType(){
	
	var progressType= $("#moduleName").val();
	 $('#caseType').html('');
	
	 $.ajax({
			type : "GET",
			contentType : "application/json",
			async:false,
			cache: false,
			dataType:"json",
			url : "/jsw-report/externalSystems/caseType/"+progressType,
			
			 success : function(response) {
				 
				 
				 var jsonArray = eval(response);
				
				 $.each(jsonArray,function(index,obj){ 
					
					 $('#caseType').append('<option value='+obj.key+'>'+obj.value+'</option>');
					
				 });
				
				 
				
			     },
			     error : function(e) {
			      alert('Error: ' + e); 
			     }
		});
	
}



