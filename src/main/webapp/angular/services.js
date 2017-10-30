'use strict';

application.service('modelService', [
  '$http',
  function ($http) {
    this.buildings = () => $http.get('/iaa-housework/api/model/buildings');
    this.eventTypes = () => $http.get('/iaa-housework/api/model/eventTypes');
    this.fieldsOfStudy = () => $http.get('/iaa-housework/api/model/fieldsOfStudy');
    
  }
]);

application.service('roomService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/room');
    this.saveRoom = (room) => $http.post('/iaa-housework/api/room', room);
  }
]);

application.service('lecturerService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/lecturer');
    this.saveLecturer = (lecturer) => $http.post('/iaa-housework/api/lecturer', lecturer);
   }
]);

application.service('studentsClassService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/studentsClass');
    this.saveStudentsClass = (studentsClass) => $http.post('/iaa-housework/api/studentsClass', studentsClass);
   }
]);

application.service('eventService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/event');
   }
]);

