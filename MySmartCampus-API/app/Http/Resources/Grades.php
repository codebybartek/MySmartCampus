<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;
use App\User;
use App\Exam;

class Grades extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {

        $user = User::all()->where('id', $this->user_id)->first();
        $exam_title = Exam::all()->where('id', $this->exam_id)->pluck('title')->first();

         return [
            'id' => $this->id,
            'grade_date' => $this->grade_date,
            'hash' => $this->hash,
            'grade' => $this->grade,
            'user' => $user,
            'exam_title' => $exam_title
        ];
    }
}
