'use strict';
// Tim Lindemann 6436 
application.controller('eventFormController', [
  '$scope',
  'modelService',
  'lecturerService',
  'eventService',
  'studentsClassService',
  'eventBus',
  'errorHandler',
  ($scope, modelService, lecturerService, eventService, studentsClassService, eventBus, errorHandler) => {
    
	  var _currentlyAssignedStudentsClasses = [];
	  var _initialEventState;
	  
//	  configure context
	$scope.today = new Date();
    $scope.lecturerListAddable = false;
    $scope.lecturerListSelectable = true;
    $scope.createWeekViewForLecturer = false;
    $scope.createWeekViewForRoom = false;
    
    $scope.roomListAddable = false;
    $scope.roomListSelectable = true;
    $scope.roomListCaption = "Verfügbare Räume";
    $scope.roomSelectionOpened = false;
    
//    init context
    modelService.fieldsOfStudy().then(response => $scope.allFieldsOfStudy = response.data);
    studentsClassService.findAll().then(response => $scope.studentsClasses = response.data);   
    
//    listen on eventBus
    eventBus.onEditEvent(function (eventToEdit) {
    	lecturerService.findAll().then(response => {
//    		restore lecturer selection
    		$scope.lecturers = response.data;
    		if ($scope.eventToEdit.lecturer !== undefined) {
        		var lecturerFilter = function (lecturer) {
        			return lecturer.personnelNumber == $scope.eventToEdit.lecturer.personnelNumber
        		};
        		$scope.selectedLecturer = $scope.lecturers.filter(lecturerFilter)[0];
        	} else {
        		$scope.selectedLecturer = {};
        	}
    	});
//    	resolve the assigned studentsclasses
    	if (eventToEdit.id) {
	    	eventService.getAssignedStudentsClasses(eventToEdit).then(response => {
	    		_currentlyAssignedStudentsClasses = response.data;
	    		if (_currentlyAssignedStudentsClasses.length > 0) {
	    			var example = _currentlyAssignedStudentsClasses[0];
	    			$scope.updateSelectableValuesForEventType();
	    			$scope.fieldOfStudyToAssignEventTo = 
	    				$scope.allFieldsOfStudy.filter(function(fos){
	    					return fos.abreviation == example.id.fieldOfStudy.abreviation
	    				})[0];
	    			$scope.updateYearsForFieldOfStudy();
	    			$scope.yearToAssignEventTo = example.id.year;
	    			$scope.updateFormsForYear();
	    			$scope.formToAssignEventTo = example.id.form;
	    		}
	    	});
    	}
// reset to initial state
    	$scope.roomSelectionOpened = false;
    	$scope.repetitions = undefined;
    	$scope.fieldOfStudyToAssignEventTo = undefined;
		$scope.yearToAssignEventTo = undefined;
		$scope.formToAssignEventTo = undefined;
    	
//		keep in memory the inital state of the event for resetting it in case of abortion
    	$scope.eventToEdit = eventToEdit;
    	_initialEventState =  {
    		id: eventToEdit.id,
    		type: eventToEdit.type,
    		title: eventToEdit.title,
    		start: eventToEdit.start,
    		end: eventToEdit.end,
    		changeDuration: eventToEdit.changeDuration,
    		rooms: eventToEdit.rooms,
    		lecturer: eventToEdit.lecturer
        };
    	if (eventToEdit.type) {
    		$scope.updateSelectableValuesForEventType();
    	}
    });
//    save routine
    $scope.saveEvent = (eventToSave) => {
//    	determine whether the event was assigned to studentsClasses
		var studentsClassesToAssignEventTo = $scope.studentsClasses.filter(studentsClass => {
    		var studentsClassId = studentsClass.id;
    		
    		var yearMatches = $scope.yearToAssignEventTo == studentsClassId.year;
    		var fieldOfStudyMatches = $scope.fieldOfStudyToAssignEventTo == undefined || 
    					$scope.fieldOfStudyToAssignEventTo.abreviation == studentsClassId.fieldOfStudy.abreviation;
    		var formMatches = $scope.formToAssignEventTo == undefined || 
    					$scope.formToAssignEventTo == studentsClassId.form;
    		return fieldOfStudyMatches && yearMatches && formMatches;
    	});
//		assign it
    	if (studentsClassesToAssignEventTo.length > 0) {
    		_assignToStudentsClasses(eventToSave, studentsClassesToAssignEventTo);
    	} else {
    		var success = (response) => {
        		for (var event of response.data) {
    				eventBus.publishUpdateEvent(event, eventToSave.id == undefined);
        		}
    			eventBus.publishEndEventEdit();
        	}
    		eventService.saveEvent(eventToSave, $scope.repetitions)
    		.then(success, response => _requestForceForSave(eventToSave, $scope.repetitions, success));
    	}
    }
//    perform assignment
    function _assignToStudentsClasses (eventToSave, studentsClassesToAssignEventTo) {
    	var success = (response) => {
    		for (var event of response.data) {
				eventBus.publishUpdateEvent(event, eventToSave.id == undefined);
				_cancelEventForPreviouslyAssignedStudentsClasses(studentsClassesToAssignEventTo, event);
    		}
    		eventBus.publishEndEventEdit();
    	}
    	var repetitions = $scope.repetitions;
		for (var studentsClass of studentsClassesToAssignEventTo) {
    		if (repetitions > 0) {
    			studentsClassService.addEvent(studentsClass, eventToSave, repetitions)
    				.then(success, response => _requestForce(studentsClass, eventToSave, repetitions, success));
    		} else {
    			studentsClassService.addEvent(studentsClass, eventToSave)
    				.then(success, response => _requestForce(studentsClass, eventToSave, 0, success))
    		}
    	}
    }
//    in case of a validationException ask the user whether he wants to perform the request anyways
    function _requestForce (studentsClass, eventToAssign, weeks, callback) {
    	setTimeout(function () {
	    	if (confirm("Bitte beachten Sie die Fehlermeldungen, welche beim Speichern der Veranstaltung aufgetreten sind. " +
	    			"Möchten Sie die Veranstaltung dennoch speichern?")) {
	    		studentsClassService.forceAdd(studentsClass, eventToAssign, false, weeks).then(callback);
	    		errorHandler.removeMessageWindow();
	    	}
    	}, 200);
    }
//    cancel the previously assigned studentsClasses
    function _cancelEventForPreviouslyAssignedStudentsClasses(newAssignedStudentsClasses, eventToCancel) {
    	var effectiveStudentsClasesToCancel = _currentlyAssignedStudentsClasses.filter(function (clazz) {
    		for (var sc of newAssignedStudentsClasses) {
    			if (sc.name == clazz.name) {
    				return false;
    			}
    		}
    		return true;
    	});
		for (var scToCancel of effectiveStudentsClasesToCancel) {
			studentsClassService.removeEvent(scToCancel, eventToCancel);
		}
    }
//  in case of a validationException ask the user whether he wants to perform the request anyways
    function _requestForceForSave (eventToSave, weeks, callback) {
    	setTimeout(function () {
	    	if (confirm("Bitte beachten Sie die Fehlermeldungen, welche beim Speichern der Veranstaltung aufgetreten sind. " +
	    			"Möchten Sie die Veranstaltung dennoch speichern?")) {
	    		eventService.forceSaveEvent(eventToAssign, weeks).then(callback);
	    		errorHandler.removeMessageWindow();
	    	}
    	}, 200);
    }
       
//    delete an event
    $scope.deleteEvent = (eventToDelete) => {
    	eventService.deleteEvent(eventToDelete).then(response => {
    		if (response.status === 200) {
    			eventBus.publishDeleteEvent(eventToDelete);
    			eventBus.publishEndEventEdit();
    		}
    	});
    }
    
    $scope.cancel = () => {
//    	restore the initial values on abort
    	if ($scope.eventToEdit.id) {
    		$scope.eventToEdit.type = _initialEventState.type;
    		$scope.eventToEdit.title = _initialEventState.title;
    		$scope.eventToEdit.start = _initialEventState.start;
    		$scope.eventToEdit.end = _initialEventState.end;
    		$scope.eventToEdit.rooms = _initialEventState.rooms;
    		$scope.eventToEdit.lecturer = _initialEventState.lecturer;	
    	}
    	eventBus.publishEndEventEdit();
    }
//    autofill the end field -> standard event length = 90 min
    $scope.autofill = () => {
    	var startDate = $scope.eventToEdit.start;
    	var endDate = new Date(startDate);
    	if (startDate !== undefined) {
	    	endDate.setHours(startDate.getHours() + 1);
	    	endDate.setMinutes(startDate.getMinutes() + 30);
	    	$scope.eventToEdit.end = endDate;
    	}
    }
//    select-routine for lecturer
    $scope.selectLecturer = (lecturer) => {
    	if ($scope.selectedLecturer === lecturer) {
    		$scope.selectedLecturer = null;
    	} else {
    		$scope.selectedLecturer = lecturer;
    	}
    	$scope.eventToEdit.lecturer = $scope.selectedLecturer;
    }
//    select-routine for room
    $scope.selectRoom = (room) => {
    	var contained = false;
    	if (!$scope.eventToEdit.rooms) {
    		$scope.eventToEdit.rooms = [];
    	}
    	for (var i = 0; i < $scope.eventToEdit.rooms.length; i++) {
    		if ($scope.eventToEdit.rooms[i].roomName == room.roomName) {
    			$scope.eventToEdit.rooms.splice(i, 1);
    			contained = true;
    			break;
    		}
    	}
    	if (!contained) {
    		$scope.eventToEdit.rooms.push(room);
    	}
    }
//    can be used by view to determine whether a room is selected or not
    $scope.isRoomSelected = (room) => {
    	return $scope.eventToEdit && $scope.eventToEdit.rooms &&
    			$scope.eventToEdit.rooms.filter(function (r) {return room.roomName == r.roomName}).length > 0;
    }
//    open the roomSelection dialogue
    $scope.toggleRoomSelection = () => {
    	$scope.roomSelectionOpened = !$scope.roomSelectionOpened;
    	if ($scope.roomSelectionOpened) {
    		eventService.getAvailableRooms($scope.eventToEdit)
//    		determine the available rooms for the selected date
    			.then(response => {
    				$scope.rooms = response.data;
    				var selectedRooms = $scope.eventToEdit.rooms;
    				if ($scope.eventToEdit.id !== undefined && selectedRooms !== undefined) {
    					for (var room of selectedRooms) {
    						$scope.rooms.push(room);
        				}
    				}
    			});
    	}
    }
//        update the assignable values depending on type
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
//    fill assignable years
    $scope.updateYearsForFieldOfStudy = () => {
    	var selectedFieldOfStudy = $scope.fieldOfStudyToAssignEventTo;
    	$scope.selectableYears = _getUniqueYears (selectedFieldOfStudy);
    }
//    fill assignable forms
    $scope.updateFormsForYear = () => {
    	$scope.selectableForms = _getForms($scope.fieldOfStudyToAssignEventTo , $scope.yearToAssignEventTo);
    }
//    get fieldsOfStudy
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
//    getYears
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
//    getForms
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