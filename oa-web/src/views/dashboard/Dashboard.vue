<template>
  <div class="dashboard">
    <div class="greeting">
      <h1 class="greeting-text">{{ greeting }}，{{ userStore.userInfo?.realName }}</h1>
      <p class="greeting-date">{{ today }}</p>
    </div>
    <div class="stat-grid">
      <div class="stat-card" v-for="item in stats" :key="item.label">
        <div class="stat-icon"><el-icon :size="14"><component :is="item.icon" /></el-icon></div>
        <div class="stat-body">
          <span class="stat-number">{{ item.value }}</span>
          <span class="stat-label">{{ item.label }}</span>
        </div>
      </div>
    </div>
    <div class="welcome-card">
      <h3 class="welcome-title">工作台</h3>
      <p class="welcome-text">当前角色：{{ roleName }}。从这里开始你的一天，所有模块均可从左侧导航栏访问。</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { Bell, List, DocumentChecked, Calendar } from '@element-plus/icons-vue'

const userStore = useUserStore()

const roleName = computed(() => {
  const map = { admin: '管理员', manager: '部门经理', employee: '普通员工', '管理员': '管理员', '部门经理': '部门经理', '普通员工': '普通员工' }
  return map[userStore.role] || ''
})

const greeting = computed(() => {
  const h = new Date().getHours()
  if (h < 12) return '早上好'
  if (h < 18) return '下午好'
  return '晚上好'
})

const today = computed(() => new Date().toLocaleDateString('zh-CN', { year:'numeric', month:'long', day:'numeric', weekday:'long' }))

const stats = ref([
  { label: '公告', value: 12, icon: Bell },
  { label: '任务', value: 5, icon: List },
  { label: '待审批', value: 3, icon: DocumentChecked },
  { label: '会议', value: 5, icon: Calendar },
])
</script>

<style scoped>
.dashboard { max-width: 720px; }
.greeting { margin-bottom: 24px; }
.greeting-text { font-size: 20px; font-weight: 500; color: var(--text-primary); margin: 0 0 4px; }
.greeting-date { font-size: 12px; color: var(--text-disabled); margin: 0; }
.stat-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 12px; margin-bottom: 20px; }
.stat-card {
  display: flex; align-items: flex-start; gap: 10px; padding: 16px;
  background: var(--bg-surface); border: 0.5px solid var(--border-color);
  border-radius: var(--radius-sm); cursor: default;
  transition: border-color 0.15s, background 0.15s;
}
.stat-card:hover { border-color: var(--text-primary); background: rgba(0,0,0,0.005); }
.stat-icon { display: flex; align-items: center; justify-content: center; width: 28px; height: 28px; border-radius: var(--radius-sm); background: rgba(0,0,0,0.04); color: var(--text-primary); flex-shrink: 0; }
.stat-body { display: flex; flex-direction: column; }
.stat-number { font-size: 22px; font-weight: 500; color: var(--text-primary); line-height: 1.1; }
.stat-label { font-size: 12px; color: var(--text-secondary); margin-top: 2px; }
.welcome-card { padding: 20px; background: var(--bg-surface); border: 0.5px solid var(--border-color); border-radius: var(--radius-sm); }
.welcome-title { font-size: 14px; font-weight: 500; color: var(--text-primary); margin: 0 0 6px; }
.welcome-text { font-size: 13px; color: var(--text-secondary); margin: 0; line-height: 1.6; }
@media (max-width: 900px) { .stat-grid { grid-template-columns: repeat(2, 1fr); } }
</style>
