<template>
  <div class="container-fluid offset-50 visible" id="get_started">
    <div class="row">
      <section class="col-12 how_does_it_work">
        <h2>How does it work?</h2>
      </section>
      <article class="col-xs-12 col-sm-4 card_item">
        <span class="hide_arrows"><i class="fa fa-mobile" aria-hidden="true"></i></span>
        <div class="content">
          <h3>Mobile Application</h3>
          <p>There is a mobile application, where in easy way professor is abble to check attandance list using smarthphone. Also it could be use by students and professor to manage theirs accounts.</p>
        </div>
        <div class="arrowAnim">
          <div class="arrowSliding">
            <div class="arrow"></div>
          </div>
          <div class="arrowSliding">
            <div class="arrow"></div>
          </div>
          <div class="arrowSliding">
            <div class="arrow"></div>
          </div>
          <div class="arrowSliding">
            <div class="arrow"></div>
          </div>
        </div>
      </article>
      <article class="col-xs-12 col-sm-4 card_item">
        <span class="hide_arrows"><i class="fa fa-globe" aria-hidden="true"></i></span>
        <div class="content">
          <h3>Synchronization</h3>
          <p>Everything is synchronized and you could use it on two ways: 
            <ul>
              <li>Mobile application</li>
              <li>Webapplication</li>
            </ul>
          </p>
        </div>
      </article>
      <article class="col-xs-12 col-sm-4 card_item">
        <span class="hide_arrows"><i class="fa fa-laptop" aria-hidden="true"></i></span>
        <div class="content">
          <h3>Web Application</h3>
          <p>In webapplication professor can check attandandance list and manage all aspects of application and each student can see theirs results, activities and courses which was enrolled before</p>
        </div>
        <div class="arrowAnim arrowAnim_rev">
          <div class="arrowSliding">
            <div class="arrow"></div>
          </div>
          <div class="arrowSliding">
            <div class="arrow"></div>
          </div>
          <div class="arrowSliding">
            <div class="arrow"></div>
          </div>
          <div class="arrowSliding">
            <div class="arrow"></div>
          </div>
        </div>
      </article>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Cards',
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

ul{
  list-style-type: none;
  color: $basic_blue;
}

.card_item{

  *{
    transition: $standart_transition;
  }

  position: relative;
  color: $light_grey;

  p{
    transform: scale(0);
    height: 0;
    margin-bottom: 100px;
  }

  &:hover{
    color: $basic_color;
    p{
      transform: scale(1);
      height: 100px;
      margin-bottom: 0;
    }

    i{
      color: $basic_color;
      font-size: 5em;
      margin-top: -10px;
      margin-bottom: 40px;
    }

    h3{
      color: $basic_color;
    }

  }

  i{
    color: $light_grey;
    font-size: 5em;
    margin-bottom: 30px;
    transition-delay: 0.2s;
  }

  h3{
    color: $light_grey;
    margin-bottom: 20px;
  }
}


.arrowAnim{
  width: 70px;
  height: 200px;
  display: flex;
  position: absolute;
  z-index: 2;
  justify-content: center;
  align-items: center;
  top: -50px;
  right: 0;

  .arrow {
    width: 15px;
    height: 15px;
    border: 2px solid;
    border-color: $basic_color transparent transparent $basic_color;
    transform: rotate(135deg);
  }

  .arrowSliding {
    margin-left: 60px;
    -webkit-animation: slide 4s linear infinite; 
          animation: slide 4s linear infinite;
  }
}

.arrowAnim_rev{
  left: 0;

  .arrow{
    transform: rotate(-45deg);
  }

  .arrowSliding{
    margin-left: auto;
    margin-right: 60px;
    -webkit-animation: slide_rev 4s linear infinite; 
          animation: slide_rev 4s linear infinite;
  }

}



.hide_arrows{
  display: inline-block;
  width: 160px;
  position: relative;
  z-index: 3;
  background-color: $dark_grey;
}

@-webkit-keyframes slide {
    0% { opacity:0; transform: translateX(-7vw); }  
   20% { opacity:1; transform: translateX(-3vw); }   
   80% { opacity:1; transform: translateX(3vw); }  
  100% { opacity:0; transform: translateX(7vw); } 
}
@keyframes slide {
    0% { opacity:0; transform: translateX(-7vw); }  
   20% { opacity:1; transform: translateX(-3vw); }   
   80% { opacity:1; transform: translateX(3vw); }  
  100% { opacity:0; transform: translateX(7vw); } 
}
@-webkit-keyframes slide_rev {
    0% { opacity:0; transform: translateX(7vw); }  
   20% { opacity:1; transform: translateX(3vw); }   
   80% { opacity:1; transform: translateX(-3vw); }  
  100% { opacity:0; transform: translateX(-7vw); } 
}
@keyframes slide_rev {
    0% { opacity:0; transform: translateX(7vw); }  
   20% { opacity:1; transform: translateX(3vw); }   
   80% { opacity:1; transform: translateX(-3vw); }  
  100% { opacity:0; transform: translateX(-7vw); } 
}






</style>
