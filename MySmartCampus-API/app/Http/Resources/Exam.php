<?php

namespace App\Http\Resources;

use App\Grade;
use Illuminate\Http\Resources\Json\JsonResource;

class Exam extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $grades = Grade::all()->where('exam_id', $this->exam_id);
        return [
            'id' => $this->exam_id,
            'title' => $this->title,
            'exam_date' => $this->exam_date,
            'duration' => $this->duration,
            'grades' => $grades
        ];
    }
}
