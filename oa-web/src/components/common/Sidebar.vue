<template>
  <div class="sidebar">
    <el-menu :default-active="route.path" router>
      <el-menu-item index="/dashboard"><el-icon><HomeFilled /></el-icon><span>首页</span></el-menu-item>
      <el-sub-menu index="notice">
        <template #title><el-icon><Bell /></el-icon><span>信息发布</span></template>
        <el-menu-item index="/notice">公告栏</el-menu-item>
        <el-menu-item index="/company-event">公司活动</el-menu-item>
        <el-menu-item index="/project-progress">项目进度</el-menu-item>
        <el-menu-item index="/weekly-report">公司周报</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="file">
        <template #title><el-icon><Folder /></el-icon><span>文件管理</span></template>
        <el-menu-item index="/incoming-file">收文管理</el-menu-item>
        <el-menu-item index="/outgoing-file">发文管理</el-menu-item>
        <el-menu-item index="/file-archive">档案管理</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/task"><el-icon><List /></el-icon><span>工作任务</span></el-menu-item>
      <el-sub-menu index="approval">
        <template #title><el-icon><DocumentChecked /></el-icon><span>审批申请</span></template>
        <el-menu-item index="/leave">请假申请</el-menu-item>
        <el-menu-item index="/travel">出差申请</el-menu-item>
        <el-menu-item index="/attendance">出勤记录</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/work-log"><el-icon><Edit /></el-icon><span>工作日志</span></el-menu-item>
      <el-sub-menu index="meeting">
        <template #title><el-icon><Calendar /></el-icon><span>会议管理</span></template>
        <el-menu-item index="/meeting-room">会议室管理</el-menu-item>
        <el-menu-item index="/meeting">会议管理</el-menu-item>
      </el-sub-menu>
      <el-menu-item v-if="userStore.role==='管理员'" index="/system"><el-icon><Setting /></el-icon><span>系统管理</span></el-menu-item>
    </el-menu>
    <div class="sidebar-footer">OA v1.0</div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'
const route = useRoute()
const userStore = useUserStore()
</script>

<style scoped>
.sidebar { display: flex; flex-direction: column; height: 100%; padding: 8px 0; }
.sidebar :deep(.el-menu) { flex: 1; }
.sidebar-footer {
  padding: 10px 20px; border-top: 0.5px solid var(--border-color);
  font-size: 11px; color: var(--text-disabled);
}
</style>
