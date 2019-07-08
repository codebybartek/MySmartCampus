<?php

namespace App\Http\Resources;

use App\Http\Resources\Courses as CoursesResource;
use App\Professor;
use App\Student;
use App\Subject;
use App\Course;
use Illuminate\Http\Resources\Json\JsonResource;
use App\StudentsGroup;
use App\Http\Resources\StudentsGroups as StudentsGroupsResource;
use PhpParser\Node\Expr\Array_;

class Groups extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $subjectId = Course::all()->where('group_id', $this->group_id)->pluck('subject_id');
        $g = 0;
        $professor_ids = array();
        for($i=0; $i<count($subjectId); $i++) {
            $professor_ids[$g] = Subject::all()->where('subject_id', $subjectId[$i])->pluck("professor_id")->first();
            $g++;
        }
        $professor_ids = array_unique($professor_ids);
        $professors = [];
        foreach($professor_ids as $id) {
            $professor = Professor::all()->where('id', $id);
            $professors[$id]['name'] = $professor->pluck('name');
        }
        $studentsGroup = StudentsGroup::all()->where('group_id', $this->group_id);
        $students = StudentsGroupsResource::collection($studentsGroup);
        return [
            'id' => $this->group_id,
            'name' => $this->name,
            'professor' =>  $professors,
            'students' => $students
        ];
    }
}
