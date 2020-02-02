
onmessage = function(eData) {
	
alert(eData);
var newJsonresultData=JSON.parse(eData);
    	  var tableString="<table class='w3-table-all w3-hoverable w3-striped w3-border w3-center dataTableId' id='dataTableId'>";
    	
       	   var countRow=0;
    	   for (var jsonOuterKey in newJsonresultData){
    	   
    	   if(countRow==0){
    	   tableString+="<thead> <tr style='background-color:blue;color:white'> "
    	   
    	      var newInnerJsonresultData=(newJsonresultData[jsonOuterKey]).toString();
    		
    		   var newInnerJsonresultDataArray = new Array();
    		 
    		   newInnerJsonresultDataArray = newInnerJsonresultData.split(",");
    		   var countTd=0;
    		   for (a in newInnerJsonresultDataArray ) {
    		
    			  if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
    			  tableString+="<th>"+newInnerJsonresultDataArray[a]+"</th>";
    			  else
    			    tableString+="<th style='display:none'>"+newInnerJsonresultDataArray[a]+"</th>";
    			 countTd++;
    			}
    		   
    		     
    		     tableString+="</tr></thead><tbody>";
    	   
    	   
    	   }
    	   else{
    	   
    	   
    		   tableString+="<tr>";
    		
    		   var newInnerJsonresultData=(newJsonresultData[jsonOuterKey]).toString();
    		
    		   var newInnerJsonresultDataArray = new Array();
    		 
    		   newInnerJsonresultDataArray = newInnerJsonresultData.split(",");
    		     var countTd=0;
    		   for (a in newInnerJsonresultDataArray ) {
    		
    			  
    			  if(countTd==1||countTd==2||countTd==3||countTd==4||countTd==5||countTd==6||countTd==19||countTd==42)
    			  tableString+="<td>"+newInnerJsonresultDataArray[a]+"</td>";
    			  else
    			    tableString+="<td style='display:none'>"+newInnerJsonresultDataArray[a]+"</td>";
    			    countTd++;
    			}
    		   
    		     
    		     tableString+="</tr>";
    	   
    	   }
    	   
    	   countRow++;
    	   
    	   
    	   
    	   
    	   
    	   
    		    
    	   }
    	   tableString+="</tbody></table>";
    	   
    	   
    	   postMessage(tableString);  
}