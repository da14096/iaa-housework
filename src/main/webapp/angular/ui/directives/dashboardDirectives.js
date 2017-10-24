application.directive('roomList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/roomListView.html',
  controller: 'roomController'
}));

application.directive('lecturersList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/lecturerListView.html',
  controller: 'lecturerController'
}));

application.directive('studentsClassList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'directives/view/studentsClassListView.html',
  controller: 'studentsClassController'
}));