<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Exam extends Model
{
    protected $fillable = ['title', 'questions', 'exam_date', 'isQuiz', 'duration', 'course_id'];
    public $primaryKey = 'exam_id';
}
