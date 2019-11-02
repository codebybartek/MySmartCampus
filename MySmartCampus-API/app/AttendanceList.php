<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class AttendanceList extends Model
{
    protected $fillable = ['data_presence', 'user_id', 'activity_id'];
}
