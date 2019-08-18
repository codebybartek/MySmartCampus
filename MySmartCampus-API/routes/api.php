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
    Route::resource('activities', 'ActivitiesController');
    Route::resource('students', 'StudentsController');
    Route::resource('recurrences', 'RecurrenceController');
    Route::resource('attandancelist', 'AttandanceListController');
    Route::resource('activitiesfree', 'ActivitiesFreeController');
    Route::resource('professors', 'ProfessorsAdminController');
    Route::resource('subjects', 'SubjectsController');
    Route::resource('studentsgroup', 'StudentsGroupController');
    Route::resource('news', 'NewsController');
    Route::resource('materials', 'MaterialsController');
    Route::resource('grades', 'GradesController');
    Route::resource('exams', 'ExamsController');
    Route::resource('home', 'HomeController');
    Route::get('user', 'JwtAuthenticateController@index');
});

// Authentication route
Route::post('login', 'JwtAuthenticateController@authenticate');


