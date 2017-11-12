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
    this.getEventsForWeek = (room, start, end) => $http.post('/iaa-housework/api/room/weekView?start=' + start
																	+ "&end=" + end, room);
  }
]);

application.service('lecturerService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/lecturer');
    this.createLecturer = (lecturer) => $http.post('/iaa-housework/api/lecturer/create', lecturer);
    this.getEventsForWeek = (lecturer, start, end) => $http.post('/iaa-housework/api/lecturer/weekView?start=' + start
    																+ "&end=" + end, lecturer);
   }
]);

application.service('studentsClassService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/studentsClass');
    this.createStudentsClass = (studentsClass) => $http.post('/iaa-housework/api/studentsClass/create', studentsClass);
    this.getEventsForWeek = (studentsClass, start, end) => $http.post('/iaa-housework/api/studentsClass/weekView?start=' + start
																	+ "&end=" + end, studentsClass);
    this.addEvent = (studentsClass, event) => $http.post('/iaa-housework/api/studentsClass/applyEvent', 
    														{studentsClass: studentsClass, event: event});
   }
]);

application.service('eventService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/event');
    this.getAvailableRooms = (event) => $http.post('/iaa-housework/api/event/availableRooms', event);
    this.create = (event) => $http.post('/iaa-housework/api/event/create', event);
    this.forceCreation = (event) => $http.post('/iaa-housework/api/event/create?validate=false', event);
    this.createRepeated = (event, weeks) => $http.post('/iaa-housework/api/event/createRepeated?weeks=' + weeks, event)
    this.forceRepeatedCreate = (event, weeks) => $http.post('/iaa-housework/api/event/createRepeated?validate=false&weeks='
    															+ weeks, event)
    this.update = (event) => $http.post('/iaa-housework/api/event/update', event);
    this.forceUpdate = (event) => $http.post('/iaa-housework/api/event/update?validate=false', event)
    
    this.getEventsForLecturer
    this.deleteEvent = (event) => $http.post('/iaa-housework/api/event/delete', event);
   }
]);

