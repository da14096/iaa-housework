// Tim Lindemann 6436 
'use strict';

application.controller('dashboardController', [
  '$scope',
  'modelService',
  'roomService',
  'lecturerService',
  'studentsClassService',
  'eventService',
  'eventBus',
  ($scope, modelService, roomService, lecturerService, studentsClassService, eventService, eventBus) => {
//    configuration-variables
	$scope.roomListAddable = true;
    $scope.lecturerListAddable = true;
    $scope.studentsClassListAddable = true;
    
    $scope.lecturerListSelectable = true;
    $scope.createWeekViewForLecturer = true;
    
    $scope.roomListSelectable = true;
    $scope.createWeekViewForRoom = true;
    
    $scope.studentsClassListSelectable = true;
    $scope.createWeekViewForStudentsClass = true;
    
    $scope.roomListCaption = 'Räume';
    $scope.dateTimeFormat = {year:"2-digit", month:"2-digit", day:"2-digit", hour: "2-digit", minute: "2-digit"};
    $scope.dateFormat = {year:"2-digit", month:"2-digit", day:"2-digit"};
	$scope.timeFormat = {hour: "2-digit", minute: "2-digit"};
	  
//    listen on eventBus
    eventBus.onDeleteEvent(function (deletedEvent) {
    	$scope.events = $scope.events.filter(function (event) {return event.id !== deletedEvent.id});
    });
//    listen on eventBus
    eventBus.onUpdateEvent(function (updatedEvent, flagCreated) {
    	_replaceEventStartAndEndWithDates(updatedEvent);
    	if (flagCreated) {
    		$scope.events.push(updatedEvent);
    	}
    });
//    listen on eventBus
    eventBus.onEndEventEdit (function () {
    	$scope.eventViewVisible = false;
    });
    
//  Initialize Enums 
    modelService.buildings().then(response => {$scope.buildings = response.data});
    modelService.fieldsOfStudy().then(response => {$scope.fieldsOfStudy = response.data})
    modelService.eventTypes().then(response => {$scope.eventTypes = response.data})
    modelService.roomTypes().then(response => {$scope.roomTypes = response.data});
    
//  room-operations  
    roomService.findAll().then(response => {$scope.rooms = response.data});
    $scope.createRoom = (roomToCreate) => {
        roomService.createRoom(roomToCreate)
          .then(response => {
            if (response.status === 200) {
              $scope.rooms.push(response.data);
              $scope.roomToCreate = {};
              $scope.newRoomFormVisible = false;
            } 
        });
    }
    
//  lecturer-operations  
    $scope.lecturerToCreate = {minimalBreakTime: 15};
    lecturerService.findAll().then(response => {$scope.lecturers = response.data});
    $scope.createLecturer = (lecturerToCreate) => {
        lecturerService.createLecturer(lecturerToCreate)
          .then(response => {
            if (response.status === 200) {
              $scope.lecturers.push(response.data);
              $scope.lecturerToCreate = {minimalBreakTime: 15};
              $scope.newLecturerFormVisible = false;
            } 
        });
    }

//  studentsClass-operations  
    $scope.studentsClassToCreate = {minimalBreakTime: 15};
    studentsClassService.findAll().then(response => {$scope.studentsClasses = response.data});
    $scope.createStudentsClass = (studentsClassToCreate) => {
    	studentsClassService.createStudentsClass(studentsClassToCreate)
          .then(response => {
            if (response.status === 200) {
              $scope.studentsClasses.push(response.data);
              $scope.studentsClassToCreate = {minimalBreakTime: 15};
              $scope.newStudentsClassFormVisible = false;
            } 
        });
    }

//  event-operations
    eventService.findAll().then(response => {
    	$scope.events = response.data;
    	for (var event of $scope.events) {
    		_replaceEventStartAndEndWithDates(event);
    	}
    });

//	the events start and end are offered as an array of ints from the server. Replace these Arrays with date Objects
    function _replaceEventStartAndEndWithDates (event) {
    	var start = event.start;
		var end = event.end;
//		months start by 0 so substract 1
		if (Array.isArray(start)) {
			event.start = new Date(Date.UTC(start[0], start[1] - 1, start[2], start[3], start[4]));
		}
		if (Array.isArray(end)) {
			event.end = new Date(Date.UTC(end[0], end[1] - 1, end[2], end[3], end[4]));
		}
    }
//    start the planning of a new event
    $scope.planEvent = () => {
    	$scope.eventViewVisible = true;
    	eventBus.publishEditEvent({});
    }
//    edit an existing event
    $scope.editEvent = (event) => {
    	$scope.eventViewVisible = true;
    	eventBus.publishEditEvent(event);
    }
//    select - routine for a lecturer
    $scope.selectLecturer = (lecturer) => {
    	if ($scope.selectedLecturer === lecturer) {
    		$scope.selectedLecturer = null;
    	} else {
    		$scope.selectedLecturer = lecturer;
    	}
    }
//    select -routine for a room
    $scope.selectRoom = (room) => {
    	if ($scope.selectedRoom === room) {
    		$scope.selectedRoom = null;
    	} else {
    		$scope.selectedRoom = room;
    	}
    }
//    indicator that can be used to find out whether a room is selected
    $scope.isRoomSelected = (room) => {
    	return $scope.selectedRoom == room;
    }
//    select-routine for a studentsClass
    $scope.selectStudentsClass = (studentsClass) => {
    	if ($scope.selectedStudentsClass === studentsClass) {
    		$scope.selectedStudentsClass = null;
    	} else {
    		$scope.selectedStudentsClass = studentsClass;
    	}
    }
    
//    weekView - calls
    $scope.createWeekViewForLecturer = (lecturer) => {
    	eventBus.publishFillWeekView(lecturerService.getEventsForWeek, lecturer, "Dozent " + lecturer.surname);
    }
    $scope.createWeekViewForRoom = (room) => {
    	eventBus.publishFillWeekView(roomService.getEventsForWeek, room, "Raum " + room.roomName);
    }
    $scope.createWeekViewForStudentsClass = (studentsClass) => {
    	eventBus.publishFillWeekView(studentsClassService.getEventsForWeek, studentsClass, "Zenturie " + studentsClass.name);
    }
  } 
]);