import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Courses from '@/components/Courses'
import Course from '@/components/Course'
import Activities from '@/components/Activities'
import Activity from '@/components/Activity'
import Groups from '@/components/Groups'
import Group from '@/components/Group'
import Subjects from '@/components/Subjects'
import Subject from '@/components/Subject'
import Materials from '@/components/Materials'
import Material from '@/components/Material'
import Exams from '@/components/Exams'
import Exam from '@/components/Exam'
import News from '@/components/News'
import NewsSingle from '@/components/NewsSingle'
import AttendanceList from '@/components/AttendanceList'
import MyExam from '@/components/MyExam'

import Login from '@/components/Login'
import Register from '@/components/Register'



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
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/courses',
      name: 'courses',
      component: Courses
    },
    {
      path: '/course/:hash',
      name: 'course',
      component: Course
    },
    {
      path: '/activities',
      name: 'activities',
      component: Activities
    },
    {
      path: '/activity/:hash',
      name: 'activity',
      component: Activity
    },
    {
      path: '/groups',
      name: 'groups',
      component: Groups
    },
    {
      path: '/group/:hash',
      name: 'group',
      component: Group
    },
    {
      path: '/subjects',
      name: 'subjects',
      component: Subjects
    },
    {
      path: '/subject/:hash',
      name: 'subject',
      component: Subject
    },
    {
      path: '/materials',
      name: 'materials',
      component: Materials
    },
    {
      path: '/material/:hash',
      name: 'material',
      component: Material
    },
    {
      path: '/exams',
      name: 'exams',
      component: Exams
    },
    {
      path: '/exam/:hash',
      name: 'exam',
      component: Exam
    },
     {
      path: '/myexams/',
      name: 'myexam',
      component: MyExam
    },
    {
      path: '/news',
      name: 'news',
      component: News
    },
    {
      path: '/news/:hash',
      name: 'newsSingle',
      component: NewsSingle
    },
    {
      path: '/attendance/',
      name: 'attendance',
      component: AttendanceList
    }
  ]
})
