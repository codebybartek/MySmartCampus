<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>Add new subject: </h1>
      <form class="form-add align-center custom-padding">      
        <input v-model="subject.name" type="text" placeholder="Subject name" id="name"/>
        <button class="u-full-width add_button" v-on:click="addSubject" >Add subject</button>
      </form>
      <div class="popup" v-bind:class="{show_pop: errors.length > 0 || success}">
        <span class="error" v-for="error in errors">{{error}}</span>
        <span class="success">{{success}}</span>             
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'AddSubject',
    data() {
      return {
        subject: {
          name: ''
        },
        errors: [],
        success: ''
      }
    },
    methods: {
      addSubject(){
        event.preventDefault();
        this.errors = [];
        if(!this.subject.name){
          this.errors.push("Subject name is required");
        }
        if(this.errors.length == 0){
          axios.post(this.$store.getters.getUrl + '/subjects', this.subject).then((response) =>{
            let alert = {content: response.data['created'], alertClass: "success"};
            this.$emit('setAlert', alert);
          })
          .catch(function(error){
            popup('You were logged out');
            this.$router.push('/login'); 
          }.bind(this));
        } 
      },
      closeBtn(){
        if(!this.group_hash){
          this.$emit('closeSingleContent');
        }else{
          this.$router.push('/'); 
        }
      }

    },
    created: function(){
      if(window.$cookies.get('token')){
       //
      }else{
        this.$router.push('/login'); 
      }
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';
h1{
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
  position: fixed;
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
  z-index: 999;
}


</style>
