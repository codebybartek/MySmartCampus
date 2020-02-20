<template>
  <div class="w100 visible">
    <div v-if='isAdmin' class="row no-margin">
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/subjects.png" alt="subjects">
        <div class="tab_content">
          <h3>Manage subjects <a href="/subjects"><i class="fa fa-list" aria-hidden="true"></i></a></h3>
          <ul>
            <li v-for="(subject, index) in dataAll.subjects" v-if="index <= 5">
              <router-link :to="'/subject/' + subject.hash" ><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{subject.name}}</router-link>
            </li>
          </ul>
          <router-link v-if="dataAll.subjects && dataAll.subjects.length > 5" class="more" :to="'/subjects'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/courses.png" alt="courses">
        <div class="tab_content">
          <h3>Manage courses <a href="/courses"><i class="fa fa-list" aria-hidden="true"></i></a></h3>
          <ul>
            <li v-for="(course, index) in dataAll.courses" v-if="index<=5">
              <router-link :to="'/course/' + course.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{course.name}}</router-link>
            </li>
          </ul>
          <router-link v-if="dataAll.courses && dataAll.courses.length > 5" class="more" :to="'/courses'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/activities.png" alt="activities">
        <div class="tab_content">
          <h3>Manage activities <a href="/activities"><i class="fa fa-list" aria-hidden="true"></i></a></h3>
          <ul>
            <li v-for="(activity, index) in dataAll.activities" v-if="index <= 5">
              <router-link :to="'/activity/' + activity.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{activity.title}}</router-link>
            </li>
          </ul>
          <router-link v-if="dataAll.activities && dataAll.activities.length > 5" class="more" :to="'/activities'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/exams.png" alt="exams">
        <div class="tab_content">
          <h3>Manage exams <a href="/exams"><i class="fa fa-list" aria-hidden="true"></i></a></h3>
          <ul>
            <li v-for="(exam, index) in dataAll.exams" v-if="index <= 5">
              <router-link :to="'/exam/' + exam.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{exam.title}}</router-link>
            </li>
          </ul>
          <router-link v-if="dataAll.exams && dataAll.exams.length > 5" class="more" :to="'/exams'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/groups.png" alt="groups">
        <div class="tab_content">
          <h3>Manage groups <a href="/groups"><i class="fa fa-list" aria-hidden="true"></i></a></h3>
          <ul>
            <li v-for="(group, index) in dataAll.groups" v-if="index <= 5">
              <router-link :to="'/group/' + group.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{group.name}}</router-link>
            </li>
          </ul>
          <router-link v-if="dataAll.groups && dataAll.groups.length > 5" class="more" :to="'/groups'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/materials.png" alt="tab">
        <div class="tab_content">
          <h3>Manage materials <a href="/materials"><i class="fa fa-list" aria-hidden="true"></i></a></h3>
          <ul>
            <li v-for="(material, index) in dataAll.materials" v-if="index <= 5">
              <router-link :to="'/material/' + material.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{material.title}}</router-link>
            </li>
          </ul>
          <router-link v-if="dataAll.materials && dataAll.materials.length > 5" class="more" :to="'/materials'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
        </div>
      </article>
    </div>

    <div v-else class="row no-margin">
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/courses.png" alt="courses">
        <div class="tab_content">
          <h3>Manage courses <a href="/courses"><i class="fa fa-list" aria-hidden="true"></i></a></h3>
          <ul>
            <li v-for="(course, index) in dataAll.courses" v-if="index<=5">
             <i class="fa fa-graduation-cap" aria-hidden="true"></i> {{course.name}}
            </li>
          </ul>
          <router-link v-if="dataAll.courses && dataAll.courses.length > 5" class="more" :to="'/courses'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/activities.png" alt="activities">
        <div class="tab_content">
          <h3>Manage activities <a href="/activities"><i class="fa fa-list" aria-hidden="true"></i></a></h3>
          <ul>
            <li v-for="(activity, index) in dataAll.activities" v-if="index <= 5">
              <i class="fa fa-graduation-cap" aria-hidden="true"></i> {{activity.title}}
            </li>
          </ul>
          <router-link v-if="dataAll.activities && dataAll.activities.length > 5" class="more" :to="'/activities'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/exams.png" alt="exams">
        <div class="tab_content">
          <h3>Manage exams <a href="/myexams"><i class="fa fa-list" aria-hidden="true"></i></a></h3>
          <ul>
            <li v-for="(exam, index) in exams_student" v-if="index <= 5">
              <i class="fa fa-graduation-cap" aria-hidden="true"></i> {{exam.exam_title}}
            </li>
          </ul>
          <router-link v-if="dataAll.exams && dataAll.exams.length > 5" class="more" :to="'/exams'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
        </div>
      </article>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Tabs',
    props: ['dataAll', 'exams_student'],
    data() {
      return {
        isAdmin: false,
      }
    },
    methods: {
      
    },
    created: function(){
      if(window.$cookies.get("user_role") === "professor"){
        this.isAdmin = true;
      }        
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';

.no-margin{
  margin: 0;
}

.no-padding{
  padding: 0;
}

.tab_article{
  overflow: hidden;
  position: relative;

  .tab_content{
    right: 0;
    left: 0;
    top: 0;
    bottom: 0;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
    transform: scale(0);
    border-radius: 50%;
    z-index: 3;
    position: absolute;
    align-items: center;
    justify-content: center;
    transition: $standart_transition;

    h3{
      color: $basic_blue;
      margin-top: 30px;
      font-size: 2.5em;
    }

  }

  img{
    transition: $standart_transition;
  }

  &:hover .tab_content{
    transform: scale(1);
    border-radius: 0;
  }

  &:hover img{
    transform: scale(1.15);
  }

  ul{
    padding: 30px 0;
    list-style-type: none;
    text-align: left;
    margin: 0 auto;
    display: table;

    li{

      transform: scale(0);

      a{
        color: $basic_color;
        font-size: 1.5em;
        display: inline-block;

        i{
          padding-right: 15px;
          transform: rotate(45deg);
          display: inline-block;
        }

        &:hover{
          color: $light_grey;
          transform: translateX(10px)!important;
        }
      }

      &:nth-child(1){
        transition-delay: 0.3s;
        a i{
          transition-delay: 0.4s;
        }
      }
      &:nth-child(2){
        transition-delay: 0.5s;
        a i{
          transition-delay: 0.6s;
        }
      }
      &:nth-child(3){
        transition-delay: 0.7s;
        a i{
          transition-delay: 0.8s;
        }
      }
      &:nth-child(4){
        transition-delay: 0.9s;
        a i{
          transition-delay: 1s;
        }
      }
      &:nth-child(5){
        transition-delay: 1.1s;
        a i{
          transition-delay: 1.2s;
        }
      }
      &:nth-child(6){
        transition-delay: 1.3s;
        a i{
          transition-delay: 1.3s;
        }
      }
    }
  }

  .more{
    display: block;
    color: $basic_green!important;
    padding: 5px 25px;
    font-size: 1.5em;

    i{
      font-weight: 300;
      transition-delay: 0s!important;
    }

    &:hover{
      color: $light_grey!important;

      i{
        margin-right: -10px!important;
        margin-left: 10px!important;
      }
    }

  }

  &:hover ul li{
    transform: scale(1);
    text-align: center;
    
    a i{
      transform: rotate(0deg);
    }
  }
}

@media screen and (max-width: 1200px) and (min-width: 992px){
  .tab_article{
    .tab_content{ 
      h3{
        font-size: 2em;
        margin-top: 15px;
      }

      ul{
        padding: 15px;

        li a{
          font-size: 1.2em;
        }
      }
    }
  }
}

@media screen and (max-width: 992px) and (min-width: 767px){
  .tab_article{
    .tab_content{ 
      h3{
        font-size: 1.6em;
        margin-top: 15px;
      }

      ul{
        padding: 10px;

        li a{
          font-size: 1em;
        }
      }
    }
  }
}

@media screen and (max-width: 767px){
  .tab_article{
    .tab_content{ 
      h3{
        font-size: 1.6em;
        margin-top: 15px;
      }

      ul{
        padding: 5px;

        li a{
          font-size: 1.3em;
        }
      }
    }
  }
}

</style>
