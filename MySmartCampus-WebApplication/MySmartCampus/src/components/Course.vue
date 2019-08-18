<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()"><i class="fa fa-times" aria-hidden="true"></i></span>
      <h1>{{course.name}} <span>{{course.group[0].name}}</span></h1>
      <h4>Activities:</h4>
      <table class="table table-hover">
        <tr class="header_table">
          <th>Id</th>
          <th>Title</th>
          <th>Date</th>
          <th>Hour</th>
          <th>Duration</th>
          <th>Classroom</th>
          <th>Delete</th>
          <th>More</th>
        </tr>
        <tr v-for="(activity, index) in course.activities">
          <td>{{index + 1}}</td>
          <td>{{activity.title}}</td>
          <td>{{activity.activityDate.split(" ")[0]}}</td>
          <td>{{activity.activityDate.split(" ")[1]}}</td>
          <td>{{activity.duration}}</td>
          <td>{{activity.class_room}}</td>
          <td><span class="delete" v-on:click=deleteActivity(activity.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
          <td><router-link :to="'./activity/' + activity.id ">view <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link></td>
        </tr>
      </table>
      <h4>News:</h4>
      <table class="table table-hover">
        <tr class="header_table">
          <th>Id</th>
          <th>Title</th>
          <th>Date</th>
          <th>Content</th>
          <th>Delete</th>
          <th>More</th>
        </tr>
        <tr v-for="(news, index) in course.news">
          <td>{{index + 1}}</td>
          <td>{{news.title}}</td>
          <td>{{news.news_date}}</td>
          <td>{{news.content}}</td>
          <td><span class="delete" v-on:click=deleteActivity(news.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
          <td><router-link :to="'./news/' + news.id ">view <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link></td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Course',
    props: ['course'],
    data() {
      return {
          
      }
    },
    methods: {
      closeBtn(){
        this.$emit('closeSingleContent');
      }
      
    },
    created: function(){

    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';

h1{

  text-align: left;
  font-size: 2.5em;  
  span{
    float: right;
    font-size: 0.7em;
    line-height: 2em;
    vertical-align: bottom;
  }
}

.single_content{
  margin-top: 75px;
  position: absolute;
  height: 100vh;
  width: 100%;
  top: 0;
  left: 0;
  background-color: $dark_grey;
  z-index: 5;
  display: flex;
  align-items: center;
  justify-content: center;

  .center_content{
    padding: 50px;
    border: 1px solid $basic_color;
  }
}

.close_btn{
  position: absolute;
  right: 30px;
  top: 20px;
  font-size: 2em;
  cursor: pointer;
}


</style>
