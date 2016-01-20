function getEvent(calendar) {
	$("#wrap").show();
	window.currentCalendar = window.calendarList[calendar.id.replace("b_", "")].calendarId;
	$('#calendar_list > li').each(function() {
		if (this.childNodes && this.childNodes[0].id) {
			$("#" + this.childNodes[0].id).removeClass('button');
		}
	});
	$("#" + calendar.id).addClass('button');
	var eventsList = [];
	var current = new Date(new Date().getFullYear(), new Date().getMonth(), 1);
	var currentClone = new Date(new Date().getFullYear(),
			new Date().getMonth() + 1, 0);
	$.ajax(
			{
				type : "get",
				url : "/user/getEventCount?calendar=" + window.currentCalendar
						+ "&start=" + current.toISOString() + "&end="
						+ currentClone.toISOString(),
				contentType : "application/json"
			}).done(
			function(data) {
				$("#wrapMessage").html("Loading " + data + " events");
				$.ajax(
						{
							type : "get",
							url : "/user/getCalendarBetweenDuration?calendar="
									+ window.currentCalendar + "&start="
									+ current.toISOString() + "&end="
									+ currentClone.toISOString(),
							contentType : "application/json"
						}).done(function(eventsList) {
					renderEvents(eventsList);
					$('#calendar').fullCalendar('gotoDate', new Date());
					$('#calendar').fullCalendar('today');
				});
			});
}

var Clock = {
		  totalSeconds: 0,

		  start: function (time) {
		    var self = this;
		    this.interval = setInterval(function () {
		    if(time){
		    	self.totalSeconds = time/1000;
		    	time = 0;
		    		
		    }
		      self.totalSeconds += 1;
		    
		      Clock.calculateTime(self.totalSeconds);
		  
		            
		    }, 1000);
		  },

		  pause: function (pausedTime) {
			  if(pausedTime){
				  var self = this;
				  self.totalSeconds = pausedTime/1000;
				  Clock.calculateTime(self.totalSeconds);
			  }
				  
		  	  var a =$('#hour').val();
		    clearInterval(this.interval);
		    delete this.interval;
		    var a =$('#hour').val();
		   
		  },

		  resume: function () {
		    if (!this.interval) this.start();
		    
		  },
		  
		  stop: function(){
		   
		   clearInterval(this.interval);
		   delete this.interval;
		    $("#hour").text('00'+":");
		      $("#min").text('00'+":");
		      $("#sec").text('00');
		      
				this.totalSeconds=0;
		  },
		  calculateTime: function(totalSeconds){
			  var hours=  Math.floor(totalSeconds / 3600),
			    minutes=  Math.floor(totalSeconds / 60 % 60),
			   seconds=  parseInt(totalSeconds % 60);
			      
			       hours = (String(hours).length >= 2) ? hours : '0' + hours;
			            minutes = (String(minutes).length >= 2) ? minutes : '0' + minutes;
			            seconds = (String(seconds).length >= 2) ? seconds : '0' + seconds;
			      $("#hour").text(hours+":");
			      $("#min").text(minutes+":");
			      $("#sec").text(seconds);
		  },
		};
function startTimer(){	
	$.ajax(
			{
				type : "get",
				url : "/user/startTimer?eventId=" +window.calEventDetails.event+ "&calendarId=" + window.currentCalendar,
			}).done(function(data){
				if(data == 200)
					Clock.start();
			});
	
}
function resumeTimer(start){
	if(start ==="start")
		restUrl= "/user/getElapsedTime?";
	else
		restUrl= "/user/resumeTimer?";
	$.ajax(
			{
				type : "get",
				url : restUrl+"eventId=" +window.calEventDetails.event+"&calendarId=" + window.currentCalendar,
			}).done(function(data){
				if(data){
					if(start ==="start" && parseInt(data.split(":")[1]) === 0){
						Clock.start(parseInt(data.split(":")[0]));
					}else  if(start ==="start" && parseInt(data.split(":")[1]) === 1){
						Clock.pause(parseInt(data.split(":")[0]));
					}
							
						
					else
						Clock.resume();
				}
					
			});
}

function pauseTimer(){
	$.ajax(
			{
				type : "get",
				url : "/user/pauseTimer?eventId=" +window.calEventDetails.event + "&calendarId=" + window.currentCalendar,
			}).done(function(data){
				if(data == 200)
					Clock.pause();
			});
}

function stopTimer(){
	$.ajax(
			{
				type : "get",
				url : "/user/resetTimer?eventId=" +window.calEventDetails.event+ "&calendarId=" + window.currentCalendar,
			}).done(function(data){
				if(data == 200)
					Clock.stop();
			});
}

function cancelEvent() {
	$('.event_input').val('');
	$('.event_input').hide();
	$('.event_label').show();
}
function deleteEvent() {
	$('#wrap').show();
	$.ajax(
			{
				type : "post",
				url : "/user/deleteEvent?calendarId=" + window.currentCalendar
						+ "&eventId=" + window.calEventDetails.event
			}).done(
			function(data) {
				$('#wrap').show();
				if (data === 200) {
					$("#calendar").fullCalendar('removeEvents',
							window.calEventDetails.id);
					showErrMsg("Deleted successfully", 1);
				} else
					showErrMsg("Failure", 0);
				$("#popup_container").hide();
				$('#wrap').hide();
			});
}

function getClientsAndProject(project, client) {
	window.clientLists = {};
	$.ajax({
		type : "get",
		url : "/user/getProject"
	}).done(
			function(data) {
				var options = $("#" + project);
				var clientOptions = $("#" + client);
				if (data) {
					$.each(data, function() {
						options.append($("<option />").val(this.id).text(
								this.projectName));
					});
				} else {
					alert("Something went wrong");
				}
				$.ajax({
					type : "get",
					url : "/user/getClient"
				}).done(
						function(clientList) {
							if (clientList) {
								window.clientLists = clientList;
								$.each(clientList, function() {
									clientOptions.append($("<option />").val(
											this.id).text(this.clientName));
								});

							} else {
								alert("Something went wrong");
							}

						});
			});
}
function getEventTime() {
	var time = {};
	var sTime = window.calEventDetails.start._i.split('T');
	var eTime;
	window.calEventDetails.end !== null ? eTime = window.calEventDetails.end._i
			.split('T') : eTime;
	sTime !== undefined && sTime.length > 1 ? eventStime = sTime[1].slice(0, 8)
			: eventStime = '';
	(eTime !== undefined && eTime !== null && eTime.length > 1) ? eventEtime = eTime[1]
			.slice(0, 8)
			: eventEtime = '';
	time.eventStime = eventStime;
	time.eventEtime = eventEtime;
	return time;
}
function editEvent() {
	if ($('#updateProjectDropDown').children())
		$('#updateProjectDropDown option[value!="0"]').remove();
	if ($('#updateClientDropDown').children())
		$('#updateClientDropDown option[value!="0"]').remove();
	if ($('#event_attendees').children())
		$('#event_attendees').children().remove();
	
	getClientsAndProject('updateProjectDropDown', 'updateClientDropDown');
	
	window.calEventDetails.timeZone != null ? $("#edit_calEventTimeZone").val(
			window.calEventDetails.timeZone) : $("#edit_calEventTimeZone").val('');
	
	$('.event_label').hide();
	$('.event_input').show();
	$('#edit_eventTitle').focus();
	var startDate = convert(window.calEventDetails.start);
	var endDate = convert(window.calEventDetails.end
			|| window.calEventDetails.start);
	$('#edit_eventTitle').val(window.calEventDetails.title);
	$('#edit_eventStartDate').val(startDate);
	$('#edit_eventEndDate').val(endDate);
	var time = getEventTime();
	$('#edit_eventStartTime').val(time.eventStime);
	$('#edit_eventEndTime').val(time.eventEtime);
	if($('#edit_eventStartTime').val()!='' && $('#edit_eventStartTime').val()!=null)
		$('#edit_calEventTimeZone').show();
	else
		$('#edit_calEventTimeZone').hide();
	if (window.calEventDetails.location != "null")
		$('#edit_location').val(window.calEventDetails.location);
	if (window.calEventDetails.description != 'null')
		$('#edit_description').val(window.calEventDetails.description);
	if (window.calEventDetails && window.calEventDetails.attendeeLists) {
		var i = 0;
		for (i; i < window.calEventDetails.attendeeLists.length; i++) {
			$("#event_attendees")
					.append(
							"<div><label> "
									+ window.calEventDetails.attendeeLists[i].attendeeEmail
									+ "</label><span class='b_close' onclick='removeAttendee(this)'>x</span></div>");

		}
	}

}

function removeAttendee(attendee) {
	var index = window.attendeeList
			.indexOf(attendee.previousElementSibling.innerText);
	if (index >= 0) {
		window.attendeeList.splice(index, 1);
		attendee.parentElement.remove();
	}
}

function saveEvent() {
	if ($('#edit_eventTitle').val() === null
			|| $('#edit_eventTitle').val() === '') {
		showErrMsg("Enter event title", 0, "edit_eventTitle");
	} else if ($('#edit_eventStartDate').val() === ''
			|| $('#edit_eventStartDate').val() === undefined) {
		showErrMsg("Enter event Start date", 0, "edit_eventStartDate");
	} else if ($('#edit_eventEndDate').val() === ''
			|| $('#edit_eventEndDate').val() === undefined) {
		showErrMsg("Enter valid event End date", 0, "edit_eventEndDate");
	} else if ($('#edit_eventStartTime').val() === ''
			&& $('#edit_eventStartTime').val() !== '') {
		showErrMsg("Enter event start time", 0, "edit_eventStartTime");
	} else {
		if ($('#edit_eventStartTime').val() !== ''
				|| $('#edit_eventStartTime').val() !== undefined) {
			if ($('#edit_eventStartTime').val().split(':').length == 2)
				$('#edit_eventStartTime').val(
						$('#edit_eventStartTime').val().concat(':00'));
		}
		if ($('#edit_eventEndTime').val() !== ''
				|| $('#edit_eventEndTime').val() !== undefined) {
			if ($('#edit_eventEndTime').val().split(':').length == 2)
				$('#edit_eventEndTime').val(
						$('#edit_eventEndTime').val().concat(':00'));
		}
		var d1 = Date.parse($('#edit_eventStartDate').val() + ' '
				+ $('#edit_eventStartTime').val());
		var d2 = Date.parse($('#edit_eventEndDate').val() + ' '
				+ $('#edit_eventEndTime').val());
		if (d1 > d2) {
			showErrMsg(
					"Start date and time is greater than end date and time!",
					0, "edit_eventEndDate", "edit_eventEndTime");
			return;
		}
		if ($('#updateProjectDropDown').val() === "0")
			$('#updateProjectDropDown').val("");
		var timeZone;
		if($('#edit_calEventTimeZone').val()==null || $('#edit_calEventTimeZone').val()==''){
			timeZone=new Date().toString().match(/([+/-])\w+/g)[0].substring(0, 3)+":"+new Date().toString().match(/([+/-])\w+/g)[0].substring(3, 5);
		}else
		 timeZone = $("#edit_calEventTimeZone option:selected").data().offset;
		var startDateTime;
		var endDateTime;
		var startDate;
		var endDate;
		$('#edit_eventStartTime').val() !== '' ? startDateTime = $(
				'#edit_eventStartDate').val()
				+ "T" + $('#edit_eventStartTime').val() + timeZone
				: startDate = $('#edit_eventStartDate').val();
		$('#edit_eventEndTime').val() !== '' ? endDateTime = $(
				'#edit_eventEndDate').val()
				+ "T" + $('#edit_eventEndTime').val() + timeZone : endDate = $(
				'#edit_eventEndDate').val();
		
		$('#wrap').show();

		if (window.clientLists)
			for (i = 0; i < window.clientLists.length; i++) {
				if (window.clientLists
						&& window.clientLists[i].id.toString() === $(
								'#updateClientDropDown').val())
					window.attendeeList
							.push(window.clientLists[i].clientEmailId);
			}
		if (i == window.clientLists.length) {
			var json = {
				title : $('#edit_eventTitle').val(),
				startDate : startDate,
				endDate : endDate,
				startDateTime : startDateTime,
				endDateTime : endDateTime,
				description : $('#edit_description').val(),
				location : $('#edit_location').val(),
				projectAttendees : $('#updateProjectDropDown').val(),
				clientAttendees : window.attendeeList,
				calendarId : window.currentCalendar,
				eventId : window.calEventDetails.event,
				timezone : $('#edit_calEventTimeZone option:selected').val()
			}
			$.ajax({
				type : "post",
				url : "/user/updateEvent",
				data : JSON.stringify(json),
				contentType : "application/json",
				dataType : 'json'
			}).done(
					function(data) {
						
						$("#popup_container").hide();
						data.editable = "true";
						data.overlap = "true";
						showErrMsg("Updated successfully", 1);
						$("#calendar").fullCalendar('removeEvents',
								window.calEventDetails.id);
						$('#calendar').fullCalendar('renderEvent', data, true);
						$("#wrap").hide();
					});
		}
	}

}

function showErrMsg(message, success, id1, id2) {

	$("#error_message").removeClass("success-msg error-msg");
	if (success)
		$("#error_message").addClass("success-msg").html(message);
	else {
		$("#error_message").addClass("error-msg").html(message);
		$("#" + id1).css('border', '2px solid rgba(255, 23, 23, 0.37)');
		$("#" + id2).css('border', '2px solid rgba(255, 23, 23, 0.37)');
	}
	$("#error_container").show();
	setTimeout(function() {
		$("#error_container").hide();
	}, 5000);
}

function insertEvent(attendeesList, eventStartDateTime, eventEndDateTime,
		eventStartDate, eventEndDate) {
	var json = {
		title : $('#calEventTitle').val(),
		startDate : eventStartDate,
		endDate : eventEndDate,
		startDateTime : eventStartDateTime,
		endDateTime : eventEndDateTime,
		description : $('#calEventDescription').val(),
		location : $('#calEventlocation').val(),
		projectAttendees : $('#projectDropDown').val(),
		clientAttendees : attendeesList,
		calendarId : window.currentCalendar,
		timezone : $("#calEventTimeZone option:selected").val()
	}
	
	$('#create_event_popup_container').hide();
	$.ajax({
		type : "post",
		url : "/user/insertEvent",
		data : JSON.stringify(json),
		contentType : "application/json",
		dataType : 'json'
	}).done(function(data) {
		data.editable = "true";
		data.overlap = "true";
		$('#calendar').fullCalendar('renderEvent', data, true);
		$('#wrap').hide();

	});
}
// Function to format date yyyy-mm-dd
function formatDate(date) {
	var newDate = new Date(date), month = '' + (newDate.getMonth() + 1), day = ''
			+ newDate.getDate(), year = newDate.getFullYear();

	if (month.length < 2)
		month = '0' + month;
	if (day.length < 2)
		day = '0' + day;

	return [ year, month, day ].join('-')
}

// Function to convert date format
function convert(str) {
	var date = new Date(str), mnth = ("0" + (date.getMonth() + 1)).slice(-2), day = ("0" + date
			.getDate()).slice(-2);
	return [ date.getFullYear(), mnth, day ].join("-");
}
$(document)
		.ready(
				function() {
					var modelAttr = $("#modelAttr").val();
					$("#wrap").show();
					$('#calEventStartTime').timepicker({
						timeFormat : 'H:i',
					});
					$('#calEventEndTime').timepicker({
						timeFormat : 'H:i',
					});
					$('#calEventStartDate').datepicker({
						dateFormat : 'yy-mm-dd'
					});
					$('#calEventEndDate').datepicker({
						dateFormat : 'yy-mm-dd'
					});
					$('#edit_eventStartDate').datepicker({
						dateFormat : 'yy-mm-dd'
					});
					$('#edit_eventEndDate').datepicker({
						dateFormat : 'yy-mm-dd'
					});
					$('#edit_eventEndTime').timepicker({
						timeFormat : 'H:i',
					});
					$('#edit_eventStartTime').timepicker({
						timeFormat : 'H:i',
					});
					$('#calEventStartTime').change(function(){
						$('#calEventTimeZone').show();
					});
					$('#edit_eventStartTime').change(function(){
						$('#edit_calEventTimeZone').show();
					});
					$('#calEventTimeZone').timezones();
					$('#edit_calEventTimeZone').timezones();

					$
							.ajax(
									{
										type : "get",
										url : "/user/getCalendarList?email="
												+ modelAttr,
										contentType : "application/json"
									})
							.done(
									function(list) {
										window.calendarList = [];
										for (var i = 0; i < list.length; i++) {
											list[i].index = i;
											window.calendarList[i] = list[i];
											$('#calendar_list')
													.append(
															"<li><button "
																	+ (modelAttr === list[i].calendarId ? "class='button'"
																			: '')
																	+ " id='b_"
																	+ i
																	+ "' onclick='getEvent(this)'>"
																	+ list[i].calendarName
																	+ "</button></li>");
										}

										if (i === list.length) {

											var current = new Date(new Date()
													.getFullYear(), new Date()
													.getMonth(), 1);
											var currentClone = new Date(
													new Date().getFullYear(),
													new Date().getMonth() + 1,
													0);
											$
													.ajax(
															{
																type : "get",
																url : "/user/getCalendarBetweenDuration?calendar="
																		+ modelAttr
																		+ "&start="
																		+ current
																				.toISOString()
																		+ "&end="
																		+ currentClone
																				.toISOString(),
																contentType : "application/json"
															})
													.done(
															function(eventsList) {
																window.currentCalendar = modelAttr;
																renderEvents(eventsList);
															});
										}

									});
					// Close event pop up on click of close button

					$(".close").click(function() {
						$("#popup_container").hide();
						$('#create_event_popup_container').hide();
						$('#project-popup').hide();
						$('#client-popup').hide();
					});
					$("#addProject").click(function() {
						$('#projectName').val('');
						$('#projectName').css('border', '');
						$('#project-popup').show();
					});
					$("#addClient").click(function() {
						$('#clientName').val('');
						$('#clientEmail').val('');
						$('.client_input').css('border', '');
						$('#client-popup').show();

					});
					$("#submitProject")
							.click(
									function() {
										if ($('#projectName').val() === null
												|| $('#projectName').val() === '') {
											showErrMsg(
													"Enter valid project name",
													0, "projectName");
											return;
										}
										$("#wrap").show();
										$
												.ajax(
														{
															type : "get",
															url : "/user/addProject?pname="
																	+ $(
																			'#projectName')
																			.val(),
														})
												.done(
														function(data) {
															$("#wrap").hide();
															if (data === "success")
																showErrMsg(
																		"Project is added successfully",
																		1);
															else if (data === "exists")
																showErrMsg(
																		"Project already exists",
																		0);
															else
																showErrMsg(
																		"Something went wrong",
																		0);
															$('#project-popup')
																	.hide();
														});
									});
					$("#submitClient")
							.click(
									function() {
										$('.client_input').css('border', '');
										var emailExp = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
										if ($('#clientName').val() === null
												|| $('#clientName').val() === '') {
											showErrMsg(
													"Enter valid client name",
													0, "clientName");
											return;
										} else if (!emailExp.test($(
												'#clientEmail').val())) {
											showErrMsg(
													"Enter valid client email",
													0, "clientEmail");
											return;
										}
										$("#wrap").show();
										$
												.ajax(
														{
															type : "get",
															url : "/user/addClient?clName="
																	+ $(
																			'#clientName')
																			.val()
																	+ "&clEmail="
																	+ $(
																			'#clientEmail')
																			.val(),
														})
												.done(
														function(data) {
															$("#wrap").hide();
															if (data === "success")
																showErrMsg(
																		"Client is added successfully",
																		1);
															else if (data === "exists")
																showErrMsg(
																		"Client already exists",
																		0);
															else
																showErrMsg(
																		"Something went wrong",
																		0);
															$('#client-popup')
																	.hide();
														});
									});

					$(document).keydown(function(e) {
						// ESCAPE key pressed
						if (e.keyCode == 27) {
							$("#popup_container").hide();
							$('#create_event_popup_container').hide();
							$('#project-popup').hide();
							$('#client-popup').hide();
						}
					});
					$('body')
							.on(
									'click',
									'button.fc-prev-button',
									function() {
										$("#wrap").show();
										var current = $('#calendar')
												.fullCalendar('getDate');

										var active = $('#calendar')
												.fullCalendar('getView').intervalUnit;
										var currentClone = new Date(current);

										if (active === "day") {
											currentClone = new Date(current
													.endOf('day'));
											currentClone.setDate(current._d
													.getDate() + 1);
											current = current.startOf('day');
										} else if (active === "week") {
											currentClone = new Date(current
													.endOf('week'));
											current = current.startOf('week');
										} else {
											currentClone.setMonth(current._d
													.getMonth() + 1);
										}
										$
												.ajax(
														{
															type : "get",
															url : "/user/getEventCount?calendar="
																	+ window.currentCalendar
																	+ "&start="
																	+ current._d
																			.toISOString()
																	+ "&end="
																	+ currentClone
																			.toISOString(),
															contentType : "application/json"
														})
												.done(
														function(data) {
															$("#wrapMessage")
																	.html(
																			"Loading "
																					+ data
																					+ " events");
															$
																	.ajax(
																			{
																				type : "get",
																				url : "/user/getCalendarBetweenDuration?calendar="
																						+ window.currentCalendar
																						+ "&start="
																						+ current._d
																								.toISOString()
																						+ "&end="
																						+ new Date(
																								currentClone
																										.setDate(currentClone
																												.getDate() + 1))
																								.toISOString(),
																				contentType : "application/json"
																			})
																	.done(
																			function(
																					eventsList) {
																				renderEvents(eventsList);
																			});
														});
									});

					$('body')
							.on(
									'click',
									'button.fc-next-button',
									function() {
										$("#wrap").show();
										var current = $('#calendar')
												.fullCalendar('getDate');
										var active = $('#calendar')
												.fullCalendar('getView').intervalUnit;
										var currentClone = new Date(current);
										if (active === "day") {
											currentClone = new Date(current
													.endOf('day'));
											currentClone.setDate(current._d
													.getDate() + 1);
											current = current.startOf('day');
										} else if (active === "week") {
											currentClone = new Date(current
													.endOf('week'));
											current = current.startOf('week');
										} else {
											currentClone.setMonth(current._d
													.getMonth() + 1);
										}
										$
												.ajax(
														{
															type : "get",
															url : "/user/getEventCount?calendar="
																	+ window.currentCalendar
																	+ "&start="
																	+ current._d
																			.toISOString()
																	+ "&end="
																	+ currentClone
																			.toISOString(),
															contentType : "application/json"
														})
												.done(
														function(data) {
															$("#wrapMessage")
																	.html(
																			"Loading "
																					+ data
																					+ " events");
															$
																	.ajax(
																			{
																				type : "get",
																				url : "/user/getCalendarBetweenDuration?calendar="
																						+ window.currentCalendar
																						+ "&start="
																						+ current._d
																								.toISOString()
																						+ "&end="
																						+ new Date(
																								currentClone
																										.setDate(currentClone
																												.getDate() + 1))
																								.toISOString(),
																				contentType : "application/json"
																			})
																	.done(
																			function(
																					eventsList) {
																				renderEvents(eventsList);
																			});
														});
									});
					$(document).find('.fc-today-button').addClass('todayBtn');
					$('body')
							.on(
									'click',
									'button.fc-month-button, button.fc-agendaWeek-button, button.fc-agendaDay-button',
									function(event) {
										var start = $('#calendar')
												.fullCalendar('getView').intervalStart._d;
										start = new Date(start.setDate(start
												.getDate() - 1)).toISOString();
										var end = $('#calendar').fullCalendar(
												'getView').intervalEnd._d;
										end = new Date(end.setDate(end
												.getDate() + 1)).toISOString();
										$
												.ajax(
														{
															type : "get",
															url : "/user/getCalendarBetweenDuration?calendar="
																	+ window.currentCalendar
																	+ "&start="
																	+ start
																	+ "&end="
																	+ end,
															contentType : "application/json"
														})
												.done(function(eventsList) {
													renderEvents(eventsList);
												});

									});
				});

function renderEvents(eventsList) {
	if (eventsList && eventsList.length > 0) {
		$.each(eventsList, function(index, value) {
			eventsList[index].editable = "true";
			eventsList[index].overlap = "true";
		});
	}
	$('#calendar').fullCalendar('removeEvents');
	$('#calendar').fullCalendar('addEventSource', eventsList);
	$('#calendar').fullCalendar('rerenderEvents');
	$("#wrap").hide();
	$("#calendar")
			.fullCalendar(
					{
						customButtons : {
							todayCustomButton : {
								text : 'Today',
								click : function() {
									$('#calendar').fullCalendar('today');
									$("#wrap").show();
									var current = new Date(new Date()
											.getFullYear(), new Date()
											.getMonth(), 1);
									var currentClone = new Date(new Date()
											.getFullYear(), new Date()
											.getMonth() + 1, 0);
									$
											.ajax(
													{
														type : "get",
														url : "/user/getEventCount?calendar="
																+ window.currentCalendar
																+ "&start="
																+ current
																		.toISOString()
																+ "&end="
																+ currentClone
																		.toISOString(),
														contentType : "application/json"
													})
											.done(
													function(data) {
														$("#wrapMessage")
																.html(
																		"Loading "
																				+ data
																				+ " events");
														$
																.ajax(
																		{
																			type : "get",
																			url : "/user/getCalendarBetweenDuration?calendar="
																					+ window.currentCalendar
																					+ "&start="
																					+ current
																							.toISOString()
																					+ "&end="
																					+ currentClone
																							.toISOString(),
																			contentType : "application/json"
																		})
																.done(
																		function(
																				eventsList) {
																			renderEvents(eventsList);
																			$(
																					'#calendar')
																					.fullCalendar(
																							'gotoDate',
																							new Date());
																		});
													});

								}

							}

						},

						// header with custom buttons.
						header : {
							left : 'prev,next todayCustomButton',
							center : 'title',
							right : 'month,agendaWeek,agendaDay'
						},
						timezone : 'local',
						eventRender : function(event, element, view) {
							$
									.each(
											window.calendarList,
											function(index, value) {
												if (window.calendarList[index].calendarId === window.currentCalendar)
													$(element)
															.css(
																	"backgroundColor",
																	window.calendarList[index].calendarColor);
											})
						},

						dayClick : function(date, jsEvent, view) {
							var attendeesList = [];
							$('#calEventTitle').val('');
							$('#calEventTimeZone').val('');
							$('#calEventTimeZone').hide();
							$('#calEventStartDate').val(
									formatDate(date._d.toLocaleDateString()));
							$('#calEventEndDate').val(
									formatDate(date._d.toLocaleDateString()));
							$('#calEventDescription').val('');
							$('#calEventlocation').val('');
							if (view.name == 'agendaDay') {
								var newDate = JSON.parse(JSON.stringify(date._d
										.toTimeString()));
								date._d.setHours(date._d.getHours() + 1);
								var startTime = newDate.split(' ')[0]
										.substring(0, 5);
								var endTime = date._d.toTimeString().split(' ')[0]
										.substring(0, 5);
								$('#calEventStartTime').val(startTime);
								$('#calEventEndTime').val(endTime);
							} else {
								$('#calEventStartTime').val('');
								$('#calEventEndTime').val('');
							}

							if ($('#projectDropDown').children())
								$('#projectDropDown option[value!="0"]')
										.remove();
							if ($('#clientDropDown').children())
								$('#clientDropDown option[value!="0"]')
										.remove();
							getClientsAndProject('projectDropDown',
									'clientDropDown');
							$('#create_event_popup_container').show();
							$('#calEventTitle').focus();
							$(".input").css('border', '');
							$('#createEvent')
									.click(
											function(event) {
												event
														.stopImmediatePropagation();
												$(".input").css('border', '');
												if ($('#calEventTitle').val() === null
														|| $('#calEventTitle')
																.val() === '') {
													showErrMsg(
															"Enter event title",
															0, "calEventTitle");
												} else if ($(
														'#calEventStartDate')
														.val() === ''
														|| $(
																'#calEventStartDate')
																.val() === undefined) {
													showErrMsg(
															"Enter event Start date",
															0,
															"calEventStartDate");
												} else if ($('#calEventEndDate')
														.val() === ''
														|| $('#calEventEndDate')
																.val() === undefined) {
													showErrMsg(
															"Enter valid event End date",
															0,
															"calEventEndDate");
												} else if ($(
														'#calEventStartTime')
														.val() === ''
														&& $('#calEventEndTime')
																.val() !== '') {
													showErrMsg(
															"Enter event start time",
															0,
															"calEventStartTime");
												} else {
													if ($('#calEventStartTime')
															.val() !== ''
															|| $(
																	'#calEventStartTime')
																	.val() !== undefined) {
														if ($(
																'#calEventStartTime')
																.val().split(
																		':').length == 2)
															$(
																	'#calEventStartTime')
																	.val(
																			$(
																					'#calEventStartTime')
																					.val()
																					.concat(
																							':00'));
													}
													if ($('#calEventEndTime')
															.val() !== ''
															|| $(
																	'#calEventEndTime')
																	.val() !== undefined) {
														if ($(
																'#calEventEndTime')
																.val().split(
																		':').length == 2)
															$(
																	'#calEventEndTime')
																	.val(
																			$(
																					'#calEventEndTime')
																					.val()
																					.concat(
																							':00'));
													}
													var d1 = Date
															.parse($(
																	'#calEventStartDate')
																	.val()
																	+ ' '
																	+ $(
																			'#calEventStartTime')
																			.val());
													var d2 = Date
															.parse($(
																	'#calEventEndDate')
																	.val()
																	+ ' '
																	+ $(
																			'#calEventEndTime')
																			.val());
													if (d1 > d2) {
														showErrMsg(
																"Start date and time is greater than end date and time!",
																0,
																"calEventEndDate",
																"calEventEndTime");
														return;
													}
													if ($('#projectDropDown')
															.val() === "0")
														$('#projectDropDown')
																.val("");
													var timeZone;
													if($('#calEventTimeZone').val()==null || $('#calEventTimeZone').val()==''){
														timeZone=new Date().toString().match(/([+/-])\w+/g)[0].substring(0, 3)+":"+new Date().toString().match(/([+/-])\w+/g)[0].substring(3, 5);
													}else
													 timeZone = $(
															"#calEventTimeZone option:selected")
															.data().offset;
													var startDateTime;
													var endDateTime;
													var startDate;
													var endDate;
													$('#calEventStartTime')
															.val() !== '' ? startDateTime = $(
															'#calEventStartDate')
															.val()
															+ "T"
															+ $(
																	'#calEventStartTime')
																	.val()
															+ timeZone
															: startDate = $(
																	'#calEventStartDate')
																	.val();
													$('#calEventEndTime').val() !== '' ? endDateTime = $(
															'#calEventEndDate')
															.val()
															+ "T"
															+ $(
																	'#calEventEndTime')
																	.val()
															+ timeZone
															: endDate = $(
																	'#calEventEndDate')
																	.val();
													
													$('#wrap').show();
													if (window.clientLists)
														for (i = 0; i < window.clientLists.length; i++) {
															if (window.clientLists
																	&& window.clientLists[i].id
																			.toString() === $(
																			'#clientDropDown')
																			.val())
																attendeesList
																		.push(window.clientLists[i].clientEmailId);
														}
													if (i == window.clientLists.length)
														insertEvent(
																attendeesList,
																startDateTime,
																endDateTime,
																startDate,
																endDate);
												}

											});

						},
						events : eventsList,
						eventClick : function(calEvent, jsEvent, view) {
							Clock.stop();
							window.calEventDetails = {};
							window.attendeeList = [];
							$
									.ajax(
											{
												url : "/user/getAttendeeDetails?event="
														+ calEvent.event,
												contentType : "application/json"
											})
									.done(
											function(attendeeLists) {
												calEvent.attendeeLists = attendeeLists;
												window.calEventDetails = calEvent;
												var attendees = "";
												var i = 0;
												for (i; i < Object
														.keys(attendeeLists).length; i++) {
													window.attendeeList
															.push(attendeeLists[i].attendeeEmail);
													attendees += attendeeLists[i].attendeeEmail
															+ " ";
												}
												if (i === Object
														.keys(attendeeLists).length) {
													resumeTimer("start");
													if(calEvent.start._d.toDateString() >= new Date().toDateString()  && calEvent.end && calEvent.end._d.toDateString() <= new Date().toDateString())
														$('#startButton').prop('disabled', false);
													else
														$('#startButton').prop('disabled', true);
													$("#popup_container")
															.show();
													$('.event_input').val('');
													$('.event_input').hide();
													$('.event_label').val('');
													$('.event_input').css('border', '');
													$('.event_label').show();
													
													$('#edit_calEventTimeZone').val('');
													var startDate = convert(calEvent.start);
													var endDate = convert(calEvent.end
															|| calEvent.start);
													var time = getEventTime();
													$('#eventTitle').html(
															calEvent.title);
													$('#eventStartDate').html(
															startDate);
													$('#eventEndDate').html(
															endDate);
													if (calEvent.location !== "null")
														$('#location')
																.html(
																		calEvent.location);
													$('#eventEndTime').html(
															time.eventEtime);
													$('#eventStartTime').html(
															time.eventStime);
													if (calEvent.description != 'null')
														$('#description')
																.html(
																		calEvent.description);
													$('#attendees').html(
															attendees);
												}
											});

						},

						eventDrop : function(event, delta, revertFunc) {
							alert(event.title + " was dropped on "
									+ event.start.format());
							if (!confirm("Are you sure about this change?")) {
								revertFunc();
							}
						},
						eventAfterAllRender : function(view) {
							if ($('.fc-view-container').find('.fc-today').length > 0
									&& view.intervalEnd
									&& view.intervalStart
									&& view.intervalEnd.isAfter(new Date())
									&& view.intervalStart.isBefore(new Date())) {
								$('button.fc-todayCustomButton-button')
										.addClass('fc-state-disabled');
								$('button.fc-todayCustomButton-button').attr(
										'disabled', true);
							} else {
								$('button.fc-todayCustomButton-button')
										.removeClass('fc-state-disabled');
								$('button.fc-todayCustomButton-button').attr(
										'disabled', false);
							}
						}
					});
	$("#wrap").hide();
}