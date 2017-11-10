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
    this.createRoom = (room) => $http.post('/iaa-housework/api/room/create', room);
  }
]);

application.service('lecturerService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/lecturer');
    this.createLecturer = (lecturer) => $http.post('/iaa-housework/api/lecturer/create', lecturer);
   }
]);

application.service('studentsClassService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/studentsClass');
    this.createStudentsClass = (studentsClass) => $http.post('/iaa-housework/api/studentsClass/create', studentsClass);
    this.addEvent = (studentClass, event) => $http.post('/iaa-housework/api/studentsClass/applyStudentsClass', 
    														{studentsClass: studentsClass, event: event});
   }
]);

application.service('eventService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/event');
    this.getAvailableRooms = (event) => $http.post('/iaa-housework/api/event/availableRooms', event);
    this.create = (event) => $http.post('/iaa-housework/api/event/create', event);
    this.update = (event) => $http.post('/iaa-housework/api/event/update', event);
    this.deleteEvent = (event) => $http.post('/iaa-housework/api/event/delete', event);
   }
]);

