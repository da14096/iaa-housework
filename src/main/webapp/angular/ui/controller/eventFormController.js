'use strict';

application.controller('eventFormController', [
  '$scope',
  'modelService',
  'lecturerService',
  'eventService',
  'studentsClassService',
  'eventBus',
  'errorHandler',
  ($scope, modelService, lecturerService, eventService, studentsClassService, eventBus, errorHandler) => {
    
	  var _orgEvent;
	  
	$scope.today = new Date();
    $scope.lecturerListAddable = false;
    $scope.lecturerListSelectable = true;
    $scope.createWeekViewForLecturer = false;
    
    $scope.roomListAddable = false;
    $scope.roomListSelectable = true;
    $scope.roomListCaption = "Verfügbare Räume";
    $scope.roomSelectionOpened = false;
    
    modelService.fieldsOfStudy().then(response => $scope.allFieldsOfStudy = response.data);
    studentsClassService.findAll().then(response => $scope.studentsClasses = response.data);   
    
    eventBus.onEditEvent(function (eventToEdit) {
    	lecturerService.findAll().then(response => {
    		$scope.lecturers = response.data;
    		if ($scope.eventToEdit.lecturer !== undefined) {
        		var lecturerFilter = function (lecturer) {
        			return lecturer.personnelNumber == eventToEdit.lecturer.personnelNumber
        		};
        		$scope.selectedLecturer = $scope.lecturers.filter(lecturerFilter)[0];
        	} else {
        		$scope.selectedLecturer = {};
        	}
    	});
// reset to initial state
    	$scope.roomSelectionOpened = false;
    	$scope.repetitions = undefined;
    	
    	_orgEvent = eventToEdit;
    	$scope.eventToEdit = eventToEdit;
    	if (eventToEdit.type) {
    		$scope.updateSelectableValuesForEventType();
    	}
    });
    
    $scope.saveEvent = (eventToSave) => {
    	if (eventToSave.id) {
    		_updateEvent(eventToSave);
    	} else if ($scope.repetition > 0){
    		_createRepeated(eventToSave);
    	} else {
    		_createEvent(eventToSave);
    	}
    }
    
    function _updateEvent (eventToUpdate) {
    	var callback = (response) => {
    		var updatedEvent = response.data;
    		eventBus.publishUpdateEvent(updatedEvent, false);
    		_assignEventToSelectedStudentsClasses(updatedEvent);
			eventBus.publishEndEventEdit();
    	};
    	eventService.update(eventToUpdate).then(
    			callback, 
    			response => _requestForce(eventService.forceUpdate, eventToUpdate, callback));
    }
    function _createRepeated (eventToCreate) {
    	var callback = (response) => {
			for (var createdEvent of response.data) {
				eventBus.publishUpdateEvent(createdEvent, true);
				_assignEventToSelectedStudentsClasses(createdEvent);
    		}
			eventBus.publishEndEventEdit();
		}
    	eventService.createRepeated(eventToCreate, $scope.repetition).then(
    		callback,
			response => _requestForce(eventService.forceRepeatedCreate, eventToCreate, callback));
    }
    function _createEvent (eventToCreate) {
    	var callback = (response) => {
    		var createdEvent = response.data;
    		eventBus.publishUpdateEvent(createdEvent, true);
    		_assignEventToSelectedStudentsClasses(createdEvent);
			eventBus.publishEndEventEdit();	
    	};
    	eventService.create(eventToCreate).then(
    		callback,
    		response => _requestForce(eventService.forceCreate, eventToCreate, callback));
    }
    
    function _requestForce (method, param, callback) {
    	setTimeout(function () {
	    	if (confirm("Bitte beachten Sie die Fehlermeldungen, welche beim Speichern der Veranstaltung aufgetreten sind. " +
	    			"Möchten Sie die Veranstaltung dennoch speichern?")) {
	    		method(param).then(callback);
	    		errorHandler.removeMessageWindow();
	    	}
    	}, 200);
    }
    
    function _assignEventToSelectedStudentsClasses (eventToAssign) {
    	if ($scope.yearToAssignEventTo !== undefined) {
	    	var studentsClassesToAssignEventTo = $scope.studentsClasses.filter(studentsClass => {
	    		var studentsClassId = studentsClass.id;
	    		
	    		var yearMatches = $scope.yearToAssignEventTo == studentsClassId.year;
	    		var fieldOfStudyMatches = $scope.fieldOfStudyToAssignEventTo == undefined || 
	    					$scope.fieldOfStudyToAssignEventTo.abreviation == studentsClassId.fieldOfStudy.abreviation;
	    		var formMatches = $scope.formToAssignEventTo == undefined || 
	    					$scope.formToAssignEventTo == studentsClassId.form;
	    		return fieldOfStudyMatches && yearMatches && formMatches;
	    	});
	    	
	    	
	    	for (var studentsClass of studentsClassesToAssignEventTo) {
	    		studentsClassService.addEvent(studentsClass, eventToAssign);
	    	}
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
    	if (startDate !== undefined) {
	    	endDate.setHours(startDate.getHours() + 1);
	    	endDate.setMinutes(startDate.getMinutes() + 30);
	    	$scope.eventToEdit.end = endDate;
    	}
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
    				if ($scope.eventToEdit.id !== undefined && roomOfEvent !== undefined) {
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
    	return $scope.allFieldsOfStudy.filter(fieldOfStudy => {
    		for (var studentsClass of $scope.studentsClasses) {
    			if (studentsClass.id.fieldOfStudy.abreviation == fieldOfStudy.abreviation) {
    				return true;
    			}
    		}
    		return false;
    	});
    }
    
    function _getUniqueYears (fieldOfStudy) {
    	var uniqueYears = new Set();
		for (var studentsClass of $scope.studentsClasses) {
			if (fieldOfStudy && fieldOfStudy.abreviation !== studentsClass.fieldOfStudy.abreviation) {
				continue;
			}
			uniqueYears.add(studentsClass.year);
		}
		return Array.from(uniqueYears);
    }
    
    function _getForms (fieldOfStudy, year) {
    	var forms = [];
    	for (var studentsClass of $scope.studentsClasses) {
			if (fieldOfStudy.abreviation === studentsClass.fieldOfStudy.abreviation && year === studentsClass.year) {
				forms.push(studentsClass.form);
			}
		}
    	return forms;
    }
  }
]);