<?php

namespace App\Http\Controllers;

use App\Activity;
use App\Course;
use App\Http\Resources\Activities as ActivityResources;
use App\Subject;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class ActivitiesController extends Controller
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            Activity::create($request->all());

            return response()->json([
                'created' => 'Activity was added'
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
            $activity = Activity::all()->where('activity_id', $id);
            return ActivityResources::collection($activity);
        }else{
            return "unauthorized";
        }
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
