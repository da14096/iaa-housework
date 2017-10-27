'use strict';

application.controller('eventController', [
  '$scope',
  'eventService',
  ($scope, eventService) => {
    eventService.findAll().then(response => {$scope.events = response.data});
    
    $scope.saveEvent = () => {
        eventService.saveEvent($scope.eventToSave)
          .then(response => {
            if (response.status === 200) {
              $scope.events.push(response.data);
              $scope.eventToSave = {};
            } 
          });
      }
  }
]);
