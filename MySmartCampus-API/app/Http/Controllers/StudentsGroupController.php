<?php

namespace App\Http\Controllers;

use App\Course;
use App\Group;
use App\StudentsGroup;
use App\Http\Resources\StudentsGroups as StudentsGroupsResource;
use App\Subject;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class StudentsGroupController extends Controller
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
        $allGroupsId = array();
        for($i=0; $i<count($subjectId); $i++){
            $groupsId = Course::all()->where('subject_id', $subjectId[$i])->pluck("group_id");
            for($j=0; $j<count($groupsId); $j++){
                $allGroupsId[$c] = $groupsId[$j];
                $c++;
            }
        }

        $d = 0;
        $c = 0;

        $allStudentsGroup = array();
        for($i=0; $i<count($allGroupsId) - 1; $i++){
            $studentsGroup[$d] = StudentsGroup::all()->where('group_id', $allGroupsId[$i])->values()->all();
            for($j = 0; $j < count($studentsGroup[$d]) ; $j++){
                $allStudentsGroup[$c] =  $studentsGroup[$d][$j];
                $c++;
            }

            $d++;
        }
        $allStudentsGroup = array_unique($allStudentsGroup);

        if(!$isAdmin) {
            $allStudentsGroup = collect($allStudentsGroup);
            return $professor;
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
            StudentsGroup::create($request->all());

            return response()->json([
                'created' => 'Student was added'
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
            $studentsGroup = StudentsGroup::all()->where('group_id', $id);
            return StudentsGroupsResource::collection($studentsGroup);
        }
        return "unauthorized";
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $student = StudentsGroup::where('student_id', $id)->first();
            $student->delete();

            return response()->json([
                'deleted' => 'Student was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}
