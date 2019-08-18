<template>
  <div class="w100 info">
    <img class="fixed_img" src="../../assets/info.jpg" alt="info">
    <div class="container">
      <div class="row">
        <section class="col-xs-12 col-sm-6 students_info">
          <i class="fa fa-users" aria-hidden="true"></i>
          <span id="students" class="count">25</span>
          <h2>Students in service</h2>
        </section>
        <section class="col-xs-12 col-sm-6 professors_info">
          <i class="fa fa-graduation-cap" aria-hidden="true"></i>
          <span id="professors" class="count">25</span>
          <h2>Professors in service</h2>
        </section>
      </div>
      <div class="row">
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
          
      }
    },
    methods: {
      
    },
    created: function(){
      
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';

.info{

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

  display: flex;
  align-items: center;
  min-height: 600px;
  text-align: center;


  .fixed_img{
    top: 0;
    left: 0;
    position: fixed;
    z-index: 1;
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
      font-size: 3em;
      top: -15px;
      margin-left: 10px;
      color: $basic_color;
      display: inline-block;
      position: relative;
    }
  }

}



</style>
