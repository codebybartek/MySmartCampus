<template>
  <div class="w100 home_content">
    <Banner /> 
    <About />
    <Tabs :dataAll=dataAll />
    <Cards />
    <Info />
    <Checkattendance />
  </div>
</template>

<script>

import axios from 'axios';

import Banner from '@/components/parts/Banner'
import Tabs from '@/components/parts/Tabs'
import About from '@/components/parts/About'
import Cards from '@/components/parts/Cards'
import Info from '@/components/parts/Info'
import Checkattendance from '@/components/parts/Checkattendance'

//import Courses from '@/components/Courses'

export default {
  name: 'Home',
  components: {
    Banner,
    Tabs,
    About,
    Cards,
    Info,
    Checkattendance
  },
  data () {
    return {
      dataAll: []
    }
  },
  methods: {
    getData(){
      axios.get(this.$store.getters.getUrl + '/home')
      .then(function (response) {
          this.dataAll = response.data;
          console.log(this.dataAll);
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/login'); 
      });
    },
    

  },
  created: function(){
    if(!window.$cookies.get('token')){
      this.$router.push('/login'); 
    }
    if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
      let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
      this.$emit('setAlert', alert);
      this.$router.push('/login'); 
    }
    this.getData();
  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss">

@import '@/styles/variables.scss';

h1, h2 {
  font-weight: normal;
}

.offset-50{
  padding: 50px 15px;
}

h1, h2{
  color: $basic_color;
  margin-bottom: 30px;
  padding-bottom: 25px;
  font-size: 45px;
  font-weight: 300;
  position: relative;

  &:after{
    height: 1px;
    background-color: $basic_grey;
    width: 70px;
    content: "";
    bottom: 0;
    right: 0;
    left: 0;
    margin: 0 auto;
    position: absolute;
    z-index: 4;
  }
}

h2{
  font-size: 45px;
  color: $basic_color;
}

h3{
  font-size: 28px;
  color: $basic_color;
}

.visible{
  z-index: 2;
  position: relative;
  background-color: $dark_grey;
}

.w100{
  position: relative;

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
  }
}

@media screen and (max-width: 767px){
.banner{
  img{
    max-width: 200%;
    height: 100vh;
    margin-left: -40vh;
  }
}
}




</style>
