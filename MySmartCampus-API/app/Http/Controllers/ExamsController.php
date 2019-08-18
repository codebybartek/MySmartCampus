<?php

namespace App\Http\Controllers;

use App\Course;
use App\Subject;
use App\Exam;
use Illuminate\Http\Request;
use App\Http\Resources\Exams as ExamResources;
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
        $user = Auth::user();
        $courses = $user::with('courses')->first()->courses;

        $courses = collect($courses);

        return ExamResources::collection($courses);

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
