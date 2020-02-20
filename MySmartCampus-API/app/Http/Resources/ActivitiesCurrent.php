<?php

namespace App\Http\Resources;

use App\Activity;
use App\AttendanceList;
use App\Group;
use App\Course;
use App\User;
use Illuminate\Http\Resources\Json\JsonResource;
use App\Http\Resources\Groups as GroupsResource;
use Tymon\JWTAuth\Facades\JWTAuth;


class ActivitiesCurrent extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $students_id = AttendanceList::all()->where('activity_id', $this->id)->pluck('user_id');
        $attendanceListDate = AttendanceList::all()->where('activity_id', $this->id)->pluck('data_presence')->first();
        $present_students = [];
        foreach($students_id as $id) {
            $student = User::all()->where('id', $id);
            $present_students[$id]['id'] = $student->pluck('id')->first();
            $present_students[$id]['name'] = $student->pluck('name')->first();
            $present_students[$id]['tagId'] = $student->pluck('tagId')->first();
            $present_students[$id]['datePresence'] = $attendanceListDate;
        }

        $activity = Activity::all()->where('id', $this->id)->first();


        $courses = $activity->courses;

        $students = Group::all()->where('id', $courses[0]['group_id'])->first()->users;
        return [
            'id' => $this->id,
            'activityDate' => $this->activityDate,
            'title' => $this->title,
            'duration' => $this->duration,
            'hash' => $this->hash,
            'students' => $students,
            'checked' => $this->checked,
            'present_students' => $present_students
        ];
    }
}
