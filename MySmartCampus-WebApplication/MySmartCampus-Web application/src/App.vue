<template>
  <div id="app">
    <b-alert v-if="alert" show dismissible fade v-bind:variant="alert.alertClass" :key="new Date().getTime()">{{alert.content}}</b-alert>
    <NavbarComponent v-if="showNavbar" />
    <router-view @toggleNavbar="toggleNavbar()" @setAlert="setAlert"/>
    <FooterComponent v-if="showNavbar" />
  </div>
</template>

<script>

import NavbarComponent from '@/components/static/NavbarComponent'
import FooterComponent from '@/components/static/FooterComponent'

export default {
  name: 'App',
  components: {
    NavbarComponent,
    FooterComponent
  },
  data () {
    return {
      showNavbar: true,
      alert: null,
      time_out: null,
    }
  },
  methods: {
    toggleNavbar(){
      this.showNavbar = !this.showNavbar;
    },
    setAlert(alert_param){
      this.alert = alert_param;
      setTimeout(function () { this.deleteAlert() }.bind(this), 5000)
    },
    deleteAlert(){
        this.alert = null;
    }
  },
  created: function () {
      this.alert = null;
  }
}
</script>

<style lang="scss">

@import 'styles/variables.scss';

html{
  background-color: $dark_grey;
}

.popup{
  position: absolute;
  top: 100px;
  right: 0;
  left: 0;
  transform: scale(0);
  transition: $standart_transition;
}
.show_pop{
  transform: scale(1);
}
select, input, textarea{
  color: #fff;
  border: 1px solid #fff;
  background: transparent;
  padding: 10px 15px!important;
  display: inline-block;
  width: 100%;
  margin-bottom: 30px!important;
}

select option {
  color: #1d1d1d;
}

.alert{
  position: fixed;
  bottom: 0;
  z-index: 999;
}
.custom-padding{
  padding-top: 30px;
  padding-bottom: 30px;
}
.custom-padding-60{
  padding-top: 60px;
  padding-bottom: 60px;
}
.popup{
  display: inline-block;
  span{
    display: block;
    background: rgba(0, 0, 0, 0.75);
  }     
  .error{
      color: $basic_red;
  }
  .close_btn{
    float: right;
    cursor: pointer;
    margin-right: 10px;
  }
  }

#app {
  font-family: 'Cormorant', serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  font-size: $basic;
  background-color: $dark_grey;
  color: $basic_color;
  font-weight: 300;
}

a{
  font-family: 'Lato', sans-serif;
}

table{
  color: $basic_color!important;
}

.navbar-offset-top{
  padding-top: 75px;
}

ul{
  list-style-type: none;
  padding-left: 0;
}

h1,h2,h3,h4,h5,h6{
  font-family: 'Lato', sans-serif;
}

img{
  max-width: 100%;
}

::placeholder { /* Chrome, Firefox, Opera, Safari 10.1+ */
  color: #fff;
  opacity: 1; /* Firefox */
  font-weight: 300;
}

:-ms-input-placeholder { /* Internet Explorer 10-11 */
  color: #fff;
}

::-ms-input-placeholder { /* Microsoft Edge */
  color: #fff;
}

a:hover, a:active, a:focus{
  text-decoration: none;
  color: $basic_blue;
  transition: $standart_transition;
}

::-moz-selection { 
  background: $basic_grey; }

::selection { 
  background: $basic_grey; 
}

.w100{
  width: 100%;
  position: relative;
  display: block;
  overflow: hidden;
  //margin-top: -3px;
}

.padding-30{
  padding-left: $custom_padding;
  padding-right: $custom_padding;
}

input:-webkit-autofill,
input:-webkit-autofill:hover, 
input:-webkit-autofill:focus,
textarea:-webkit-autofill,
textarea:-webkit-autofill:hover,
textarea:-webkit-autofill:focus,
select:-webkit-autofill,
select:-webkit-autofill:hover,
select:-webkit-autofill:focus {
  font-weight: 300;
  font-size: $basic;
  -webkit-text-fill-color: $basic_color;
  -webkit-box-shadow: 0 0 0px 1000px transparent inset;
  transition: background-color 5000s ease-in-out 0s;
}

.button_normal_white{
  cursor: pointer;

  a{
    border: 2px solid $basic_color;
    color: $basic_color;
    padding: 10px 25px;
    display: inline-block;
    text-transform: uppercase;
    margin: 0 10px;

    &:hover{
      border-color: $basic_green;
      color: $basic_green;
    }
  }
}

.button_normal_blue{

  a{
    border: 2px solid $basic_blue;
    color: $basic_blue;
    padding: 10px 25px;
    display: inline-block;
    text-transform: uppercase;
    margin: 0 10px;

    &:hover{
      border-color: $basic_green;
      color: $basic_green;
    }
  }
}

.button_delete{
  padding: 7px 15px;
  min-width: 130px;
  color: $basic_red!important;
  position: absolute;
  bottom: 0;
  left: 0;
  margin: 0!important;
  font-size: 1.2em!important;
  cursor: pointer;

  &:hover{
    color: $basic_blue!important;
  }
}
.button_edit{
  padding: 7px 15px;
  min-width: 130px;
  text-align: center;
  color: $basic_purple!important;
  position: absolute;
  bottom: 0;
  right: 0;
  margin: 0!important;
  font-size: 1.2em!important;
  cursor: pointer;

   &:hover{
    color: $basic_blue!important;
  }
}
.button_more{
  padding: 7px 15px;
  position: absolute;
  bottom: 0;
  margin: 0 auto;
  text-align: center;
  font-size: 1.2em!important;
  margin-bottom: 0!important;
  width: 158px;
  background-position: center;
  background-image: url(assets/more_bcg.png);
  right: 0;
  left: 0;
  color: $basic_green!important;
  cursor: pointer;

  i{
      transition: $standart_transition;
  }

  &:hover{
    color: $basic_blue!important;

    i{
      margin-right: -3px;
      margin-left: 3px;
    }
  }

}

.delete{
  color: $basic_red!important;
  cursor: pointer;

  &:hover{
    color: $basic_blue!important;
  }
}

.news_slider .carousel-3d-slider{
  width: 33%!important;
}

.single_hidden{
  transform: scale(0);
  transition: $standart_transition;
}

.show_single_content, .show_add_form_content, .show_edit_form_content{
  transform: scale(1)!important;
}

.navigation_buttons{

  margin-top: 30px;

  i{
    margin-left: 5px;
  }
}

.single_content{
  form{
    input, textarea{
      background: transparent;
      border: 1px solid $basic_color;
      color: $basic_color!important;
    }
    select{
      background: transparent;
      border: 1px solid $basic_color;
      padding: 7.1px;
      color: $basic_color!important;
    }
    select option[disabled] { color: #fff;}


    option{
        color: $basic_grey!important;
      }
  }
}

.add_button{
  border: 2px solid $basic_color;
  color: $basic_color;
  padding: 4px 25px;
  display: inline-block;
  background: transparent;
  text-transform: uppercase;
  margin: 0 10px;

  &:hover{
    border-color: $basic_green;
    color: $basic_green;
  }
}

.carousel-3d-slide {
  background: $light_grey;
  opacity: 0.5!important;
  border: none;

  .course{
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
  opacity: 1!important;
}


@media screen and (min-width: 1700px){
  .w100, .container-fluid{
    position: relative;
    margin: 0 auto;
  }
  .fixed_img{
    bottom: 0!important;
    top: auto!important;

  }
}

@media screen and (max-width: 992px){

.news_slider .carousel-3d-slider{
  width: 70%!important;
}

}


@media screen and (max-width: 767px){

.table th, .table td{
  padding: 0!important;
  font-size: 12px;  
}

.news_slider .carousel-3d-slider{
  width: 85%!important;
}

}

</style>
