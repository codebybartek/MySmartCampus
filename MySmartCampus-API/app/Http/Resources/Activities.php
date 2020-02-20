<?php

namespace App\Http\Resources;

use App\AttendanceList;
use App\Course;
use App\Activity;
use App\User;
use Illuminate\Http\Resources\Json\JsonResource;
use function MongoDB\BSON\toJSON;
use phpDocumentor\Reflection\Types\Object_;
use PhpParser\Node\Expr\Array_;
use function Sodium\add;

class Activities extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $attendanceListDate = AttendanceList::all()->where('activity_id', $this->id)->pluck('data_presence')->first();
        $studentsId = AttendanceList::all()->where('activity_id', $this->id)->pluck('user_id');
        $allStudents = [];
        foreach($studentsId as $id) {
            $student = User::all()->where('id', $id);
            $allStudents[$id]['id'] = $student->pluck('id')->first();
            $allStudents[$id]['name'] = $student->pluck('name')->first();
            $allStudents[$id]['tagId'] = $student->pluck('tagId')->first();
            $allStudents[$id]['datePresence'] = $attendanceListDate;
        }

        $activity = Activity::all()->where('id', $this->id)->first();

        $courses = $activity->courses;
        return [
            'id' => $this->id,
            'activityDate' => $this->activityDate,
            'title' => $this->title,
            'duration' => $this->duration,
            'hash' => $this->hash,
            'class_room' => $this->class_room,
            'checked' => $this->checked,
            'materials' => $activity->materials,
            'exams' => $activity->exams,
            'students' => $allStudents,
            'course' => $courses[0]
        ];
    }
}
