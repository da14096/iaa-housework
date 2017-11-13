'use strict';
//Henrik Kriegshammer 6291
//services for model (to retrieve enum values)
application.service('modelService', [
  '$http',
  function ($http) {
    this.buildings = () => $http.get('/iaa-housework/api/model/buildings');
    this.eventTypes = () => $http.get('/iaa-housework/api/model/eventTypes');
    this.fieldsOfStudy = () => $http.get('/iaa-housework/api/model/fieldsOfStudy');
    this.roomTypes = () => $http.get('/iaa-housework/api/model/roomTypes');
  }
]);

// services for room
application.service('roomService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/room');
    this.createRoom = (room) => $http.post('/iaa-housework/api/room/create', room);
    this.getEventsForWeek = (room, start, end) => $http.post('/iaa-housework/api/room/weekView?start=' + start
																	+ "&end=" + end, room);
  }
]);

//lecturerServices
application.service('lecturerService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/lecturer');
    this.createLecturer = (lecturer) => $http.post('/iaa-housework/api/lecturer/create', lecturer);
    this.getEventsForWeek = (lecturer, start, end) => $http.post('/iaa-housework/api/lecturer/weekView?start=' + start
    																+ "&end=" + end, lecturer);
   }
]);

//studentsClass Services
application.service('studentsClassService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/studentsClass');
    this.createStudentsClass = (studentsClass) => $http.post('/iaa-housework/api/studentsClass/create', studentsClass);
    this.getEventsForWeek = (studentsClass, start, end) => $http.post('/iaa-housework/api/studentsClass/weekView?start=' + start
																	+ "&end=" + end, studentsClass);
    this.addEvent = (studentsClass, event, weeks) => 
    	$http.post('/iaa-housework/api/studentsClass/applyEvent?validate=true' + (weeks? '&weeks=' + weeks: ''), 
    				{studentsClass: studentsClass, event: event});
    this.forceAdd = (studentsClass, event, weeks) => 
    	$http.post('/iaa-housework/api/studentsClass/applyEvent?validate=false'+ (weeks? '&weeks=' + weeks: ''),
    				{studentsClass: studentsClass, event: event});
    this.removeEvent = (studentsClass, event) => $http.post('/iaa-housework/api/studentsClass/removeEvent',
    				{studentsClass: studentsClass, event: event});
   }
]);

// eventService
application.service('eventService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/event');
    this.getAvailableRooms = (event) => $http.post('/iaa-housework/api/event/availableRooms', event);
    this.getAssignedStudentsClasses = (event) => $http.post('/iaa-housework/api/event/studentsClasses', event);
    this.saveEvent = (event, weeks) => $http.post('/iaa-housework/api/event/save' + (weeks? '?weeks=' + weeks: ''), event);
    this.forceSaveEvent = (event, weeks) => $http.post('iaa-housework/api/event/save?validate=false'
    														+ (weeks? '&weeks=' + weeks: ''), event);
    this.deleteEvent = (event) => $http.post('/iaa-housework/api/event/delete', event);
   }
]);

