<?php

namespace App\Http\Controllers\studentController;

use App\Course;
use App\Group;
use App\Http\Resources\Subject as SubjectResource;
use App\StudentsGroup;
use App\Subject;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class SubjectsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $student = Auth::user();
        $studentId = $student->student_id;
        $groups_id = StudentsGroup::all()->where('student_id', $studentId)->pluck('group_id');
        $c = 0;
        $allSubjectsId = array();
        for($i=0; $i<count($groups_id); $i++){
            $subjectsId = Course::all()->where('group_id', $groups_id[$i])->pluck('subject_id');
            for($j=0; $j<count($subjectsId); $j++){
                $allSubjectsId[$c] = $subjectsId[$j];
                $c++;
            }
        }
        $c = 0;
        $allSubjects = array();
        for($i=0; $i<count($allSubjectsId); $i++){
            $subjects = Subject::all()->where('subject_id', $allSubjectsId[$i])->values()->all();
            for($j=0; $j<count($subjects); $j++){
                $allSubjects[$c] = $subjects[$j];
                $c++;
            }
        }

        $allSubjects = array_unique($allSubjects);
        $allSubjects = collect($allSubjects);
        return SubjectResource::collection($allSubjects);;
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
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
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
        //
    }
}
