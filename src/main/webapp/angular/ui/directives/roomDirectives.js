application.directive('roomList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/roomListView.html',
  controller: 'roomController'
}));