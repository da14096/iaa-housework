'use strict';

application.directive('roomList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'view/room/roomListView.html',
  controller: 'roomListController'
}));
