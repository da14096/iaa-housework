'use strict';

const application = angular.module('scheduleManagement', []);

application.service('errorHandler', [
	function () {
		var _errorWindow;
		this.displayErrorMessage = (error) => {
			if (error.status >= 500) {
				_errorWindow = document.createElement('div');
				_errorWindow.innerHTML = "Beim Verarbeiten Ihrer Anfrage kam es zu folgenden Fehlern:"
				for (var i in error.data.violations) {
					var message = document.createElement('div');
					var indxPlusOne = i + 1;
					message.innerHTML = indxPlusOne + ".) " + error.data.violations[i].message;
					_errorWindow.appendChild(message);
				}
				_errorWindow.className = "errorWindow shadowed-container";			
				document.body.appendChild(_errorWindow);
				
				_errorWindow.onclick = this.removeMessageWindow;
			}
		}
		this.removeMessageWindow = () => {
			if (_errorWindow) {
				document.body.removeChild(_errorWindow);
				_errorWindow = null;
			}
		}
	}
]);

application.factory('errorInterceptor', ['$q', 'errorHandler',
    function ($q, errorHandler) {
        return {
            request: function (config) {
                return config || $q.when(config);
            },
            requestError: function(request){
                return $q.reject(request);
            },
            response: function (response) {
                return response || $q.when(response);
            },
            responseError: function (response) {
                if (response && response.status >= 500) {
                	errorHandler.displayErrorMessage(response);
                }
                return $q.reject(response);
            }
        };
}]);

application.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('errorInterceptor');    
}]);
