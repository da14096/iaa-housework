// Henrik Kriegshammer 6291
// general services
application.service('eventBus', [
//	eventBus that is used to communicate between controllers
	function () {
//		editEvent
		var _editEventListener = [];
		this.onEditEvent = (callback) => {
			_editEventListener.push(callback);
		}
		this.publishEditEvent = (eventToEdit) => {
			for (var listener of _editEventListener) {
				listener(eventToEdit);
			}
		}
//		updatedEvent
		var _updateEventListener = [];
		this.onUpdateEvent = (callback) => {
			_updateEventListener.push(callback);
		}
		this.publishUpdateEvent = (updatedEvent, flagCreated) => {
			for (var listener of _updateEventListener) {
				listener(updatedEvent, flagCreated);
			}
		}
//		eventDeleted
		var _deleteEventListener = [];
		this.onDeleteEvent = (callback) => {
			_deleteEventListener.push(callback);
		}
		this.publishDeleteEvent = (eventToDelete) => {
			for (var listener of _deleteEventListener) {
				listener(eventToDelete);
			}
		}
//		eventEditing ended
		var _endEventEditListener = [];
		this.onEndEventEdit = (callback) => {
			_endEventEditListener.push(callback);
		}
		this.publishEndEventEdit = () => {
			for (var listener of _endEventEditListener) {
				listener();
			}
		}
		
//		start weekView
		var _fillWeekViewListener = [];
		this.onFillWeekView = (callback) => {
			_fillWeekViewListener.push(callback);
		}
		this.publishFillWeekView = (serviceMethod, weekViewObj, caption) => {
			for (var listener of _fillWeekViewListener) {
				listener(serviceMethod, weekViewObj, caption);
			}
		}
	}
]);