<template>
  <div class="w100 banner visible">
    <agile @afterChange="writeLetters(true)" :fade="true" :navButtons="false" :dots="false" ref="carousel">
      <div class="slide">
        <img src="../../assets/banner.jpg" alt="MySmartCampuse-Banner">
         <section class="banner_content">
          <div class="container">
            <h2>Welcome!<br> In MySmartCampus</h2>
            <h3>{{banner_text}}</h3>
           <span class="button_normal_blue"><a href="" >Get started</a></span>
           <span class="button_normal_white"><a href="" >About service</a></span>
          </div>
        </section>
      </div>
      <div class="slide">
        <img src="../../assets/banner2.jpg" alt="MySmartCampuse-Banner">
        <section class="banner_content">
          <div class="container">
            <h2>Make your's study easy...</h2>
            <h3>{{banner_text}}</h3>
          </div>
        </section>
      </div>
    </agile>
  </div>
    
</template>

<script>

import { VueAgile } from 'vue-agile'

export default {
  name: 'Banner',
  components: {
    agile: VueAgile
  },
  data () {
    return {
      banner_text: '',
      i: 0,
      w: 0
    }
  },
  methods: {
    writeLetters(started){
      if(started){
        this.banner_text = '';
        this.i = 0;
        this.w = this.$refs.carousel.getCurrentSlide();
      }
      let txt = ['University Management System, the place where you manage all your student work. Start now and have everything in one smart application.', 'In convenient and modern way!']; 
      if (this.i < txt[this.w].length) {
        this.banner_text += txt[this.w].charAt(this.i);
        this.i++;
        setTimeout(this.writeLetters, 150);
      }
    },
  },
  created: function(){
    this.writeLetters(false);
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

@import '@/styles/variables.scss';

h1, h2 {
  font-weight: normal;
}

.banner{
  z-index: 2;
  padding-top: 75px;

img{
  max-width: 100%;
}

.banner_content{
    position: absolute;
    z-index: 999;
    width: 100%;
    height: 100%;
    top: 0;
    display: flex;
    align-items: center;

    .button_normal_blue, .button_normal_white{

        transform: scale(0);
        transition: $standart_transition - 0.2;
        transition-delay: 0.5s;
        display: inline-block;
      
    }

    .button_normal_blue {
      margin-left: -30px;
      margin-right: 30px;
    }

    .button_normal_white {
      transition-delay: 1s;
      margin-right: -30px;
      margin-left: 30px;
    }
  }

  h2{
    font-size: 55px;
    color: $basic_color;
    text-transform: uppercase;
    font-weight: 700;
  }

  h3{
    font-size: 23px;
    color: $basic_grey;
    max-width: 600px;
    margin: 0 auto;
    min-height: 90px;
  }

  .agile__slide--current .button_normal_blue{
      transform: scale(1);
      margin: 30px 15px;
  }

  .agile__slide--current .button_normal_white{
      transform: scale(1);
      margin: 30px 15px;
  }
}

@media(max-width: 992px){
.banner{
  img{
    max-width: 130%;
  }

  h2{
    font-size: 40px;
  }

  h3{
    font-size: 20px;
  }

}
}
@media(max-width: 767px){
.banner{
  img{
    max-width: 200%;
  }
}
}

@media(max-width: 500px){
.banner{
  img{
    margin-left: -30vh;
    min-height: 500px;
    height: auto;
  }
}
}




</style>
