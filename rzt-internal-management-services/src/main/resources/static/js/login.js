$(document).ready(function() {
	if ($("#errorMessage").val() == "error") {
		alert("Invalid User");
	}

	$("#loginButton").on("click", function(e) {
		var username=$("#userName").val();
		var password=$("#userPassword").val();
		alert("password=="+password);
		$.ajax({
			headers : {
				'Content-Type' : 'application/json'
			},
			type : "GET",
			url : "/login"+ '?username=' + username + '&password='
				+ password,
		}).done(function(url) {
			alert(url);
			window.location.replace(url);
		});
	});
	
	$("#googleLogin").on("click", function(e) {
		$.ajax({
			headers : {
				'Content-Type' : 'application/json'
			},
			type : "GET",
			url : "/GoogleOAuth/getOAuthUrl"
		}).done(function(url) {
			window.location.replace(url);
		});
	});
});