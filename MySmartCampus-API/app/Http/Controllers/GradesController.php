<?php

namespace App\Http\Controllers;

use App\Course;
use App\Exam;
use App\Grade;
use App\Subject;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class GradesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {

      $exams = Exam::all();
      return ['data' => $exams];
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

        $grade = new Grade();
        $grade->grade_date = $request->grade_date;
        $grade->exam_id = $request->exam_id;
        $grade->grade = $request->grade;
        $grade->user_id = $request->student_id;
        $grade->hash = md5(time()).rand(0, 999);

        Grade::create($grade->toArray());

        return response()->json([
            'created' => 'Grade was added'
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
       /*$activityId = DB::table('activity_exam')->where('exam_id', $id)->first()->pluck('activity_id');
       $courseId = DB::table('activity_course')->where('activity_id', $activityId)->first()->pluck('course_id');
       $groupId = Course::all()->where('course_id', $courseID)->first()->pluck('group_id');

       return ['exam_id' => $exam->id, 'exam_name' => $exam->title, 'grades' => GradeResources::collection($grades)];*/
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
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $grade = Grade::where('id', $id);
        $grade->delete();

        return response()->json([
            'deleted' => 'Grade was deleted'
        ], 200);
    }
}
