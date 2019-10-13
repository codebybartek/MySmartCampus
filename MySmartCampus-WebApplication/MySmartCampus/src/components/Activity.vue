<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <div class="row">
        <div class="col-xs-6 col-sm-6">
          <h1><span>Title: </span>{{activity.title}} <span>Date: </span>{{activity.activityDate.split(' ')[0]}}</h1>
        </div>
        <div class="col-xs-6 col-sm-6">
          <h2><span>Duration: </span>{{activity.duration}} <span>Classroom: </span>{{activity.class_room}}</h2>
        </div>
      </div>
        <h4>Materials:</h4>
        <table class="table table-hover">
          <tr class="header_table">
            <th>Id</th>
            <th>Title</th>
            <th>Content</th>
            <th>Date</th>
            <th>Attachment</th>
            <th>Delete</th>
            <th>More</th>
          </tr>
          <tr v-if="activity.materials.length > 0" v-for="(material, index) in activity.materials">
            <td>{{index + 1}}</td>
            <td>{{material.title}}</td>
            <td>{{material.content}}</td>
            <td>{{material.material_date}}</td>
            <td>{{material.attachment_url}}</td>
            <td><span class="delete" v-on:click=deleteMaterial(material.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
            <td><router-link :to="'/material/' + material.hash ">view <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link></td>
          </tr>
        </table>
        <h4>Exams:</h4>
        <table class="table table-hover">
          <tr class="header_table">
            <th>Id</th>
            <th>Title</th>
            <th>Questions</th>
            <th>Date</th>
            <th>Duration</th>
            <th>Delete</th>
            <th>More</th>
          </tr>
          <tr v-if="activity.exams.length > 0" v-for="(exam, index) in activity.exams">
            <td>{{index + 1}}</td>
            <td>{{exam.title}}</td>
            <td>{{exam.questions}}</td>
            <td>{{exam.exam_date}}</td>
            <td>{{exam.duration}}</td>
            <td><span class="delete" v-on:click=deleteExam(exam.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
            <td><router-link :to="'./exam/' + exam.hash ">view <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link></td>
          </tr>
        </table>
      </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Activity',
    props: ['activity_props'],
    data() {
      return {
        activity_hash: this.$route.params.hash,
        activity: null, 
      }
    },
    methods: {
      getActivity(){
        axios.get(this.$store.getters.getUrl + '/activities/' + this.activity_hash)
        .then(function (response) {
            this.activity = response.data.data[0];
            console.log(this.activity);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      closeBtn(){
        if(!this.activity_hash){
          this.$emit('closeSingleContent');
        }else{
          this.$router.push('/'); 
        }
      },
      deleteMaterial(id){

      }
      
    },
    created: function(){
      if(window.$cookies.get('token')){
        if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
          let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
          this.$emit('setAlert', alert);
          this.$router.push('/login'); 
        }
        if(this.activity_hash){
            this.getActivity();
        }else{
          this.activity = this.activity_props;
        }
      }
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';

h1, h2{

  text-align: center;
  font-size: 2em;  

  span{
    font-size: 0.5em;
    margin-top: 10px;
    display: block;
    width: 100%;
    color: $basic_blue;
  }
}
h4{
  color: $basic_blue;
  margin-top: 20px;
}

.single_content{
  padding-top: 75px;
  position: absolute;
  min-height: 100vh;
  width: 100%;
  top: 0;
  left: 0;
  background-color: $dark_grey;
  z-index: 5;
  display: flex;
  align-items: center;
  justify-content: center;

  .center_content{
    padding: 50px;
    border: 1px solid $basic_color;
  }
}

.close_btn{
  position: absolute;
  right: 30px;
  top: 105px;
  font-size: 4em;
  line-height: 0;
  cursor: pointer;
}


</style>
