$(document).ready(function() {

	$
	.ajax({
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		type : "GET",
		url : "/designation/findAllDesignation",
	}).done(function(jsonResponse){
		for(var i=0;i<jsonResponse.length;i++){
			jsonResponse[i].id=i+1;
		}
		$("#grid").bootgrid("clear");
		$("#grid").bootgrid().bootgrid("append", jsonResponse.object);
	});
	
	$("#saveDesignation").on("click", function() {
		alert("in designation");
		var designation = {};
		var designationName = $("#designationName").val();
		designation.name = designationName;
		console.log("designationName", designationName);
		alert("designaion name is " + designationName);
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "/designation/createOrUpdate",
			data : JSON.stringify(designation)
		});

	});

});