$(document).ready(function() {
	
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
            "<input type=\"image\" src=\"../img/bin.png\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"Delete\" class=\"delete\" width=\"20\" height=\"20\" id=\""+ row.id +"\" data-row-id=\"" + row.id + "\"></input> ";
            
        }
    }
}).on("loaded.rs.jquery.bootgrid", function()
		{
    grid.find(".view").on("click", function(e)
    {
    	alert("Clicked");
    	window.location.replace("/employee");
    	$("#divdeps").dialog('open');
    }).end().find(".btn-danger").on("click", function(e)
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