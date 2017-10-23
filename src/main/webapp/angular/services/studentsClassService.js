'use strict';

application.service('eventService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/events');
    this.saveEvent = (event) => $http.post('/iaa-housework/api/events/update', event);
    this.deleteEvent = (event) => $http.post('/iaa-housework/api/events/delete', event);
  }
]);
