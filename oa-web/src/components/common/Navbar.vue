<template>
  <div class="navbar">
    <div class="navbar-brand">
      <span class="navbar-logo">OA</span>
      <span class="navbar-title">办公系统</span>
    </div>
    <div class="navbar-right">
      <span class="navbar-role">{{ roleName }}</span>
      <span class="navbar-sep"></span>
      <el-dropdown trigger="click">
        <span class="navbar-user">{{ userStore.userInfo?.realName || '用户' }} <el-icon><ArrowDown /></el-icon></span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="handleLogout"><el-icon><SwitchButton /></el-icon>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
const router = useRouter()
const userStore = useUserStore()
const roleName = computed(() => {
  const map = { admin: '管理员', manager: '部门经理', employee: '普通员工' }
  return map[userStore.role] || ''
})
function handleLogout() { userStore.logout(); router.push('/login') }
</script>

<style scoped>
.navbar { display: flex; justify-content: space-between; align-items: center; height: 48px; padding: 0 20px; }
.navbar-brand { display: flex; align-items: center; gap: 8px; }
.navbar-logo { font-size: 15px; font-weight: 600; color: var(--text-primary); letter-spacing: -0.01em; }
.navbar-title { font-size: 12px; color: var(--text-secondary); }
.navbar-right { display: flex; align-items: center; gap: 8px; height: 48px; }
.navbar-role { font-size: 12px; color: var(--text-secondary); }
.navbar-sep { width: 0.5px; height: 16px; background: var(--border-color); }
.navbar-user { display: flex; align-items: center; gap: 4px; cursor: pointer; font-size: 13px; color: var(--text-primary); padding: 2px 4px; border-radius: var(--radius-sm); transition: background-color 0.12s; }
.navbar-user:hover { background: rgba(0,0,0,0.04); }
.navbar-user .el-icon { font-size: 10px; color: var(--text-disabled); }
</style>
