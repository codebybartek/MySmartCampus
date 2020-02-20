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
            if($users->get($i)->role->first()->name === "user"){
                $students[$s] = $users->get($i);
                $s++;
            }else if($users->get($i)->role->first()->name === "professor"){
                $professors[$s] = $users->get($i);
                $p++;
            }
        }

        return [
            'id' => $this->id,
            'name' => $this->name,
            'hash' => $this->hash,
            'students' => $students,
            'professor' => $professors
        ];
    }
}
