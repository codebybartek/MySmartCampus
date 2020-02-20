<template>
  <footer class="w100 custom-padding visible footer">
    <div class="container-fluid padding-30">
      <div class="row">
          <header class="col-xs-12 col-sm-6 header" >
            <a href="#"><img src="../../assets/logo.png" alt="B.Koziel"></a>
          </header>
          <div class="col-xs-12 col-sm-6 copyrights">
            <p>
              Copyrights & Bartłomiej Kozieł
            </p>
          </div>
      </div>
    </div>
  </footer>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'FooterComponent',
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

.footer{
  .copyrights{
    font-weight: 300;
    display: flex;
    align-items: center;
    text-align: right;
    font-family: 'Dancing Script', cursive;

    p{
      margin: 0;
      width: 100%;
    }
  }

  .header{
    text-align: left;
    img{
      max-width: 150px;
    }
  }
}


</style>
