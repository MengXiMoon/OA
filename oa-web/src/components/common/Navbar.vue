<template>
  <div style="display:flex;justify-content:space-between;align-items:center;height:60px;padding:0 20px">
    <span>OA办公系统</span>
    <el-dropdown>
      <span>{{ userStore.userInfo?.realName }} ({{ roleName }})</span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
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

function handleLogout() {
  userStore.logout()
  router.push('/login')
}
</script>
