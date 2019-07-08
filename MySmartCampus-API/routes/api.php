<?php


use Illuminate\Support\Facades\Auth;

Route::group([
    'prefix' => 'professors'
], function () {
    Route::group([
        'middleware' => ['api', 'auth:api_prof']
    ], function () {
        Route::resource('courses', 'professorController\CoursesController');
        Route::resource('groups', 'professorController\GroupsController');
        Route::resource('activities', 'professorController\ActivitiesController');
        Route::resource('students', 'professorController\StudentsController');
        Route::resource('recurrences', 'professorController\RecurrenceController');
        Route::resource('attandancelist', 'professorController\AttandanceListController');
        Route::resource('activitiesfree', 'professorController\ActivitiesFreeController');
        Route::resource('professors', 'professorController\ProfessorsAdminController');
        Route::resource('subjects', 'professorController\SubjectsController');
        Route::resource('studentsgroup', 'professorController\StudentsGroupController');
        Route::resource('news', 'professorController\NewsController');
        Route::resource('materials', 'professorController\MaterialsController');
        Route::resource('grades', 'professorController\GradesController');
        Route::resource('exams', 'professorController\ExamsController');
    });
    Route::post('login', 'professorController\ProfessorsController@login');

});
Route::group([
    'prefix' => 'students'
], function () {
    Route::group([
        'middleware' => ['api', 'auth:api_stud']
    ], function () {
        Route::resource('grades', 'professorController\GradesController');
        Route::resource('subjects', 'studentController\SubjectsController');
    });
    Route::post('login', 'studentController\StudentsController@login');
});


Route::post('loginMobileApp', 'professorController\ProfessorsController@loginMobileApp');



