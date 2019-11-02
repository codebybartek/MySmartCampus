<template>
  <div class="w100 background_login navbar-offset-top">
    <div class="container">
      <div class="row">
        <section class="col-12 form_register">
          <h1>Registration: </h1>
          <form class="form-add align-center custom-padding">      
            <input v-model="user.name" type="text" placeholder="User name" id="name"/>
            <input v-model="user.email" type="email" placeholder="User email" id="email"/>
            <input v-model="user.tagId" type="number" placeholder="User tagId" id="tagid"/>
            <input v-model="user.password" type="password" placeholder="User password" id="password"/>
            <select v-model="user.role" class="user_role">  
              <option value="0" disabled selected>Chose role</option>
              <option value="1">student</option>         
              <option value="2">professor</option>                                                   
            </select>
            <input type="file" v-on:change="onImageChange">
            <button class="u-full-width add_button" v-on:click="adduser()" >Add user</button>
          </form>
          <div class="popup" v-bind:class="{show_pop: errors.length > 0}">
            <span class="close_btn" v-on:click="clear_errors()">&times;</span>
            <span class="error" v-for="error in errors">{{error}}</span>            
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Register',
    data() {
      return {
        user: {
          name: '',
          email: '',
          tagId: '',
          password: '',
          role: 0,
          image: null
        },
        errors: [],
        success: ''
      }
    },
    methods: {
      onImageChange(e){
          this.user.image = e.target.files[0];
      },
      adduser(){
        event.preventDefault();

        this.validate();

        let currentObj = this;
        const config = {
          headers: { 'content-type': 'multipart/form-data' }
        }

        let user = new FormData();
        user.append('name', this.user.name);
        user.append('email', this.user.email);
        user.append('tagId', this.user.tagId);
        user.append('password', this.user.password);
        user.append('image', this.user.image);
        user.append('role', this.user.role);
        if(this.errors.length === 0){
          axios.post(this.$store.getters.getUrl + '/register', user, config).then((response) =>{
            let currentDateWithFormat =  new Date().getTime();
            localStorage.setItem('Expiration', currentDateWithFormat);
            window.$cookies.set("token",response.data.token,"10MIN");
            window.$cookies.set("user_name",response.data.user_name,"10MIN")
            window.$cookies.set("user_role",response.data.user_role,"10MIN")
            this.$router.push('/');
            let alert = {content: 'You were sucessfull registred!', alertClass: "success"};
            this.$emit('setAlert', alert);
            this.$emit('toggleNavbar'); 
          })
          .catch(function(error){
            console.log(error);
            let alert = {content: 'Your registration failed. Please try again.)', alertClass: "danger"};
            this.$emit('setAlert', alert);
            this.$router.push('/Register'); 
          }.bind(this));
        } 
      },
      validate(){
        this.errors = [];
        if(!this.user.name){
          this.errors.push("Name and Surename are required");
        }
        if(!this.user.email){
          this.errors.push("Email is required");
        }else if (!this.validEmail(this.user.email)) {
            this.errors.push('Valid email required.');
          }
        if(!this.user.tagId){
          this.errors.push("Tag Id is required");
        }
        if(!this.user.password){
          this.errors.push("Password is required");
        }
        if(this.user.role===0){
          this.errors.push("Role is required");
        }
      },
      clear_errors(){
        this.errors = [];
      },
       validEmail(email){
        var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
      }
    },
    created: function(){
      this.$emit('toggleNavbar');
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';
h1{
  font-size: 2.5em;  
  margin-top: 15px;
  span{
    float: right;
    font-size: 0.7em;
    line-height: 2em;
    vertical-align: bottom;
  }
}

.background_login{
  background: url('../assets/banner.jpg');
  background-position: center;
  background-size: cover;
  display: flex;
  align-items: center;
}
.w100{
   height: 100vh;

  .login_bcg{
    position: absolute;
    top: 0;
    left: 0;
    min-height: 100vh;
    margin: 0 auto;
  }

  .header{
    margin-bottom: 70px;
  }

  .form_register{
    margin: 50px auto;

    .error{
      color: red;
      display: block;
    }

    input, select{
      width: 100%;
      max-width: 500px;
      display: block;
      margin: 0 auto;
      padding: 10px;
      border: 1px solid $basic_color;
      background: transparent;
      text-align: center;
      font-weight: 300;
      color: #fff;
      font-size: $basic;
      margin-bottom: 15px;
      outline: none;
    }

    select{
      option{
        color: $dark_grey!important;
      }
    }

    input:focus, input:focus, input:active, input:active{
      border-color: $basic_blue;
    }
    .button-primary{
      border: 1px solid $basic_color;
      background-color: transparent;
      padding: 10px 80px;
      margin-top: 20px;
      transition: 0.4s;
      outline: none;
      cursor: pointer;
      color: $basic_color;
    }
    .button-primary:focus, .button-primary:active, .button-primary:hover{
      border-color: $basic_green;
      color: $basic_green;
    }
  }

  .popup{
    display: inline-block;
      span{
        display: inline-block;
        background: rgba(0, 0, 0, 0.75);
      }     
      .close_btn{
        float: right;
        cursor: pointer;
        margin-right: 10px;
      }
    }

}
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
  }




</style>
