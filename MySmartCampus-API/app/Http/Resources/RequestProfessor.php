<?php

namespace App\Http\Resources;

use App\User;
use App\Group;
use Illuminate\Http\Resources\Json\JsonResource;

class RequestProfessor extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {

        $student = User::all()->where('id', $this->student_id)->first();
        $group = Group::all()->where('id', $this->group_id)->first();

        return [
            'student' => $student,
            'group' => $group
        ];
    }
}
