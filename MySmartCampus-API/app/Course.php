<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Course extends Model
{
    protected $fillable = ['name', 'group_id', 'hash'];

    protected $primaryKey = "id";

    protected $hidden = ['activities', 'news', 'pivot'];


    public function activities() {
        return $this->belongsToMany('App\Activity', 'activity_course');
    }

    public function news() {
        return $this->belongsToMany('App\News', 'course_news');
    }

}
