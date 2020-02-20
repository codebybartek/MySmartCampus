<?php

namespace App\Http\Controllers;

use App\User;
use App\Course;
use App\Grade;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use App\Http\Resources\Grades as GradesResources;

class StudentsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //return Student::all();
        $AuthUser = Auth::user();
        $grades = Grade::all()->where('user_id', $AuthUser->id);

        return GradesResources::collection($grades);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {

    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        DB::table('group_user')->insert(
            ['user_id' => $request->user_id, 'group_id' => $request->group_id]
        );

        $courseId = Course::all()->where('group_id', $request->group_id)->pluck('id')->first();
        $subjectId = DB::table('course_user')->where('course_id', $courseId)->pluck('subject_id')->first();

        DB::table('course_user')->insert(
            ['user_id' => $request->user_id, 'course_id' => $courseId, 'subject_id' => $subjectId]
        );

        DB::table('buffors')->where('student_id', $request->user_id)->delete();

        return response()->json([
            'created' => 'Student was added'
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
       
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function destroy(Request $request)
    {
       $jsonArray = json_decode($request->id,true);
       $user = DB::table('group_user')->where([['user_id', '=', $jsonArray['student_id']],['group_id', '=', $jsonArray['group_id']]]);
       $user->delete();

       return response()->json([
            'deleted' => 'Student was removed from a group'
        ], 200);
    }
}
