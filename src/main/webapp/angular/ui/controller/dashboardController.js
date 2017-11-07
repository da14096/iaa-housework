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
    
    $scope.roomListCaption = 'Räume';
    
//  Initialize Enums 
    modelService.buildings().then(response => {$scope.buildings = response.data});
    modelService.fieldsOfStudy().then(response => {$scope.fieldsOfStudy = response.data})
    modelService.eventTypes().then(response => {$scope.eventTypes = response.data})
    
//  room-operations  
    roomService.findAll().then(response => {$scope.rooms = response.data});
    $scope.createRoom = () => {
        roomService.createRoom($scope.roomToCreate)
          .then(response => {
            if (response.status === 200) {
              $scope.rooms.push(response.data);
              $scope.roomToCreate = {};
              $scope.newRoomFormVisible = false;
            } 
        });
    }
    
//  lecturer-operations  
    lecturerService.findAll().then(response => {$scope.lecturers = response.data});
    $scope.createLecturer = () => {
        lecturerService.createLecturer($scope.lecturerToCreate)
          .then(response => {
            if (response.status === 200) {
              $scope.lecturers.push(response.data);
              $scope.lecturerToCreate = {};
              $scope.newLecturerFormVisible = false;
            } 
        });
    }

//  studentsClass-operations  
    studentsClassService.findAll().then(response => {$scope.studentsClasses = response.data});
    $scope.createStudentsClass = () => {
    	studentsClassService.createStudentsClass($scope.studentsClassToCreate)
          .then(response => {
            if (response.status === 200) {
              $scope.studentsClasses.push(response.data);
              $scope.studentsClassToCreate = {};
              $scope.newStudentsClassFormVisible = false;
            } 
        });
    }

    
    
    $scope.planEvent = () => {
    	$scope.eventViewVisible = true;
    }
  } 
]);