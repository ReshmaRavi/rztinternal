$(document).ready(function() {
	$
	.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : "GET",
		url : "/users/findAllUsers",
	}).done(function(jsonResponse){
		for(var i=0;i<jsonResponse.length;i++){
			jsonResponse[i].id=i+1;
		}
		$("#grid").bootgrid("clear");
		$("#grid").bootgrid().bootgrid("append", jsonResponse);
	});
	
	$("#addButton").on("click", function() {
		  $("#myForm").find('input:text, input:password, input:file, select, textarea').val('');
		    $("#myForm").find('input:radio, input:checkbox')
		         .removeAttr('checked').removeAttr('selected');
		$('#myModal').modal('show'); 
	});
			
	
	$("#saveUser").on("click", function(e) {
		e.preventDefault();
		var users = {};
		var employeeId = $("#userId").val();
		var username = $("#username").val();
		var password = $("#password").val();
		var isAdmin = $("#isAdmin").is(':checked');
		
		users.id=null;
		users.employeeId=employeeId;
		users.username=username;
		users.password=password;
		users.isAdmin=isAdmin;
		alert(isAdmin);
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "/users/createOrUpdate",
			data : JSON.stringify(users),
			dataType : "json",

		}).done(function(obj) {
	
		});
		
	});
	var rowIds = [];
	var grid = $("#grid").bootgrid({
	    selection:true,
	    multiSelect: false,
	    rowSelect : true,
	    formatters: {
	        "commands": function(column, row)
	        {
	            return "<input type=\"image\" src=\"../img/view.png\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"View\" class=\"view\" width=\"20\" height=\"20\" id=\""+ row.id +"\" data-row-id=\"" + row.id + "\"></input> " + 
	            "<input type=\"image\" src=\"../img/edit.png\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Edit\" class=\"edit\" width=\"20\" height=\"20\" id=\""+ row.id +"\" data-row-id=\"" + row.id + "\"></input> " + 
	            "<input type=\"image\" src=\"../img/bin.png\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Delete\" class=\"delete\" id=\"delete\" width=\"20\" height=\"20\" id=\""+ row.id +"\" data-row-id=\"" + row.id + "\"></input> ";
	            
	        }
	    }
	}).on("loaded.rs.jquery.bootgrid", function()
			{
	    grid.find(".view").on("click", function(e)
	    {
	    	alert("Clicked");
	    	window.location.replace("/users");
	    	$("#divdeps").dialog('open');
	    }).end().find(".edit").on("click", function(e)
	    {

	    	var username=$(this).closest('tr').find('td:eq(1)').text();
	    	var empId=$(this).closest('tr').find('td:eq(2)').text();
	    	var isAdmin=$(this).closest('tr').find('td:eq(5)').text();
	    	$("#username").val(username);
	    	$("#empId").val(empId);
	    	if(isAdmin=="true")	
	    	{
	    		$("#isAdmin").prop( "checked", true );
	    	}
	    	else
	    	{
	    		$("#isAdmin").prop( "checked", false );
	    	}
	    	$('#myModal').modal('show'); 
	    	var userData="&id="+iden;
	        $.ajax({
	        	
				  type: "POST",
				  url: "../Locale/DeleteServlet",
				  data:userData,
	        }) .done(function ( responseText ) {
	        	$("#divdeps").dialog('close');
					  if(responseText=="Done")
						  {
						  		$.confirm({
								'title'		: 'Delete Successfull!!!',
								'message'	: 'You have successfully deleted the Details. <br />What do you want to do next?',
								'buttons'	: {
									'Register'	: {
										'class'	: 'blue',
										'action': function(){
											window.location.replace("index.html");
										}
									},
									'Search'	: {
										'class'	: 'gray',
										'action': function(){
											window.location.replace("search.html");
										}
									}
								}
							});
						  }
					  else
						  {
						  
						  $.confirm({
								'title'		: 'Deletion Failed!!!',
								'message'	: 'Some error occured while deleting Details. <br />What do you want to do next?',
								'buttons'	: {
									'Search'	: {
										'class'	: 'blue',
										'action': function(){
										}
									},
									'Search'	: {
										'class'	: 'gray',
										'action': function(){
											window.location.replace("search.html");
																										}
																									}
																								}
							});
						  }
						  
				    
				});
	    }).end().find(".delete").on("click", function(e)
	    {
	    	e.preventDefault();
	    	   var empnum=$(this).closest('tr').find('td:eq(2)').text();
	    	    $.confirm({
	    	        text: "Are you sure to delete the User??! Please confirm:",
	    	        confirm: function(button) {
	    	         
	    	        	$
	    	    		.ajax({
	    	    			headers : {
	    	    				'Accept' : 'application/json',
	    	    				'Content-Type' : 'application/json'
	    	    			},
	    	    			type : "GET",
	    	    			url : "/users/removeUsers?empNum="+empnum,
	    	            }) .done(function ( response ) {
	    	    				  if(response.errorMessage==null)
	    	    					  {modal({
	    	    							type: 'info',
	    	    							title: 'Deletion Success!!!',
	    	    							text: 'User has been deleted Successfully!',
	    	    							autoclose: false,
	    	    							callback:function(argument){
	    	    								$
	    	    								.ajax({
	    	    									headers : {
	    	    										'Accept' : 'application/json',
	    	    										'Content-Type' : 'application/json'
	    	    									},
	    	    									type : "GET",
	    	    									url : "/users/findAllUsers",
	    	    								}).done(function(jsonResponse){
	    	    									$("#grid").bootgrid("clear");
	    	    									$("#grid").bootgrid().bootgrid("append", jsonResponse);
	    	    								});
	    	    							},
	    	    						});}
	    	    				  else
	    	    					  {
	    	    					  
	    	    						modal({
	    	    							type: 'error',
	    	    							title: 'Error',
	    	    							text: 'Error deleting user!',
	    	    							callback:function(argument){
	    	    								$
	    	    								.ajax({
	    	    									headers : {
	    	    										'Accept' : 'application/json',
	    	    										'Content-Type' : 'application/json'
	    	    									},
	    	    									type : "GET",
	    	    									url : "/users/findAllUsers",
	    	    								}).done(function(jsonResponse){
	    	    									$("#grid").bootgrid("clear");
	    	    									$("#grid").bootgrid().bootgrid("append", jsonResponse);
	    	    								});
	    	    							},
	    	    						});
	    	    						
	    	    					  }
	    	    					  
	    	    			    
	    	    			});
	    	        },
	    	        cancel: function(button) {
	    	        }
	    	});
	    	
	    });
	});
});	
