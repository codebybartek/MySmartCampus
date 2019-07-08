<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AlterGradesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('grades', function (Blueprint $table) {
            $table->integer('student_id')
                ->unsigned();
            $table->integer('exam_id')
                ->unsigned();
            $table->foreign('student_id')
                ->references('student_id')
                ->on('students')
                ->onDelete('cascade');
            $table->foreign('exam_id')
                ->references('exam_id')
                ->on('exams')
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
