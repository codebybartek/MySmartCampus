<template>
  <div class="attendance_button">
    <router-link to="/attendance"><span class="icon"><i class="fa fa-list-ol" aria-hidden="true"></i></span><span class="icon_text">Check attendance list</span></router-link>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Checkattendance',
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

.attendance_button{
  position: fixed;
  bottom: 15px;
  right: 15px;
  z-index: 999;
  display: flex;
  align-items: center;
  transition: 0.5s;

  a{
    padding: 20px;
    color: $basic_color;
    transition: 0.5s;

    .icon{
      width: 38px;
      height: 38px;
      line-height: 38px;
      text-align: center;
      vertical-align: middle;
      background-color: $basic_green;
      display: inline-block;
      position: relative;
    }

    .icon_text:before{
      height: 100%;
      width: 0;
      position: absolute;
      content: "";
      background: rgba(0, 0, 0, 0.4);
      display: block;
      left: 0;
      z-index: -1!important;
    }

    .icon_text{
      display: none;
      border: 1px solid $basic_green;
      padding: 0 10px;
      height: 38px;
      position: relative;
      line-height: 36px;
      vertical-align: middle;
      z-index: 999;
      position: relative;
      transition: 0.5s;
    }


    &:hover > .icon_text{
      display: inline-block;
      transition: 0.5s;
    }

    &:hover > .icon_text:before{
      animation-name: showButton;
      animation-duration: 0.5s;
      animation-fill-mode: forwards;  
    }
  }
}

@media screen and (max-width: 767px){

.attendance_button{
  bottom: 0;
  right: 0;
}

}

/*ANIMATIONS*/

@-webkit-keyframes showButton {
  from {width: 0}
  to {width: 100%}
}

</style>
