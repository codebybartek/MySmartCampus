<?php

namespace App\Http\Resources;

use App\Course;
use Illuminate\Http\Resources\Json\JsonResource;


class Materials extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */


    public function toArray($request)
    {
        $course = Course::all()->where('id', $this->id)->first();

        $activities = [];

        for($i = 0; $i < count($course->activities); $i++){
            $activities[$i]['materials'] = $course->activities->get($i)->first()->materials;
            $activities[$i]['activity_id'] = $course->activities->get($i)->id;
            $activities[$i]['activity_title'] = $course->activities->get($i)->title;
            $activities[$i]['activity_date'] = $course->activities->get($i)->activityDate;
        }

        return [
            'course_id' => $this->id,
            'course_title' => $this->name,
            'activities' => $activities
        ];
    }
}
