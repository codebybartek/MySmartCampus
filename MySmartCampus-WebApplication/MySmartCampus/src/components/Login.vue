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
          console.log(response.data.token);
          this.$store.commit("setToken", {
            token: response.data.token
          })
          let currentDateWithFormat =  new Date().getTime();
          localStorage.setItem('Expiration', currentDateWithFormat);
          window.$cookies.set("token",response.data.token,"10MIN")
          this.$router.push('/');
          this.$emit('toggleNavbar'); 
        })
        .catch(function(error){
          this.errors = [];
          this.errors.push(error);
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
      margin-bottom: 30px;
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
