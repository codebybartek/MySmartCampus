<?php

namespace App\Http\Controllers;

use App\Activity;
use App\Http\Resources\Activities as ActivityResources;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class ActivitiesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $AuthUser = Auth::user();
        $user = User::all()->where('id', $AuthUser->id)->first();
        $courses = $user->courses;


        $activitiesAll = [];
        $w = 0;
        for($i = 0; $i < count($courses); $i++){
            $activities = $courses[$i]->activities;
            for($j = 0; $j < count($activities); $j++) {
                $activitiesAll[$w]=  $activities[$j];
                $w++;
            }
        }

        $activities = collect($activitiesAll);

        return ActivityResources::collection($activities);
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
        $activity = new Activity();
        $activity->title = $request->title;
        $activity->activityDate = $request->activityDate;
        $activity->duration = $request->duration;
        $activity->class_room = $request->class_room;
        $activity->hash = md5(time()).rand(0, 999);

        Activity::create($activity->toArray());

        $activityId = Activity::all()->where('hash', $activity->hash)->pluck('id')->first();

        DB::table('activity_course')->insert(
            ['course_id' => $request->course_id, 'activity_id' => $activityId]
        );

        return response()->json([
            'created' => 'Activity was added'
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  string  $hash
     * @return \Illuminate\Http\Response
     */
    public function show($hash)
    {
        $activity = Activity::all()->where('hash', $hash);
        return ActivityResources::collection($activity);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  Activity $activity
     * @return \Illuminate\Http\Response
     */
    public function edit(Activity $activity)
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $activity = Activity::where('activity_id', $id)->first();
            $activity->update($request->all());

            return response()->json([
                'updated' => 'Activity was added'
            ], 200);
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
        $activity = Activity::where('id', $id);
        $activity->delete();

        return response()->json([
            'deleted' => 'Activity was deleted'
        ], 200);
    }
}
