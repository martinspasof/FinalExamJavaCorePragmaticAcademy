<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="notificationAnimalList" value="<%= request.getSession().getAttribute(\"notificationAnimalList\") %>" />
<c:set var="showMsg"  value="<%= request.getSession().getAttribute(\"showMessage\") %>" />
<c:set var="msg"  value="<%= request.getSession().getAttribute(\"messageText\") %>" />

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Book List</title>
<script src="../js/jquery-3.1.1.min.js"	type="text/javascript"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<body id="bodyAnimalList">
<h1 style="margin-left:20px;">Animal List</h1>
<br/>	

<c:if test="${not empty showMsg}"><div id="actionMessage" class="alert alert-success" style="width:60%;margin-left:15%;<c:if test="${empty showMsg}"> ${display}</c:if>" >
   <strong  style="margin-left:32%">${msg}</strong>
</div></c:if>
	<br/>
<form name="actionNotification" action="../DynamicServlet" method="post" >
<div style="float:right;margin-right: 54%;"> Enter output file name: <input type="text" id="insertFileName" name="insertFileName"/>
<button class="btn btn-primary btn-sm" style="margin-left: 17px;margin-bottom:5px;" type="submit" onclick="sendButton()">Save in new file</button></div>
<input type="hidden" name="hiddenActionName" />	
	<br/>	
<div class="col-sm-13">
	<table id="bookList" class="table table-bordered">
		<thead>
			<tr>
			    <th>No</th>				
				<th>Date</th>
				<th>Color</th>		
				<th>Breed</th>					
				<th>Sex</th>
				<th>State</th>
				<th>Name</th>		
				<th>DateCreated</th>
				<th>Action</th>	
			</tr>
		</thead>

		  <c:if test="${not empty notificationAnimalList}">		 
		    <input type="hidden" name="hiddenName" />		    
	    	<tbody>
	  
		      <c:forEach var="s" items="${notificationAnimalList}" varStatus="loop" >
		         
		            <tr>
		                <td>${loop.index+1}</td>		               
		                <td>${s.value.getDate()}</td>
		                <td>${s.value.getColor()}</td>
		                <td>${s.value.getBreed()}</td>
		                <td>${s.value.getSex()}</td>	
		                <td>${s.value.getState()}</td>		                		                
		                <td>${s.value.getName()}</td>
		                <td>${s.value.getDateCreated()}</td>
		                <td>
			                  <input type="button" class="btn btn-danger btn-sm" style="margin-bottom:2px;" 
			                  name="deleteNotificationAnimal_${s.value.getName()}${s.value.getColor()}${s.value.getBreed()}" 
			                  id="deleteNotificationAnimal_${s.value.getName()}${s.value.getColor()}${s.value.getBreed()}" 
			                  value="Delete" onclick="actionButton(this)"/>
		                </td>
		            </tr>
		         
		      </c:forEach>
		        
	        </tbody>
		</c:if>

	</table>
	</div>
</form>	
<script>
$(document).ready(function() {
    //hide message after add, delete, sale book after 2000ms
	setTimeout(function() {  $('#actionMessage').hide() }, 2000);	
});

function actionButton(el){
	var res = el.id.split("_");

	document.actionNotification.hiddenActionName.value=res[0];
	document.actionNotification.hiddenName.value=res[1];
	document.actionNotification.submit();
}


function sendButton(){	
	document.actionNotification.hiddenActionName.value="createFileRecords";		
	
}

</script>
</body>
</html>