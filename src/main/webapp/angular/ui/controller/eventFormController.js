'use strict';

application.controller('eventFormController', [
  '$scope',
  'eventService',
  ($scope, eventService) => {
    
	$scope.today = new Date();
    $scope.lecturerListAddable = false;
    $scope.lecturerListSelectable = true;
    
    $scope.roomListAddable = false;
    $scope.roomListSelectable = true;
    $scope.roomListCaption = "Verfügbare Räume";
    $scope.roomSelectionOpened = false;
    
    $scope.saveEvent = () => {
    	var eventToSave = $scope.eventToEdit;
    	var callback = function (response) {
    		console.log(response.data);
    	};
    	if (eventToSave.id) {
    		eventService.update(eventToSave).then(callback);
    	} else {
    		eventService.create(eventToSave).then(callback);
    	}
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
    		eventService.getAvailableRooms($scope.eventToEdit).then(response => {$scope.rooms = response.data});
    	}
    }
    
    $scope.closeAndClearDialogue = () => {
    	$scope.eventToEdit = {};
    	$scope.roomSelectionOpened = false;
    	$scope.eventViewVisible = false;
    	$scope.selectedLecturer = {};
    	$scope.selectedRoom = {};
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