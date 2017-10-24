'use strict';

application.service('roomService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/room');
    this.saveRoom = (event) => $http.post('/iaa-housework/api/room', event);
  }
]);

application.service('lecturerService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/lecturer');
   }
]);

application.service('studentsClassService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/studentsClass');
   }
]);
