<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>Subject: {{subject.name}}</h1>
      <h4>Courses:</h4>
      <table class="table table-hover">
        <tr class="header_table">
          <th>Id</th>
          <th>Name</th>
          <th>Delete</th>
          <th>More</th>
        </tr>
        <tr v-if="subject.courses.length > 0" v-for="(course, index) in subject.courses">
          <td>{{index + 1}}</td>
          <td>{{course.name}}</td>
          <td><span class="delete" v-on:click=deleteCourse(course.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
          <td><router-link :to="'/course/' + course.hash ">view <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link></td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Subject',
    props: ['subject_props'],
    data() {
      return {
        subject_hash: this.$route.params.hash,
        subject: null,
      }
    },
    methods: {
      getSubject(){
        axios.get(this.$store.getters.getUrl + '/subjects/' + this.subject_hash)
        .then(function (response) {
            this.subject = response.data.data[0];
            console.log(this.subject);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      closeBtn(){
        if(!this.subject_hash){
          this.$emit('closeSingleContent');
        }else{
          this.$router.push('/'); 
        }
      },
      deleteCourse(id){
         axios.delete(this.$store.getters.getUrl + '/courses/'+ id)
         .then(function (response) {
            let alert = {content: response.data['deleted'], alertClass: "danger"};
            this.$emit('setAlert', alert);
            if(this.subject_hash){
              this.getSubject();
            }else{
              this.subject = this.subject_props;
            }
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      }

    },
    created: function(){
    this.$emit('setAlert');
      if(window.$cookies.get('token')){
        if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
          let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
          this.$emit('setAlert', alert);
          this.$router.push('/login'); 
        }
        if(this.subject_hash){
            this.getSubject();
        }else{
          this.subject = this.subject_props;
        }
      }
      
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';

h1{

  text-align: left;
  font-size: 2.5em;  
  span{
    float: right;
    font-size: 0.7em;
    line-height: 2em;
    vertical-align: bottom;
  }
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
