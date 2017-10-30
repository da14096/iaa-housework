application.directive('roomList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/roomListView.html',
}));

application.directive('lecturersList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/lecturerListView.html',
}));

application.directive('studentsClassList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/studentsClassListView.html',
  controller: 'studentsClassListController'
}));

application.directive('eventList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/eventListView.html',
  controller: 'eventListController'
}));

application.directive('eventView', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/eventView.html',
  controller: 'eventController'
}));