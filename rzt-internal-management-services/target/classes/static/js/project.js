	$(document)
		.ready(
				function() {
					var clientList = [];
					var employeeList = [];
					$("#show-employees").hide();
					$
							.ajax({
								headers : {
									'Accept' : 'application/json',
									'Content-Type' : 'application/json'
								},
								type : "GET",
								url : "/client/getAllClients",

							})
							.done(
									function(response) {
										if (response.object
												&& response.object.length > 0) {
											$('#client').html("");
											var option = '';
											for (var i = 0; i < response.object.length; i++) {
												clientList
														.push(response.object[i]);
												option += '<option value='
														+ response.object[i].id
														+ ' >'
														+ response.object[i].clientName
														+ '</option>';
											}
											$('#client').html(option);
										}
									});

					$("#saveProject").on("click", function() {
						var project = {};
						project.name = $("#projectName").val();
						project.status = $("#status").val();
						project.repository = $("#repository").val();
						var clientId = $("#client").val();
						project.client = findClient(clientId);
						$.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : "POST",
							url : "/project/createOrUpdateProject",
							data : JSON.stringify(project)
						});

					});

					$("#listProjects").on("click", function() {
						$("#show-employees").show();
						$.ajax({
							headers : {
								'Accept' : 'application/json',
								'Content-Type' : 'application/json'
							},
							type : "GET",
							url : "/project/findAllProjects",
						}).done(function(response) {
							console.log("list of projects...");
							console.log(response);
							fetchProjects(response, function() {

							});
						});
					});

					var fetchProjects = function(projects, callback) {
						if (projects.object && projects.object.length < 1) {
							$("#projectTable")
							.html(
									'<tr style="text-align: center;"><td>No Data Found</td></tr>');
							return;
						}
						
						else {
							$('#projectTable').html("");
							var option = '';
							for (var i = 0; i < projects.object.length; i++) {
								var tr = '';
								tr += '<tr id=' + projects.object[i].id + ' >';
								tr += '<td ><input type="checkbox" class="checkbox"></td><td>&nbsp</td>';
								tr += '<td><span>' + projects.object[i].id
										+ '</span></td> <td>&nbsp</td>';
								tr += '<td><span>' + projects.object[i].name
										+ '</span></td><td>&nbsp</td>';
								tr += '<td><span>'
										+ projects.object[i].client.clientName
										+ '</span></td><td>&nbsp</td>';
								tr += '<td><span>' + projects.object[i].status
										+ '</span></td><td>&nbsp</td>';
								tr += '<td><span>'
										+ projects.object[i].repository
										+ '</span></td><td>&nbsp</td>';
								tr += '</td>';
								tr += '</tr>';
								$('#projectTable').append(tr);
							}
							callback();
						}
					};

					var findClient = function(searchId) {
						if (clientList.length < 1)
							return null;
						var id = parseInt(searchId);
						for (var i = 0, len = clientList.length; i < len; i++) {
							if (clientList[i].id === id)
								return clientList[i];
						}
						return -1;
					}
				});
