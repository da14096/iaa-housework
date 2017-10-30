'use strict';

application.controller('eventController', [
  '$scope',
  'eventService',
  ($scope, eventService) => {
    $scope.today = new Date();
    
    $scope.autofill = () => {
    	var startDate = $scope.eventToDisplay.start;
    	var endDate = $scope.eventToDisplay.start;
    	endDate.setHours(startDate.getHours() + 1);
    	endDate.setMinutes(startDate.getMinutes() + 30);
    	$scope.eventToDisplay.end = endDate;
    }
  }
]);