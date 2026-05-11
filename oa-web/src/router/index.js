import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/login', name: 'Login', component: () => import('@/views/login/Login.vue') },
  {
    path: '/',
    component: () => import('@/views/layout/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('@/views/dashboard/Dashboard.vue') },
      { path: 'notice', name: 'Notice', component: () => import('@/views/notice/NoticeList.vue') },
      { path: 'company-event', name: 'CompanyEvent', component: () => import('@/views/notice/CompanyEventList.vue') },
      { path: 'project-progress', name: 'ProjectProgress', component: () => import('@/views/notice/ProjectProgressList.vue') },
      { path: 'weekly-report', name: 'WeeklyReport', component: () => import('@/views/notice/WeeklyReportList.vue') },
      { path: 'incoming-file', name: 'IncomingFile', component: () => import('@/views/file/IncomingFileList.vue') },
      { path: 'outgoing-file', name: 'OutgoingFile', component: () => import('@/views/file/OutgoingFileList.vue') },
      { path: 'file-archive', name: 'FileArchive', component: () => import('@/views/file/FileArchiveList.vue') },
      { path: 'task', name: 'Task', component: () => import('@/views/task/TaskList.vue') },
      { path: 'leave', name: 'Leave', component: () => import('@/views/approval/LeaveList.vue') },
      { path: 'travel', name: 'Travel', component: () => import('@/views/approval/TravelList.vue') },
      { path: 'attendance', name: 'Attendance', component: () => import('@/views/approval/AttendanceList.vue') },
      { path: 'work-log', name: 'WorkLog', component: () => import('@/views/log/WorkLogList.vue') },
      { path: 'meeting-room', name: 'MeetingRoom', component: () => import('@/views/meeting/MeetingRoomList.vue') },
      { path: 'meeting', name: 'Meeting', component: () => import('@/views/meeting/MeetingList.vue') },
      { path: 'system', name: 'System', component: () => import('@/views/system/UserList.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('oa_token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
