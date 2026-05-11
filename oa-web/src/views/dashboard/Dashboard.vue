<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <div class="stat">
            <p>公告</p>
            <h2>12</h2>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat">
            <p>任务</p>
            <h2>{{ taskCount }}</h2>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat">
            <p>待审批</p>
            <h2>3</h2>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat">
            <p>会议</p>
            <h2>5</h2>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-card style="margin-top:20px">
      <h3>欢迎使用OA办公自动化系统</h3>
      <p>当前角色：{{ roleName }}</p>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const taskCount = ref(5)
const roleName = computed(() => {
  const map = { admin: '管理员', manager: '部门经理', employee: '普通员工' }
  return map[userStore.role] || ''
})
</script>

<style scoped>
.stat { text-align:center; }
.stat h2 { font-size:32px; margin:10px 0; }
</style>
