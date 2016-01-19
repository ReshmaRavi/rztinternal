$(document).ready(function(){
	
	$(".Save").on("click",function(){
		var roleName= $("#roleName").val();
		console.log("rolename=",roleName);
		EmployeeRole role= new EmployeeRole();
		role.name=roleName;
		$.ajax({
				type: "POST",
				url:"/role/createOrUpdate",
				data: {JSON.stringify(role)}
		});
		
	});
	
	function EmployeeRole(){
		this.id;
		this.name;
		this.isActive;
	};
});