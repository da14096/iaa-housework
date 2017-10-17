'use strict';

application.service('lectureService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-lecture/api/lectures');

    this.saveLecture = (lecture) => $http.post('/iaa-lecture/api/lectures', lecture);
  }
]);
