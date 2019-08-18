<template>
  <div class="visible">
    <div class="container-fluid">
      <div class="row">
        <footer class="col-xs-12 col-sm-12 padding-30 footer">
          <header class="header">
            <a href="#"><img src="../../assets/logo.png" alt="B.Koziel"></a>
          </header>
          <p class="copyrights">
            Copyrights & Bartłomiej Kozieł
          </p>
        </footer>
      </div>
    </div>
  </div>
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
  
  padding-top: 15px;
  padding-bottom: 15px;

  vertical-align: middle;

  .copyrights{
    margin: auto 0;
    position: absolute;
    right: 30px;
    top: 0;
    bottom: 0;
    display: table;
    font-weight: 300;
    font-family: 'Cormorant', serif;
  }

  .header{
    float: left;
    img{
      max-width: 130px;
    }
  }
}


</style>
