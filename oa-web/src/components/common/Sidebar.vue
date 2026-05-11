<template>
  <el-menu :default-active="route.path" router background-color="#304156"
           text-color="#bfcbd9" active-text-color="#409EFF">
    <el-menu-item index="/dashboard"><el-icon><HomeFilled /></el-icon>首页</el-menu-item>
    <el-sub-menu index="notice">
      <template #title><el-icon><Bell /></el-icon>信息发布</template>
      <el-menu-item index="/notice">公告栏</el-menu-item>
      <el-menu-item index="/company-event">公司活动</el-menu-item>
      <el-menu-item index="/project-progress">项目进度</el-menu-item>
      <el-menu-item index="/weekly-report">公司周报</el-menu-item>
    </el-sub-menu>
    <el-sub-menu index="file">
      <template #title><el-icon><Folder /></el-icon>文件管理</template>
      <el-menu-item index="/incoming-file">收文管理</el-menu-item>
      <el-menu-item index="/outgoing-file">发文管理</el-menu-item>
      <el-menu-item index="/file-archive">档案管理</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="/task"><el-icon><List /></el-icon>工作任务</el-menu-item>
    <el-sub-menu index="approval">
      <template #title><el-icon><DocumentChecked /></el-icon>审批申请</template>
      <el-menu-item index="/leave">请假申请</el-menu-item>
      <el-menu-item index="/travel">出差申请</el-menu-item>
      <el-menu-item index="/attendance">出勤记录</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="/work-log"><el-icon><Edit /></el-icon>工作日志</el-menu-item>
    <el-sub-menu index="meeting">
      <template #title><el-icon><Calendar /></el-icon>会议管理</template>
      <el-menu-item index="/meeting-room">会议室管理</el-menu-item>
      <el-menu-item index="/meeting">会议管理</el-menu-item>
    </el-sub-menu>
    <el-menu-item v-if="userStore.role==='admin'" index="/system">
      <el-icon><Setting /></el-icon>系统管理
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
const route = useRoute()
const userStore = useUserStore()
</script>
