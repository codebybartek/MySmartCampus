<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AlterTableAttendanceLists extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('attendance_lists', function (Blueprint $table) {
            $table->integer('student_id')
                ->unsigned();
            $table->integer('activity_id')
                ->unsigned();
            $table->foreign('student_id')
                ->references('student_id')
                ->on('students')
                ->onDelete('cascade');
            $table->foreign('activity_id')
                ->references('activity_id')
                ->on('activities')
                ->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        //
    }
}
