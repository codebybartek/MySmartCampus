<?php

namespace App\Http\Resources;

use App\Course;
use App\Http\Resources\Courses as CoursesResource;
use App\Professor;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Facades\Auth;

class StudentHome extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $courses = Course::all()->where('id', $this->subject_id);
        $student = Auth::user();
        $studentId = $student->student_id;
        return [
            'subject_id' => $studentId,
            'name' => $this->name,
            'professor' => Professor::all()->where('id', $this->professor_id)->toArray(),
            'courses' => CoursesResource::collection($courses)
        ];
    }
}
