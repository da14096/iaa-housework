application.directive('roomList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/roomListView.html'
}));

application.directive('lecturersList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/lecturerListView.html'
}));

application.directive('studentsClassList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/studentsClassListView.html'
}));

application.directive('eventList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/eventListView.html'
}));

application.directive('eventView', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/eventView.html',
  controller: 'eventFormController'
}));

application.directive('weekView', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/weekView.html',
  controller: 'weekViewController'
}));