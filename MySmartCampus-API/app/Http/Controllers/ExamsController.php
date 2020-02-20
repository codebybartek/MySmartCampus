<?php

namespace App\Http\Controllers;

use App\Course;
use App\Subject;
use App\Grade;
use App\Exam;
use Illuminate\Http\Request;
use App\Http\Resources\Exams as ExamResources;
use App\Http\Resources\Grades as GradeResources;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class ExamsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $user = Auth::user();
        $courses = $user->courses;

        $courses = collect($courses);

        return ExamResources::collection($courses);

    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function showGrades()
    {
        $user = Auth::user();
        $exams = Grade::all()->where('user_id', $user->id);

        //$courses = collect($courses);

        return $exams;

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

        $exam = new Exam();
        $exam->title = $request->title;
        $exam->hash = md5(time()).rand(0, 999);
        $exam->save();

        $examId = Exam::all()->where('hash', $exam->hash)->pluck('id')->first();

        DB::table('activity_exam')->insert(
            ['activity_id' => $request->activity_id, 'exam_id' => $examId]
        );

        return response()->json([
            'created' => 'Exam was added'
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
        $exam = Exam::all()->where('hash', $hash)->first();
        $grades = Grade::all()->where('exam_id', $exam->id);

        //Get Group Id
        $activityId = DB::table('activity_exam')->where('exam_id', $exam->id)->pluck('activity_id')->first();
        $courseId = DB::table('activity_course')->where('activity_id', $activityId)->pluck('course_id')->first();
        $groupId = Course::all()->where('id', $courseId)->pluck('group_id')->first();

        $data = ['exam_id' => $exam->id, 'exam_name' => $exam->title, 'group_id' => $groupId, 'grades' => GradeResources::collection($grades)];

        return ['data' => [$data]];
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
        $exam = Exam::where('id', $id);
        $exam->delete();

        return response()->json([
            'deleted' => 'Exam was deleted'
        ], 200);
    }
}
