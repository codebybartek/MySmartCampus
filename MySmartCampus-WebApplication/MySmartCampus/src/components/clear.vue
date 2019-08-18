<template>
  <div class="w100">
  
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'clear',
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
