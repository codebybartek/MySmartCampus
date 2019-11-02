<?php

namespace App\Http\Resources;


use App\Group;
use Illuminate\Http\Resources\Json\JsonResource;
use PhpParser\Node\Expr\Array_;
use function Sodium\add;

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
        $users = Group::all()->where('id', $this->id)->first()->users;

        $students = [];
        $professors = [];
        $s = 0;
        $p = 0;
        for($i=0; $i<count($users); $i++){
            if($users->get($i)->role->first()->name == "user"){
                $students[$s] = $users->get($i);
                $s++;
            }else if($users->get($i)->role->first()->name == "professor"){
                $professors[$s] = $users->get($i);
                $p++;
            }
        }

        /*$g = 0;
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
        $students = StudentsGroupsResource::collection($studentsGroup);*/

        return [
            'id' => $this->id,
            'name' => $this->name,
            'hash' => $this->hash,
            'students' => $students,
            'professor' => $professors
        ];
    }
}
