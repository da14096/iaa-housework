'use strict';

application.controller('eventListController', [
  '$scope',
  'eventService',
  ($scope, eventService) => {
    eventService.findAll().then(response => {$scope.events = response.data});
  }
]);
