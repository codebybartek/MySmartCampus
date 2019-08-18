<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Activity extends Model
{
    protected $fillable = ['title', 'activityDate', 'duration', 'class_room', 'checked', 'hash'];

    protected $primaryKey = 'id';

    protected $hidden = ['pivot'];

    public function materials() {
        return $this->belongsToMany('App\Material', 'activity_material');
    }

    public function exams() {
        return $this->belongsToMany('App\Exam', 'activity_exam');
    }
}
