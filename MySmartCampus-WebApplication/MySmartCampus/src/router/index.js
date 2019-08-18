import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Courses from '@/components/Courses'
import Activities from '@/components/Activities'
import Groups from '@/components/Groups'
import Subjects from '@/components/Subjects'
import Materials from '@/components/Materials'
import Exams from '@/components/Exams'
import News from '@/components/News'


import Login from '@/components/Login'



Vue.use(Router)

export default new Router({
	mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/courses',
      name: 'courses',
      component: Courses
    },
    {
      path: '/activities',
      name: 'activities',
      component: Activities
    },
    {
      path: '/groups',
      name: 'groups',
      component: Groups
    },
    {
      path: '/subjects',
      name: 'subjects',
      component: Subjects
    },
    {
      path: '/materials',
      name: 'materials',
      component: Materials
    },
    {
      path: '/exams',
      name: 'exams',
      component: Exams
    },
    {
      path: '/news',
      name: 'news',
      component: News
    },
  ]
})
