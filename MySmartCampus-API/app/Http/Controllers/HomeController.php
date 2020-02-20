<?php

namespace App\Http\Controllers;

use App\Activity;
use App\Course;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class HomeController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $user = Auth::user();
        if($user->roles->first()->name == "professor"){
            $courses = $user->courses;

            $activitiesAll = [];
            $w = 0;
            for($i = 0; $i < count($courses); $i++){
                $activities = $courses->get($i)->activities;
                for($j = 0; $j < count($activities); $j++){
                    $activitiesAll[$w] = $activities->get($j);
                    $w++;
                }
            }
                $newsAll = [];
            $w = 0;
            for($i = 0; $i < count($courses); $i++){
                $news = $courses->get($i)->news;
                for($j = 0; $j < count($news); $j++){
                    $newsAll[$w] = $news->get($j);
                    $w++;
                }
            }
            
        }else{
        	$courses = [];
            $groupId = DB::table('group_user')->where('user_id', $user->id)->pluck('group_id')->first();
            $coursesTemp = Course::all()->where('group_id', $groupId);
            $i = 0;
            foreach ($coursesTemp as $key => $value) {
                $courses[$i] = $value;
                $i++;
            }

            $activitiesAll = [];
            $w = 0;
            for($i = 0; $i < count($courses); $i++){
                $activities = $courses[$i]->first()->activities;
                for($j = 0; $j < count($activities); $j++){
                    $activitiesAll[$w] = $activities->get($j);
                    $w++;
                }
            }

            $newsAll = [];
            $w = 0;
            for($i = 0; $i < count($courses); $i++){
                $news = $courses[$i]->first()->news;
                for($j = 0; $j < count($news); $j++){
                    $newsAll[$w] = $news->get($j);
                    $w++;
                }
            }
        	

        }

  

        $materialsAll = [];
        $w = 0;
        for($i = 0; $i < count($activitiesAll); $i++){
            $materials = $activitiesAll[$i]->materials;
            for($j = 0; $j < count($materials); $j++){
                $materialsAll[$w] = $materials->get($j);
                $w++;
            }
        }

        $materialsAll = array_unique($materialsAll);

        $examsAll = [];
        $w = 0;
        for($i = 0; $i < count($activitiesAll); $i++){
            $exams = $activitiesAll[$i]->exams;
            for($j = 0; $j < count( $exams); $j++){
                $examsAll[$w] =  $exams->get($j);
                $w++;
            }
        }

        $groupsAll = $user->groups;

        $subjectsAll = $user->subjects;
        $subjectsAll = iterator_to_array($subjectsAll);
        $subjectsAll = array_unique($subjectsAll);

        return ([
            'subjects' => array_values($subjectsAll),
            'courses' => $courses,
            'activities' => $activitiesAll,
            'groups' => $groupsAll,
            'news' => $newsAll,
            'materials' => $materialsAll,
            'exams' => $examsAll
        ]);
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
