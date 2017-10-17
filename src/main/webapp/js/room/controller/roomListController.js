'use strict';

application.controller('roomListController', [
  '$scope',
  'roomService',
  ($scope, roomService) => {
    roomService.findAll()
      .then(response => {
        $scope.rooms = response.data;
      })
  }
]);
