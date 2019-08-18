<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Grade extends Model
{
    protected $fillable = ['grade_date', 'grade', 'student_id', 'exam_id'];

    protected $primaryKey = 'id';

}
