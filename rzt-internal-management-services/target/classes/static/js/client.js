$(document).ready(function() {
	$("#SecondAddress").hide();
	$("#AddImg").on("click", function(e) {
		e.preventDefault();
		$("#SecondAddress").show();
		$("#AddImg").hide();
	});
	$("#RemoveImg").on("click", function(e) {
		e.preventDefault();
		$("#SecondAddress").hide();
		$("#AddImg").show();
	});
	$("#submit").on("click", function(e) {
		e.preventDefault();
		var client = {};
		var clientAddress = {};
		var clientName = $("#clientName").val();
		var addressLine1 = $("#addressLine1").val();
		var addressLine2 = $("#addressLine2").val();
		var city = $("#city").val();
		var state = $("#state").val();
		var country = $("#country").val();
		var postalCode = $("#postalCode").val();
		client.id = null;
		client.clientName = clientName;
		clientAddress.addressLine1 = addressLine1;
		clientAddress.addressLine2 = addressLine2;
		clientAddress.city = city;
		clientAddress.state = state;
		clientAddress.country = country;
		clientAddress.postalCode = postalCode;
		client.clientAddress = [];
		client.clientAddress[0] = clientAddress;
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "/client/createOrUpdate",
			data : JSON.stringify(client),
			dataType : "json",

		}).done(function(obj) {
			$.each(obj, function(key, element) {
				alert('key: ' + key + '\n' + 'value: ' + element);
			});
		});

	});

});