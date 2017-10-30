'use strict';

application.controller('eventController', [
  '$scope',
  'eventService',
  ($scope, eventService) => {
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
    
    $scope.log = () => {
    	console.log($scope.eventToDisplay.lecturer);
    }
  }
]);