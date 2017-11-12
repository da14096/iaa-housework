'use strict';

application.controller('weekViewController', [
  '$scope',
  'eventBus',
  ($scope, eventBus) => {
    
	  $scope.dateFormatter = {year:"2-digit", month:"2-digit", day:"2-digit"};
	  $scope.timeFormatter = {hour: "2-digit", minute: "2-digit"};
	  
	  var _serviceMethod;
	  var _overviewObject;
	  
	  eventBus.onFillWeekView (function (serviceMethod, object, caption) {
		  _serviceMethod = serviceMethod;
		  _overviewObject = object;
		  $scope.weekViewVisible = true;
		  $scope.selDate = new Date();
		  $scope.fillWeekView();
		  $scope.caption = caption;
	  });
	    
	  $scope.fillWeekView = () => {
		  var selDate = $scope.selDate;
		  var dayOfWeek = selDate.getDay();
		  var difference =  selDate.getDate() - dayOfWeek + (dayOfWeek == 0 ? -6: 1);
		  selDate.setDate(difference);
		  var end = new Date(selDate).setDate(selDate.getDate() + 5);
		  $scope.selDate = selDate;
		  _serviceMethod(_overviewObject, selDate.toJSON(), new Date(end).toJSON())
		  	.then(response => {
		  		$scope.eventsMatchingVisibleDates = [];
		  		var maxEventsPerDay = 0;
		  		for (var date of $scope.visibleDates) {
		  			console.log(date.toLocaleTimeString(undefined, $scope.timeFormatter));
		  			var eventsForDate = response.data[date.toISOString().split("T")[0]];
		  			$scope.eventsMatchingVisibleDates.push(eventsForDate);
		  			if (eventsForDate) {
		  				var eventAmount = eventsForDate.length;
		  				maxEventsPerDay = maxEventsPerDay < eventAmount? eventAmount: maxEventsPerDay;
		  			}
		  		}
		  		$scope.maxEventAmount = [];
		  		for (var i = 0; i < maxEventsPerDay; i++) {
		  			$scope.maxEventAmount.push(i);
		  		}
		  });
		  _fillDates(selDate);
	  }
	  function _fillDates (selDate) {
		  $scope.selDate = selDate;
		  $scope.visibleDates = [selDate];
		  for (var i = 1; i < 5; i++) {
			  var newDate = new Date(selDate);
			  newDate.setDate(selDate.getDate() + i);
			  $scope.visibleDates.push(newDate);
		  }
	  }
	
	  $scope.close = () => {
		  $scope.weekViewVisible = false;
	  }
  }
]);