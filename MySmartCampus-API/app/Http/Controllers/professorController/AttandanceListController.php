<?php

namespace App\Http\Controllers\professorController;

use App\AttendanceList;
use App\Http\Resources\AttendanceList as AttandanceListResource;
use App\Http\Resources\StudentsGroups as StudentsGroupsResource;
use App\Subject;
use Illuminate\Http\Request;
use App\Activity;
use App\Course;
use App\StudentsGroup;
use Illuminate\Support\Facades\Auth;

class AttandanceListController extends Controller
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
        $allActivitiesId = array();
        for($i=0; $i<count($allCoursesId); $i++){
            $activitiesId[$d] = Activity::all()->where('course_id', $allCoursesId[$i])->pluck("activity_id");
            for($j = 0; $j < count($activitiesId[$d]) ; $j++){
                $allActivitiesId[$c] =  $activitiesId[$d][$j];
                $c++;
            }
            $d++;
        }
        $d = 0;
        $c = 0;
        $allAttendanceLists = array();
        for($i=0; $i<count($allActivitiesId); $i++){
            $attendanceLists[$d] = AttendanceList::all()->where('activity_id', $allActivitiesId[$i])->values()->all();
            for($j = 0; $j < count($attendanceLists[$d]) ; $j++){
                $allAttendanceLists[$c] =  $attendanceLists[$d][$j];
                $c++;
            }
            $d++;
        }
        if(!$isAdmin) {
            $attendances = collect($allAttendanceLists);
            return AttandanceListResource::collection($attendances);
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
            $activity = Activity::all()->where('activity_id', $request->activity_id)->first();
            $activity->checked = true;
            $activity->save();

            AttendanceList::create($request->all());

            return response()->json([
                'created' => 'Student was added to attandance list'
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
            $course = Course::all()->where('course_id', $id)->first();
            $studentsGroup = StudentsGroup::all()->where('group_id', $course->group_id);
            $students = StudentsGroupsResource::collection($studentsGroup);


            $ids= array();
            $i = 0;
            foreach($students as $student){
                $activitiesStudent = AttendanceList::all()->where('student_id', $student->student_id)->pluck('activity_id');
                $activities = Activity::all()->where('course_id', $id)->pluck('activity_id')->toArray();
                $activitiesWithStudent = 0;
                for($i=0; $i<count($activitiesStudent); $i++){
                    if(in_array($activitiesStudent[$i], $activities)){
                        $activitiesWithStudent++;
                    }
                }
                $activities = count($activities);

                $attandanceStudent = $activitiesWithStudent/$activities;
                $ids[ $student->student_id]['student'] = $student;
                $ids[ $student->student_id]['total'] = $attandanceStudent;
                $i++;
            }
            return $ids;
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
            $attandance = AttendanceList::where('student_id', $id);
            $attandance->delete();

            return response()->json([
                'deleted' => 'Student was deleted'
            ], 200);
        }
    }
}
