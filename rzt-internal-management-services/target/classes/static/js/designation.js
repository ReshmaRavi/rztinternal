$(document).ready(function() {

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