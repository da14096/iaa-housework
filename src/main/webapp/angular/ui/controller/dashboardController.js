'use strict';

application.controller('dashboardController', [
  '$scope',
  'modelService',
  'roomService',
  'lecturerService',
  'studentsClassService',
  ($scope, modelService, roomService, lecturerService, studentsClassService) => {
    
	$scope.roomListAddable = true;
    $scope.lecturerListAddable = true;
    $scope.studentsClassListAddable = true;

//  Initialize Enums 
    modelService.buildings().then(response => {$scope.buildings = response.data});
    modelService.fieldsOfStudy().then(response => {$scope.fieldsOfStudy = response.data})
    modelService.eventTypes().then(response => {$scope.eventTypes = response.data})
    
//  room-operations  
    roomService.findAll().then(response => {$scope.rooms = response.data});
    $scope.saveRoom = () => {
        roomService.saveRoom($scope.roomToSave)
          .then(response => {
            if (response.status === 200) {
              $scope.rooms.push(response.data);
              $scope.roomToSave = {};
              $scope.newRoomFormVisible = false;
            } 
        });
    }
    
//  lecturer-operations  
    lecturerService.findAll().then(response => {$scope.lecturers = response.data});
    $scope.saveLecturer = () => {
        lecturerService.saveLecturer($scope.lecturerToSave)
          .then(response => {
            if (response.status === 200) {
              $scope.lecturers.push(response.data);
              $scope.lecturerToSave = {};
              $scope.newLecturerFormVisible = false;
            } 
        });
    }

//  studentsClass-operations  
    studentsClassService.findAll().then(response => {$scope.studentsClasses = response.data});
    $scope.saveStudentsClass = () => {
    	studentsClassService.saveStudentsClass($scope.studentsClassToSave)
          .then(response => {
            if (response.status === 200) {
              $scope.studentsClasses.push(response.data);
              $scope.studentsClassToSave = {};
              $scope.newStudentsClassFormVisible = false;
            } 
        });
    }

    
    
    $scope.planEvent = () => {
    	$scope.eventViewVisible = true;
    }
  } 
]);