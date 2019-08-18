<template>
  <div class="visible">
    <div class="container">
      <div class="row">
          <section class="col-12 about_service offset-50">
              <h1>About service</h1>
              <p>MySmartCampus is an application that can be use both by students and professors. It allow to manage everything in easy and convenient in two ways: mobile application and web-application. Take advantage of service and start now. This could make your work easier and more secure</p>
              <div class="row">
                <div class="col-xs-12 col-sm-6">
                    <h2>For professors: </h2>
                    <p>Professor can chack attandance list using NFC students cards and manage all courses, groups, and activities.</p>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <h2>For students: </h2>
                    <p>Stdudent can look for all activities, attendence and do a quiz online in easy and modern way</p>
                </div>
                 <div class="col-xs-12 col-sm-12 how_to_start">
                    <h2>How to start?</h2>
                    <p><span>If you are professor:</span> register account, add courses, groups, and activities and let students to create theirs accounts.<br></p>
                    <p><span>If you are student:</span> register account and send to professor application to enroll to group.</p>
                 </div>
              </div>
          </section>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'About',
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

.about_service{
  color: $basic_color;

  h2{
    margin-top: 20px;
    color: $light_grey;
  }

  .how_to_start{
    h2{
      color: $basic_green;
    }
    span{
      color: $basic_grey;
    }
  }
}



</style>
