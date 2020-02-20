<?php

namespace App\Http\Controllers;

use App\AttendanceList;
use App\Http\Resources\AttendanceList as AttandanceListResource;
use App\Http\Resources\StudentsGroups as StudentsGroupsResource;
use App\Subject;
use Illuminate\Http\Request;
use App\Activity;
use App\Course;
use App\Group;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class AttandanceListController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return "attendance_list";
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
        $activity = Activity::all()->where('id', $request->activity_id)->first();
        if($activity->checked == false) {
            $activity->checked = true;
            $activity->save();
        }

        AttendanceList::create($request->all());

        return response()->json([
            'created' => 'Student/s was added to attandance list'
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

        $course = Course::all()->where('id', $id)->first();
        $users = Group::all()->where('id', $course->group_id)->first()->users;


        $data = array();
        $i = 0;
        $w = 0;

        $activities = $course->activities->pluck('id')->toArray();
        $activitiesAmount = count($activities);

        foreach($users as $user){
            if($user->role->first()->name != "professor"){
                $w++;
                $activitiesStudent = AttendanceList::all()->where('user_id', $user->id)->pluck('activity_id');
                $activitiesWithStudent = 0;
                for($i=0; $i<count($activitiesStudent); $i++){
                    if(in_array($activitiesStudent[$i], $activities)){
                        $activitiesWithStudent++;
                    }
                }

                $attandanceStudent = $activitiesWithStudent/$activitiesAmount;
                $ids['student'] = $user;
                $ids['total'] = $attandanceStudent;
                array_push($data, $ids);
                $i++;
            }
        }


        
        return ['data' => $data];
       
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
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function destroy(Request $request)
    {

       $jsonArray = json_decode($request->id,true);
       $user = DB::table('attendance_lists')->where([['user_id', '=', $jsonArray['student_id']],['activity_id', '=', $jsonArray['activity_id']]]);
       $user->delete();

       return response()->json([
            'deleted' => "Student was deleted"
        ], 200);
    }
}
