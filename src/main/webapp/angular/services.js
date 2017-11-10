application.service('eventBus', [
	function () {
		var _editEventListener = [];
		this.onEditEvent = (callback) => {
			_editEventListener.push(callback);
		}
		this.publishEditEvent = (eventToEdit) => {
			for (var listener of _editEventListener) {
				listener(eventToEdit);
			}
		}
		
		var _updateEventListener = [];
		this.onUpdateEvent = (callback) => {
			_updateEventListener.push(callback);
		}
		this.publishUpdateEvent = (updatedEvent, flagCreated) => {
			for (var listener of _updateEventListener) {
				listener(updatedEvent, flagCreated);
			}
		}
		
		var _deleteEventListener = [];
		this.onDeleteEvent = (callback) => {
			_deleteEventListener.push(callback);
		}
		this.publishDeleteEvent = (eventToDelete) => {
			for (var listener of _deleteEventListener) {
				listener(eventToDelete);
			}
		}
		
		var _endEventEditListener = [];
		this.onEndEventEdit = (callback) => {
			_endEventEditListener.push(callback);
		}
		this.publishEndEventEdit = () => {
			for (var listener of _endEventEditListener) {
				listener();
			}
		}
		
		
	}
]);