'use strict';

application.controller('eventController', [
  '$scope',
  'eventService',
  'roomService',
  'lecturerService',
  'studentsClassService',
  ($scope, eventService, roomService, lecturerService, studentsClassService) => {
    $scope.today = new Date();
    
    $scope.roomListAddable = false;
    $scope.roomListCaption = "Verfügbare Räume";
    $scope.rooms = [];
    
    $scope.autofill = () => {
    	var startDate = $scope.eventToDisplay.start;
    	var endDate = $scope.eventToDisplay.start;
    	endDate.setHours(startDate.getHours() + 1);
    	endDate.setMinutes(startDate.getMinutes() + 30);
    	$scope.eventToDisplay.end = endDate;
    }
    
    $scope.updateAssignableOrganisatory = () => {
    	var chosenEventType = $scope.eventToDisplay.type;
    	switch (chosenEventType) {
    	case 'Vorlesung':
    		studentsClassService.findAll().then(response => $scope.assignableOrganisatory = response.data);
    		break;
    	default:
    		console.log(chosenEventType);
    	}
    }
  }
]);