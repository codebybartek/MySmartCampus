<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AlterStudentsGroupTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('students_groups', function (Blueprint $table) {
            $table->integer('student_id')
                ->unsigned();
            $table->integer('group_id')
                ->unsigned();
            $table->foreign('student_id')
                ->references('student_id')
                ->on('students')
                ->onDelete('cascade');
            $table->foreign('group_id')
                ->references('group_id')
                ->on('groups')
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
