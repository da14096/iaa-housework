'use strict';
// Henrik Kriegshammer 6291
application.controller('weekViewController', [
  '$scope',
  'eventBus',
  ($scope, eventBus) => {
    
	  var _serviceMethod;
	  var _overviewObject;
	  
//	  listen on eventBUs
	  eventBus.onFillWeekView (function (serviceMethod, object, caption) {
		  _serviceMethod = serviceMethod;
		  _overviewObject = object;
		  $scope.weekViewVisible = true;
		  $scope.selDate = new Date();
		  $scope.fillWeekView();
		  $scope.caption = caption;
	  });
//	    fill the view with the data of the selected date
	  $scope.fillWeekView = () => {
		  var selDate = $scope.selDate;
		  var dayOfWeek = selDate.getDay();
		  var difference =  selDate.getDate() - dayOfWeek + (dayOfWeek == 0 ? -6: 1);
		  selDate.setDate(difference);
		  var end = new Date(selDate).setDate(selDate.getDate() + 7);
		  $scope.selDate = selDate;
		  _serviceMethod(_overviewObject, selDate.toJSON(), new Date(end).toJSON())
		  	.then(response => {
		  		$scope.eventsMatchingVisibleDates = [];
		  		var maxEventsPerDay = 0;
		  		for (var date of $scope.visibleDates) {
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
		  for (var i = 1; i < 7; i++) {
			  var newDate = new Date(selDate);
			  newDate.setDate(selDate.getDate() + i);
			  $scope.visibleDates.push(newDate);
		  }
	  }
	
	  $scope.parseTime = (value) => {
		  return value? new Date(Date.UTC(value[0], value[1] - 1, value[2], value[3], value[4]))
				  .toLocaleTimeString(undefined, $scope.timeFormat): '';
	  }
	  $scope.close = () => {
		  $scope.weekViewVisible = false;
	  }
  }
]);