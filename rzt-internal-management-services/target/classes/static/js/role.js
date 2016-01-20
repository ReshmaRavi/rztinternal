$(document).ready(function(){
	alert("ready");
	$(".Save").on("click",function(){
		alert("Here");
		var role={};
		var roleName= $("#roleName").val();
		console.log("rolename=",roleName);
		role.id=null;
		role.name=roleName;
		$.ajax({
				headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
				},
				type: "POST",
				url:"/role/createOrUpdate",
				data: JSON.stringify(role),
				dataType: "json"
				
		});
		
	});
	
});