<template>
   <div class="container navbar-offset-top">

      <AddExam class="single_hidden" v-if="showAddFormContent" v-bind:class="{show_add_form_content: showAddFormContent}" v-on:closeSingleContent="closeSingleContent()" @setAlert="setAlert" />

      <div class="row">
        <section class="col-12 section_header">
          <h1>Exams</h1>
        </section>
        <carousel-3d @after-slide-change="onAfterSlideChange" :style="{ height: article_height}" :controls-visible="true" :controls-next-html="'&#10093;'" :controls-prev-html="'&#10092;'">
          <slide v-for="(exam, index) in exams" :index="index" :style="{ height: article_height}" v-bind:class="{ current: index==exams.length-1 && !was_changed}" :key="index">
            <article  class="exam" ref="articles_height" :style="{ height: article_height}">
              <h2>{{exam.course_title}}</h2>
              <h4>Course: {{exam.course_title}}</h4>
              <ul class="course_activities">
                  <h4>Activities:</h4>
                  <li v-for="(activity, index) in exam.activities" v-if="index < 100 ">
                      {{activity.activity_title}} - <span>{{activity.activity_date.split(" ")[0]}}</span>
                    <ul class="exams">
                      <li v-for="(exam, index) in activity.exams" v-if="index < 100 ">
                        <router-link :to="'./exam/' + exam.hash "><i class="fa fa-pencil-square-o" aria-hidden="true"></i> {{exam.title}}</router-link>
                      </li>
                    </ul>
                  </li>
                  
              </ul>
            </article>
          </slide>
        </carousel-3d>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li v-if="is_professor" class="button_normal_white"><a class="nav-button" v-on:click="addFormContent()">Add Exam <i class="fa fa-plus" aria-hidden="true"></i></a></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';
import { Carousel3d, Slide } from 'vue-carousel-3d';
import AddExam from './add_forms/AddExam.vue';


export default {
  name: 'exams',
  components: {
    Carousel3d,
    Slide,
    AddExam,
  },
  data () {
    return {
      exams: [],
      exam_id: this.$route.params.id,
      activity_id: 0,
      article_height: 'auto',
      showAddFormContent: false,
      height: 0,
      was_changed: false,
      is_professor: false,
    }
  },
  methods: {
      getExams(){
        if(window.$cookies.get('user_role') == "professor"){
          this.is_professor = true;
        }
        axios.get(this.$store.getters.getUrl + '/exams')
        .then(function (response) {
            this.exams = response.data.data;
            setTimeout(this.matchHeight, 50);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      matchHeight() {
        for(let i = 0; i < this.$refs.articles_height.length; i++){
          if(this.$refs.articles_height[i].clientHeight > this.height){
            this.height = this.$refs.articles_height[i].clientHeight;
          } 
        }
        this.height = this.height + 30;
        this.article_height = this.height + 'px';
        console.log(this.article_height);
      },
      onAfterSlideChange(index) {
        this.was_changed = true;
      },
      addFormContent(){
        this.showAddFormContent = true;
      },
      setAlert(alert){
        this.$emit('setAlert', alert);
        this.getExams();
      },
      closeSingleContent(){
        this.showAddFormContent = false;
      },
  },
  created: function () {
    if(!window.$cookies.get('token')){
      this.$router.push('/login'); 
    }
    if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
      let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
      this.$emit('setAlert', alert);
      this.$router.push('/login'); 
    }
    this.getExams();
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

@import '@/styles/variables.scss';

h1{
  margin: 50px 0;
  font-size: 3em;
}
h2{
  margin-bottom: 20px;
  font-size: 2.2em;
}

.carousel-3d-slide {
  background: $light_grey;
  opacity: 0.2!important;

  .exam{
    border: 1px solid $light_grey;
    padding: 15px;
    text-align: center;

    h2{
      color: $dark_grey;
      text-align: center;
    }

    h4{
      color: $basic_blue;
    }

    .more{
      color: $basic_green;

      &:hover{

        i{
          margin-right: -10px!important;
          margin-left: 10px!important;
        }
      }
    }

    a{
      color: $basic_color;
      margin-bottom: 5px;
      display: inline-block;
      transition: $standart_transition;
      font-size: 0.9em;

      span{
        color: $basic_green;
        transition: $standart_transition;
      }

      &:hover{
        color: $basic_blue;

        span{
          color: $basic_blue;
        }
      }
    }
  }

}
.current{
  background: $light_grey;
  opacity: 0.8!important;
}

</style>
