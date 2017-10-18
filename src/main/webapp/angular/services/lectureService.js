'use strict';

application.service('lectureService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/lectures');

    this.saveLecture = (lecture) => $http.post('/iaa-housework/api/lectures', lecture);
  }
]);
