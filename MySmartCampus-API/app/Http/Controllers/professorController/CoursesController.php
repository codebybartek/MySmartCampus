<?php

namespace App\Http\Controllers\professorController;

use App\Course;
use App\Http\Resources\Courses;
use App\Http\Resources\Courses as CoursesResource;
use App\Subject;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class CoursesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $professor = Auth::user();
        $professorId = $professor->id;
        $isAdmin = $professor->isAdmin;
        $subjectId = Subject::all()->where('professor_id', $professorId)->pluck("subject_id");
        $allCoursesId = array();
        $c = 0;
        for($i=0; $i<count($subjectId); $i++){
            $coursesId = Course::all()->where('subject_id', $subjectId[$i])->values()->all();
            for($j=0; $j<count($coursesId); $j++){
                $allCoursesId[$c] = $coursesId[$j];
                $c++;
            }
        }

        if(!$isAdmin) {
            $courses = collect($allCoursesId);
            return CoursesResource::collection($courses);
        }
        return "unauthorized";
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            Course::create($request->all());

            return response()->json([
                'created' => 'Course was added'
            ], 201);
        }else{
            return "unauthorized";
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $course = Course::all()->where('course_id', $id);
            return CoursesResource::collection($course);
        }else{
            return "unauthorized";
        }
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $course = Course::all()->where('course_id', $id)->first();
            return $course;
        }else{
            return "unauthorized";
        }
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $course = Course::where('course_id', $id)->first();
            $course->name = $request->name;
            $course->group_id = $request->group_id;
            $course->subject_id = $request->subject_id;
            $course->save();

            return response()->json([
                'updated' => 'Course was updated'
            ], 201);
        }else{
            return "unauthorized";
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $course = Course::where('course_id', $id);
            $course->delete();

            return response()->json([
                'deleted' => 'Course was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}
