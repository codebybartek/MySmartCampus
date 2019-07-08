<?php

namespace App\Http\Controllers\professorController;

use App\Course;
use App\Exam;
use App\Grade;
use App\Subject;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class GradesController extends Controller
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
        $c = 0;
        $allCoursesId = array();
        for($i=0; $i<count($subjectId); $i++){
            $coursesId = Course::all()->where('subject_id', $subjectId[$i])->pluck("course_id");
            for($j=0; $j<count($coursesId); $j++){
                $allCoursesId[$c] = $coursesId[$j];
                $c++;
            }
        }

        $d = 0;
        $c = 0;
        $allExamsId = array();
        for($i=0; $i<count($allCoursesId); $i++){
            $examsId[$d] = Exam::all()->where('course_id', $allCoursesId[$i])->pluck("exam_id");
            for($j = 0; $j < count($examsId[$d]) ; $j++){
                $allExamsId[$c] =  $examsId[$d][$j];
                $c++;
            }
            $d++;
        }
        $d = 0;
        $c = 0;
        $allGrades = array();
        for($i=0; $i<count($allExamsId); $i++){
            $grades[$d] = Grade::all()->where('exam_id', $allExamsId[$i])->values()->all();
            for($j = 0; $j < count($grades[$d]) ; $j++){
                $allGrades[$c] =  $grades[$d][$j];
                $c++;
            }
            $d++;
        }
        if(!$isAdmin) {
            return $allGrades;
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
            Grade::create($request->all());

            return response()->json([
                'created' => 'Grade was added'
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
            $grade = Grade::all()->where('grade_id', $id);
            return $grade;
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
            $grade = Grade::all()->where('grade_id', $id)->first();
            return $grade;
        }else{
            return "unauthorized";
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
            $grade = Grade::where('grade_id', $id)->first();
            $grade->grade = $request->grade;
            $grade->grade_date = $request->grade_date;
            $grade->student_id = $request->student_id;
            $grade->exam_id = $request->exam_id;
            $grade->save();

            return response()->json([
                'updated' => 'Grade was updated'
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
            $grade = Grade::where('grade_id', $id);
            $grade->delete();

            return response()->json([
                'deleted' => 'Grade was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}
