$(document)
		.ready(
				function() {
					$(function() {
						$("#datepicker").datepicker();
					});
					var designationList = [];
					$
							.ajax({
								headers : {
									'Accept' : 'application/json',
									'Content-Type' : 'application/json'
								},
								type : "GET",
								url : "/designation/findAllDesignation",

							})
							.done(
									function(response) {
										if (response.object
												&& response.object.length > 0) {
											$('#designation').html("");
											var option = '';
											for (var i = 0; i < response.object.length; i++) {
												designationList
														.push(response.object[i]);
												option += '<option value='
														+ response.object[i].id
														+ ' >'
														+ response.object[i].name
														+ '</option>';
											}
											$('#designation').html(option);
										}
									});




				    var form = $("#example-advanced-form").show();
				     
				    form.steps({
				        headerTag: "h3",
				        bodyTag: "fieldset",
				        transitionEffect: "slideLeft",
				        onStepChanging: function (event, currentIndex, newIndex)
				        {
				            // Allways allow previous action even if the current form is not valid!
				            if (currentIndex > newIndex)
				            {
				                return true;
				            }
				            // Forbid next action on "Warning" step if the user is to young
				            if (newIndex === 3 && Number($("#age-2").val()) < 18)
				            {
				                return false;
				            }
				            // Needed in some cases if the user went back (clean up)
				            if (currentIndex < newIndex)
				            {
				                // To remove error styles
				                form.find(".body:eq(" + newIndex + ") label.error").remove();
				                form.find(".body:eq(" + newIndex + ") .error").removeClass("error");
				            }
				            form.validate().settings.ignore = ":disabled,:hidden";
				            return form.valid();
				        },
				        onStepChanged: function (event, currentIndex, priorIndex)
				        {
				            // Used to skip the "Warning" step if the user is old enough.
				            if (currentIndex === 2 && Number($("#age-2").val()) >= 18)
				            {
				                form.steps("next");
				            }
				            // Used to skip the "Warning" step if the user is old enough and wants to the previous step.
				            if (currentIndex === 2 && priorIndex === 3)
				            {
				                form.steps("previous");
				            }
				        },
				        onFinishing: function (event, currentIndex)
				        {
				            form.validate().settings.ignore = ":disabled";
				            return form.valid();
				        },
				        onFinished: function (event, currentIndex)
				        {
				            alert("Submitted!");
				        }
				    }).validate({
				        errorPlacement: function errorPlacement(error, element) { element.before(error); },
				        rules: {
				            confirm: {
				                equalTo: "#password-2"
				            }
				        }
				    });





					$(".Save")
							.on(
									"click",
									function() {
										var employee = {};
										var empNum = $("#empNumber").val();
										var empFirstName = $("#empFirstName")
												.val();
										var empLastName = $("#empLastName")
												.val();
										var empGender, designation;
										var radios = document
												.getElementsByName('genderS');
										for (var i = 0, length = radios.length; i < length; i++) {
											if (radios[i].checked) {
												empGender = (radios[i].value);
											}
										}
										var dob = $("#datepicker").datepicker(
												'getDate');
										var dateOfJoin = $("#datepickerJoin")
												.datepicker('getDate');
										var address1 = $("#addressLine1").val();
										var address2 = $("#addressLine2").val();
										var city = $("#city").val();
										var state = $("#state").val();
										var country = $("#country").val();
										var postalCode = $("#postalCode").val();

										var address = {};
										address.id = null;
										address.addressLine1 = address1;
										address.addressLine2 = address2;
										address.city = city;
										address.state = state;
										address.country = country;
										address.postalCode = postalCode;
										var addresses = [];
										addresses.push(address);
										employee.id = null;
										employee.employeeNum = empNum;
										employee.firstName = empFirstName;
										employee.lastName = empLastName;
										employee.gender = empGender;
										employee.dateOfBirth = dob;
										employee.dateOfJoining = dateOfJoin;
										employee.empAddress = addresses;
										employee.bloodGroup = $("#bloodGroup")
												.val();
										var desgnId = $("#designation").val();
										employee.designation = findDesignation(desgnId);
										var contactDetails = {};
										contactDetails.personalEmail = $(
												"#personalEmail").val();
										contactDetails.officeEmail = $(
												"#officeEmail").val();
										contactDetails.contactNumber = $(
												"#contactNumber").val();
										contactDetails.emergencyContactNum = $(
												"#emergencyContactNumber")
												.val();
										contactDetails.skypeId = $("#skypeId")
												.val();
										contactDetails.slackId = $("#slackId")
												.val();
										employee.contactDetails = contactDetails;
										console.log(employee);
										console.log("json sting", JSON
												.stringify(employee));
										$
												.ajax({
													headers : {
														'Accept' : 'application/json',
														'Content-Type' : 'application/json'
													},
													type : "POST",
													url : "/employee/createOrUpdate",
													data : JSON
															.stringify(employee)
												});

									});

					var findDesignation = function(searchId) {
						console.log("desg list; ", designationList);
						if (designationList.length < 1)
							return null;
						var id = parseInt(searchId);
						for (var i = 0, len = designationList.length; i < len; i++) {
							if (designationList[i].id === id)
								return designationList[i];
						}
						return -1;
					}

				});