
<nav class="w3-sidenav w3-text-white w3-collapse w3-animate-left"
	style="z-index: 3; width: 300px;background-color:#3f51b5" id="mySidenav" >
	<br>
<p class="w3-right w3-hide" onClick="openchat()" id="myChatLogo">
		<i class="fa fa-comments fa-fw w3-margin-right"></i>
	</p>
	<div class="w3-container">
		<a href="#" onclick="w3_close()"
			class="w3-hide-large w3-right w3-jumbo w3-padding" title="close menu">
			<i class="fa fa-remove"></i>
		</a>
		<center onclick="window.location.href='/jsw-report/home'">
		<img src="<c:url value='/img/logo.png'/>"
			style="width:100%;height: 15%" class="w3-round w3-white" ><br>
		</center><br>


		<hr style="width: 100%; border: 3px solid white" class="w3-round">

		<h3 class="w3-center">
			<b>JSW-REPORTING</b>
		</h3>
	
	</div>
	<c:forEach var="parentMenu" items="${menuNames}">
	<c:if test="${parentMenu.subMenu=='null'}">
	<div class="w3-dropdown-hover">
		<a href="${parentMenu.href}" 
			class="w3-padding w3-text-white w3-border w3-round-xxlarge" ><i
			class="fa ${parentMenu.action} fa-fw w3-margin-right"></i>	${parentMenu.menuName}</a>
				<c:if test="${parentMenu.isleaf=='n'}">
			<div  class="w3-dropdown-content w3-animate-zoom w3-right w3-padding w3-medium" style='background-color:#3f51b5'>
	<c:forEach var="subMenufirst" items="${menuNames}">
	<c:if test="${subMenufirst.subMenu==parentMenu.menuName}">
	<div class="dropdown dropbtn" >
	<a  href="${subMenufirst.href}"
				class="w3-padding w3-border w3-round-xxlarge 1div"><i
				class="fa ${subMenufirst.action} fa-fw w3-margin-right"></i>${subMenufirst.menuName}</a>
		<c:if test="${subMenufirst.isleaf=='n'}">		
	<div class="dropdown-content w3-animate-zoom w3-right w3-padding w3-medium" style='background-color:#3f51b5'>

<c:forEach var="subMenusecond" items="${menuNames}">
	<c:if test="${subMenusecond.subMenu==subMenufirst.menuName}">

<a href="${subMenusecond.href}" class="w3-padding w3-border w3-round-xxlarge"><i
				class="fa ${subMenusecond.action} fa-fw w3-margin-right"></i>${subMenusecond.menuName}</a>

</c:if>
	</c:forEach>
	
	</div></c:if>
	
	
		</div>
	</c:if>
	</c:forEach>
	     </div> 	</c:if>
	     
	     
	      </div>
	</c:if>

	</c:forEach>
<a
		href="/jsw-report/login" onclick="w3_close()"
		class="w3-text-white w3-padding w3-border w3-round-xxlarge" ><i
		class="fa fa-sign-out fa-fw w3-margin-right"></i>Log Out</a>
		
		<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
		<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

</nav >
<style>
.dropbtn {
   
    color: white;

    
    cursor: pointer;
}

.dropdown {
    position: relative;
   
}

.dropdown-content {
    display: none;
    position: absolute;

    min-width: 160px;

    z-index: 1;
}

.dropdown-content a {
    color: black;

    display: block;
}

.dropdown-content a:hover {background-color: #f1f1f1}

.dropdown:hover .dropdown-content {
    display: block;
}

#img{
 animation: animatedBackground 40s linear infinite;
}
@keyframes animatedBackground {
    from { background-position: 0 0; }
    to { background-position: 100% 0; }
}
</style>
<script>

/* 
       var image = $("#img");
   function animateLogo(){   
      // image.animate({height: '300px', opacity: '0.4'}, "slow");
       image.animate({width: '1%', opacity: '0.4'}, "slow");
    //   image.animate({height: '100px', opacity: '0.4'}, "slow");
     image.animate({width: '100%', opacity: '1'}, "slow");
 
  
   
       
       setInterval(animateLogo(), 2000);
   } 

   animateLogo(); */

 
   
   
   
   
	var sesAttr =
<%=request.getSession().getAttribute("sessionVar")%>
	if (sesAttr == 1 & '${userName}' != "admin") {
		var x = document.getElementById("myChatLogo");
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}

	}

	if ('${userName}' == "admin") {
		var x = document.getElementById("myAdminLogo");
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}

	} else {
		var x = document.getElementById("myOtherLogo");
		if (x.className.indexOf("w3-show") == -1) {
			x.className += " w3-show";
		} else {
			x.className = x.className.replace(" w3-show", "");
		}

	}

	
	

</script>


