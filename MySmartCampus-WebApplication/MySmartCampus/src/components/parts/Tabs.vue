<template>
  <div class="w100 visible">
    <div class="row no-margin">
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/subjects.png" alt="subjects">
        <div class="tab_content">
          <h3>Manage subjects</h3>
          <ul>
            <li v-for="(subject, index) in dataAll.subjects" v-if="index <= 5">
              <a :href="'/subject/' + subject.hash" ><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{subject.name}}</a>
            </li>
             <router-link v-if="dataAll.subjects.length > 5" class="more" :to="'/subjects'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
          </ul>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/courses.png" alt="courses">
        <div class="tab_content">
          <h3>Manage courses</h3>
          <ul>
            <li v-for="(course, index) in dataAll.courses" v-if="index<=5">
              <a :href="'/course/' + course.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{course.name}}</a>
            </li>
            <router-link v-if="dataAll.courses.length > 5" class="more" :to="'/courses'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
          </ul>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/activities.png" alt="activities">
        <div class="tab_content">
          <h3>Manage activities</h3>
          <ul>
            <li v-for="(activity, index) in dataAll.activities" v-if="index <= 5">
              <a :href="'/activity/' + activity.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{activity.title}}</a>
            </li>
            <router-link v-if="dataAll.activities.length > 5" class="more" :to="'/activities'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
          </ul>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/exams.png" alt="exams">
        <div class="tab_content">
          <h3>Manage exams</h3>
          <ul>
            <li v-for="(exam, index) in dataAll.exams" v-if="index <= 5">
              <a :href="'/exam/' + exam.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{exam.title}}</a>
            </li>
            <router-link v-if="dataAll.exams.length > 5" class="more" :to="'/exams'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
          </ul>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/groups.png" alt="groups">
        <div class="tab_content">
          <h3>Manage groups</h3>
          <ul>
            <li v-for="(group, index) in dataAll.groups" v-if="index <= 5">
              <a :href="'/group/' + group.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{group.name}}</a>
            </li>
            <router-link v-if="dataAll.groups.length > 5" class="more" :to="'/groups'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
          </ul>
        </div>
      </article>
      <article class="col-xs-12 col-sm-6 col-md-4 tab_article no-padding">
        <img src="../../assets/materials.png" alt="tab">
        <div class="tab_content">
          <h3>Manage materials</h3>
          <ul>
            <li v-for="(material, index) in dataAll.materials" v-if="index <= 5">
              <a :href="'/material/' + material.hash"><i class="fa fa-graduation-cap" aria-hidden="true"></i> {{material.title}}</a>
            </li>
            <router-link v-if="dataAll.materials.length > 5" class="more" :to="'/materials'">More <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
          </ul>
        </div>
      </article>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Tabs',
    props: ['dataAll'],
      data() {
        return {
        }
      },
      methods: {
        
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

        i{
          padding-right: 15px;
          transform: rotate(45deg);
          display: inline-block;
        }

        &:hover{
          color: $light_grey;
          margin-right: -10px;
          margin-left: 10px;
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
    margin-top: 20px;
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

    a i{
      transform: rotate(0deg);
    }
  }
}




</style>
