'use strict';

application.controller('lectureListController', [
  '$scope',
  'lectureService',
  ($scope, lectureService) => {
    lectureService.findAll()
      .then(response => {
        $scope.lectures = response.data;
      })
  }
]);
