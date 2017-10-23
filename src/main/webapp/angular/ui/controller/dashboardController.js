'use strict';

application.controller('dashboardController', [
  '$scope',
  'roomService',
  ($scope, roomService) => {
	roomService.findAll().then(response => {$scope.rooms = response.data})  
  }
]);
