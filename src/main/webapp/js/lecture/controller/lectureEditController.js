'use strict';

application.controller('lectureEditController', [
  '$scope',
  'lectureService',
  ($scope, lectureService) => {
    $scope.saveLecture = () => {
      lectureService.saveLecture($scope.lecture)
        .then(response => {
          if (response.status === 200) {
            $scope.lectures.push(response.data);
          }
        });
    }
  }
]);
