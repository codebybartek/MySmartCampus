<?php

namespace App\Http\Resources;

use App\Activity;
use App\Course;
use App\Material;
use App\Exam;
use App\News;
use Illuminate\Http\Resources\Json\JsonResource;
use App\Http\Resources\Exam as ExamResources;

use App\Group;

class Courses extends JsonResource
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
        $subject = $course->subject;

        return [
            'id' => $this->id,
            'name' => $this->name,
            'subject' => $subject->first(),
            'group' => Group::all()->where('id', $this->group_id)->first(),
            'activities' => $course->activities,
            'news' => $course->news
        ];
    }
}
