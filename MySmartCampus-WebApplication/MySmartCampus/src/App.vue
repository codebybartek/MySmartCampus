<template>
  <div id="app">
    <NavbarComponent v-if="showNavbar" />
    <router-view v-on:toggleNavbar="toggleNavbar()" />
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
    }
  },
  methods: {
    toggleNavbar(){
      this.showNavbar = !this.showNavbar;
    }
  }
}
</script>

<style lang="scss">

@import 'styles/variables.scss';
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
.show_single_content{
  transform: scale(1)!important;
}

@media(max-width: 992px){

.news_slider .carousel-3d-slider{
  width: 70%!important;
}

}


@media(max-width: 767px){

.table th, .table td{
  padding: 0!important;
  font-size: 12px;  
}

.news_slider .carousel-3d-slider{
  width: 85%!important;
}

}

</style>
