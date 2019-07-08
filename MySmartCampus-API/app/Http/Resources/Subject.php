<?php

namespace App\Http\Resources;


use Illuminate\Http\Resources\Json\JsonResource;
use App\Course;
use App\Professor;
use App\Http\Resources\Courses as CoursesResource;

class Subject extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $courses = Course::all()->where('subject_id', $this->subject_id);
        return [
            'subject_id' => $this->subject_id,
            'name' => $this->name,
            'professor' => Professor::all()->where('id', $this->professor_id)->toArray(),
            'courses' => CoursesResource::collection($courses)
        ];
    }
}
