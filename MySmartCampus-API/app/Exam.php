<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Exam extends Model
{
    protected $fillable = ['title', 'questions', 'exam_date', 'isQuiz', 'duration', 'hash'];

    public $primaryKey = 'id';

    protected $hidden = ['pivot'];
}
