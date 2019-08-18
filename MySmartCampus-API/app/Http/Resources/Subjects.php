<?php

namespace App\Http\Resources;


use App\Subject;
use Illuminate\Http\Resources\Json\JsonResource;
use App\Professor;

class Subjects extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
       $courses = Subject::all()->where('id', $this->id)->first()->courses;

        return [
            'id' => $this->id,
            'name' => $this->name,
            'courses' => $courses
        ];
    }
}
