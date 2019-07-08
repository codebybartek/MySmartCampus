<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class News extends Model
{
    protected $fillable = ['title', 'content', 'news_date', 'duration', 'img_src', 'course_id'];
    public $primaryKey = 'news_id';
}
