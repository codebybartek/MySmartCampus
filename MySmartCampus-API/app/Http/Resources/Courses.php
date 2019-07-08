<?php

namespace App\Http\Resources;

use App\Activity;
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
        $materials = Material::all()->where('course_id', $this->course_id);
        $news = News::all()->where('course_id', $this->course_id);
        $exams = Exam::all()->where('course_id', $this->course_id);
        return [
            'id' => $this->course_id,
            'name' => $this->name,
            'group' => Group::all()->where('group_id', $this->group_id)->toArray(),
            'activities' => Activity::all()->where('course_id', $this->course_id)->toArray(),
            'materials' => $materials,
            'news' => $news,
            'exams' => ExamResources::collection($exams)
        ];
    }
}
