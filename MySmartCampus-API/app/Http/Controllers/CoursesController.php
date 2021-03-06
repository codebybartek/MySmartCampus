<?php

namespace App\Http\Controllers;

use App\Course;
use App\Http\Resources\Courses;
use App\Http\Resources\Courses as CoursesResource;
use App\Subject;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class CoursesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $AuthUser = Auth::user();
        $user = User::all()->where('id', $AuthUser->id)->first();

        $user = Auth::user();
        if($user->roles->first()->name == "professor"){
            $courses = $user->courses;
        }else{
            $courses = [];
            $groupId = DB::table('group_user')->where('user_id', $user->id)->pluck('group_id')->first();
            $courses = Course::all()->where('group_id', $groupId);
        }
        return CoursesResource::collection($courses);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $course = new Course();
        $course->name = $request->name;
        $course->group_id = $request->group_id;
        $course->hash = md5(time()).rand(0, 999);

        Course::create($course->toArray());

        $user = Auth::user();
        $courseId = Course::all()->where('hash', $course->hash)->pluck('id')->first();

        DB::table('course_user')->insert(
            ['user_id' => $user->id, 'course_id' => $courseId, 'subject_id' => $request->subject_id]
        );

        return response()->json([
            'created' => 'Course was added'
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  String  $hash
     * @return \Illuminate\Http\Response
     */
    public function show($hash)
    {
        $course = Course::all()->where('hash', $hash);
        return CoursesResource::collection($course);
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
     * @param  int $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $course = Course::where('id', $id)->first();
        $course->name = $request->name;
        $course->group_id = $request->group_id;
        $course->save();

        return response()->json([
            'updated' => 'Course was updated'
        ], 201);
    }
    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $course = Course::where('id', $id);
        $course->delete();

        return response()->json([
            'deleted' => 'Course was deleted'
        ], 200);
    }
}
