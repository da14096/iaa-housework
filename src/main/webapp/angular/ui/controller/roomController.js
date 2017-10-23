'use strict';

application.controller('roomController', [
  '$scope',
  'roomService',
  ($scope, roomService) => {
    roomService.findAll().then(response => {$scope.rooms = response.data});
    
    $scope.saveRoom = () => {
        roomService.saveRoom($scope.roomToSave)
          .then(response => {
            if (response.status === 200) {
              $scope.rooms.push(response.data);
              $scope.roomToSave = {};
            } 
          });
      }
  }
]);
