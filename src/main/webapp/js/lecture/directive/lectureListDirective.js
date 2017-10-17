'use strict';

application.directive('lectureList', () => ({
  restrict: 'E',
  transclude: false,
  templateUrl: 'view/lecture/lectureListView.html',
  controller: 'lectureListController'
}));
