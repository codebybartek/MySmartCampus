<template>
  <div class="w100">
    <div class="row no-margin">
      <img src="../../assets/courses.png" alt="Courses">
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Tabs',
      data() {
        return {
            
        }
      },
      methods: {
        
      },
      created: function(){
        this.$emit('toggleNavbar');
        if(localStorage.getItem('token')){
          localStorage.clear();
          this.$router.go(0);
        }
      }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';




</style>
