<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Buffor extends Model
{
    protected $fillable = ['table_name', 'id_in_table', 'date_create', 'isDownloaded', 'professor_id'];
    public $primaryKey = 'id_buffor';
}
