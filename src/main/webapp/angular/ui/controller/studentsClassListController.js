'use strict';

application.controller('studentsClassListController', [
  '$scope',
  'studentsClassService',
  ($scope, studentsClassService) => {
	  studentsClassService.findAll().then(response => {$scope.studentsClasses = response.data});
    
    $scope.saveStudentsClass = () => {
    	studentsClassService.saveStudentsClass($scope.studentsClassToSave)
          .then(response => {
            if (response.status === 200) {
              $scope.studentsClasses.push(response.data);
              $scope.studentsClassToSave = {};
            } 
          });
      }
    
    $scope.selectStudentsClass = (selValue) => {
    	if ($scope.selStudentsClass === selValue) {
    		$scope.selStudentsClass = {};
    	} else {
    		$scope.selStudentsClass = selValue;
    	}
    }
  }
]);
