<template>
  <div class="w100 background_login">
    <div class="container">
      <div class="row">
        <div class="col-12">
          <header class="header padding-30">
            <img src="../assets/logo.png" alt="B.Koziel">
          </header>
          <div class="form_login align-center">                   
              <input v-model="credential.email" type="email" placeholder="Your email" id="email"/>
              <input v-model="credential.password" type="password" placeholder="Your password" id="password" v-on:keyup.enter="login" />
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="login">Login</button>
          </div>
          <div class="buttons_additional">
            <router-link class="register_button" to="/register">Register <i class="fa fa-plus" aria-hidden="true"></i></router-link>
            <router-link class="google_button" to="/register">Login with Google <i class="fa fa-google" aria-hidden="true"></i></router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Login',
    data() {
      return {
          errors: [],
          credential: {
            email:'',
            password:''
          }
      }
    },
    methods: {
      login() {
        this.errors = [];
        if(!this.credential.email){
          this.errors.push("Email is required");
          return;
        }
        if(!this.credential.password){
          this.errors.push("Password is required");
          return;
        }
        
       axios.post(this.$store.getters.getUrl + '/login', this.credential).then((response) =>{
          let currentDateWithFormat =  new Date().getTime();
          localStorage.setItem('Expiration', currentDateWithFormat);
          window.$cookies.set("token",response.data.token,"10MIN");
          window.$cookies.set("user_name",response.data.user_name,"10MIN")
          window.$cookies.set("user_role",response.data.user_role,"10MIN")
          this.$router.push('/');
          let alert = null;
          this.$emit('setAlert', alert);
          this.$emit('toggleNavbar'); 
        })
        .catch(function(error){
          this.errors = [];
          this.errors.push(error.response.data.error);
        }.bind(this));
      }
    },
    created: function(){
      this.$emit('toggleNavbar');
      
      if(this.$store.getters.getToken){
      
        this.$router.go(0);
      }
    }
  }
</script>
<style scoped lang="scss">

@import '@/styles/variables.scss';

h1, h2 {
    font-size: 50px;
    text-align: center;
    width: 100%;
    margin: 30px 0;
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

  .form_login{
    margin: 50px auto;

    .error{
      color: red;
      display: block;
    }

    #email, #password{
      width: 100%;
      max-width: 400px;
      display: block;
      margin: 0 auto;
      padding: 10px;
      border: 1px solid $basic_color;
      background: transparent;
      text-align: center;
      font-weight: 300;
      color: #fff!important;
      font-size: $basic;
      margin-bottom: 15px;
      outline: none;
    }

    #email:focus, #password:focus, #email:active, #password:active{
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

  .buttons_additional{
    .register_button, .google_button{
      color: $basic_color;
      padding: 10px 15px;
      margin: 15px;
      display: inline-block;
      min-width: 200px;
      position: relative;
      text-indent: 999;

      &:before{
        height: 100%;
        width: 0;
        position: absolute;
        content: "";
        background: rgba(0, 0, 0, 0.4);
        display: block;
        left: 0;
        top: 0;
      }

    }

    .register_button:hover, .google_button:hover{
      &:before{
        animation-name: showButton;
        animation-duration: 0.5s;
        animation-fill-mode: forwards;  
      }
    }

    .register_button{
      background-color: $basic_green;
    }

    .google_button{
      background-color: $basic_grey;
    }
  }
}

  /*ANIMATIONS*/

@-webkit-keyframes showButton {
  from {width: 0}
  to {width: 100%}
}

</style>
