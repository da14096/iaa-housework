'use strict';

application.controller('roomEditController', [
  '$scope',
  'roomService',
  ($scope, roomService) => {
    $scope.saveRoom = () => {
      roomService.saveRoom($scope.room)
        .then(response => {
          if (response.status === 200) {
            $scope.rooms.push(response.data);
          }
        });
    }
  }
]);
