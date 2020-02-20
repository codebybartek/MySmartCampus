
<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>Group: {{group.name}}</h1>
      <h4>Students:</h4>
      <table class="table table-hover">
        <tr class="header_table">
          <th>Id</th>
          <th>Name</th>
          <th>Email</th>
          <th>Tag id</th>
          <th>Delete</th>
          <th>More</th>
        </tr>
        <tr v-for="(student, index) in group.students">
          <td>{{index + 1}}</td>
          <td>{{student.name}}</td>
          <td>{{student.email}}</td>
          <td>{{student.tagId}}</td>
          <td v-if="is_admin"><span class="delete" v-on:click=deleteStudent(student.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
          <td v-if="is_admin"><router-link :to="'./student/' + student.id ">view <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link></td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Group',
    props: ['group_props'],
    data() {
      return {
        group_hash: this.$route.params.hash,
        group: null,
        student_group: {
          student_id: 0,
          group_id: 0,
          is_admin: false,
        },
      }
    },
    methods: {
      getGroup(){
        axios.get(this.$store.getters.getUrl + '/groups/' + this.group_hash)
        .then(function (response) {
            this.group = response.data.data;
            console.log(this.group);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      closeBtn(){
        if(!this.group_hash){
          this.$emit('closeSingleContent');
        }else{
          this.$router.push('/'); 
        }
      },
      deleteStudent(id){
        this.student_group.student_id = id;
        this.student_group.group_id = this.group.id;
        console.log(this.student_group);

        axios.delete(this.$store.getters.getUrl + '/students', {params: {id: this.student_group}})
         .then(function (response) {
            alert(response.data['deleted']);
            this.getGroup();
        }.bind(this))
        .catch((error)=>{
          console.log(error);
          //this.$router.push('/login'); 
        });
      },
    },
    created: function(){
      if(window.$cookies.get('token')){
        if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
          let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
          this.$emit('setAlert', alert);
          this.$router.push('/login'); 
        }
        if(window.$cookies.get('user_role') == "professor"){
          this.is_admin = true;
        }
        if(this.group_hash){
            this.getGroup();
        }else{
          this.group = this.group_props;
        }
      }else{
        this.$router.push('/login'); 
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
  min-height: 100vh;
  position: absolute;
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
