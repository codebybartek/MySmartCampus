<?php

namespace App\Http\Controllers\professorController;

use App\Course;
use App\Subject;
use App\Exam;
use Illuminate\Http\Request;
use App\Http\Resources\Exam as ExamResources;
use Illuminate\Support\Facades\Auth;

class ExamsController extends Controller
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
            $coursesId = Course::all()->where('subject_id', $subjectId[$i])->pluck("course_id");
            for($j=0; $j<count($coursesId); $j++){
                $allCoursesId[$c] = $coursesId[$j];
                $c++;
            }
        }

        $d = 0;
        $c = 0;
        $allExams = array();
        for($i=0; $i<count($allCoursesId); $i++){
            $exams[$d] = Exam::all()->where('course_id', $allCoursesId[$i])->values()->all();
            for($j = 0; $j < count($exams[$d]) ; $j++){
               $allExams[$c] =  $exams[$d][$j];
               $c++;
            }
            $d++;
        }
        if(!$isAdmin) {
            $exams = collect($allExams);
            return ExamResources::collection($exams);
        }
        return "dddd";
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
            Exam::create($request->all());

            return response()->json([
                'created' => 'Exam was added'
            ], 201);
        }else{
            return "dddd";
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
            $course = Exam::all()->where('exam_id', $id);
            return ExamResources::collection($course);
        }else{
            return "dddd";
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
            $exam = Exam::all()->where('exam_id', $id)->first();
            return $exam;
        }else{
            return "dddd";
        }
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $exam = Exam::where('exam_id', $id)->first();
            $exam->title = $request->title;
            $exam->questions = $request->questions;
            $exam->exam_date = $request->exam_date;
            $exam->isQuiz = $request->isQuiz;
            $exam->duration = $request->duration;
            $exam->course_id = $request->course_id;
            $exam->save();

            return response()->json([
                'updated' => 'Exam was updated'
            ], 201);
        }else{
            return "dddd";
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
            $exam = Exam::where('exam_id', $id);
            $exam->delete();

            return response()->json([
                'deleted' => 'Exam was deleted'
            ], 200);
        }else{
            return "dddd";
        }
    }
}
