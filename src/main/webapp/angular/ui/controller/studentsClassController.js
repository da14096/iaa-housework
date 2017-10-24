'use strict';

application.controller('studentsClassController', [
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
  }
]);
