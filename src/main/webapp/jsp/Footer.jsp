<!-- <div id="id01" class="w3-modal w3-border" style="width:100%">
    <div class="w3-modal-content w3-border w3-border-blue"  style="width:100%">
      <header class="w3-container "> 
        <span onclick="document.getElementById('id01').style.display='none'" style='cursor:pointer'
        class="w3-badge w3-blue w3-large w3-button w3-display-topright">&times;</span>
        <h2 class="w3-light-gray">Case Detail</h2>
         <div  class="w3-container w3-right" id="export">
     
      
      </div>
      
      
      </header>
     
      <div class="w3-container" id="caseTable"   >
     
      </div>
      
     
    </div>

    
  </div> -->

<footer class="w3-center w3-light-grey w3-padding-32 w3-round-xxlarge">
  <p>Copyright © <a href="" title="" target="_blank" class="w3-hover-text-green">JSW</a>
   2017 All rights reserved
  </p>
  
  
</footer>


<script
	src="/jsw-report/js/bootstrap-multiselect.js"></script>	
<script>
$('.multiSelect').multiselect({allSelectedText: 'ALL',includeSelectAllOption: true, buttonWidth: "50%"})


    .multiselect('updateButtonText');


</script>	


<script type="text/javascript">



  
  var today = new Date();
	 today=today.toString();
	 var now=(today.split("GMT"));

	$(".currentDate").html(now[0]); 
	
		

 function openDataTable(){

  $("#dataTableId").DataTable( );

   var table=  $("#dataTableId");
   $('#export').html("");
	 var buttons = new $.fn.dataTable.Buttons(table, {
     buttons: [
       'copyHtml5',
       'excelHtml5',
       'csvHtml5'
    ]
}).container().appendTo($('#export'));

 
 }
 
 
 
 
 
/*  ,
       {
                extend: 'pdfHtml5',
                orientation: 'landscape',
                pageSize: 'LEGAL'
               ,
               exportOptions: {
                    columns: [ 2,3,4,5,6,19,42 ]
                }
            },
        {
                extend: 'print',
                orientation: 'landscape',
                pageSize: 'LEGAL'
               ,
               exportOptions: {
                    columns: [ 2,3,4,5,6,19,42 ]
                }
            } */
 
 
            if( $("select[name='businessGroup']").length){
                if( $("select[name='businessGroup']").has('option').length <= 0 ) {
                	
                alert(" Hi ! Please Contact ADMIN to provide user filters rights. Thank You.");
                }
                }
  </script>