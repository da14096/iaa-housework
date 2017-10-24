'use strict';

application.controller('lecturerController', [
  '$scope',
  'lecturerService',
  ($scope, lecturerService) => {
	  lecturerService.findAll().then(response => {$scope.lecturers = response.data});
    
	$scope.saveLecturer = () => {
		lecturerService.saveLecturer($scope.lecturerToSave)
	      .then(response => {
	        if (response.status === 200) {
	          $scope.lecturers.push(response.data);
	          $scope.lecturerToSave = {};
	        } 
	      });
	  }
  }
]);
