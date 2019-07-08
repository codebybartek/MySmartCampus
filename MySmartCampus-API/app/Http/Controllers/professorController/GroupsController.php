<?php

namespace App\Http\Controllers\professorController;

use App\Course;
use App\Group;
use App\Http\Resources\Groups;
use App\Subject;
use Illuminate\Http\Request;
use App\Http\Resources\Groups as GroupsResource;
use Illuminate\Support\Facades\Auth;

class GroupsController extends Controller
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
        $allGroups = array();
        for($i=0; $i<count($allGroupsId) - 1; $i++){
            $groups[$d] = Group::all()->where('group_id', $allGroupsId[$i])->values()->all();
            for($j = 0; $j < count($groups[$d]) ; $j++){
                $allGroups[$c] =  $groups[$d][$j];
                $c++;
            }

            $d++;
        }
        if(!$isAdmin) {
            $groups = collect($allGroups);
            return GroupsResource::collection($groups);
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
            Group::create($request->all());

            return response()->json([
                'created' => 'Group was added'
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
            $groups = Group::all()->where('group_id', $id);
            return GroupsResource::collection($groups);
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
            $group = Group::all()->where('group_id', $id);
            return $group;
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
            $group = Group::where('group_id', $id)->first();
            $group->name = $request->name;
            $group->save();

            return response()->json([
                'updated' => 'Group was updated'
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
            $group = Group::where('group_id', $id);
            $group->delete();

            return response()->json([
                'deleted' => 'Group was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}
