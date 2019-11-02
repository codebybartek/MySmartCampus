import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
     name: '',
     role: '',
     token: null,
     serverUrl: 'http://127.0.0.1:8000/api',
     activities: [],
     course: '',
     selectedDate: ""
  },
  getters: {
    	getName(state){
    		return state.name;
    	},
      getRole(state){
        return state.role;
      },
      getToken(state){
          return state.token;
      },
      getUrl(state){
          return state.serverUrl;
      },
      getActivities(state){
          return state.activities;
      },
      getSelectedDate(state){
          return state.selectedDate;
      }
  },
  mutations: {
  	setName(state, payload){
  		state.name = payload.name;
  	},
    setRole(state, payload){
      state.role = payload.role;
    },
    setToken(state, payload){
        state.token = payload.token;
    },
    setActivities(state, payload){
        state.activities = payload.activities;
    },
    setSelectedDate(state, payload){
        state.selectedDate = payload.selectedDate;
    }
}
});