'use strict';

application.controller('lecturerListController', [
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
	
	$scope.selectLecturer = (selValue) => {
		if ($scope.selLecturer === selValue) {
    		$scope.selLecturer = {};
    	} else {
    		$scope.selLecturer = selValue;
    	}
	}
  }
]);
