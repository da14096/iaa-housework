'use strict';

application.controller('eventFormController', [
  '$scope',
  'lecturerService',
  'eventService',
  'studentsClassService',
  'eventBus',
  ($scope, lecturerService, eventService, studentsClassService, eventBus) => {
    
	$scope.today = new Date();
    $scope.lecturerListAddable = false;
    $scope.lecturerListSelectable = true;
    
    $scope.roomListAddable = false;
    $scope.roomListSelectable = true;
    $scope.roomListCaption = "Verfügbare Räume";
    $scope.roomSelectionOpened = false;
    
    lecturerService.findAll().then(response => $scope.lecturers = response.data);
    
    eventBus.onEditEvent(function (eventToEdit) {
//    	reset to initial state
    	$scope.eventToEdit = JSON.parse(JSON.stringify(eventToEdit));
    	$scope.roomSelectionOpened = false;
    	
    	if (eventToEdit.lecturer !== undefined) {
    		var lecturerFilter = function (lecturer) {
    			return lecturer.personnelNumber == eventToEdit.lecturer.personnelNumber
    		};
    		$scope.selectedLecturer = $scope.lecturers.filter(lecturerFilter)[0];
    	} else {
    		$scope.selectedLecturer = {};
    	}
    	
    });
    
    $scope.saveEvent = (eventToSave) => {
    	var callback = (response, flagCreated) => {
    		if (response.status === 200) {
    			eventBus.publishUpdateEvent(response, flagCreated);
    			eventBus.publishEndEventEdit();
			}
    	};
    	if (eventToSave.id) {
    		eventService.update(eventToSave).then(response => callback(response, false));
    	} else {
    		eventService.create(eventToSave).then(response => callback(response, true));
    	}
    }
    
    $scope.deleteEvent = (eventToDelete) => {
    	eventService.deleteEvent(eventToDelete).then(response => {
    		if (response.status === 200) {
    			eventBus.publishDeleteEvent(response.data);
    			eventBus.publishEndEventEdit();
    		}
    	});
    }
    
    $scope.cancel = () => {
    	eventBus.publishEndEventEdit();
    }
    
    $scope.autofill = () => {
    	var startDate = $scope.eventToEdit.start;
    	var endDate = new Date(startDate);
    	endDate.setHours(startDate.getHours() + 1);
    	endDate.setMinutes(startDate.getMinutes() + 30);
    	$scope.eventToEdit.end = endDate;
    }
    
    $scope.selectLecturer = (lecturer) => {
    	if ($scope.selectedLecturer === lecturer) {
    		$scope.selectedLecturer = null;
    	} else {
    		$scope.selectedLecturer = lecturer;
    	}
    	$scope.eventToEdit.lecturer = $scope.selectedLecturer;
    }
    $scope.selectRoom = (room) => {
    	if ($scope.selectedRoom === room) {
    		$scope.selectedRoom = null;
    	} else {
    		$scope.selectedRoom = room;
    	}
    	$scope.eventToEdit.room = $scope.selectedRoom;
    }
    
    $scope.toggleRoomSelection = () => {
    	$scope.roomSelectionOpened = !$scope.roomSelectionOpened;
    	if ($scope.roomSelectionOpened) {
    		eventService.getAvailableRooms($scope.eventToEdit)
    			.then(response => {
    				$scope.rooms = response.data;
    				var roomOfEvent = $scope.eventToEdit.room;
    				if (roomOfEvent !== undefined) {
    					$scope.rooms.push(roomOfEvent);
    					$scope.selectedRoom = roomOfEvent;
    				}
    			});
    	}
    }
        
    $scope.updateSelectableValuesForEventType = () => {
    	var chosenEventType = $scope.eventToEdit.type;
    	switch (chosenEventType) {
    	case "Vorlesung":
    		$scope.selectableFieldsOfStudy = _getUniqueFieldsOfStudy();
    		break;
    	case "Klausur":
    		$scope.selectableFieldsOfStudy = _getUniqueFieldsOfStudy();
    		break;
    	case "Wahlpflichtkurs":
    		$scope.selectableYears = _getUniqueYears();
    		break;
    	default:
    		console.log("No handling defined for eventType [" + chosenEventType + "]")
    	}
    }
    
    $scope.updateYearsForFieldOfStudy = () => {
    	var selectedFieldOfStudy = $scope.fieldOfStudyToAssignEventTo;
    	$scope.selectableYears = _getUniqueYears (selectedFieldOfStudy);
    }
    
    $scope.updateFormsForYear = () => {
    	$scope.selectableForms = _getForms($scope.fieldOfStudyToAssignEventTo , $scope.yearToAssignEventTo);
    }
    
    function _getUniqueFieldsOfStudy () {
    	var uniqueFieldsOfStudy = new Set();
		for (var studentsClass of $scope.studentsClasses) {
			uniqueFieldsOfStudy.add(studentsClass.fieldOfStudy);
		}
		return Array.from(uniqueFieldsOfStudy);
    }
    
    function _getUniqueYears (fieldOfStudy) {
    	var uniqueYears = new Set();
		for (var studentsClass of $scope.studentsClasses) {
			if (fieldOfStudy && fieldOfStudy !== studentsClass.fieldOfStudy) {
				continue;
			}
			uniqueYears.add(studentsClass.year);
		}
		return Array.from(uniqueYears);
    }
    
    function _getForms (fieldOfStudy, year) {
    	var forms = [];
    	for (var studentsClass of $scope.studentsClasses) {
			if (fieldOfStudy === studentsClass.fieldOfStudy && year === studentsClass.year) {
				forms.push(studentsClass.form);
			}
		}
    	return forms;
    }
  }
]);