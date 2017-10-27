'use strict';

application.controller('roomListController', [
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
    
    $scope.selectRoom = (selValue) => {
    	if ($scope.selRoom === selValue) {
    		$scope.selRoom = {};
    	} else {
    		$scope.selRoom = selValue;
    	}
    }
  }
]);
