<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/


Route::post('role', 'JwtAuthenticateController@createRole');
Route::post('permission', 'JwtAuthenticateController@createPermission');
Route::post('assign-role', 'JwtAuthenticateController@assignRole');
Route::post('attach-permission', 'JwtAuthenticateController@attachPermission');


Route::group(['middleware' => ['ability:professor']], function()
{
    Route::resource('courses', 'CoursesController');
    Route::resource('groups', 'GroupsController');
    Route::get('group/{id}', 'GroupsController@showWithId');
    Route::get('currentactivities', 'ActivitiesController@currentActivities');
    Route::resource('activities', 'ActivitiesController');
    Route::resource('students', 'StudentsController');
    Route::delete('students', 'StudentsController@destroy');
    Route::resource('attandancelist', 'AttandanceListController');
    Route::delete('attandancelist', 'AttandanceListController@destroy');
    Route::resource('activitiesfree', 'ActivitiesFreeController');
    Route::resource('professors', 'ProfessorsAdminController');
    Route::resource('subjects', 'SubjectsController');
    //Route::resource('studentsgroup', 'StudentsGroupController');
    Route::resource('news', 'NewsController');
    Route::resource('materials', 'MaterialsController');
    Route::resource('grades', 'GradesController');
    Route::resource('exams', 'ExamsController');
    Route::resource('home', 'HomeController');
    Route::get('user', 'JwtAuthenticateController@index');
});

Route::group(['middleware' => ['ability:user|professor']], function()
{
    Route::get('courses', 'CoursesController@index');
    Route::get('groups', 'GroupsController@index');
    Route::get('group/{id}', 'GroupsController@showWithId');
    Route::get('activities', 'ActivitiesController@index');
    Route::get('activity/{hash}', 'ActivitiesController@show');
    Route::get('newss/{hash}', 'NewsController@show');
    Route::get('students', 'StudentsController@index');
    Route::get('attandancelist', 'AttandanceListController@index');
    Route::get('activitiesfree', 'ActivitiesFreeController@index');
    Route::get('professors', 'ProfessorsAdminController@index');
    Route::get('subjects', 'SubjectsController@index');
    //Route::get('studentsgroup', 'StudentsGroupController@index');
    Route::resource('requestprofessor', 'RequestProfessorController');
    Route::get('news', 'NewsController@index');
    Route::get('materials', 'MaterialsController@index');
    Route::get('material/{hash}', 'ActivitiesController@show');
    Route::get('grades', 'GradesController@index');
    Route::get('exams', 'ExamsController@index');
    Route::get('exam_grades', 'ExamsController@showGrades');
    Route::get('home', 'HomeController@index');
    Route::get('user', 'JwtAuthenticateController@index');
});

// Authentication route
Route::post('login', 'JwtAuthenticateController@authenticate');
Route::post('loginMobile', 'JwtAuthenticateController@authenticateMobileApp');
Route::post('register', 'JwtAuthenticateController@register');




