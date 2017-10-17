'use strict';

application.service('roomService', [
  '$http',
  function ($http) {
    this.findAll = () => $http.get('/iaa-lecture/api/rooms');

    this.saveRoom = (room) => $http.post('/iaa-lecture/api/rooms', room);
  }
]);
