$(document).ready(function() {
	window.name = "home";
	$('[data-toggle="tooltip"]').tooltip(); 
	$("#logout").on("click", function() {
		$
		.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "/logout",
		}).done(function(response){
			if(response=="logout");
			{
				alert("logging out");
			window.location.replace("/");
			}
		});
		
	});
	var name=$("#dropdownButton").val()+"<span class=\"caret\" id=\"dp-caret\"></span>";
	$("#dropdownButton").html(name);
$("#employee-button").on("click", function() {
						$
						.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : "GET",
							url : "/employee/findAllEmployeesMin",
						}).done(function(jsonResponse){
							for(var i=0;i<jsonResponse.length;i++){
								jsonResponse[i].id=i+1;
							}
							$("#grid").bootgrid("clear");
							$("#grid").bootgrid().bootgrid("append", jsonResponse);
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
		{// $("#delete").confirm();
    grid.find(".view").on("click", function(e)
    {
    	alert("Clicked");
    	window.location.replace("/employee");
    	$("#divdeps").dialog('open');
    }).end().find(".edit").on("click", function(e)
    {

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
    }).end().find(".delete").on("click", function(e,rows)
    {
    	e.preventDefault();
    	   var empnum=$(this).closest('tr').find('td:eq(3)').text();
    	    $.confirm({
    	        text: "Are you sure to delete the Employee??! Please confirm:",
    	        confirm: function(button) {
    	         
    	        	$
    	    		.ajax({
    	    			headers : {
    	    				'Accept' : 'application/json',
    	    				'Content-Type' : 'application/json'
    	    			},
    	    			type : "GET",
    	    			url : "/employee/removeEmployeeByNumber?empNum="+empnum,
    	            }) .done(function ( response ) {
    	    				  if(response.errorMessage==null)
    	    					  {modal({
    	    							type: 'info',
    	    							title: 'Deletion Success!!!',
    	    							text: 'Employee has been deleted Successfully!',
    	    							autoclose: false,
    	    							callback:function(argument){
    	    								$
    	    								.ajax({
    	    									headers : {
    	    										'Accept' : 'application/json',
    	    										'Content-Type' : 'application/json'
    	    									},
    	    									type : "GET",
    	    									url : "/employee/findAllEmployeesMin",
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
    	    							text: 'Error deleting employee!',
    	    							callback:function(argument){
    	    								$
    	    								.ajax({
    	    									headers : {
    	    										'Accept' : 'application/json',
    	    										'Content-Type' : 'application/json'
    	    									},
    	    									type : "GET",
    	    									url : "/employee/findAllEmployeesMin",
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
$("#project-button").on("click", function() {
	$
	.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : "GET",
		url : "/project/findAllProjects",
	}).done(function(jsonResponse){
		$("#grid").bootgrid("clear");
		$("#grid").bootgrid().bootgrid("append", jsonResponse);
	});
});
});