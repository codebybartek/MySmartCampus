<template>
  <div id="info_offset" class="w100 info custom-padding-60">
    <img class="fixed_img" src="../../assets/info.jpg" alt="info">
    <div class="container">
      <div class="row">
        <section class="col-xs-12 col-sm-6 students_info custom-padding-60">
          <i class="fa fa-users" aria-hidden="true"></i>
          <span id="students" class="count">{{student_counter}}</span>
          <h2>Students in service</h2>
        </section>
        <section class="col-xs-12 col-sm-6 professors_info custom-padding-60">
          <i class="fa fa-graduation-cap" aria-hidden="true"></i>
          <span id="professors" class="count">{{professor_counter}}</span>
          <h2>Professors in service</h2>
        </section>
      </div>
      <div class="row row-margin">
        <agile :fade="true" :navButtons="false" :dots="false" ref="carousel">
          <div class="slide">
            <div class="head">
              <img src="../../assets/user.jpg" alt="User">
              <h3>Magic Mike</h3>
              <span class="comment_date">06-06-2019</span>
            </div>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex</p>
          </div>
          <div class="slide">
            <div class="head">
              <img src="../../assets/user.jpg" alt="User">
              <h3>Bartek Koziel</h3>
              <span class="comment_date">06-06-2019</span>
            </div>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor </p>
          </div>
        </agile>
      </div>
    </div>
  </div>
</template>

<script>

  import { VueAgile } from 'vue-agile'

  import axios from 'axios';
  export default {
    name: 'Info',
    components: {
      agile: VueAgile
    },
    data() {
      return {
        professor_number: 45,
        student_number: 50,
        professor_counter: 0,
        student_counter: 0
      }
    },
    methods: {
      count(event){
          let container = document.getElementById('info_offset');
          let scroller = document.documentElement.scrollTop;
          if(window.scrollY > container.offsetTop - 350){
            this.counter();
          }
      },
      counter(){
        let open_counter = false;
        if(this.professor_counter <= this.professor_number){
          this.professor_counter++;
          open_counter = true;
        }
        if(this.student_counter <= this.student_number){
          this.student_counter++;
          open_counter = true;
        }
        if(open_counter === true){
           setTimeout(this.counter, 70);
        }
        
      }

    },
    created: function(){
      setTimeout(this.count, 150);
      window.addEventListener('scroll', this.count);
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';

.info{

  h2{
    font-family: 'Dancing Script', cursive;
  }

  .agile{
    z-index: 6;

    .head{

      margin-bottom: 20px;
      width: auto;
      display: inline-block;
      position: relative;

      img{
        border-radius: 100%;
        max-width: 120px;
        display: inline-block;
        position: relative;
        margin-right: 10px;
      }

      h3{
        display: inline-block;
        width: auto;
      }
    }

    .comment_date{
      color: $basic_blue;
      position: absolute;
      left: 135px;
      bottom: 20px;
    }
  }

  &:before{
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    z-index: 3;
    background: rgba(0, 0, 0, 0.35);
    content: "";
    display: block;
  }

  .fixed_img{
    top: 0;
    left: 0;
    right: 0;
    position: fixed;
    z-index: 1;
    max-width: 1700px;
    margin: 0 auto;
  }

  .students_info, .professors_info{

    z-index: 6;

    h2{
      margin: 20px 0;
    }

    i{
      font-size: 6em;
      color: $basic_color;
    }

    .count{
      font-size: 4em;
      top: -15px;
      margin-left: 10px;
      color: $basic_color;
      display: inline-block;
      position: relative;
    }
  }
}

@media screen and (max-width: 992px){
  .info .fixed_img {
    top: 0;
    right: 0;
    left: auto;
    height: 100vh;
    position: fixed;
    min-width: auto;
    z-index: 1;
  }
}

</style>
