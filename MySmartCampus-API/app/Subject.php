<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Subject extends Model
{
    protected $fillable = ['name', 'hash'];

    protected $primaryKey = "id";

    protected $hidden = ['pivot'];

    public function courses() {
        return $this->belongsToMany('App\Course', 'course_user');
    }
}
