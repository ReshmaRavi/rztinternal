$(document).ready(function() {
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
});	
