<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Grade extends Model
{
    protected $fillable = ['grade_date', 'grade', 'user_id', 'exam_id', 'hash'];

    protected $primaryKey = 'id';

}
