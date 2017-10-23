'use strict';

application.service('roomService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-housework/api/room');
    this.saveRoom = (event) => $http.post('/iaa-housework/api/room', event);
  }
]);
